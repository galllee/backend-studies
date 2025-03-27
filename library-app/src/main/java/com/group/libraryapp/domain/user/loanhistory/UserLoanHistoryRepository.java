package com.group.libraryapp.domain.user.loanhistory;

import com.group.libraryapp.dto.book.request.BookLoanRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserLoanHistoryRepository extends JpaRepository<UserLoanHistory, Long> {

    boolean existsByBookNameAndIsReturn(String name, boolean isReturn);

}
