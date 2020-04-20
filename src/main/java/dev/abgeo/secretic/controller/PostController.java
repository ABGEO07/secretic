package dev.abgeo.secretic.controller;

import dev.abgeo.secretic.model.Post;
import dev.abgeo.secretic.model.User;
import dev.abgeo.secretic.repository.PostRepository;
import dev.abgeo.secretic.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
public class PostController {

    private final UserService userService;

    private final PostRepository postRepository;

    @Autowired
    public PostController(UserService userService, PostRepository postRepository) {
        this.userService = userService;
        this.postRepository = postRepository;
    }

    @PostMapping("/post/{username}")
    @ResponseBody
    public ResponseEntity<Post> create(
            @PathVariable("username") String username,
            @RequestBody Post post,
            Authentication authentication
    ) {
        User author = userService.findByUsername(authentication.getName());
        User destination = userService.findByUsername(username);

        post.setAuthor(author);
        post.setDestination(destination);

        postRepository.save(post);

        return ResponseEntity.ok(post);
    }

    @DeleteMapping("/post/{id}")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> remove(@PathVariable("id") Long id, Authentication authentication) {
        User user = userService.findByUsername(authentication.getName());
        Optional<Post> postMaybe = postRepository.findById(id);
        Post post;

        if (postMaybe.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        post = postMaybe.get();

        if (!post.getDestination().equals(user)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        post.setDeleted(true);
        postRepository.save(post);

        Map<String, Object> response = new HashMap<>();
        response.put("deleted", true);
        response.put("id", post.getId());

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/post/{id}")
    @ResponseBody
    public ResponseEntity<Post> update(
            @PathVariable("id") Long id,
            @RequestParam("public") Boolean aPublic,
            Authentication authentication
    ) {
        User user = userService.findByUsername(authentication.getName());
        Optional<Post> postMaybe = postRepository.findById(id);
        Post post;

        if (postMaybe.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        post = postMaybe.get();

        if (!post.getDestination().equals(user)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        post.setPublic(aPublic);
        postRepository.save(post);

        return ResponseEntity.ok(post);
    }

}
