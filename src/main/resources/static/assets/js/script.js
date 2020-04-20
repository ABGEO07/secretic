(function ($) {
    $(document).ready(function () {
        // Hide the loader and show the elements.
        setTimeout(function () {
            $('.loader').addClass('hidden').delay(200).remove();
            $('.slide-in').each(function () {
                $(this).addClass('visible');
            });
        }, 1900);

        $('[data-toggle="popover"]').popover();
        $('[data-toggle="tooltip"]').tooltip();

        const stompClient = getStomp();
        subscribeNotifications(stompClient);
    });
})(jQuery);

/**
 * Connect to socket and get STOMP client instance.
 *
 * @param endpoint Socket endpoint for connect to.
 *
 * @returns {*}
 */
function getStomp(endpoint = '/secretic-socket') {
    const socket = new SockJS(endpoint);

    return Stomp.over(socket);
}
