package com.giftexchange.gex3.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.giftexchange.gex3.Constants;
import com.giftexchange.gex3.util.NavblockGenerator;

@Controller
public class ManageController {
    
    @GetMapping("/")
    public String content(Model model){
        model.addAttribute("navblock", NavblockGenerator.generateNavblock(Constants.NAVBLOCK_MAP, "Your List"));
        return "manage";
    }
}
