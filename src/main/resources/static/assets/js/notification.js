$(document).ready(function() {
    const socket = new SockJS('/secretic-socket');
    const stompClient = Stomp.over(socket);

    stompClient.connect({}, function (frame) {
        stompClient.subscribe('/user/topic/notification', function (result) {
            const notification = JSON.parse(result.body);

            console.log(notification);
        });
    });
});
