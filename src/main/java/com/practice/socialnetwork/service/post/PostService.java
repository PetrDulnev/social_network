package com.practice.socialnetwork.service.post;

import com.practice.socialnetwork.model.post.Post;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;

@Component
public interface PostService {
    List<Post> getAllPosts();

    Post getPostById(Long id);

    Post createPost(Principal principal, Post post, List<MultipartFile> file);
}
