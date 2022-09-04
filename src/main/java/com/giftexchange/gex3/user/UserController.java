package com.giftexchange.gex3.user;

import java.util.LinkedList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.giftexchange.gex3.websocket.WebsocketFormData;
import com.giftexchange.gex3.websocket.WebsocketServerResponse;

@Controller
public class UserController {

    private class UserInfoJoined {
        public String username;
        public String interest;
        public int itemCount;

        UserInfoJoined(String username, String interest, int itemCount){
            this.username = username;
            this.interest = interest;
            this.itemCount = itemCount;

            //to suppress unused var warning
            interest = this.username + this.interest;
            itemCount = this.itemCount + 1;
        }
    }

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/create")
    public String create(Model model){
        model.addAttribute("data", new UserCreationForm());//object which is populated by user input
        return "create";
    }

    @PostMapping("/create")
    public String createSubmit(@ModelAttribute UserCreationForm userInput, Model model){
        model.addAttribute("data", userInput);

        String msg = UserService.validateCreationFormInput(userInput, userRepository);

        if(msg.equals("OK")){
            GexUserDetails user = new GexUserDetails(UserService.createNewUser(userInput, userRepository));
            Authentication auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(auth);
            return "redirect:/";
        }

        model.addAttribute("msg", msg);
        return "create";
        
    }

    @GetMapping("/users")
    public String users(Model model, Authentication auth){
        LinkedList<UserInfoJoined> attr = new LinkedList<UserInfoJoined>(); 
        for (UserTable x : userRepository.findAllUsersExcept(auth.getName())){
            attr.add(new UserInfoJoined(x.getUsername(), x.getInterest(), userRepository.itemCountForOwner(x.getUsername())));
        }

        model.addAttribute("users", attr);
        return "users";
    }

    @GetMapping("/account")
    public String account(Model model, Authentication auth){
        String username = auth.getName();

        model.addAttribute("interest", userRepository.getInterestForUsername(username));

        if(userRepository.getEmailEnabledForUsername(username)){
            model.addAttribute("email", userRepository.getEmailForUsername(username));
        }else{
            model.addAttribute("email", "Email disabled.");
        }
        return "account";
    }

    @MessageMapping("/account/interest")
    @SendTo("/socket/account/interest")
    public WebsocketServerResponse accountInterestForm(WebsocketFormData data, Authentication auth) {
        String msg = UserService.updateInterest(userRepository, data, auth.getName());
        if(msg.equals("OK")){
            return new WebsocketServerResponse(data.getData(), "output", false, null);
        }
        return new WebsocketServerResponse(msg, "error", true, null);
    }

    @MessageMapping("/account/password")
    @SendTo("/socket/account/password")
    public WebsocketServerResponse accountPasswordForm(WebsocketFormData rawData, Authentication auth) {
        String msg = UserService.updatePassword(userRepository, rawData, auth.getName());
        if(msg.equals("OK")){
            return new WebsocketServerResponse("Password updated successfully", "output", true, null);
        }
        return new WebsocketServerResponse(msg, "error", true, null);
    }

}
