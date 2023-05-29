var socket = new SockJS('/rabbitmq');
stompClient = Stomp.over(socket);
stompClient.connect({}, function(frame) {
    console.log('Connected: ' + frame);
    stompClient.subscribe('/topic/sensors', function (sensor) {
        console.log(JSON.parse(sensor.body));

    });
    // to send via the web service-WORKING ( but websocket not called in springs)
    $.post("http://localhost:8080/messages");

    // to send via websocket - WORKING
    stompClient.send("/app/rabbitmq", {}, JSON.stringify({'message': 'message'}));
})