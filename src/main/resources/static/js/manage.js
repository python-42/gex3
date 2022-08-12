import WebsocketForm from "./WebsocketForm.js";

    $(function () {
        var socket = new SockJS("/websocket");
        var stompClient = Stomp.over(socket);
        
        var ItemForm;

        stompClient.connect({}, ()=> {
            ItemForm = new WebsocketForm(stompClient, "/socket/manage/item", "/app/manage/item", "itemForm", "#tableBody", "#error", ["#id", "#item", "#url", "#title", "#comment"]);
        });

        $("#itemForm").on('submit', function (e) {
            e.preventDefault();
            ItemForm.sendData();
            $("#formModal").modal("hide");
        });
    });