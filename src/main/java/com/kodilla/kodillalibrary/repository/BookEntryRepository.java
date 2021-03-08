package com.kodilla.kodillalibrary.repository;

import com.kodilla.kodillalibrary.domain.BookEntry;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookEntryRepository extends CrudRepository<BookEntry, Integer> {
    @Override
    BookEntry save(BookEntry bookEntry);

    BookEntry findById(Long id);

    List<BookEntry> findByTitleAndStatus(String title, String status);

}
