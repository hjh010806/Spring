package com.example.demo.SocialLogin;

import com.example.demo.User.SiteUser;
import com.example.demo.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {
    @Autowired
    private UserRepository userRepository;

    //구글로 부터 받은 userRequest 데이터에 대한 후처리 되는 함수
    //함수종료시 @AuthenticationPrincipal 어노테이션이 만들어진다.
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        //각 서비스에 맞게 정보를 가져옴
        //OAuth2UserInfo는 직접 만든 인터페이스 이고,
        OAuth2UserInfo oAuth2UserInfo;

        //각 브랜드별로 구현체를 만듬
        if (userRequest.getClientRegistration().getRegistrationId().equals("google")) {
            System.out.println("구글 로그인 요청");
            oAuth2UserInfo = new GoogleUserInfo(oAuth2User.getAttributes());
        }else {
            System.out.println("우리는 구글만 지원해요.");
            return null;
        }

        String provider = oAuth2UserInfo.getProvider(); // google
        String providerId = oAuth2UserInfo.getProviderId();
        String username = provider+"_"+providerId;
        String email = oAuth2UserInfo.getEmail();
        String role = "ROLE_USER";

        SiteUser userEntity = userRepository.findByUsername(username);
        if (userEntity == null) {
            System.out.println(provider + "로그인이 최초입니다.");
            //강제 회원가입
            //회원 DB에 추가함
            //password 가 null 이기 때문에 일반적인 회원가입을 할 수가 없음
            userEntity = SiteUser.builder()
                    .username(username)
                    .email(email)
                    .role(role)
                    .provider(provider)
                    .providerId(providerId)
                    .build();
            userRepository.save(userEntity);
        } else {
            System.out.println(provider + "로그인을 이미 한 적이 있습니다.");
        }
        return new PrincipalDetail(userEntity,oAuth2User.getAttributes());
    }

}
