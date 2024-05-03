package com.practice.socialnetwork.controller;

import com.practice.socialnetwork.model.post.Post;
import com.practice.socialnetwork.service.post.PostService;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    @GetMapping
    public List<Post> getAllPosts() {
        return postService.getAllPosts();
    }

    @GetMapping("/{id}")
    public Post getPostById(@PathVariable Long id) {
        return postService.getPostById(id);
    }

    @PostMapping
    public Post createPost(Principal principal,
                           @RequestPart("post") Post post,
                           @RequestPart("file") @Size(max = 5, message = "max files 5") List<MultipartFile> file) {

        if (file.get(0).getSize() == 0) {
            file = null;
        }

        return postService.createPost(principal, post, file);
    }
}
