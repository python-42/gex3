package com.giftexchange.gex3.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.giftexchange.gex3.gex.Constants;
import com.giftexchange.gex3.util.NavblockGenerator;

@Controller
public class ItemController {

    @Autowired
    private ItemRepository itemRepository;

    @GetMapping("/")
    public String manage(Model model, Authentication auth){
        model.addAttribute("navblock", NavblockGenerator.generateNavblock(Constants.NAVBLOCK_MAP, "Your List"));
        model.addAttribute("items", itemRepository.findAllWithOwner(auth.getName()));
        return "manage";
    }

    @GetMapping("/users")
    public String lists(Model model, Authentication auth){
        model.addAttribute("navblock", NavblockGenerator.generateNavblock(Constants.NAVBLOCK_MAP, "Users"));
        
        model.addAttribute("users", itemRepository.findAllDistinctOwnersExcept(auth.getName()));
        return "users";
    }

}
