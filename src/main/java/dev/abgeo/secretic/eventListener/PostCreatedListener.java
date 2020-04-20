package dev.abgeo.secretic.eventListener;

import dev.abgeo.secretic.event.PostCreatedEvent;
import dev.abgeo.secretic.model.Notification;
import dev.abgeo.secretic.model.Post;
import dev.abgeo.secretic.repository.NotificationRepository;
import dev.abgeo.secretic.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class PostCreatedListener implements ApplicationListener<PostCreatedEvent> {

    private final NotificationRepository notificationRepository;

    private final NotificationService notificationService;

    @Autowired
    public PostCreatedListener(NotificationRepository notificationRepository, NotificationService notificationService) {
        this.notificationRepository = notificationRepository;
        this.notificationService = notificationService;
    }

    @Override
    public void onApplicationEvent(PostCreatedEvent event) {
        createNotification(event.getPost());
    }

    private void createNotification(Post post) {
        Notification notification = new Notification();
        notification.setUser(post.getDestination());

        if (post.isAnonym()) {
            notification.setTitle("ახალი ანონიმური შეტყობინება");
            notification.setMessage("თქვენს კედელზე დაემატა ახალი ანონიმური შეტყობინება.");
        } else {
            notification.setTitle("ახალი შეტყობინება");
            notification.setMessage("თქვენ გაქვთ ახალი შეტყობინება " + post.getDestination().getUsername() + "-სგან.");
        }

        notificationRepository.save(notification);
        notificationService.notify(notification);
    }

}
