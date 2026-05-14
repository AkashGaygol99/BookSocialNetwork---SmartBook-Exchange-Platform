package com.booknetwork.booksocialnetwork.dto;

import lombok.Data;

@Data
public class BookDto {
    private Long id;
    private String title;
    private String author;
    private String description;

    private boolean archived;
    private boolean available;

    private Long ownerId;      // user who owns the book
    private Long borrowedById;// user who borrowed the book
    private String ownerName;        // OPTIONAL
    private String borrowedByName;
}