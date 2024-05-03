package com.practice.socialnetwork.repository.userprofile;

import com.practice.socialnetwork.model.userprofile.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
    Optional<UserProfile> findByUserEmail(String email);

    Optional<UserProfile> findByLink(String link);
}
