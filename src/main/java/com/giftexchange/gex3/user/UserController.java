package com.giftexchange.gex3.user;

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

import com.giftexchange.gex3.gex.Constants;
import com.giftexchange.gex3.util.NavblockGenerator;
import com.giftexchange.gex3.websocket.WebsocketFormData;
import com.giftexchange.gex3.websocket.WebsocketServerResponse;

@Controller
public class UserController {

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
        model.addAttribute("navblock", NavblockGenerator.generateNavblock(Constants.NAVBLOCK_MAP, "Users"));
        model.addAttribute("users", userRepository.findAllUsersExcept(auth.getName()));
        return "users";
    }

    @GetMapping("/account")
    public String account(Model model, Authentication auth){
        String username = auth.getName();

        model.addAttribute("navblock", NavblockGenerator.generateNavblock(Constants.NAVBLOCK_MAP, "Account"));
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
            return new WebsocketServerResponse(data.getData(), "output", false);
        }
        return new WebsocketServerResponse((Object) Constants.CSS_DISMISSABLE_ERROR_MODAL + msg + "</div>", "error", true);
    }

    @MessageMapping("/account/password")
    @SendTo("/socket/account/password")
    public WebsocketServerResponse accountPasswordForm(WebsocketFormData rawData, Authentication auth) {
        String msg = UserService.updatePassword(userRepository, rawData, auth.getName());
        if(msg.equals("OK")){
            return new WebsocketServerResponse((Object) Constants.CSS_DISMISSABLE_SUCCESS_MODAL + "Password updated successfully</div>", "output", true);
        }
        return new WebsocketServerResponse((Object) Constants.CSS_DISMISSABLE_ERROR_MODAL + msg + "</div>", "error", true);
    }

}
