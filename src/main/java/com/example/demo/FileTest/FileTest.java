package com.example.demo.FileTest;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class FileTest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private int id;

    @Column(unique = true)
    private String key;
    // {user_id} + "_" + profile  // find 있으면 get 없으면 new
    // {user_id} + "_" + profile_temp  // find 있으면 get 없으면 new

    private String url;

    @Setter(AccessLevel.NONE)
    private LocalDateTime createDate;

    @Builder
    public FileTest(String key, String url) {
        this.key = key;
        this.url = url;
        this.createDate = LocalDateTime.now();
    }

}
