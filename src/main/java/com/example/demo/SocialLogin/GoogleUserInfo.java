package com.example.demo.SocialLogin;

import java.util.Map;

public class GoogleUserInfo implements OAuth2UserInfo{
    private String id;
    private String name;
    private String email;
    private String imageUrl;
    
    private String provider;
    private String providerId;

    public GoogleUserInfo(Map<String, Object> attributes) {
        this.id = (String) attributes.get("sub");
        this.name = (String) attributes.get("name");
        this.email = (String) attributes.get("email");
        this.imageUrl = (String) attributes.get("picture");
        this.provider = "google"; // 소셜 로그인 공급자를 구글로 설정
        this.providerId = (String) attributes.get("sub"); // 구글 ID를 providerId로 설정
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public String getImageUrl() {
        return imageUrl;
    }

    @Override
    public String getProvider() {
        return provider;
    }

    @Override
    public String getProviderId() {
        return providerId;
    }
}
