package com.example.demo.Image;

import com.example.demo.User.SiteUser;
import com.example.demo.User.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
@RequiredArgsConstructor
@RequestMapping("/image")
public class ImageController {
    private final ImageService imageService;
    private final UserService userService;

    @PostMapping("/load")
    public String imageUpload(@AuthenticationPrincipal UserDetails userDetails, @RequestParam("temp") String temp_url) {
        SiteUser user  =  userService.getUser( userDetails.getUsername());
        //

        //
        userService.setUrl(user,temp_url);
        // 사용자 이름을 기반으로 리다이렉트합니다.
        return "redirect:/question/list";
    }

    public String image(@RequestParam("file") MultipartFile  file){

        // imageService




        return "";
    }


    @PostMapping("/temp")
    public String tempUpload(ImageUploadDto imageUploadDto, @AuthenticationPrincipal UserDetails userDetails, RedirectAttributes redirectAttributes) {
        if (userDetails == null) {
            // 사용자 정보가 없는 경우 처리
            return "login_form"; // 로그인 페이지로 리디렉션
        }
        // 이미지를 업로드합니다.
        String temp =  imageService.tempUpload(imageUploadDto, userDetails.getUsername());
        // 사용자 이름을 기반으로 리다이렉트합니다.
        redirectAttributes.addFlashAttribute("temp",temp);
        return "redirect:/question/list";
    }
    // skldfjlksdjflksjdf
    //lksjdflkjsdlkf
    //gorjkgdrjgr
    //grogjd;lgkr

//    @PostMapping("")
//    public String test(RedirectAttributes redirectAttributes, @RequestParam("file") MultipartFile multipartFile){
//        if(multipartFile.isEmpty()){
//            System.out.println("error");
//        }else
//            System.out.println("okay");
//
//        return "redirect:/question/list";
//    }

    // 이미지 파일의 경로를 HTML에 전달하는 메서드

    private final String uploadDir = "classpath:static/aaaa";

    @GetMapping("/image/{filename}")
    public ResponseEntity<byte[]> getImage(@PathVariable String filename) {
        try {
            Path imagePath = Paths.get(uploadDir, filename);
            File file = imagePath.toFile();
            byte[] data = Files.readAllBytes(file.toPath());
            return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(data);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }

}
