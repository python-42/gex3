package com.giftexchange.gex3.util;

import java.util.List;
import java.util.Map;

import com.giftexchange.gex3.Constants;

public class NavblockGenerator {

    /* 
    Input should be an arrayList of Map<String, String> with the following structure:
    name - string which should be displayed on the button
    url - url which the button should redirect to. Not necessary for 'active' buttons
     */
    public static String generateNavblock(List<Map<String, String>> pages, String currentPage){
        String rtn = "";

        for (Map<String, String> map : pages){
            if(map.get("name") == "Logout"){
                rtn += "<button type='button' class='"+Constants.CSS_NAVBLOCK_CLICKABLE+"' data-toggle='modal' data-target='#logoutModal'>Logout</button>";
            }
            else if(map.get("name") == currentPage){
                rtn += "<button type='button' class='"+Constants.CSS_NAVBLOCK_ACTIVE+"'>"+ map.get("name") +"</button>";
            }else{
                rtn += "<button type='button' class='"+Constants.CSS_NAVBLOCK_CLICKABLE+"' onclick='location.assign(\""+ map.get("url") +"\")'>"+ map.get("name") +"</button>";
            }
        }

        return rtn;
    }
}
