package com.giftexchange.gex3.websocket;

import java.util.ArrayList;
import java.util.List;

public class WebsocketServerResponse {
    private List<Object> data;
    private String mode;
    private boolean append;
    private String remove;


    public WebsocketServerResponse(){}

    public WebsocketServerResponse(List<Object> data, String mode, boolean append, String remove) {
        this.data = data;
        this.mode = mode;
        this.append = append;
        this.remove = remove;
    }

    public WebsocketServerResponse(Object data, String mode, boolean append, String remove){
        this.data = new ArrayList<Object>();
        this.data.add(data);
        this.mode = mode;
        this.append = append;
        this.remove = remove;
    }

    public List<Object> getData(){
        return data;
    }

    public Object getDataPart(int pos){
        return data.get(pos);
    }

    public String getMode() {
        return this.mode;
    }

    public boolean isAppend() {
        return this.append;
    }

    public boolean getAppend() {
        return this.append;
    }

    public String getRemove(){
        return remove;
    }
}
