package cc.nfscan.server.controller.fe;

import cc.nfscan.server.controller.be.AbstractController;
import cc.nfscan.server.controller.exclusion.ResultProcessStatusExclusion;
import cc.nfscan.server.controller.response.ResultProcessAuthResponse;
import cc.nfscan.server.controller.response.ResultProcessStatusResponse;
import cc.nfscan.server.controller.response.ResultResponse;
import cc.nfscan.server.dao.OCRTransactionDAO;
import cc.nfscan.server.dao.TaxReceiptDAO;
import cc.nfscan.server.domain.OCRTransaction;
import cc.nfscan.server.domain.TaxReceipt;
import cc.nfscan.server.service.cloudwatch.ProcessTaxReceiptCloudWatchService;
import cc.nfscan.server.service.s3.TaxReceiptS3Upload;
import cc.nfscan.server.service.sqs.impl.ProcessTaxReceiptSQSService;
import cc.nfscan.server.service.sqs.model.ProcessInQueueModel;
import cc.nfscan.server.utils.Constants;
import cc.nfscan.server.utils.SignatureUtils;
import cc.nfscan.server.utils.StringUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * Controller in charge of handling TaxReceipt related requests on frontend interface
 *
 * @author Paulo Miguel Almeida <a href="http://github.com/PauloMigAlmeida">@PauloMigAlmeida</a>
 *
 */
@Controller
public class TaxReceiptMobileController extends AbstractController {

    /**
     * TaxReceiptDAO DAO object
     */
    @Autowired
    private TaxReceiptDAO taxReceiptDAO;

    /**
     * OCRTransactionDAO DAO object
     */
    @Autowired
    private OCRTransactionDAO ocrTransactionDAO;

    /**
     * TaxReceiptS3Upload object
     */
    @Autowired
    private TaxReceiptS3Upload s3Upload;

    /**
     * SignatureUtils object
     */
    @Autowired
    private SignatureUtils signatureUtils;

    /**
     * ProcessTaxReceiptSQSService object
     */
    @Autowired
    private ProcessTaxReceiptSQSService processTaxReceiptSQSService;

    /**
     * ProcessTaxReceiptCloudWatchService object
     */
    @Autowired
    private ProcessTaxReceiptCloudWatchService processTaxReceiptCloudWatchService;

    /**
     * Upload an image encoded in base64 to a AWS bucket
     *
     * @param base64Image - a string containing a image base 64 encoded
     * @return the AWS S3 path where the image has been saved
     * @throws Exception
     */
    private String uploadBase64FileToBucket(String base64Image) throws Exception {
        Base64 base64 = new Base64();
        byte[] bytes = base64.decode(base64Image.getBytes());

        File tempFile = File.createTempFile("taxReceipt", ".jpg");

        //Write TaxReceipt to a file
        FileOutputStream fileOutputStream = new FileOutputStream(tempFile);
        fileOutputStream.write(bytes);
        fileOutputStream.flush();
        fileOutputStream.close();

        String s3OutputFileName = uploadFileToBucket(tempFile);

        //Delete when it's done
        tempFile.deleteOnExit();

        return s3OutputFileName;
    }

    /**
     * Upload a file to a AWS bucket
     *
     * @param file - the file you want to upload
     * @return the AWS S3 path where the image has been saved
     * @throws Exception
     */
    private String uploadFileToBucket(File file) throws Exception {
        //Upload to AWS S3
        String s3OutputFileName = String.format("%s.jpg", UUID.randomUUID().toString());
        s3Upload.startUpload(s3OutputFileName, file);
        return s3OutputFileName;
    }


    /**
     * Generates a signature and a transaction Id that must be processed and used on all other requests to fend off requests that aren't coming from our app
     *
     * @return a JSON containing the ResultProcessAuthResponse properties
     * @see ResultProcessAuthResponse
     */
    @RequestMapping(value = "/fe/taxreceipts/process/auth", method = RequestMethod.POST)
    public ResponseEntity<String> auth() {
        HttpHeaders responseHeaders = super.createBasicHttpHeaderResponse(APPLICATION_JSON);
        Gson gson = new Gson();
        ResultResponse resultResponse;
        try {

            String signature = signatureUtils.generateSignature();

            Date currDate = new Date();

            OCRTransaction ocrTransaction = new OCRTransaction(signature, currDate, new SimpleDateFormat(Constants.DATE_FORMAT).format(currDate));
            ocrTransactionDAO.save(ocrTransaction);

            resultResponse = new ResultProcessAuthResponse(true, ocrTransaction.getId(), ocrTransaction.getSignature());
        } catch (Exception ex) {
            ex.printStackTrace();
            resultResponse = new ResultResponse(false);
        }
        return new ResponseEntity<String>(gson.toJson(resultResponse), responseHeaders, HttpStatus.OK);
    }

    /**
     * Uploads a Tax Receipt file encoded in Base64 and starts processing this file looking for meaningful values. Note this is
     * a asynchronous process which means you aren't getting the values right away. You must periodically check if the process identified
     * by the transactionId has been finished on the {@link TaxReceiptMobileController#check(String, String) check} method
     *
     * @param base64Image      a string containing a file encoded in base64 (use JPEG compression for lighter sizes)
     * @param transactionId    a string containing the transactionId generated in the {@link TaxReceiptMobileController#auth() auth} method
     * @param counterSignature a string of the signature generated in the auth method processed by you mobile app
     * @return a JSON containing the ResultResponse properties
     * @see ResultResponse
     */
    @RequestMapping(value = "/fe/taxreceipts/process/start", method = RequestMethod.POST)
    public ResponseEntity<String> start(@RequestParam(value = "base64Image", required = true) final String base64Image,
                                        @RequestParam(value = "transactionId", required = true) final String transactionId,
                                        @RequestParam(value = "counterSignature", required = true) final String counterSignature) {
        HttpHeaders responseHeaders = super.createBasicHttpHeaderResponse(APPLICATION_JSON);
        Gson gson = new Gson();
        ResultResponse resultResponse;
        try {
            String s3OutputFileName = uploadBase64FileToBucket(base64Image);

            OCRTransaction ocrTransaction = ocrTransactionDAO.findById(new OCRTransaction(transactionId));
            Assert.notNull(ocrTransaction);
            Assert.isTrue(signatureUtils.validateCounterSignature(ocrTransaction.getSignature(), counterSignature));

            ocrTransaction.setS3Object(s3OutputFileName);
            ocrTransaction.setProcessed(false);

            ocrTransactionDAO.save(ocrTransaction);
            processTaxReceiptSQSService.sendMessage(new ProcessInQueueModel(ocrTransaction.getId(), ocrTransaction.getS3Object()));
            processTaxReceiptCloudWatchService.putMetricData(ProcessTaxReceiptCloudWatchService.OCR_PROCESS_IN_QUEUE_MESSAGES_SENT, 1.0);

            resultResponse = new ResultResponse(true);
        } catch (Exception ex) {
            ex.printStackTrace();
            resultResponse = new ResultResponse(false);
        }
        return new ResponseEntity<String>(gson.toJson(resultResponse), responseHeaders, HttpStatus.OK);
    }


    /**
     * Checks whether or not the OCR process has been finished
     *
     * @param transactionId    a string containing the transactionId generated in the {@link TaxReceiptMobileController#auth() auth} method
     * @param counterSignature a string of the signature generated in the auth method processed by you mobile app
     * @return a JSON containing the ResultProcessStatusResponse properties
     * @see ResultProcessStatusResponse
     */
    @RequestMapping(value = "/fe/taxreceipts/process/check", method = RequestMethod.POST)
    public ResponseEntity<String> check(@RequestParam(value = "transactionId", required = true) final String transactionId,
                                        @RequestParam(value = "counterSignature", required = true) final String counterSignature) {
        HttpHeaders responseHeaders = super.createBasicHttpHeaderResponse(APPLICATION_JSON);
        Gson gson = new GsonBuilder().setDateFormat(Constants.DATE_FORMAT).setExclusionStrategies(new ResultProcessStatusExclusion()).create();
        ResultResponse resultResponse;
        try {
            OCRTransaction ocrTransaction = ocrTransactionDAO.findById(new OCRTransaction(transactionId));
            Assert.notNull(ocrTransaction);
            Assert.isTrue(signatureUtils.validateCounterSignature(ocrTransaction.getSignature(), counterSignature));

            resultResponse = new ResultProcessStatusResponse(true, ocrTransaction);
        } catch (Exception ex) {
            ex.printStackTrace();
            resultResponse = new ResultResponse(false);
        }

        return new ResponseEntity<String>(gson.toJson(resultResponse), responseHeaders, HttpStatus.OK);
    }


    /**
     * Donates the tax receipt after the user has verified all meaningful values and fixed if needed
     *
     * @param cnpj          a valid National Registry of Legal Entities
     * @param date          a date in the form dd/MM/yyyy
     * @param coo           a valid COO (Operation Counter)
     * @param total         a total value of the receipt
     * @param transactionId a string containing the transactionId generated in the {@link TaxReceiptMobileController#auth() auth} method
     * @return a JSON containing the ResultResponse properties
     * @see ResultResponse
     * @see <a href="http://en.wikipedia.org/wiki/CNPJ">http://en.wikipedia.org/wiki/CNPJ</a>
     */
    @RequestMapping(value = "/fe/taxreceipts/process/donate")
    public ResponseEntity<String> donate(
            @RequestParam(value = "cnpj") String cnpj
            , @RequestParam(value = "date") Date date
            , @RequestParam(value = "coo") String coo
            , @RequestParam(value = "total") double total
            , @RequestParam(value = "transactionId") String transactionId) {

        HttpHeaders responseHeaders = super.createBasicHttpHeaderResponse(APPLICATION_JSON);
        Gson gson = new Gson();
        ResultResponse resultResponse;

        try {

            Assert.isTrue(StringUtils.validateCNPJ(cnpj));
            Assert.notNull(coo);
            Assert.isTrue(StringUtils.isNumeric(coo));
            Assert.isTrue(total != 0);
            Assert.notNull(transactionId);
            Assert.isTrue(!transactionId.equals(""));

            OCRTransaction ocrTransaction = ocrTransactionDAO.findById(new OCRTransaction(transactionId));
            Assert.notNull(ocrTransaction);

            //Save to database
            TaxReceipt taxReceipt = new TaxReceipt();
            taxReceipt.setCnpj(cnpj.replaceAll("[^\\d]", ""));
            taxReceipt.setDate(date);
            taxReceipt.setCoo(coo);
            taxReceipt.setTotal(total);
            taxReceipt.setS3Object(ocrTransaction.getS3Object());
            taxReceipt.setDateInsertion(new Date());

            taxReceiptDAO.save(taxReceipt);
            ocrTransactionDAO.remove(ocrTransaction);
            resultResponse = new ResultResponse(true);

        } catch (Exception ex) {
            ex.printStackTrace();
            resultResponse = new ResultResponse(false);
        }

        return new ResponseEntity<String>(gson.toJson(resultResponse), responseHeaders, HttpStatus.OK);
    }

}
