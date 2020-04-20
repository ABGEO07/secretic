package dev.abgeo.secretic.eventPublisher;

import dev.abgeo.secretic.event.PostCreatedEvent;
import dev.abgeo.secretic.model.Post;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;

@Component
public class PostCreatedEventPublisher implements ApplicationEventPublisherAware {

    private ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void publish(final Post post) {
        applicationEventPublisher.publishEvent( new PostCreatedEvent(this, post));
    }

}
