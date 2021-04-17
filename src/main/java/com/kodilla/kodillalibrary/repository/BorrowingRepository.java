package com.kodilla.kodillalibrary.repository;

import com.kodilla.kodillalibrary.domain.Borrowing;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Transactional
@Repository
public interface BorrowingRepository extends CrudRepository<Borrowing, Long> {
    @Override
    Borrowing save(Borrowing borrowing);
}
