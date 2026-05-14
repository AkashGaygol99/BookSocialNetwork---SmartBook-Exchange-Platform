package com.booknetwork.booksocialnetwork.controller;

import com.booknetwork.booksocialnetwork.dto.BookDto;
import com.booknetwork.booksocialnetwork.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    // CREATE
    @PostMapping("/create")
    public BookDto create(@RequestBody BookDto dto, Principal principal) {
        return bookService.create(dto, principal.getName());
    }

    // BORROW
    @PostMapping("/borrow/{id}")
    public String borrow(@PathVariable Long id, Principal principal) {
        if (principal == null) throw new RuntimeException("User not authenticated!");
        bookService.borrow(id, principal.getName());
        return "Book borrowed successfully!";
    }

    // RETURN
    @PostMapping("/return/{id}")
    public String returnBook(@PathVariable Long id, Principal principal) {
        if (principal == null) throw new RuntimeException("User not authenticated!");
        bookService.returnBook(id);
        return "Book returned!";
    }

    // GET ALL
    @GetMapping("/all")
    public List<BookDto> getAllBooks(Principal principal) {
        return bookService.getAll();
    }

    // UPDATE
    @PutMapping("/update/{id}")
    public BookDto updateBook(@PathVariable Long id, @RequestBody BookDto dto, Principal principal) {
        return bookService.update(id, dto, principal.getName());
    }

    // DELETE
    @DeleteMapping("/delete/{id}")
    public String deleteBook(@PathVariable Long id, Principal principal) {
        bookService.delete(id, principal.getName());
        return "Book deleted!";
    }

    // GET BY ID
    @GetMapping("/{id}")
    public BookDto getBookById(@PathVariable Long id, Principal principal) {
        return bookService.getDtoById(id);
    }
}