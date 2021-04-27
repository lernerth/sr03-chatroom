window.addEventListener("load", function (event) {

    let roomName = document.getElementById("roomName").innerHTML;
    let login = document.getElementById("userLogin").innerHTML;

    let ws = new WebSocket("ws://localhost:8080/Devoir2/chatserver/" + roomName + "?login=" + login);

    let txtHistory = document.getElementById("history");
    let txtMessage = document.getElementById("txtMessage");
    txtMessage.focus();

    ws.addEventListener("open", function (evt) {
        console.log("Connection established");
    });

    ws.addEventListener("message", function (evt) {
        let message = evt.data;
        console.log("Receive new message: " + message);
        txtHistory.value += message + "\n";
    });

    ws.addEventListener("close", function (evt) {
        console.log("Connection closed");
    });

    let btnSend = document.getElementById("btnSend");
    btnSend.addEventListener("click", function (clickEvent) {
        ws.send(txtMessage.value);
        txtMessage.value = "";
        txtMessage.focus();
    });

    let btnClose = document.getElementById("btnBack");
    btnClose.addEventListener("click", function (clickEvent) {
        ws.close();
    });

});