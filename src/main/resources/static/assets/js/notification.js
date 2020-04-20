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

    // Fucking JS :(
    let createdAt = new Date(notification.createdAt);
    createdAt = `${
        (createdAt.getMonth()+1).toString().padStart(2, '0')}/${
        createdAt.getDate().toString().padStart(2, '0')}/${
        createdAt.getFullYear().toString().padStart(4, '0')} ${
        createdAt.getHours().toString().padStart(2, '0')}:${
        createdAt.getMinutes().toString().padStart(2, '0')}`;

    let notificationElement = $(`
        <a class="dropdown-item notification-dropdown-item" href="#">
            <div class="notifications-body">
                <p class="notification-title text-info">${notification.title}</p>
                <p class="notification-text">${notification.message}</p>
                <p class="notification-date text-muted">
                    <i class="fa fa-clock-o" aria-hidden="true"></i> ${createdAt}
                </p>
            </div>
        </a>
    `);

    if (true !== notification.seen) {
        notificationElement.addClass('notification-unread');
    }

    if (notificationsContainer.hasClass('empty')) {
        notificationsContainer.removeClass('empty');
        notificationsContainer.html(null);
    } else if (5 <= notificationsContainer.children().length) {
        notificationsContainer.children().last().remove();
    }

    notificationsContainer.prepend(notificationElement);
}
