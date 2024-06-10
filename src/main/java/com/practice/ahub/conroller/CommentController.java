package com.practice.ahub.conroller;

import com.practice.ahub.model.Comment;
import com.practice.ahub.service.coments.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{postId}")
    @Secured("USER")
    public Comment createComment(Principal principal, @RequestBody Comment comment, @PathVariable Long postId) {
        return commentService.createComment(principal, comment, postId);
    }

}
