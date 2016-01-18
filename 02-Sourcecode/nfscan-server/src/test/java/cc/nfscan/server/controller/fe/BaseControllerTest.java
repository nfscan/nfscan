package cc.nfscan.server.controller.fe;

import cc.nfscan.server.domain.OCRTransaction;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import org.apache.log4j.Logger;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * Base controller test for handling web requests
 *
 * @author Paulo Miguel Almeida <a href="http://github.com/PauloMigAlmeida">@PauloMigAlmeida</a>
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@Ignore
@ContextConfiguration(locations = "classpath:springconfiguration/spring-servlet.xml")
@WebAppConfiguration
class BaseControllerTest {

    @Autowired
    protected WebApplicationContext webApplicationContext;


    public MockMvc mockMvc;

    protected Logger logger = Logger.getLogger(this.getClass());

    protected void init(){
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

}