package com.practice.ahub.service.posts;

import com.practice.ahub.model.Post;
import com.practice.ahub.model.UserProfile;
import com.practice.ahub.repository.PostRepository;
import com.practice.ahub.repository.PostSortingRepository;
import com.practice.ahub.repository.UserProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService{

    private final PostRepository postRepository;
    private final UserProfileRepository userProfileRepository;
    private final PostSortingRepository postSortingRepository;

    @Override
    public Optional<Post> getPostById(Long id) {
        return Optional.ofNullable(postRepository.findById(id).orElseThrow(
                () -> new RuntimeException("can not find post")
        ));
    }

    @Override
    public Post createPost(Principal principal, Post post) {
        UserProfile user = userProfileRepository.findByUserEmail(principal.getName());
        Post newPost = Post
                .builder()
                .body(post.getBody())
                .user(user)
                .build();
        return postRepository.save(newPost);
    }

    @Override
    public Page<Post> getAllPosts(Integer page, Integer size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return postSortingRepository.findAll(pageable);
    }
}
