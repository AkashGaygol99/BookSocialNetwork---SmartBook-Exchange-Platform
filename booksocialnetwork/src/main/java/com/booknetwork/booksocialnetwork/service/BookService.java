package com.booknetwork.booksocialnetwork.service;

import com.booknetwork.booksocialnetwork.dto.BookDto;
import com.booknetwork.booksocialnetwork.entity.Book;

import java.util.List;

public interface BookService {

    BookDto create(BookDto dto, String ownerEmail);

    BookDto getById(Long id);

    BookDto update(Long id, BookDto dto, String ownerEmail);

    void delete(Long id, String ownerEmail);

    BookDto getDtoById(Long id);

    void borrow(Long bookId, String userEmail);

    void returnBook(Long bookId);

    List<BookDto> getAll();

    Book findById(Long id);
}