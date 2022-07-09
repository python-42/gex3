package com.giftexchange.gex3;

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

import com.giftexchange.gex3.forms.WebsocketFormData;
import com.giftexchange.gex3.sql.UserRepository;
import com.giftexchange.gex3.sql.UserTable;
import com.giftexchange.gex3.forms.UserCreationForm;
import com.giftexchange.gex3.user.User;
import com.giftexchange.gex3.util.NavblockGenerator;
import com.giftexchange.gex3.websocket.WebsocketServerResponse;

@Controller
public class Gex3Controller {

    @Autowired
    private UserRepository userRepository;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @GetMapping("/")
    public String manage(Model model){
        model.addAttribute("navblock", NavblockGenerator.generateNavblock(Constants.NAVBLOCK_MAP, "Your List"));
        return "manage";
    }

    @GetMapping("/lists")
    public String lists(Model model){
        model.addAttribute("navblock", NavblockGenerator.generateNavblock(Constants.NAVBLOCK_MAP, "Lists"));
        return "lists";
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

    @MessageMapping("/accountSocket")
    @SendTo("/socket/account")
    public WebsocketServerResponse accountWebSocket(WebsocketFormData data, Authentication auth) throws Exception {
        UserTable userInfo = userRepository.findByUsername(auth.getName());
        userInfo.setInterest((String) data.getDataPart(0));
        userRepository.save(userInfo);
        return new WebsocketServerResponse(data.getData());
    }



    @GetMapping("/bought")
    public String bought(Model model){
        model.addAttribute("navblock", NavblockGenerator.generateNavblock(Constants.NAVBLOCK_MAP, "Bought"));
        return "bought";
    }

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

}
