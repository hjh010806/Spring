package com.example.demo.Image;

import com.example.demo.User.SiteUser;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ImageUploadDto {
    private MultipartFile file;
    private String caption;

    public Image toEntity(String postImageUrl, SiteUser siteUser) {
        return Image.builder()
                .caption(caption)
                .postImageUrl(postImageUrl)
                .siteUser(siteUser)  // sitUser -> siteUser로 수정
                .build();
    }
}
