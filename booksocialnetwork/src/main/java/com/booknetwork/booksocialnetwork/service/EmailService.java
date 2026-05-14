package com.booknetwork.booksocialnetwork.service;

public interface EmailService {
    void sendActivationEmail(String to, String activationCode);
}