package com.giftexchange.gex3.item;

import java.util.HashMap;

import org.springframework.stereotype.Service;

import com.giftexchange.gex3.websocket.WebsocketFormData;

@Service
public class ItemService {

    public static String itemForm(ItemRepository itemRepository, WebsocketFormData data, String name){
        HashMap<String, String> dataMap = new HashMap<String, String>();

        String id = (String) data.getDataPart(0);
        dataMap.put("name", (String)data.getDataPart(1));
        dataMap.put("url", (String)data.getDataPart(2));
        dataMap.put("title", (String)data.getDataPart(3));
        dataMap.put("comment", (String)data.getDataPart(4));

        //TODO checks

        //all checks passed
        if(id.equals("")){
            itemRepository.save(new ItemTable(name, dataMap.get("name"), dataMap.get("url"), dataMap.get("title"), dataMap.get("comment")));
        }else{
            itemRepository.updateSelfItemForID(dataMap.get("name"), dataMap.get("url"), dataMap.get("title"), dataMap.get("comment"), Integer.valueOf(id));
        }
        return "OK";
    }
}
