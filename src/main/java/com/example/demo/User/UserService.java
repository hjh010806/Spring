package com.example.demo.User;

import com.example.demo.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public SiteUser create(String username, String email, String paswword) {
        SiteUser user = new SiteUser();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(paswword));
//        if(username.equalsIgnoreCase("admin"))
//            user.setRole(UserRole.ADMIN.name());
//        else
//            user.setRole(UserRole.USER.name());
        user.setRole(username.equalsIgnoreCase("admin") ? UserRole.ADMIN.name() : UserRole.USER.name());
        this.userRepository.save(user);
        return user;
    }

    public SiteUser getUser(String username) {
        Optional<SiteUser> siteUser = this.userRepository.findByusername(username);
        if (siteUser.isPresent()) {
            return siteUser.get();
        } else {
            throw new DataNotFoundException("siteuser not found");
        }
    }
    public SiteUser userEmailCheck(String userEmail, String userName) {
        Optional<SiteUser> _user = userRepository.findByEmail(userEmail);

        if( _user.isPresent() && _user.get().getUsername().equals(userName))
            return _user.get();
        else
            return null;
    }
    public void changePassword(SiteUser siteUser, String password){
        siteUser.setPassword(passwordEncoder.encode(password));
        userRepository.save(siteUser);
    }

    public void setUrl(SiteUser user, String url){
        user.setUrl(url);
        userRepository.save(user);
    }


}
