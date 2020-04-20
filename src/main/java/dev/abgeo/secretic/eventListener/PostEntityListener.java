package dev.abgeo.secretic.eventListener;

import dev.abgeo.secretic.eventPublisher.PostCreatedEventPublisher;
import dev.abgeo.secretic.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.PostPersist;

@Component
public class PostEntityListener {

    private PostCreatedEventPublisher postCreatedEventPublisher;

    @Autowired
    public void setPostEventPublisher(PostCreatedEventPublisher postCreatedEventPublisher) {
        this.postCreatedEventPublisher = postCreatedEventPublisher;
    }

    @PostPersist
    public void postPersist(Post post) {
        postCreatedEventPublisher.publish(post);
    }

}
