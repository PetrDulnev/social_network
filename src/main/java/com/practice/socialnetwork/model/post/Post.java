package com.practice.socialnetwork.model.post;

import com.practice.socialnetwork.model.file.FileModel;
import com.practice.socialnetwork.model.userprofile.UserProfile;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String text;
    private LocalDateTime createdAt;
    @OneToMany
    private List<FileModel> files;
    @ManyToOne
    private UserProfile author;
}
