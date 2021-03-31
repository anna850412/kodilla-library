package com.kodilla.kodillalibrary.repository;

import com.kodilla.kodillalibrary.domain.BorrowedBooks;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Transactional
@Repository
public interface BorrowedBooksRepository extends CrudRepository <BorrowedBooks, Long> {
    @Override
    BorrowedBooks save(BorrowedBooks borrowedBooks);
    @Query(nativeQuery = true)
    Long returnBook(Long readerId, Long bookEntryId);
}
