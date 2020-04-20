package dev.abgeo.secretic.event;

import dev.abgeo.secretic.model.Post;
import org.springframework.context.ApplicationEvent;

public class PostCreatedEvent extends ApplicationEvent {

    private Post post;

    public PostCreatedEvent(Object source, Post post) {
        super(source);
        this.post = post;
    }

    public Post getPost() {
        return post;
    }

}
