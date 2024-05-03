package com.practice.socialnetwork.service.post;

import com.practice.socialnetwork.exception.CustomException;
import com.practice.socialnetwork.model.file.FileModel;
import com.practice.socialnetwork.model.post.Post;
import com.practice.socialnetwork.model.userprofile.UserProfile;
import com.practice.socialnetwork.repository.FileModel.FileModelRepository;
import com.practice.socialnetwork.repository.post.PostRepository;
import com.practice.socialnetwork.repository.userprofile.UserProfileRepository;
import com.practice.socialnetwork.service.minio.MinioServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserProfileRepository profileRepository;
    private final MinioServiceImpl minioServiceImpl;
    private final FileModelRepository fileRepository;

    @Override
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    @Override
    public Post getPostById(Long id) {
        return postRepository.findById(id).orElseThrow(
                () -> new CustomException(String.format("Post with id %s not found", id))
        );
    }

    @Override
    public Post createPost(Principal principal, Post post, List<MultipartFile> files) {
        Post newPost = new Post();

        UserProfile author = profileRepository.findByUserEmail(principal.getName())
                .orElseThrow(
                        () -> new CustomException("Can't find user")
                );

        if (files != null) {
            List<FileModel> fileModels = new ArrayList<>();

            files.forEach(
                    file -> fileModels.add(
                            FileModel.builder()
                                    .createdDate(LocalDateTime.now())
                                    .fileName(minioServiceImpl.save(file))
                                    .contentType(file.getContentType())
                                    .build()
                    )
            );

            newPost.setFiles(fileRepository.saveAll(fileModels));
        }

        newPost.setAuthor(author);
        newPost.setCreatedAt(LocalDateTime.now());
        newPost.setText(post.getText());

        return postRepository.save(newPost);
    }
}
