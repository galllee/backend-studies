package com.group.libraryapp.domain.book;

import com.group.libraryapp.dto.book.request.BookCreateRequest;
import com.group.libraryapp.dto.book.request.BookLoanRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    //void save(BookCreateRequest request);

    Optional<Book> findByName(String name);

    //void saveBookLoan(BookLoanRequest request);
}
