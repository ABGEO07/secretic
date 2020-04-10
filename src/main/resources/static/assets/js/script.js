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
    });
})(jQuery);
