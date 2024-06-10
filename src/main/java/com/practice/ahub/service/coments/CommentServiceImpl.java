package com.practice.ahub.service.coments;

import com.practice.ahub.model.Comment;
import com.practice.ahub.model.Post;
import com.practice.ahub.model.UserProfile;
import com.practice.ahub.repository.CommentRepository;
import com.practice.ahub.repository.PostRepository;
import com.practice.ahub.repository.UserProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserProfileRepository userProfileRepository;

    @Override
    public Comment createComment(Principal principal, Comment comment, Long postId) {
        UserProfile userProfile = userProfileRepository.findByUserEmail(principal.getName());
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new RuntimeException("Post not found")
        );
        comment.setCreatedAt(LocalDateTime.now());
        comment.setUser(userProfile);
        comment = commentRepository.save(comment);
        post.addComment(comment);
        postRepository.save(post);
        return comment;
    }
}
