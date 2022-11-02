import WebsocketForm from "./WebsocketForm.js";

document.addEventListener("DOMContentLoaded", function(event) { 
    var socket = new SockJS("/websocket");
    var stompClient = Stomp.over(socket);
    
    var InterestForm;
    var PasswordForm;

    stompClient.connect({}, ()=> {
        InterestForm = new WebsocketForm(stompClient, "/socket/account/interest", "/app/account/interest", "interestForm", "interest-text", "interest-error-text-div", ["interest-box"]);
        //const BirthdayForm = new WebsocketForm("/socket/account/interest", "/app/account/interest", "interestForm", "interest-text", false, ["interest-box"]);
        //const EmailForm = new WebsocketForm("/socket/account/interest", "/app/account/interest", "interestForm", "interest-text", false, ["interest-box"]);
        PasswordForm = new WebsocketForm(stompClient, "/socket/account/password", "/app/account/password", "passwordForm", "passwordForm", "passwordForm", ["current-box", "new-box", "confirm-box"]);
    });

    document.getElementById("interestForm").addEventListener("submit", function(e) {
        e.preventDefault();
        InterestForm.sendData();
    });

    document.getElementById("passwordForm").addEventListener("submit", function(e) {
        e.preventDefault();
        PasswordForm.sendData();
    });

});