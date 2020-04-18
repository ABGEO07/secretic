(function ($) {
    $('form[name="create-post"]').submit(function(event){
        event.preventDefault();

        const csrfParameter = $("meta[name='_csrf_parameter']").attr("content");
        const csrfHeader = $("meta[name='_csrf_header']").attr("content");
        const csrfToken = $("meta[name='_csrf']").attr("content");

        let data = {
            'text': $(this).find('textarea[name="text"]').val(),
            'anonym': $(this).find('input[name="anonym"]').is(':checked'),
        };
        let headers = [];
        data[csrfParameter] = headers[csrfHeader] = csrfToken;

        $.ajax({
            url: $(this).attr("action"),
            type: $(this).attr("method"),
            headers: headers,
            contentType: "application/json",
            data: JSON.stringify(data),
            success: function(result) {
                let postAuthor = 'ანონიმურად';
                if (!result.anonym) {
                    postAuthor = `<a href="/user/${result.author.username}">${result.author.username}</a>`;
                }

                // Fucking JS :(
                let createdAt = new Date(result.createdAt);
                createdAt = `${
                    (createdAt.getMonth()+1).toString().padStart(2, '0')}/${
                    createdAt.getDate().toString().padStart(2, '0')}/${
                    createdAt.getFullYear().toString().padStart(4, '0')} ${
                    createdAt.getHours().toString().padStart(2, '0')}:${
                    createdAt.getMinutes().toString().padStart(2, '0')}`;

                const timelineEntry = `<div class="timeline-entry" data-id="${result.id}">
                                        <div class="timeline-stat">
                                            <div class="timeline-icon">
                                                <img src="/assets/images/avatar.png" alt="User">
                                            </div>
                                            <div class="timeline-time">${createdAt}</div>
                                        </div>
                                        <div class="timeline-label">
                                            <h5 class="text-danger">${postAuthor}</h5>
                                            <p class="timeline-text">${result.text}</p>
                                        </div>
                                    </div>`;
                const timeline = $('div.timeline');

                if (timeline.length) {
                    timeline.prepend(timelineEntry);
                } else {
                    const timelineEmpty = $('div.timeline-empty');

                    timelineEmpty.removeClass('timeline-empty');
                    timelineEmpty.addClass('timeline');
                    timelineEmpty.html(timelineEntry);
                }

                const audio = new Audio('/assets/audio/posted.mp3');
                audio.play();
            }
        });
    });

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
