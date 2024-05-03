package com.practice.socialnetwork.repository.FileModel;

import com.practice.socialnetwork.model.file.FileModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileModelRepository extends JpaRepository<FileModel, Long> {
}
