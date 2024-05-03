package com.practice.socialnetwork.controller;

import com.practice.socialnetwork.model.userprofile.UserProfile;
import com.practice.socialnetwork.service.userprofile.UserProfileServiceImpl;
import com.practice.socialnetwork.validation.file.FileConstraint;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

@RestController
@RequestMapping("/profiles")
@RequiredArgsConstructor
public class UserProfileController {

    private final UserProfileServiceImpl profileService;

    @PatchMapping("/decoration")
    @Secured("USER")
    public UserProfile updateAvatarOrBackGround(Principal principal,
                                                @RequestPart("file") @FileConstraint MultipartFile file,
                                                @RequestParam(name = "object") String object) {
        return profileService.updateAvatarOrBackGroundImage(principal, file, object);
    }

    @PutMapping
    @Secured("USER")
    public UserProfile updateProfile(Principal principal,
                                     @RequestBody UserProfile userProfile) {
        return profileService.updateUserProfile(principal, userProfile);
    }

    @GetMapping("/{link}")
    public String getAvatarByLink(@PathVariable String link) {
        return profileService.getAvatar(link);
    }
}
