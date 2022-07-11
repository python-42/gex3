package com.giftexchange.gex3.websocket;

import java.util.List;

public class WebsocketFormData {
    
    private List<Object> data;

    public List<Object> getData(){
        return data;
    }

    public Object getDataPart(int pos){
        return data.get(pos);
    }

    public void setInterest(List<Object> data){
        this.data = data;
    }
}
