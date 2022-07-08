package com.giftexchange.gex3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.giftexchange.gex3.user.User;
import com.giftexchange.gex3.user.UserRepository;
import com.giftexchange.gex3.util.NavblockGenerator;

@Controller
public class Gex3Controller {
    
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
    public String account(Model model){
        model.addAttribute("navblock", NavblockGenerator.generateNavblock(Constants.NAVBLOCK_MAP, "Account"));
        return "account";
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
    public String create(){
        return "create";
    }
    
    @Autowired
    private UserRepository userRepository;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @GetMapping(path="add")
    public @ResponseBody String addNewUser (@RequestParam String name, @RequestParam String password){

        User user = new User(name, encoder.encode(password));
        userRepository.save(user);
        return "Saved";
    }

}
