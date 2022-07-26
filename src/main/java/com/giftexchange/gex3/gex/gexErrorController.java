package com.giftexchange.gex3.gex;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class gexErrorController implements ErrorController {
    
    private static final Logger logger = LoggerFactory.getLogger(gexErrorController.class);

    @Value("${send-ntfy}")
    private boolean sendNtfy;

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

        logger.error("Error code " + status + " at " + request.getAttribute(RequestDispatcher.ERROR_REQUEST_URI) + ", message: " + errorMsg + request.getAttribute(RequestDispatcher.ERROR_EXCEPTION));

        //Publish errors using ntfy service (https://ntfy.sh/)
        if(sendNtfy){
            Date obj = new Date();
            String date = obj.toString().replaceAll(" ", "+");;
            final String newline = "%0D%0A";
            String ntfy = "https://ntfy.sh/giftexchangeerror/publish?title=GEX+Error:+Code+"+Integer.toString(status)+"&message=GEX+error+occured+at+"+date+ "+." + newline + "Error+Code:+" + Integer.toString(status) + newline + "Error+Message:+" + errorMsg + request.getAttribute(RequestDispatcher.ERROR_EXCEPTION) + newline + "Error+URL:+" + request.getAttribute(RequestDispatcher.ERROR_REQUEST_URI);
            
            try {
                URL url = new URL(ntfy);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                con.getResponseCode();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        return "error";
    }

    public enum logLevel{
        INFO,
        WARN,
        ERROR
    }

    public static void logServerStart(logLevel level){
        if(level == logLevel.INFO){
            logger.info("GEX3 Server started on " + new Date().toString());
        }else if(level == logLevel.WARN){
            logger.warn("GEX3 Server started on " + new Date().toString());
        }else{
            logger.error("GEX3 Server started on " + new Date().toString());
        }
        
    }
}
