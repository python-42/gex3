package com.giftexchange.gex3.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.giftexchange.gex3.gex.Constants;
import com.giftexchange.gex3.util.NavblockGenerator;
import com.giftexchange.gex3.websocket.WebsocketFormData;
import com.giftexchange.gex3.websocket.WebsocketServerResponse;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/create")
    public String create(Model model){
        model.addAttribute("data", new UserCreationForm());
        return "create";
    }

    @PostMapping("/create")
    public String createSubmit(@ModelAttribute UserCreationForm data, Model model){
        model.addAttribute("data", data);

        if(data.getUsername() == null || data.getPassword() == null || data.getConfirm() == null){
            model.addAttribute("msg", "No fields may be blank!");
            return "create";
        }

        if(!data.getConfirm().equals(data.getPassword())){
            model.addAttribute("msg", "Passwords do not match!");
            return "create";
        }

        if(data.getUsername().length() < 4 || data.getUsername().length() > 8){
            model.addAttribute("msg", "Username is not the correct length! Username must be between 4 and 8 characters long inclusive.");
            return "create";
        }

        if(data.getPassword().length() < 8 || data.getPassword().length() > 50){
            model.addAttribute("msg", "Password is not the correct length! Username must be between 8 and 50 characters long inclusive.");//https://security.stackexchange.com/questions/39849/does-bcrypt-have-a-maximum-password-length
            return "create";
        }

        //TODO enforce password security

        UserTable userInfo = userRepository.findByUsername(data.getUsername());
        if(userInfo != null){
            model.addAttribute("msg", "Username is already taken! Please choose another.");
            return "create";
        }

        //all checks passed successfully
        userInfo = new UserTable(data.getUsername(), encoder.encode(data.getPassword()));
        userRepository.save(userInfo);

        User user = new User(userInfo);
        Authentication auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);

        return "redirect:/";
    }

    @GetMapping("/account")
    public String account(Model model, Authentication auth){
        model.addAttribute("navblock", NavblockGenerator.generateNavblock(Constants.NAVBLOCK_MAP, "Account"));
        UserTable userInfo = userRepository.findByUsername(auth.getName());
        model.addAttribute("interest", userInfo.getInterest());
        if(userInfo.getEmailEnabled()){
            model.addAttribute("email", userInfo.getEmail());
        }else{
            model.addAttribute("email", "Email disabled.");
        }
        return "account";
    }

    @MessageMapping("/account/interest")
    @SendTo("/socket/account/interest")
    public WebsocketServerResponse accountInterestForm(WebsocketFormData data, Authentication auth) throws Exception {
        UserTable userInfo = userRepository.findByUsername(auth.getName());
        userInfo.setInterest((String) data.getDataPart(0));
        userRepository.save(userInfo);
        //TODO data processing and injection prevention
        return new WebsocketServerResponse(data.getData());
    }

    @MessageMapping("/account/password")
    @SendTo("/socket/account/password")
    public WebsocketServerResponse accountPasswordForm(WebsocketFormData rawData, Authentication auth) throws Exception {
        boolean updateOK = true;

        UserTable userInfo = userRepository.findByUsername(auth.getName());

        HashMap<String, String> data = new HashMap<String, String>();

        List<Object> rtn = new ArrayList<Object>();

        data.put("current", (String)rawData.getDataPart(0));
        data.put("new", (String)rawData.getDataPart(1));
        data.put("confirm", (String)rawData.getDataPart(2));

        for (Entry<String, String> x : data.entrySet()){
            if(x.getValue() == null || x.getValue().equals("")){
                rtn.add(Constants.CSS_DISMISSABLE_ERROR_MODAL + x.getKey() + " field may not be blank!</div>");    
                updateOK = false;
            }

            if(x.getValue().length() > 50 || x.getValue().length() < 8 ){
                rtn.add(Constants.CSS_DISMISSABLE_ERROR_MODAL + x.getKey().substring(0, 1).toUpperCase() + x.getKey().substring(1) + " password must be between 8 and 50 characters inclusive.</div>");
                updateOK = false;
            }
        }

        if(! encoder.matches(data.get("current"), userInfo.getPassword())){
            rtn.add(Constants.CSS_DISMISSABLE_ERROR_MODAL + "Current password is incorrect. Please try again.");
            updateOK = false;
        }

        if(!data.get("new").equals(data.get("confirm"))){
            rtn.add(Constants.CSS_DISMISSABLE_ERROR_MODAL + "New passwords do not match!</div>");
            updateOK = false;
            
        }

        //checks successful
        if(updateOK){
            userInfo.setPassword(encoder.encode(data.get("new")));
            userRepository.save(userInfo);

            rtn.add(Constants.CSS_DISMISSABLE_SUCCESS_MODAL + "Password updated successfully!</div>");
        }
        return new WebsocketServerResponse(rtn);
    }

}
