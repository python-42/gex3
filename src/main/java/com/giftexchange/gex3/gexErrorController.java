package com.giftexchange.gex3;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class gexErrorController implements ErrorController {
    
    @RequestMapping("/error")
    public String error(Model model, HttpServletRequest request){
        int status = (int) request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        Object errorMsg = request.getAttribute(RequestDispatcher.ERROR_MESSAGE);

        model.addAttribute("code", "Error code: " + status);

        if(errorMsg == ""){
            model.addAttribute("errorMsg", "Message: None");
        }else{
            model.addAttribute("errorMsg", "Message: " + errorMsg);
        }

        if(status == 404){//TODO add more error code explanations
            model.addAttribute("userMsg", "Resource not found. Check that your URL is correct.");
        }else if(status == 500){
            model.addAttribute("userMsg", "Internal server error. You likely cannot do anything to fix this.");
        }

        return "error";
    }
}
