import WebsocketForm from "./WebsocketForm.js";

const InterestForm = new WebsocketForm("/socket/account/interest", "/app/account/interest", "interestForm", "#interest-text", "#interest-error-text-div", ["#interest-box"]);
//const BirthdayForm = new WebsocketForm("/socket/account/interest", "/app/account/interest", "interestForm", "#interest-text", false, ["#interest-box"]);
//const EmailForm = new WebsocketForm("/socket/account/interest", "/app/account/interest", "interestForm", "#interest-text", false, ["#interest-box"]);
const PasswordForm = new WebsocketForm("/socket/account/password", "/app/account/password", "passwordForm", "#passwordForm", "#passwordForm", ["#current-box", "#new-box", "#confirm-box"]);

    $(function () {
        InterestForm.connect();
        PasswordForm.connect();

        $("#interestForm").on('submit', function (e) {
            e.preventDefault();
            InterestForm.sendData();
        });

        $("#passwordForm").on('submit', function (e) {
            e.preventDefault();
            PasswordForm.sendData();
        });
    });