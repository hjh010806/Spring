package com.example.demo.User;

//@RequiredArgsConstructor
//@Service
public class UserSecurityService {
//    implement UserDetailsService {
//    private final UserRepository userRepository;
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        Optional<SiteUser> _siteUser = this.userRepository.findByusername(username);
//        if (_siteUser.isEmpty()) {
//            throw new UsernameNotFoundException("사용자를 찾을수 없습니다.");
//        }
//        SiteUser siteUser = _siteUser.get();
//        List<GrantedAuthority> authorities = new ArrayList<>();
//        if ("admin".equals(username)) {
//            authorities.add(new SimpleGrantedAuthority(UserRole.ADMIN.getValue()));
//        } else {
//            authorities.add(new SimpleGrantedAuthority(UserRole.USER.getValue()));
//        }
//        return new User(siteUser.getUsername(), siteUser.getPassword(), authorities);
//    }
}
