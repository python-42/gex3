package com.giftexchange.gex3.websocket;

import java.util.List;

public class WebsocketFormData {
    
    private List<String> data;

    public List<String> getData(){
        return data;
    }

    public String getDataPart(int pos){
        return data.get(pos);
    }

    public void setData(List<String> data){
        this.data = data;
    }
}
