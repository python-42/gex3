export default class WebsocketForm {

    /**
    * @param {string} listenerURL The URL which the STOMP client subscribes to and recieves messages from.
    * @param {string} senderURL The URL which the STOMP client sends information to. This should be the URL the Controller is listening to.
    * @param {string} formID The id of the form which should be reset after data is sent.
    * @param {string} outputTextID The id of the element which should recieve data sent by the server.
    * @param {string} errorOutputTextID The id of the element which should recieve data sent by the server.
    * @param {list} fieldIDs The ids of all the fields in the form.
    */
    constructor(stompClient, listenerURL, senderURL, formID, outputTextID, errorOutputTextID, fieldIDs) {
        this.stompClient = stompClient;
        this.listenerURL = listenerURL;//'/socket/account'
        this.senderURL = senderURL;//'app/accountSocket'
        this.formID = formID;
        this.outputTextID = outputTextID;
        this.errorOutputTextID = errorOutputTextID;
        this.fieldIDs = fieldIDs;

        this.stompClient.subscribe(this.listenerURL, (response) => {
            this.outputData(response.body);
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
        var remove = JSON.parse(data).remove;
        console.log("Remove:");
        console.log(remove);
        if(remove!=null && remove!=""){
            document.getElementById(remove).remove();
        }
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