package com.practice.ahub.repository;

import com.practice.ahub.model.UserProfile;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
    @Query("select u from UserProfile u where u.user.email = ?1")
    UserProfile findByUserEmail(String email);
    @EntityGraph(value = "UserProfile.eagerlyFetchUser")
    UserProfile findByLink(String link);
}
