import WebsocketForm from "./WebsocketForm.js";

const AccountForm = new WebsocketForm("/socket/account", "/app/accountSocket", "interestForm", "#interest-text", false, ["#interest-box"]);

    $(function () {
        AccountForm.connect();
        $("#interestForm").on('submit', function (e) {
            e.preventDefault();
            AccountForm.sendData();
        });
    });