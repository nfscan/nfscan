package cc.nfscan.server.controller.be;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Controller in charge of handling Login/Logout request and create required initialization values on application startup
 *
 * @author Paulo Miguel Almeida <a href="http://github.com/PauloMigAlmeida">@PauloMigAlmeida</a>
 */
@Controller
public class BackendController extends AbstractController {


    /**
     * Main entry point
     *
     * @param map      - a HashMap&lt;String,Object&gt; used to define attributes that will b used on View layer
     * @param response - Http Servlet Response object
     * @return backend's index page or login page if user hasn't logged yet
     */
    @RequestMapping("/be/index")
    public String index(Map<String, Object> map, HttpServletResponse response) {
        return "be/index";
    }

    /**
     * Handler from JSP and Spring Security to login users
     *
     * @param map      - HashMap&lt;String,Object&gt; used to define attributes that will b used on View layer
     * @param message  - Validation message just in case we fail to login user to backend
     * @param response - Http Servlet Response object
     * @return login page
     */

    @RequestMapping("/be/login")
    public String login(Map<String, Object> map, @RequestParam(required = false) String message, HttpServletResponse response) {
        if (message != null) {
            map.put("message", message);
        }
        return "be/login";
    }

    /**
     * Handler from JSP and Spring Security to login users
     *
     * @param map      - HashMap&lt;String,Object&gt; used to define attributes that will b used on View layer
     * @param response - Http Servlet Response object
     * @return redirect to /
     */
    @RequestMapping("/be/logout")
    public String logout(Map<String, Object> map, HttpServletResponse response) {
        return login(map, null, response);
    }


}
