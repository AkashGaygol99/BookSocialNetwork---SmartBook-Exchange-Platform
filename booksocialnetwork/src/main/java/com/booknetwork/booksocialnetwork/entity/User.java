package com.booknetwork.booksocialnetwork.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;

    @Column(unique = true)
    private String email;

    private String password;

    private Boolean enabled = false;

    private String activationCode;

    private String role = "ROLE_USER";

    @OneToMany(mappedBy = "owner")
    private List<Book> books;
}