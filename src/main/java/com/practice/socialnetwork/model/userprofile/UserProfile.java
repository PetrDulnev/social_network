package com.practice.socialnetwork.model.userprofile;

import com.practice.socialnetwork.model.file.FileModel;
import com.practice.socialnetwork.model.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "users_profile")
@Builder
public class UserProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Column(unique = true)
    private String link;
    @OneToOne
    private User user;
    @OneToOne
    private FileModel profileImage;
    @OneToOne
    private FileModel bannerImage;
    @OneToMany
    private List<FileModel> allImages;
}
