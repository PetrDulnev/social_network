package com.practice.ahub.service;

import com.practice.ahub.model.FileModel;
import com.practice.ahub.model.UserProfile;
import com.practice.ahub.repository.FileModelRepository;
import com.practice.ahub.repository.UserProfilePagingAndSorting;
import com.practice.ahub.repository.UserProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserProfileService {

    private final UserProfileRepository userProfileRepository;
    private final FileModelRepository fileModelRepository;
    private final MinioService minioService;
    private final UserProfilePagingAndSorting userProfilePagingAndSorting;

    public UserProfile setProfileImage(Principal principal, MultipartFile file) {
        UserProfile profile = userProfileRepository.findByUserEmail(principal.getName());

        String fileName = minioService.save(file);

        FileModel fileModel = FileModel.builder()
                .fileName(fileName)
                .contentType(file.getContentType())
                .createdDate(LocalDateTime.now())
                .build();

        profile.setProfileImage(fileModelRepository.save(fileModel));
        return profile;
    }

    public UserProfile setBannerImage(Principal principal, MultipartFile file) {
        UserProfile profile = userProfileRepository.findByUserEmail(principal.getName());

        String fileName = minioService.save(file);

        FileModel fileModel = FileModel.builder()
                .fileName(fileName)
                .contentType(file.getContentType())
                .createdDate(LocalDateTime.now())
                .build();

        profile.setBannerImage(fileModelRepository.save(fileModel));

        return profile;
    }

    public UserProfile updateUserProfile(Principal principal, UserProfile userProfile) {
        UserProfile profile = userProfileRepository.findByUserEmail(principal.getName());

        if (userProfile.getGender() != null) {
            profile.setGender(userProfile.getGender());
        }

        if (userProfile.getLink() != null) {
            profile.setLink(userProfile.getLink());
        }

        return userProfileRepository.save(profile);
    }


    public Page<UserProfile> getAllUsers(Integer page, Integer size, String sortBy, String name) {
        Pageable pages = PageRequest.of(page, size, Sort.by(sortBy));
        if (name == null){
            return userProfilePagingAndSorting.findAll(pages);
        }
        return userProfilePagingAndSorting.findUserProfileByUserSurnameContaining(name, pages);
    }
}
