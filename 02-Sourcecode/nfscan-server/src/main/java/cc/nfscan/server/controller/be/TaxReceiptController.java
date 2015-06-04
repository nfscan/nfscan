package cc.nfscan.server.controller.be;

import cc.nfscan.server.dao.TaxReceiptArchiveDAO;
import cc.nfscan.server.dao.TaxReceiptDAO;
import cc.nfscan.server.domain.TaxReceipt;
import cc.nfscan.server.domain.TaxReceiptArchive;
import cc.nfscan.server.service.s3.TaxReceiptS3Retrieve;
import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Controller in charge of handling TaxReceipt related requests
 *
 * @author Paulo Miguel Almeida <a href="http://github.com/PauloMigAlmeida">@PauloMigAlmeida</a>
 */
@Controller
public class TaxReceiptController extends AbstractController {

    /**
     * TaxReceiptDAO DAO object
     */
    @Autowired
    private TaxReceiptDAO taxReceiptDAO;

    /**
     * TaxReceiptArchiveDAO DAO object
     */
    @Autowired
    private TaxReceiptArchiveDAO taxReceiptArchiveDAO;

    /**
     * S3Retrieve object
     */
    @Autowired
    private TaxReceiptS3Retrieve s3Retrieve;

    /**
     * Main entry point
     *
     * @param map      a HashMap&lt;String,Object&gt; used to define attributes that will b used on View layer
     * @param response Http Servlet Response object
     * @return Redirect to /be/taxreceipts/all
     */
    @RequestMapping("/be/taxreceipts")
    public String index(Map<String, Object> map, HttpServletResponse response) {
        return getAll(map, response);
    }

    /**
     * List all taxReceipts on database
     *
     * @param map      a HashMap&lt;String,Object&gt; used to define attributes that will b used on View layer
     * @param response Http Servlet Response object
     * @return a list taxReceipts page
     */
    @RequestMapping(value = "/be/taxreceipts/all")
    public String getAll(Map<String, Object> map, HttpServletResponse response) {
        List<TaxReceipt> taxReceipts = taxReceiptDAO.findAll();
        map.put("taxReceipts", taxReceipts);

        return "be/taxreceipt/taxreceipt_list";
    }

    /**
     * Approves a taxreceipt
     *
     * @param map      a HashMap&lt;String,Object&gt; used to define attributes that will b used on View layer
     * @param response Http Servlet Response object
     * @param id       object id if it's an edit operation or null if it's an insert operation
     * @return Redirect to /be/taxreceipts/all
     */
    @RequestMapping(value = "/be/taxreceipts/approve", method = RequestMethod.POST)
    public String approve(Map<String, Object> map, HttpServletResponse response, @RequestParam(value = "id", required = false) String id) {
        TaxReceipt taxreceipt = null;
        if (id != null) {
            try {
                TaxReceipt entity = new TaxReceipt();
                entity.setId(id);

                taxreceipt = taxReceiptDAO.findById(entity);

                TaxReceiptArchive archiveEntity = new TaxReceiptArchive();
                archiveEntity.setId(taxreceipt.getId());
                archiveEntity.setCnpj(taxreceipt.getCnpj());
                archiveEntity.setDate(taxreceipt.getDate());
                archiveEntity.setCoo(taxreceipt.getCoo());
                archiveEntity.setTotal(taxreceipt.getTotal());
                archiveEntity.setS3Object(taxreceipt.getS3Object());
                archiveEntity.setDateInsertion(new Date());
                archiveEntity.setApproved(true);

                taxReceiptDAO.remove(taxreceipt);
                taxReceiptArchiveDAO.save(archiveEntity);

            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }

        return "redirect:/be/taxreceipts/all.action";
    }

    /**
     * Reproves a taxreceipt
     *
     * @param map      a HashMap&lt;String,Object&gt; used to define attributes that will b used on View layer
     * @param response Http Servlet Response object
     * @param id       object id if it's an edit operation or null if it's an insert operation
     * @return Redirect to /be/taxreceipts/all
     */
    @RequestMapping(value = "/be/taxreceipts/reprove", method = RequestMethod.POST)
    public String reprove(Map<String, Object> map, HttpServletResponse response, @RequestParam(value = "id", required = false) String id) {
        TaxReceipt taxreceipt = null;
        if (id != null) {
            try {
                TaxReceipt entity = new TaxReceipt();
                entity.setId(id);

                taxreceipt = taxReceiptDAO.findById(entity);

                TaxReceiptArchive archiveEntity = new TaxReceiptArchive();
                archiveEntity.setId(taxreceipt.getId());
                archiveEntity.setCnpj(taxreceipt.getCnpj());
                archiveEntity.setDate(taxreceipt.getDate());
                archiveEntity.setCoo(taxreceipt.getCoo());
                archiveEntity.setTotal(taxreceipt.getTotal());
                archiveEntity.setS3Object(taxreceipt.getS3Object());
                archiveEntity.setDateInsertion(new Date());
                archiveEntity.setApproved(false);

                taxReceiptDAO.remove(taxreceipt);
                taxReceiptArchiveDAO.save(archiveEntity);

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        return "redirect:/be/taxreceipts/all.action";
    }


    /**
     * Gets a tax Receipt image
     *
     * @param s3ObjectName file name from s3 bucket
     * @return a taxReceipt image
     * @throws AmazonClientException  If any internal errors are encountered inside the client while attempting to make the request or handle the response. For example if a network connection is not available.
     * @throws AmazonServiceException If an error response is returned by AmazonSQS indicating either a problem with the data in the request, or a server side issue.
     * @throws IOException            if any error happens when downloading the file from Amazon S3
     */
    @RequestMapping(value = "/be/taxreceipts/viewreceipt", method = RequestMethod.GET)
    public ResponseEntity<byte[]> viewReceipt(@RequestParam(value = "s3ObjectName", required = true) String s3ObjectName) throws AmazonClientException, AmazonServiceException, IOException {
        BufferedInputStream bufferedInputStream = s3Retrieve.startDownload(s3ObjectName);
        byte[] bytes = IOUtils.toByteArray(bufferedInputStream);
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);

        return new ResponseEntity<>(bytes, headers, HttpStatus.CREATED);
    }

}
