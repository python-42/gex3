package com.giftexchange.gex3.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.giftexchange.gex3.Constants.NavblockConstants;
import com.giftexchange.gex3.util.NavblockGenerator;

@Controller
public class IndexController {
    
    @GetMapping("/")
    public String content(Model model){
        model.addAttribute("navblock", NavblockGenerator.generateNavblock(NavblockConstants.INDEX));
        return "index";
    }
}
