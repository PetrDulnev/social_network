package com.practice.ahub.repository;

import com.practice.ahub.model.Post;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    @NotNull
    @Override
    Page<Post> findAll(@NotNull Pageable pageable);
}
