package com.booknetwork.booksocialnetwork.service;

import com.booknetwork.booksocialnetwork.dto.AuthResponseDto;
import com.booknetwork.booksocialnetwork.dto.UserRegistrationDto;
import com.booknetwork.booksocialnetwork.entity.User;

public interface UserService {
    void register(UserRegistrationDto dto);
    boolean activate(String code);
    AuthResponseDto login(String email, String password);
}
