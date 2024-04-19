package com.example.demo.Image;


import com.example.demo.User.SiteUser;
import com.example.demo.User.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ImageService {
    private final ImageRepository imageRepository;
    private final UserService userService;
    private final ResourceLoader resourceLoader;
//    private final String uploadFolder = "classpath:static/aaaa";

    @Transactional
    public void imageUpload(ImageUploadDto imageUploadDto, String username) {
        try {
            UUID uuid = UUID.randomUUID();// uuid(Universally Unique IDentifier) : 네트워크상에서 고유성이 보장되는 id를 만들기 위해 사용한다. 중복이 되지 않게 나온다.
            String imageFileName = "/aaaa/" + uuid + "_" + imageUploadDto.getFile().getOriginalFilename();
            String uploadFolder = resourceLoader.getResource("classpath:static").getFile().getPath();
            Path imageFilePath = Paths.get(uploadFolder + imageFileName); // 실제 저장되는 경로를 지정(경로 + 파일명)

            // 파일을 저장합니다.
            Files.write(imageFilePath, imageUploadDto.getFile().getBytes());

            // 사용자 이름을 기반으로 사용자 객체를 가져옵니다.
            SiteUser user = userService.getUser(username);

            // 이미지를 생성합니다.
            Image image = new Image(imageUploadDto.getCaption(), imageFileName, user);
            // 이미지를 저장합니다.
            Image savedImage = imageRepository.save(image);

            userService.setUrl(user, imageFileName);

            System.out.println(savedImage);
        } catch (IOException e) {
            e.printStackTrace();
            // 파일 저장 중 에러가 발생하면 처리합니다.
        }
    }

    @Transactional
    public String tempUpload(ImageUploadDto imageUploadDto, String username) {
        try {
            UUID uuid = UUID.randomUUID();// uuid(Universally Unique IDentifier) : 네트워크상에서 고유성이 보장되는 id를 만들기 위해 사용한다. 중복이 되지 않게 나온다.
            String imageFileName = "/aaaa/" + uuid + "_" + imageUploadDto.getFile().getOriginalFilename(); //  >>> src= '/aaaa/uuid.png'
            String uploadFolder = resourceLoader.getResource("classpath:static").getFile().getPath(); // static
            Path imageFilePath = Paths.get(uploadFolder + imageFileName); // 실제 저장되는 경로를 지정(경로 + 파일명)

            // 파일을 저장합니다.
            Files.write(imageFilePath, imageUploadDto.getFile().getBytes());

            // 사용자 이름을 기반으로 사용자 객체를 가져옵니다.
            return imageFileName;


        } catch (IOException e) {
            e.printStackTrace();
            // 파일 저장 중 에러가 발생하면 처리합니다.
            return "";
        }
    }
}

