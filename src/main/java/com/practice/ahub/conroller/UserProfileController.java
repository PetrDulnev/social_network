package com.practice.ahub.conroller;

import com.practice.ahub.model.UserProfile;
import com.practice.ahub.service.FileModelService;
import com.practice.ahub.service.UserProfileService;
import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
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
    @PermitAll
    public  UserProfile getByEmail(@RequestParam(value = "email") String email){
        return profileService.getbyemail(email);
    }

    @GetMapping("/{link}")
    @PermitAll
    public UserProfile getByLink(@PathVariable String link){
        return profileService.getByLink(link);
    }
}