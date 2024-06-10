package com.practice.ahub.repository;

import com.practice.ahub.model.Post;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PostSortingRepository extends PagingAndSortingRepository<Post, Long> {
}
