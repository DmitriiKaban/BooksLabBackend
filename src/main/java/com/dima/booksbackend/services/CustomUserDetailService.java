package com.dima.booksbackend.services;

import com.dima.booksbackend.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService {

    private final UserService userService;

    @Transactional(readOnly = true)
    public User loadUserByEmail(String username) throws UsernameNotFoundException {
        User user = userService.loadUserByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        return user;
    }
}
