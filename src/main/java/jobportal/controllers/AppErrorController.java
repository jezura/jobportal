package jobportal.controllers;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

/**
 * Controller for errors handling and error.html page displaying
 */
@Controller
public class AppErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        String errorMsg = "";
        int errorCode = 0;

        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());
            switch (statusCode) {
                case 400: {
                    errorMsg = "Bad request";
                    errorCode = 400;
                    break;
                }
                case 401: {
                    errorMsg = "Unauthorized access";
                    errorCode = 401;
                    break;
                }
                case 403: {
                    errorMsg = "Forbidden, you don´t have permission to access";
                    errorCode = 403;
                    break;
                }
                case 404: {
                    errorMsg = "Page/Resource nod found";
                    errorCode = 404;
                    break;
                }
                case 500: {
                    errorMsg = "Internal server error";
                    errorCode = 500;
                    break;
                }
                case 503: {
                    errorMsg = "Service unavailable";
                    errorCode = 503;
                    break;
                }
                default: {
                    errorMsg = "Unspecified internal error";
                    errorCode = 0;
                    break;
                }
            }
        }
        model.addAttribute("errorMsg", errorMsg);
        model.addAttribute("errorCode", errorCode);
        return "error";
    }

    @Override
    public String getErrorPath() {
        return null;
    }
}