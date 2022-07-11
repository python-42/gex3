package com.giftexchange.gex3.item;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.giftexchange.gex3.gex.Constants;
import com.giftexchange.gex3.util.NavblockGenerator;

@Controller
public class ItemController {

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

}
