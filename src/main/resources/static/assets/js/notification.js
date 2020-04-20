/**
 * Subscribe to user notifications topic.
 *
 * @param stompClient
 */
function subscribeNotifications(stompClient) {
    stompClient.connect({}, function (frame) {
        stompClient.subscribe('/user/topic/notification', function (result) {
            const notification = JSON.parse(result.body);
            addNavbarNotification(notification);
        });
    });
}

/**
 * Add notification to navbar notifications.
 *
 * @param notification Notification.
 */
function addNavbarNotification(notification) {
    let notificationsContainer = $('.notifications-container');
    let notificationsCounter = $('.notifications-counter');

    notificationsCounter.html(notificationsCounter.html() - 1 + 2);

    let notificationElement = $(`
        <a class="dropdown-item notification-dropdown-item" href="#">
            <div class="notifications-body">
                <p class="notification-title text-info">ახალი შეტყობინება</p>
                <p class="notification-text">${notification.message}</p>
                <p class="notification-date text-muted">
                    <i class="fa fa-clock-o" aria-hidden="true"></i> 17/04/2020 23:44
                </p>
            </div>
        </a>
    `);

    notificationElement.addClass('notification-unread');

    if (notificationsContainer.hasClass('empty')) {
        notificationsContainer.html(notificationElement);
    } else {
        notificationsContainer.prepend(notificationElement);
    }
}
