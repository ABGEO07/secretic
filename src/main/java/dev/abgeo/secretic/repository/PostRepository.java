package dev.abgeo.secretic.repository;

import dev.abgeo.secretic.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
