package com.giftexchange.gex3.item;

import java.util.HashMap;
import java.util.Map.Entry;

import com.giftexchange.gex3.websocket.WebsocketFormData;

import org.springframework.stereotype.Service;

@Service
public class ItemService {

    public static String itemForm(ItemRepository itemRepository, WebsocketFormData data, String name){
        HashMap<String, String> dataMap = new HashMap<String, String>();

        String id = (String) data.getDataPart(0);
        dataMap.put("name", (String)data.getDataPart(1));
        dataMap.put("url", (String)data.getDataPart(2));
        dataMap.put("title", (String)data.getDataPart(3));
        dataMap.put("comment", (String)data.getDataPart(4));

        for (Entry<String, String> x : dataMap.entrySet()){
            if(x.getValue() == null || x.getValue() == ""){
                return x.getKey() + " may not be blank.";
            }

            if(x.getValue().length() > 255){
                return x.getKey() + " may not be longer than 255 characters.";
            }
        }

        //all checks passed
        if(id.equals("")){
            itemRepository.save(new ItemTable(name, dataMap.get("name"), dataMap.get("url"), dataMap.get("title"), dataMap.get("comment")));
        }else{
            if(itemRepository.itemOwnerById(Integer.valueOf(id)) == name){
                itemRepository.updateSelfItemForID(dataMap.get("name"), dataMap.get("url"), dataMap.get("title"), dataMap.get("comment"), Integer.valueOf(id));
            }else{
                return "An error occured: Item ID and owner mismatch. Try reloading the page.";
            }
            
        }
        return "OK";
    }
}
