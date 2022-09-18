package com.giftexchange.gex3.websocket;

import java.util.ArrayList;
import java.util.List;

public class WebsocketServerResponse {
    private List<String> data;
    private String mode;
    private boolean append;
    private String remove;
    private String template;


    public WebsocketServerResponse(){}

    public WebsocketServerResponse(List<String> data, String mode, boolean append, String remove, String template) {
        this.data = data;
        this.mode = mode;
        this.append = append;
        this.remove = remove;
        this.template = template;
    }

    public WebsocketServerResponse(String data, String mode, boolean append, String remove, String template){
        this.data = new ArrayList<String>();
        this.data.add(data);
        this.mode = mode;
        this.append = append;
        this.remove = remove;
        this.template = template;
    }

    public List<String> getData(){
        return data;
    }

    public String getDataPart(int pos){
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

    public String getTemplate(){
        return template;
    }
}
