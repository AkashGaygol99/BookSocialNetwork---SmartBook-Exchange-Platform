package com.booknetwork.booksocialnetwork.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String author;
    private String description;

    private boolean archived = false;
    private boolean available = true;

    @ManyToOne
    @JsonIgnore
    private User owner;

    @ManyToOne
    @JsonIgnore
    private User borrowedBy;
}
