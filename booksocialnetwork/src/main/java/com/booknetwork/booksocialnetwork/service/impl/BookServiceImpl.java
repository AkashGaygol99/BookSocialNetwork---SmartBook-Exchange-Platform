package com.booknetwork.booksocialnetwork.service.impl;

import com.booknetwork.booksocialnetwork.dto.BookDto;
import com.booknetwork.booksocialnetwork.entity.Book;
import com.booknetwork.booksocialnetwork.entity.User;
import com.booknetwork.booksocialnetwork.exception.BadRequestException;
import com.booknetwork.booksocialnetwork.exception.BookNotAvailableException;
import com.booknetwork.booksocialnetwork.exception.ResourceNotFoundException;
import com.booknetwork.booksocialnetwork.repository.BookRepository;
import com.booknetwork.booksocialnetwork.repository.UserRepository;
import com.booknetwork.booksocialnetwork.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepo;

    @Autowired
    private UserRepository userRepo;

    @Override
    public BookDto create(BookDto dto, String email) {

        User owner = userRepo.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + email));

        Book book = new Book();
        book.setTitle(dto.getTitle());
        book.setAuthor(dto.getAuthor());
        book.setDescription(dto.getDescription());
        book.setOwner(owner);
        book.setAvailable(true);
        book.setArchived(false);

        bookRepo.save(book);

        return convertToDto(book);
    }

    @Override
    public BookDto getById(Long id) {

        Book book = bookRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found: " + id));

        return convertToDto(book);
    }

    // ------------------- UPDATE BOOK -------------------
    @Override
    public BookDto update(Long id, BookDto dto, String ownerEmail) {

        Book book = bookRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found: " + id));

        User user = userRepo.findByEmail(ownerEmail)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        boolean isAdmin = "ROLE_ADMIN".equals(user.getRole());

        if (!isAdmin &&
                !book.getOwner().getEmail().equals(ownerEmail)) {

            throw new BadRequestException(
                    "You can update only your own books."
            );
        }

        book.setTitle(dto.getTitle());
        book.setAuthor(dto.getAuthor());
        book.setDescription(dto.getDescription());

        bookRepo.save(book);

        return convertToDto(book);
    }

    // ------------------- DELETE BOOK -------------------
    @Override
    public void delete(Long id, String ownerEmail) {

        Book book = bookRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found: " + id));

        User user = userRepo.findByEmail(ownerEmail)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        boolean isAdmin = "ROLE_ADMIN".equals(user.getRole());

        if (!isAdmin &&
                !book.getOwner().getEmail().equals(ownerEmail)) {

            throw new BadRequestException(
                    "You can delete only your own books."
            );
        }

        if (!book.isAvailable()) {
            throw new BadRequestException("Cannot delete: Book is currently borrowed.");
        }

        bookRepo.delete(book);
    }

    // ------------------- BORROW BOOK -------------------
    @Override
    public void borrow(Long bookId, String userEmail) {

        Book book = bookRepo.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found: " + bookId));

        User user = userRepo.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + userEmail));

        if (!book.isAvailable())
            throw new BookNotAvailableException("This book is already borrowed.");

        if (book.getOwner().getId().equals(user.getId()))
            throw new BadRequestException("You cannot borrow your own book.");

        book.setAvailable(false);
        book.setBorrowedBy(user);

        bookRepo.save(book);
    }

    // ------------------- RETURN BOOK -------------------
    @Override
    public void returnBook(Long bookId) {

        Book book = bookRepo.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found: " + bookId));

        book.setBorrowedBy(null);
        book.setAvailable(true);

        bookRepo.save(book);
    }

    // ------------------- GET ALL -------------------
    @Override
    public List<BookDto> getAll() {
        return bookRepo.findAll()
                .stream()
                .map(this::convertToDto)
                .toList();
    }

    @Override
    public BookDto getDtoById(Long id) {
        Book book = findById(id);

        BookDto dto = new BookDto();
        dto.setId(book.getId());
        dto.setTitle(book.getTitle());
        dto.setAuthor(book.getAuthor());
        dto.setDescription(book.getDescription());
        dto.setAvailable(book.isAvailable());
        dto.setArchived(book.isArchived());

        if (book.getOwner() != null) {
            dto.setOwnerId(book.getOwner().getId());
            dto.setOwnerName(book.getOwner().getFullName());
        }
        if (book.getBorrowedBy() != null) {
            dto.setBorrowedById(book.getBorrowedBy().getId());
            dto.setBorrowedByName(book.getBorrowedBy().getFullName());
        }

        return dto;
    }

    // ------------------- DTO MAPPER -------------------
    private BookDto convertToDto(Book book) {
        BookDto dto = new BookDto();

        dto.setId(book.getId());
        dto.setTitle(book.getTitle());
        dto.setAuthor(book.getAuthor());
        dto.setDescription(book.getDescription());
        dto.setAvailable(book.isAvailable());
        dto.setArchived(book.isArchived());

        // Owner details
        if (book.getOwner() != null) {
            dto.setOwnerId(book.getOwner().getId());
            dto.setOwnerName(book.getOwner().getFullName());
        }

        // Borrowed by details
        if (book.getBorrowedBy() != null) {
            dto.setBorrowedById(book.getBorrowedBy().getId());
            dto.setBorrowedByName(book.getBorrowedBy().getFullName());
        }

        return dto;
    }
    @Override
    public Book findById(Long id) {
        return bookRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found: " + id));
    }
}