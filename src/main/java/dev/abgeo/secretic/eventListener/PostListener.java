package dev.abgeo.secretic.eventListener;

import dev.abgeo.secretic.model.Notification;
import dev.abgeo.secretic.model.Post;
import dev.abgeo.secretic.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.PostPersist;

@Component
public class PostListener {

    private final NotificationService notificationService;

    @Autowired
    public PostListener(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostPersist
    public void postPersist(Post post) {
        Notification notification = new Notification();
        notification.setUser(post.getDestination());
        notification.setMessage("New Post on your timeline");

        notificationService.notify(notification);
    }

}
