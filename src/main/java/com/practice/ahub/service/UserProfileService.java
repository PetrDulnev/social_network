package com.practice.ahub.service;

import com.practice.ahub.exception.CustomException;
import com.practice.ahub.model.FileModel;
import com.practice.ahub.model.UserProfile;
import com.practice.ahub.repository.FileModelRepository;
import com.practice.ahub.repository.UserProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserProfileService {

    private final UserProfileRepository userProfileRepository;
    private final FileModelRepository fileModelRepository;
    private final MinioService minioService;

    @Transactional
    public UserProfile setNewProfileImage(Principal principal, MultipartFile file, String attribute) {
        UserProfile profile = userProfileRepository.findByUserEmail(principal.getName());

        String fileName = minioService.save(file);

        FileModel fileModel = FileModel.builder()
                .fileName(fileName)
                .contentType(file.getContentType())
                .createdDate(LocalDateTime.now())
                .build();

        try {
            switch (attribute) {
                case "background":
                    profile.setBannerImage(fileModelRepository.save(fileModel));
                    break;
                case "avatar":
                    profile.setProfileImage(fileModelRepository.save(fileModel));
                    break;
                default:
                    throw new CustomException("miss attribute");
            }

        } catch (Exception e) {
            throw new CustomException(e.getMessage());
        }
        return profile;

    }


    @Transactional
    public UserProfile updateUserProfile(Principal principal, UserProfile userProfile) {
        try {
            UserProfile profile = userProfileRepository.findByUserEmail(principal.getName());

            if (userProfile.getGender() != null) {
                profile.setGender(userProfile.getGender());
            }

            if (userProfile.getLink() != null) {
                profile.setLink(userProfile.getLink());
            }

            return userProfileRepository.save(profile);
        } catch (Exception e) {
            throw new CustomException(e.getMessage());
        }
    }


    public UserProfile getbyemail(String email) {
        return userProfileRepository.findByUserEmail(email);
    }

    public UserProfile getByLink(String link) {
        return userProfileRepository.findByLink(link);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    protected void saveUserProfile(UserProfile userProfile) {
        userProfileRepository.save(userProfile);
    }
}
