package com.practice.socialnetwork.service.userprofile;

import com.practice.socialnetwork.exception.CustomException;
import com.practice.socialnetwork.model.file.FileModel;
import com.practice.socialnetwork.model.userprofile.UserProfile;
import com.practice.socialnetwork.repository.FileModel.FileModelRepository;
import com.practice.socialnetwork.repository.userprofile.UserProfileRepository;
import com.practice.socialnetwork.service.minio.MinioServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserProfileServiceImpl implements UserProfileService {

    private final UserProfileRepository userProfileRepository;
    private final FileModelRepository fileModelRepository;
    private final MinioServiceImpl minioServiceImpl;

    @Override
    public UserProfile updateAvatarOrBackGroundImage(Principal principal, MultipartFile file, String object) {
        UserProfile profile = userProfileRepository.findByUserEmail(principal.getName())
                .orElseThrow(
                        () -> new CustomException("Can't find user")
                );

        String fileName = minioServiceImpl.save(file);

        FileModel fileModel = FileModel.builder()
                .fileName(fileName)
                .contentType(file.getContentType())
                .createdDate(LocalDateTime.now())
                .build();

        if (object.equals("background")) {
            profile.setBannerImage(fileModelRepository.save(fileModel));
        } else if (object.equals("avatar")) {
            profile.setProfileImage(fileModelRepository.save(fileModel));
        }

        userProfileRepository.save(profile);

        return profile;
    }

    @Override
    public UserProfile updateUserProfile(Principal principal, UserProfile userProfile) {
        UserProfile profile = userProfileRepository.findByUserEmail(principal.getName())
                .orElseThrow(
                        () -> new CustomException("Can't find user")
                );

        if (userProfile.getGender() != null) {
            profile.setGender(userProfile.getGender());
        }

        if (userProfile.getLink() != null) {
            profile.setLink(userProfile.getLink());
        }

        return userProfileRepository.save(profile);
    }

    @Override
    public String getAvatar(String link) {
        UserProfile profile = userProfileRepository.findByLink(link).orElseThrow(
                () -> new CustomException(String.format("User profile not found for link: %s", link))
        );

        return minioServiceImpl.getFileUrl(profile.getProfileImage());
    }
}
