var ws;

init = function () {
	    ws = new WebSocket("ws://localhost:8090/front");
	    ws.onopen = function (event) { }
	    ws.onmessage = function (event) {
	        document.getElementById("dataField").innerHTML = event.data;
	    }
	    ws.onclose = function (event) {}
};

function sendMessage() {
	ws.send(document.getElementById("userId").value);
}