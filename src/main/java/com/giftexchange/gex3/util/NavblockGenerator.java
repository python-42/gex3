package com.giftexchange.gex3.util;

import java.util.List;
import java.util.Map;

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
                rtn += "<button type='button' class='btn bg-white btn-block border border-body' data-toggle='modal' data-target='#logoutModal'>Logout</button>";
            }
            else if(map.get("name") == currentPage){
                rtn += "<button type='button' class='btn btn-primary btn-block active border border-body'>"+ map.get("name") +"</button>";//TODO make this some type of constant
            }else{
                rtn += "<button type='button' class='btn bg-white btn-block border border-body' onclick='location.assign(\""+ map.get("url") +"\")'>"+ map.get("name") +"</button>";//TODO make this some type of constant
            }
        }

        return rtn;
    }
}
