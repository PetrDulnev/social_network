package com.practice.socialnetwork.service.userprofile;

import com.practice.socialnetwork.model.userprofile.UserProfile;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

public interface UserProfileService {
    UserProfile updateAvatarOrBackGroundImage(Principal principal, MultipartFile file, String object);

    UserProfile updateUserProfile(Principal principal, UserProfile userProfile);

    String getAvatar(String link);
}
