package com.giftexchange.gex3;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
    
}
