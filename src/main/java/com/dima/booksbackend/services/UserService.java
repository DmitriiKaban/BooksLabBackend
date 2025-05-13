package com.dima.booksbackend.services;

import com.dima.booksbackend.models.User;
import com.dima.booksbackend.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
    }

    public User loadUserByEmail(String username) {
        Optional<User> userOpt = userRepository.findByEmail(username);
        return userOpt.orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));
    }
}
