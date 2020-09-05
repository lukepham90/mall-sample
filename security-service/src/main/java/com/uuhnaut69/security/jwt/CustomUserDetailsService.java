package com.uuhnaut69.security.jwt;

import com.uuhnaut69.core.domain.model.User;
import com.uuhnaut69.core.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static com.uuhnaut69.security.jwt.CustomUserdetails.createUser;

/**
 * @author uuhnaut
 * @project mall
 */
@Component
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsernameIgnoreCaseAndIsEnabled(username, true)
                        .orElseThrow(() -> new UsernameNotFoundException("User name not found !!!"));
        return createUser(user);
    }
}
