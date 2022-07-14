export default class WebsocketForm {

    stompClient = null;

    /**
    * @param {string} listenerURL The URL which the STOMP client subscribes to and recieves messages from.
    * @param {string} senderURL The URL which the STOMP client sends information to. This should be the URL the Controller is listening to.
    * @param {string} formID The id of the form which should be reset after data is sent.
    * @param {string} outputTextID The id of the element which should recieve data sent by the server.
    * @param {string} errorOutputTextID The id of the element which should recieve data sent by the server.
    * @param {list} fieldIDs The ids of all the fields in the form.
    * @param {string} socketName The name of the websocket prefix, defined in `WebSocketConfig.java`. Defaults to 'websocket'.
    */
    constructor(listenerURL, senderURL, formID, outputTextID, errorOutputTextID, fieldIDs, socketName = "/websocket") {
        this.listenerURL = listenerURL;//'/socket/account'
        this.senderURL = senderURL;//'app/accountSocket'
        this.formID = formID;
        this.outputTextID = outputTextID;
        this.errorOutputTextID = errorOutputTextID;
        this.fieldIDs = fieldIDs;
        this.socketName = socketName;
    }

    connect() {
        var socket = new SockJS(this.socketName);
        this.stompClient = Stomp.over(socket);
        this.stompClient.connect({},  () => {
            this.stompClient.subscribe(this.listenerURL, (response) => {
                this.outputData(response.body);
            });
        });
    }

    sendData() {
        const value = [];
        for (var x in this.fieldIDs){
            value.push($(this.fieldIDs[x]).val());
        }
        this.stompClient.send(this.senderURL, {}, JSON.stringify({'data' : value}));
        document.getElementById(this.formID).reset();
    }

    outputData(data) {
        var message = JSON.parse(data).data;
        var mode = JSON.parse(data).mode;
        var append = JSON.parse(data).append;
        if(mode == "error"){
            if(append){
                for (var x in message){
                    $(this.errorOutputTextID).append(message[x]);
                }
            }else{
                for (var x in message){
                    $(this.errorOutputTextID).text(message[x]);
                }
            }
        }else{
            if(append){
                for (var x in message){
                    $(this.outputTextID).append(message[x]);
                }
            }else{
                for (var x in message){
                    $(this.outputTextID).text(message[x]);
                }
            }   
        }
    }
}