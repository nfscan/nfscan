package cc.nfscan.server.controller.fe;

import cc.nfscan.server.controller.response.ResultResponse;
import com.google.gson.Gson;
import org.hamcrest.MatcherAssert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Controller test for handling electronic tax receipts requests
 *
 * @author Paulo Miguel Almeida <a href="http://github.com/PauloMigAlmeida">@PauloMigAlmeida</a>
 */
public class ElectronicTaxReceiptMobileControllerTest extends BaseDatabaseControllerTest {

    @Before
    public void init() {
        super.init();
    }

    @After
    public void clean() {
        super.clean();
    }

    @Test
    public void testDonate_SuccessExpected() throws Exception {
        ResultResponse resultResponse;
        resultResponse = donateRequest("3599 0922 8436 4200 0195 6500 1000 0000 2314 0000 2313", 0.01);
        MatcherAssert.assertThat(resultResponse.isSuccess(), equalTo(Boolean.TRUE));

        resultResponse = donateRequest("35990922843642000195650010000000231400002313", 10.01);
        MatcherAssert.assertThat(resultResponse.isSuccess(), equalTo(Boolean.TRUE));

        resultResponse = donateRequest("35150561099008000141599000012200005098600482", 10.01);
        MatcherAssert.assertThat(resultResponse.isSuccess(), equalTo(Boolean.TRUE));
    }

    @Test
    public void testDonateInvalidAccessKey_failExpected() throws Exception {
        ResultResponse resultResponse;
        resultResponse = donateRequest("3500 0932 8436 4300 0195 6500 1000 0000 2314 0000 2313", 0.01);
        MatcherAssert.assertThat(resultResponse.isSuccess(), equalTo(Boolean.FALSE));

        resultResponse = donateRequest("35000932843643000195650010000000231400002313", 0.01);
        MatcherAssert.assertThat(resultResponse.isSuccess(), equalTo(Boolean.FALSE));

        resultResponse = donateRequest("fasdfasdfasdfasd", 0.01);
        MatcherAssert.assertThat(resultResponse.isSuccess(), equalTo(Boolean.FALSE));

        resultResponse = donateRequest("3599 0922 8436 4200 0195 6500 1000 0000 2314 0000 2313", -0.01);
        MatcherAssert.assertThat(resultResponse.isSuccess(), equalTo(Boolean.FALSE));

        resultResponse = donateRequest("3599 0922 8436 4200 0195 6500 1000 0000 2314 0000 2313", 0.0);
        MatcherAssert.assertThat(resultResponse.isSuccess(), equalTo(Boolean.FALSE));
    }

    private ResultResponse donateRequest(String accessKey, double total) throws Exception {
        MockHttpServletRequestBuilder builder = post("/fe/electronictaxreceipts/donate.action");
        builder.contentType(MediaType.APPLICATION_FORM_URLENCODED);
        builder.param("accessKey", accessKey);
        builder.param("total", String.valueOf(total));

        Gson gson = new Gson();
        return gson.fromJson(
                mockMvc.perform(builder).andExpect(status().isOk()).andReturn().getResponse().getContentAsString(),
                ResultResponse.class
        );
    }
}