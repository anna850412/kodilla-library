package com.kodilla.kodillalibrary.repository;

import com.kodilla.kodillalibrary.domain.Borrowing;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Transactional
@Repository
public interface Borrowings extends CrudRepository<Borrowing, Long> {
    @Override
    Borrowing save(Borrowing borrowing);

    @Modifying
    @Query(nativeQuery = true)
    void returnBook(Long readerId, Long bookEntryId);

    @Modifying
    @Query(nativeQuery = true)
    void bookRental(Long readerId, Long titleId);

}
