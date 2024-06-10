package com.practice.ahub.service.posts;

import com.practice.ahub.model.Post;
import org.springframework.data.domain.Page;

import java.security.Principal;
import java.util.Optional;

public interface PostService {
    Optional<Post> getPostById(Long id);

    Post createPost(Principal principal, Post post);

    Page<Post> getAllPosts(Integer page, Integer size, String sortBy);
}
