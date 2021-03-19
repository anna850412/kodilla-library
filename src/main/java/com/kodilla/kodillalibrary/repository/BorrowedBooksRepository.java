package com.kodilla.kodillalibrary.repository;

import com.kodilla.kodillalibrary.domain.BorrowedBooks;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BorrowedBooksRepository extends CrudRepository <BorrowedBooks, Long> {
    @Override
    BorrowedBooks save(BorrowedBooks borrowedBooks);
}
