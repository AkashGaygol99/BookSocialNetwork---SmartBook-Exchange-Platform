package com.booknetwork.booksocialnetwork.controller;

import com.booknetwork.booksocialnetwork.dto.AuthResponseDto;
import com.booknetwork.booksocialnetwork.dto.LoginDto;
import com.booknetwork.booksocialnetwork.dto.UserRegistrationDto;
import com.booknetwork.booksocialnetwork.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    @Autowired
    private UserService userService;

    @GetMapping("/activate")
    public String activateAccount(@RequestParam String code) {
        boolean success = userService.activate(code);
        return success ? "Account activated successfully!" : "Invalid activation link!";
    }

    @PostMapping("/register")
    public String register(@RequestBody UserRegistrationDto dto) {
        userService.register(dto);
        return "Registration successful! Please check your email.";
    }

    @PostMapping("/login")
    public AuthResponseDto login(@RequestBody LoginDto dto) {

        return userService.login(dto.getEmail(), dto.getPassword());
    }
}