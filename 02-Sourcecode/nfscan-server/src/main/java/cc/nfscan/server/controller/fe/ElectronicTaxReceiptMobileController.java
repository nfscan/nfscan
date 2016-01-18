package cc.nfscan.server.controller.fe;

import cc.nfscan.server.controller.be.AbstractController;
import cc.nfscan.server.controller.response.ResultResponse;
import cc.nfscan.server.dao.ElectronicTaxReceiptDAO;
import cc.nfscan.server.domain.ElectronicTaxReceipt;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import static cc.nfscan.server.utils.StringUtils.*;

/**
 * Controller in charge of handling ElectronicTaxReceipt related requests on frontend interface
 *
 * @author Paulo Miguel Almeida <a href="http://github.com/PauloMigAlmeida">@PauloMigAlmeida</a>
 *
 */
@Controller
public class ElectronicTaxReceiptMobileController extends AbstractController{

    @Autowired
    private ElectronicTaxReceiptDAO electronicTaxReceiptDAO;


    @RequestMapping(value = "/fe/electronictaxreceipts/donate", method = RequestMethod.POST)
    public ResponseEntity<String> donate(
            @RequestParam(value = "accessKey") String accessKey,
            @RequestParam(value = "total") double total
    ) {
        HttpHeaders responseHeaders = super.createBasicHttpHeaderResponse(APPLICATION_JSON);
        Gson gson = new Gson();
        ResultResponse resultResponse;

        try {

            Assert.hasLength(accessKey);
            accessKey = removeNonNumeric(accessKey);
            Assert.isTrue(validateElectronicTaxReceiptAccessKey(accessKey));
            Assert.isTrue(total > 0.0);

            //Save to database
            ElectronicTaxReceipt receipt = new ElectronicTaxReceipt(accessKey, total);
            electronicTaxReceiptDAO.save(receipt);

            resultResponse = new ResultResponse(true);
        }catch (Exception ex){
            ex.printStackTrace();
            resultResponse = new ResultResponse(false);
        }

        return new ResponseEntity<>(gson.toJson(resultResponse), responseHeaders, HttpStatus.OK);
    }
}