package cc.nfscan.server.controller.be;

import org.apache.log4j.Logger;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import java.text.SimpleDateFormat;
import java.util.Date;
import static cc.nfscan.server.utils.Constants.DATE_FORMAT;

/**
 * Base controller for backend objects using Spring MVC.
 * This class provides common methods to allow developers to expose through JSON.
 *
 * @author Paulo Miguel Almeida <a href="http://github.com/PauloMigAlmeida">@PauloMigAlmeida</a>
 */
public abstract class AbstractController {

    /**
     * Default Logger
     */
    protected Logger logger = Logger.getLogger(getClass().getName());

    /**
     * Content Types
     */
    protected static final String APPLICATION_JSON = "application/json; charset=utf-8";


    /**
     * Initiates the data binding from web request parameters to JavaBeans objects
     *
     * @param webDataBinder - a Web Data Binder instance
     */
    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        dateFormat.setLenient(true);
        webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    /**
     * Creates a base HttpHeaders given the content type such as JSON, Excel and so on
     *
     * @param contentType a string containing the mime type that will be send through HTTP response
     * @return a HTTPHeaders object
     */
    protected HttpHeaders createBasicHttpHeaderResponse(String contentType) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("Content-Type", contentType);
        return responseHeaders;
    }
}
