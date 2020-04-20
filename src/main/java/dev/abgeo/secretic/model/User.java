package dev.abgeo.secretic.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "user")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String firstName;

    private String lastName;

    @JsonIgnore
    private String password;

    @ManyToMany
    private Set<Role> roles;

    @OneToMany(mappedBy = "destination")
    @OrderBy("id DESC")
    @JsonIgnore
    private Set<Post> posts;

    @OneToMany(mappedBy = "author")
    @OrderBy("id DESC")
    @JsonIgnore
    private Set<Post> ownedPosts;

    @OneToMany(mappedBy = "user")
    @OrderBy("id DESC")
    @JsonIgnore
    private Set<Notification> notifications;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Set<Post> getPosts() {
        return posts;
    }

    public Set<Post> getOwnedPosts() {
        return ownedPosts;
    }

    public Set<Notification> getNotifications() {
        return notifications;
    }

    @Override
    public Object clone() {
        User user = new User();
        user.setId(this.id);
        user.setUsername(this.username);
        user.setPassword(this.password);
        user.setFirstName(this.firstName);
        user.setLastName(this.lastName);
        user.setRoles(this.roles);

        return user;
    }

    @Override
    public boolean equals(Object obj) {
        return obj.getClass() == User.class && this.id.equals(((User) obj).getId());
    }

}
