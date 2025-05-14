package com.dima.booksbackend.services;

import com.dima.booksbackend.models.LoginUserDto;
import com.dima.booksbackend.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    public User authenticate(LoginUserDto input) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getUsername(),
                        input.getPassword()
                )
        );

        User user = userService.loadUserByEmail(input.getUsername());
        if (user == null) {
            throw new UsernameNotFoundException("User not found with email: " + input.getUsername());
        }

        return user;
    }
}
