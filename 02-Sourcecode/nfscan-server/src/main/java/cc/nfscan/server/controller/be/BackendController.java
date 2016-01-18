package cc.nfscan.server.controller.be;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

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
     * @param error    - Validation message just in case we fail to login user to backend
     * @param logout   - Validation message just in case we logout use from backend
     * @param response - Http Servlet Response object
     * @return login page
     */

    @RequestMapping("/be/login")
    public ModelAndView login(Map<String, Object> map, @RequestParam(value = "error", required = false) String error,
                              @RequestParam(value = "logout", required = false) String logout, HttpServletResponse response) {
        ModelAndView model = new ModelAndView();
        if (error != null) {
            model.addObject("error", "Invalid credentials");
        }

        if (logout != null) {
            model.addObject("message", "Log out completo.");
        }

        model.setViewName("be/login");
        return model;
    }

}
