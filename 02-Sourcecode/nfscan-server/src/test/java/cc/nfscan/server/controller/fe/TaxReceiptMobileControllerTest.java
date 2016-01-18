package cc.nfscan.server.controller.fe;

import cc.nfscan.server.controller.response.ResultResponse;
import cc.nfscan.server.domain.TaxReceipt;
import com.google.gson.Gson;
import org.hamcrest.MatcherAssert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.text.SimpleDateFormat;
import java.util.Date;

import static cc.nfscan.server.utils.Constants.DATE_FORMAT;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Controller test for handling regular tax receipts requests
 *
 * @author Paulo Miguel Almeida <a href="http://github.com/PauloMigAlmeida">@PauloMigAlmeida</a>
 */
public class TaxReceiptMobileControllerTest extends BaseDatabaseControllerTest {

    @Before
    public void init() {
        super.init();
    }

    @After
    public void clean() {
        super.clean();
    }

    @Test
    public void testManualDonate() throws Exception {
        ResultResponse resultResponse;

        // valid
        resultResponse = manualDonate(new TaxReceipt("47.508.411/0285-90", new Date(), "123456", 0.01));
        MatcherAssert.assertThat(resultResponse.isSuccess(), equalTo(Boolean.TRUE));

        // total can't be <= 0.00
        resultResponse = manualDonate(new TaxReceipt("47.508.411/0285-90", new Date(), "123456", 0.00));
        MatcherAssert.assertThat(resultResponse.isSuccess(), equalTo(Boolean.FALSE));

        // total can't be <= 0.00
        resultResponse = manualDonate(new TaxReceipt("47.508.411/0285-91", new Date(), "123456", -0.00));
        MatcherAssert.assertThat(resultResponse.isSuccess(), equalTo(Boolean.FALSE));

    }

    private ResultResponse manualDonate(TaxReceipt taxReceipt) throws Exception {
        MockHttpServletRequestBuilder builder = post("/fe/taxreceipts/manual/donate.action");
        builder.contentType(MediaType.APPLICATION_FORM_URLENCODED);
        builder.param("cnpj", taxReceipt.getCnpj());
        builder.param("date", new SimpleDateFormat(DATE_FORMAT).format(taxReceipt.getDate()));
        builder.param("coo", taxReceipt.getCoo());
        builder.param("total", String.valueOf(taxReceipt.getTotal()));

        Gson gson = new Gson();
        return gson.fromJson(
                mockMvc.perform(builder).andExpect(status().isOk()).andReturn().getResponse().getContentAsString(),
                ResultResponse.class
        );
    }
}
