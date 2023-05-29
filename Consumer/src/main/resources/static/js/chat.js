const url = 'http://localhost:8080';
let stompClient;
let selectedUser;
let newMessages = new Map();

function connectToChat(userName) {
    console.log("connecting to chat...")
    let socket = new SockJS(url + '/chat');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log("connected to: " + frame);
        stompClient.subscribe("/topic/messages/" + userName, function (response) {
            let data = JSON.parse(response.body);
            if (selectedUser === data.fromLogin) {
                render(data.message, data.fromLogin);
            } else {
                newMessages.set(data.fromLogin, data.message);
                $('#userNameAppender_' + data.fromLogin).append('<span id="newMessage_' + data.fromLogin + '" style="color: red">+1</span>');
            }
        });
    });
}

function sendMsg(from, text) {
    stompClient.send("/app/chat/" + selectedUser, {}, JSON.stringify({
        fromLogin: from,
        message: text
    }));
}

var ws;

function connectToConsumer() {
    ws = new WebSocket('ws://localhost:8080/goodE');
    ws.onopen = function () {
        console.log("Opening WebSocket ...");
        ws.send(JSON.stringify({isConsuming: true, sender: 'Zilai'}));
    };
    ws.onmessage = function(event) {
        console.log("Web socket message received!");
        alert("Web socket message received!");
        alert(event.data);
    }

}

function disconnectFromConsumer() {
    if (ws != null) {
        ws.close();
    }
    console.log("Cannot read from consumer now!");
}

function registration() {
    console.log('Hello');
    let socket = new SockJS(url + '/rabbitmq');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function(frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/sensors', function (sensor) {
            console.log(JSON.parse(sensor.body));

        });
        // // to send via the web service-WORKING ( but websocket not called in springs)
        // $.post("http://localhost:8080/messages");
        //
        // // to send via websocket - WORKING
        // stompClient.send("/app/rabbitmq", {}, JSON.stringify({'message': 'message'}));
    })




    // let userName = document.getElementById("userName").value;
    // $.get(url + "/registration/" + userName, function (response) {
    //     connectToChat(userName);
    // }).fail(function (error) {
    //     if (error.status === 400) {
    //         alert("Login is already busy!")
    //     }
    // })
}

function selectUser(userName) {
    console.log("selecting users: " + userName);
    selectedUser = userName;
    let isNew = document.getElementById("newMessage_" + userName) !== null;
    if (isNew) {
        let element = document.getElementById("newMessage_" + userName);
        element.parentNode.removeChild(element);
        render(newMessages.get(userName), userName);
    }
    $('#selectedUserId').html('');
    $('#selectedUserId').append('Chat with ' + userName);
}

function fetchAll() {
    $.get(url + "/fetchAllUsers", function (response) {
        let users = response;
        let usersTemplateHTML = "";
        for (let i = 0; i < users.length; i++) {
            usersTemplateHTML = usersTemplateHTML + '<a href="#" onclick="selectUser(\'' + users[i] + '\')"><li class="clearfix">\n' +
                '                <img src="https://rtfm.co.ua/wp-content/plugins/all-in-one-seo-pack/images/default-user-image.png" width="55px" height="55px" alt="avatar" />\n' +
                '                <div class="about">\n' +
                '                    <div id="userNameAppender_' + users[i] + '" class="name">' + users[i] + '</div>\n' +
                '                    <div class="status">\n' +
                '                        <i class="fa fa-circle offline"></i>\n' +
                '                    </div>\n' +
                '                </div>\n' +
                '            </li></a>';
        }
        $('#usersList').html(usersTemplateHTML);
    });
}

function on_message(m) {
    console.log('message received');
    console.log(m);
    output.innerHTML += m.body + '<br />';
}
