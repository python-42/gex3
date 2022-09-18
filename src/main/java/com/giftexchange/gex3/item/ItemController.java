package com.giftexchange.gex3.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.giftexchange.gex3.websocket.WebsocketFormData;
import com.giftexchange.gex3.websocket.WebsocketServerResponse;

@Controller
public class ItemController {

    @Autowired
    private ItemRepository itemRepository;

    @GetMapping("/")
    public String manage(Model model, Authentication auth){
        model.addAttribute("items", itemRepository.findAllWithOwner(auth.getName()));
        return "manage";
    }

    @MessageMapping("/manage/item")
    @SendTo("/socket/manage/item")
    public WebsocketServerResponse manageItemForm(WebsocketFormData data, Authentication auth){
        String msg = ItemService.itemForm(itemRepository, data, auth.getName());
        if(msg.equals("OK")){
            return new WebsocketServerResponse(data.getData(), "output", true, data.getDataPart(0), Elements.ROW.value);
        }
        return new WebsocketServerResponse(msg.substring(0, 1).toUpperCase() + msg.substring(1), "error", true, null, Elements.ERROR_MODAL.value);
    }

    @GetMapping("/users/{owner}")
    public String userItems(@PathVariable("owner") String owner, Model model, Authentication auth){
        if(owner.equals(auth.getName())){
            return "redirect:/";
        }
        model.addAttribute("owner", owner);
        model.addAttribute("itemCount", itemRepository.itemCountForOwner(owner));
        model.addAttribute("items", itemRepository.findAllWithOwner(owner));
        return "list";
    }


}
