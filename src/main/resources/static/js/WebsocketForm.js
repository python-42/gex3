export default class WebsocketForm {

    /**
    * @param {string} listenerURL The URL which the STOMP client subscribes to and recieves messages from.
    * @param {string} senderURL The URL which the STOMP client sends information to. This should be the URL the Controller is listening to.
    * @param {string} formID The id of the form which should be reset after data is sent.
    * @param {string} outputTextID The id of the element which should recieve data sent by the server.
    * @param {string} errorOutputTextID The id of the element which should recieve data sent by the server.
    * @param {list} fieldIDs The ids of all the fields in the form.
    * @param {list} elementIDs The ids of template elements which data should be put into
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
        const values = [];
        for (var x in this.fieldIDs){
            values.push(document.getElementById(this.fieldIDs[x]).value);
        }
        this.stompClient.send(this.senderURL, {}, JSON.stringify({'data' : values}));
        document.getElementById(this.formID).reset();
    }

    outputData(data) {
        var message = JSON.parse(data).data;
        var mode = JSON.parse(data).mode;
        var append = JSON.parse(data).append;
        var remove = JSON.parse(data).remove;
        var templateName = JSON.parse(data).template;

        if(remove!=null && remove!=""){
            document.getElementById(remove).remove();
        }
        const template = document.querySelector(templateName);
        const clone = document.importNode(template.content, true);

        if(clone.querySelectorAll("td").length == 0){
            let txt = clone.querySelectorAll("p")
            for (let x in txt){ 
                txt[x].textContent = message[x];
            }
        }else{
            clone.querySelector("tr").id = message[0];

            let link = clone.querySelector("a");
            link.href = message[2];//url
            link.textContent = message[3];//url title

            let txt = clone.querySelectorAll("td");
            txt[0].textContent = message[1];//name
            txt[2].textContent = message[4];//comment
            
        }

        if(mode == "error"){
            if(append){
                for (var x in message){
                    document.getElementById(this.errorOutputTextID).appendChild(clone);
                }
            }else{
                for (var x in message){
                    document.getElementById(this.errorOutputTextID).text(message[x]);
                }
            }
        }else{
            if(append){
                for (var x in message){
                    document.getElementById(this.outputTextID).append(clone);
                }
            }else{
                for (var x in message){
                    document.getElementById(this.outputTextID).text(message[x]);
                }
            }   
        }
    }
}