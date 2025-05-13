package com.dima.booksbackend.controllers;

import com.dima.booksbackend.models.LoginResponse;
import com.dima.booksbackend.models.LoginUserDto;
import com.dima.booksbackend.models.User;
import com.dima.booksbackend.services.AuthenticationService;
import com.dima.booksbackend.services.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/auth")
@RestController
@RequiredArgsConstructor
public class AuthController {
    private final JwtService jwtService;
    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<Object> authenticate(@RequestBody LoginUserDto loginUserDto) {

        User authenticatedUser = authenticationService.authenticate(loginUserDto);
        String jwtToken = jwtService.generateToken(authenticatedUser);
        LoginResponse loginResponse = new LoginResponse().setToken(jwtToken).setExpiresIn(jwtService.getExpirationTime()).setUsername(authenticatedUser.getFullName());

        return ResponseEntity.ok(loginResponse);
    }
}
