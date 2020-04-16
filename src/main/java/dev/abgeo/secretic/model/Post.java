package dev.abgeo.secretic.model;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "post")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn()
    private User author;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn()
    private User destination;

    @Column(length=1024)
    private String text;

    @CreationTimestamp
    private LocalDateTime createdAt;

    private Boolean anonym;

    @Column(name = "public")
    private Boolean aPublic;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public User getDestination() {
        return destination;
    }

    public void setDestination(User destination) {
        this.destination = destination;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Boolean isAnonym() {
        return anonym;
    }

    public void setAnonym(Boolean anonym) {
        this.anonym = anonym;
    }

    public Boolean isPublic() {
        return aPublic;
    }

    public void setPublic(Boolean aPublic) {
        this.aPublic = aPublic;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

}