package com.practice.socialnetwork.repository.post;

import com.practice.socialnetwork.model.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
