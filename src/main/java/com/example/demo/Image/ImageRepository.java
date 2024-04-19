package com.example.demo.Image;

import com.example.demo.User.SiteUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {
    List<Image> findBySiteUser(SiteUser siteUser);

    Image findProfileImageBySiteUser(SiteUser siteUser);
}
