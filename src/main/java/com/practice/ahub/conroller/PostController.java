package com.practice.ahub.conroller;

import com.practice.ahub.model.Post;
import com.practice.ahub.service.posts.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    @Secured("USER")
    public Post createPost(Principal principal, @RequestBody Post post) {
        return postService.createPost(principal, post);
    }

    @GetMapping
    public Page<Post> getPosts(@RequestParam(defaultValue = "0") Integer page,
                               @RequestParam(defaultValue = "10") Integer size,
                               @RequestParam(defaultValue = "id") String sortBy) {
        return postService.getAllPosts(page, size, sortBy);
    }

    @GetMapping("/{id}")
    public Post getPost(@PathVariable Long id) {
        return postService.getPostById(id).orElse(null);
    }
}
