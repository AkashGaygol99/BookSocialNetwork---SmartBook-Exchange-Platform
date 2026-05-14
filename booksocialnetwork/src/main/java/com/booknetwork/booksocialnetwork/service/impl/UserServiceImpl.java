package com.booknetwork.booksocialnetwork.service.impl;

import com.booknetwork.booksocialnetwork.dto.AuthResponseDto;
import com.booknetwork.booksocialnetwork.dto.UserRegistrationDto;
import com.booknetwork.booksocialnetwork.entity.User;
import com.booknetwork.booksocialnetwork.exception.BadRequestException;
import com.booknetwork.booksocialnetwork.exception.ResourceNotFoundException;
import com.booknetwork.booksocialnetwork.exception.UnauthorizedException;
import com.booknetwork.booksocialnetwork.repository.UserRepository;
import com.booknetwork.booksocialnetwork.security.JwtUtil;
import com.booknetwork.booksocialnetwork.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserServiceImpl implements com.booknetwork.booksocialnetwork.service.UserService {

    @Autowired
    private UserRepository repo;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private EmailService emailService;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    // ==============================
    // REGISTER USER
    // ==============================
    @Override
    public void register(UserRegistrationDto dto) {

        // Prevent duplicate registration
        if (repo.findByEmail(dto.getEmail()).isPresent()) {
            throw new BadRequestException("Email already registered: " + dto.getEmail());
        }

        User user = new User();
        user.setFullName(dto.getFullName());
        user.setEmail(dto.getEmail());
        user.setPassword(encoder.encode(dto.getPassword()));
        user.setEnabled(false); // must activate using email

        String activationCode = UUID.randomUUID().toString();
        user.setActivationCode(activationCode);
        user.setRole("ROLE_USER");
        repo.save(user);

        emailService.sendActivationEmail(user.getEmail(), activationCode);
    }

    // ==============================
    // ACTIVATE USER ACCOUNT
    // ==============================
    @Override
    public boolean activate(String code) {

        User user = repo.findByActivationCode(code);

        if (user == null)
            throw new ResourceNotFoundException("Invalid or expired activation code.");

        user.setEnabled(true);
        user.setActivationCode(null);

        repo.save(user);
        return true;
    }

    // ==============================
    // LOGIN USER
    // ==============================
    @Override
    public AuthResponseDto login(String email, String password) {

        User user = repo.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found with email: " + email));

        if (!user.getEnabled()) {
            throw new UnauthorizedException("Account not activated.");
        }

        if (!encoder.matches(password, user.getPassword())) {
            throw new BadRequestException("Invalid password.");
        }

        String token = jwtUtil.generateToken(email);

        return new AuthResponseDto(token, user.getRole());
    }
}