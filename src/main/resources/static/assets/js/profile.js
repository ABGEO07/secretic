(function ($) {
    $('.remove-post').click(function (event) {
        event.preventDefault();

        const self = $(this);

        const csrfParameter = $("meta[name='_csrf_parameter']").attr("content");
        const csrfHeader = $("meta[name='_csrf_header']").attr("content");
        const csrfToken = $("meta[name='_csrf']").attr("content");

        let data = [];
        let headers = [];
        data[csrfParameter] = headers[csrfHeader] = csrfToken;

        $.ajax({
            url: self.attr('href'),
            type: 'DELETE',
            headers: headers,
            data: data,
            success: function(result) {
                if (result.deleted) {
                    self.tooltip('hide');
                    $("div[data-id='" + result.id + "']").remove();
                }
            }
        });
    });

    $('.modify-post').click(function (event) {
        event.preventDefault();

        const self = $(this);

        const csrfParameter = $("meta[name='_csrf_parameter']").attr("content");
        const csrfHeader = $("meta[name='_csrf_header']").attr("content");
        const csrfToken = $("meta[name='_csrf']").attr("content");

        let data = {};
        let headers = [];
        data[csrfParameter] = headers[csrfHeader] = csrfToken;

        data['public'] = !self.data('value');

        $.ajax({
            url: self.attr('href'),
            type: 'PATCH',
            headers: headers,
            data: data,
            success: function(result) {
                self.find('i').removeClass();
                self.find('i').addClass('fa');
                self.data('value', result.public);

                if (result.public) {
                    self.find('i').addClass('fa-lock');
                    self.data('original-title', 'დამალვა');
                } else {
                    self.find('i').addClass('fa-globe');
                    self.data('original-title', 'გასაჯაროება');
                }
            }
        });
    });
})(jQuery);
