package com.example.demo.Image;


import com.example.demo.User.SiteUser;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter@Setter
// GETTER SETTER ToString Hashcode
@Entity
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String caption; // 이미지 설명
    // profile
    // -> findByCaption
    // Optional 확인 -> 가져오기, 없으면 새로 생성

    private String postImageUrl; // 이미지 파일 경로

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id") // 외래 키 이름 설정 (원하는 이름으로 변경 가능)
    private SiteUser siteUser; // 이미지를 올린 사용자

    // 생성일자
    private LocalDateTime createDate;

    // 생성 시 자동으로 현재 시간을 저장하는 메서드
    @PrePersist
    public void updateCreateDate() {
        this.createDate = LocalDateTime.now();
    }

    // 생성자 추가
    public Image(String caption, String postImageUrl, SiteUser siteUser) {
        this.caption = caption;
        this.postImageUrl = postImageUrl;
        this.siteUser = siteUser;
    }
}