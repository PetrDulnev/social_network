package com.practice.ahub.model;

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
@NamedEntityGraph(
            name = "UserProfile.eagerlyFetchUser",
        attributeNodes = {
                @NamedAttributeNode("user"),
                @NamedAttributeNode("profileImage"),
                @NamedAttributeNode("bannerImage"),
                @NamedAttributeNode("allImages")
        }
)
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
