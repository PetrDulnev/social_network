package com.practice.ahub.conroller;

import com.practice.ahub.model.UserProfile;
import com.practice.ahub.service.FileModelService;
import com.practice.ahub.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

@RestController
@RequestMapping("/profiles")
@RequiredArgsConstructor
public class UserProfileController {

    private final UserProfileService profileService;
    private final FileModelService fileModelService;

    @PatchMapping("/image")
    @Secured("USER")
    public UserProfile setProfileImage(Principal principal, @RequestPart("file") MultipartFile file) {
        fileModelService.isValidFile(file);
        return profileService.setProfileImage(principal, file);
    }

    @PatchMapping("/banner")
    @Secured("USER")
    public UserProfile setBannerImage(Principal principal, @RequestPart("file") MultipartFile file) {
        fileModelService.isValidFile(file);
        return profileService.setBannerImage(principal, file);
    }

    @PutMapping
    @Secured("USER")
    public UserProfile updateProfile(Principal principal, @RequestBody UserProfile userProfile) {
        return profileService.updateUserProfile(principal, userProfile);
    }

    @GetMapping
    public Page<UserProfile> getAllUsers(@RequestParam(defaultValue = "0") Integer page,
                                         @RequestParam(defaultValue = "5") Integer size,
                                         @RequestParam(defaultValue = "id") String sortBy,
                                         @RequestParam(required = false) String name) {
        return profileService.getAllUsers(page, size, sortBy, name);
    }
}
