package com.practice.ahub.service.coments;

import com.practice.ahub.model.Comment;

import java.security.Principal;

public interface CommentService {
    Comment createComment(Principal principal, Comment comment, Long postId);
}
