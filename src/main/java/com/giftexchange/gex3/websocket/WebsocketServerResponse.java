package com.giftexchange.gex3.websocket;

import java.util.List;

public class WebsocketServerResponse {
    private List<Object> data;

    public WebsocketServerResponse(){}

    public WebsocketServerResponse(List<Object> data) {
        this.data = data;
    }

    public List<Object> getData(){
        return data;
    }

    public Object getDataPart(int pos){
        return data.get(pos);
    }

}
