package com.example.demo.SocialLogin;

import com.example.demo.User.SiteUser;
import com.example.demo.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class PrincipalDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    //함수 종료시 @AuthenticationPrincipal 어노테이션이 만들어진다.
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("username : " + username);
        SiteUser userEntity = userRepository.findByUsername(username);
        if (userEntity != null) {
            return new PrincipalDetail(userEntity); // SiteUser 타입을 인자로 하는 생성자
        }

        return null;
    }


}
