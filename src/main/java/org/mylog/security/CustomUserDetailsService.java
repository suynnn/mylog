package org.mylog.security;

import lombok.RequiredArgsConstructor;
import org.mylog.domain.User;
import org.mylog.repository.UserRepository;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findById(username).orElse(null);

        if (user == null) {
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
        }

        UserBuilder userBuilder = org.springframework.security.core.userdetails.User.withUsername(username);
        userBuilder.password(user.getPassword());
        userBuilder.roles(user.getUserRoles().stream()
                .map(role -> role.getRole().getRoleEnum().name())
                .toArray(String[]::new));

        return userBuilder.build();
    }
}
