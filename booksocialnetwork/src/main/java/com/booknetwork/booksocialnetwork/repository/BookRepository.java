package com.booknetwork.booksocialnetwork.repository;

import com.booknetwork.booksocialnetwork.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {

}
