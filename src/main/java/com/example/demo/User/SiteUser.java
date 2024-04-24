package com.example.demo.User;

import com.example.demo.Image.Image;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class SiteUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    private String password;

    private String role;

    @Column(unique = true)
    private String email;

    @OneToMany(mappedBy = "siteUser", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties({"sitUser"})
    private List<Image> images = new ArrayList<>();


    private String url;



    private String provider;
    private String providerId;

    private LocalDateTime createDate;

    @Builder
    public SiteUser(String username, String password, String email, String role, String provider, String providerId) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.provider = provider;
        this.providerId = providerId;
        this.createDate = LocalDateTime.now();
    }
}
