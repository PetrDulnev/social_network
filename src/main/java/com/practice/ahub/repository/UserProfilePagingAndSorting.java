package com.practice.ahub.repository;

import com.practice.ahub.model.UserProfile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserProfilePagingAndSorting extends PagingAndSortingRepository<UserProfile, Long> {
    @Query("select u from UserProfile u where u.user.surname ilike concat('%', ?1, '%') or u.user.name ilike concat('%',?1,'%') ")
    Page<UserProfile> findUserProfileByUserSurnameContaining(String name, Pageable pageable);
}
