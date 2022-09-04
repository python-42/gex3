import WebsocketForm from "./WebsocketForm.js";

    document.addEventListener("DOMContentLoaded", function(event) { 
        var socket = new SockJS("/websocket");
        var stompClient = Stomp.over(socket);
        
        var ItemForm;

        stompClient.connect({}, ()=> {
            ItemForm = new WebsocketForm(stompClient, "/socket/manage/item", "/app/manage/item", "itemForm", "tableBody", "error", ["id", "item", "url", "title", "comment"]);
        });

        document.getElementById("itemForm").addEventListener("submit", function(e) {
            e.preventDefault();
            ItemForm.sendData();
        });

    });