package com.kodilla.kodillalibrary.repository;

import com.kodilla.kodillalibrary.domain.BookEntry;
import com.kodilla.kodillalibrary.domain.Reader;
import com.kodilla.kodillalibrary.domain.Status;
import com.kodilla.kodillalibrary.domain.Title;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
@Transactional
@Repository
public interface BookEntryRepository extends CrudRepository<BookEntry, Long> {
    @Override
    BookEntry save(BookEntry bookEntry);

    Optional<BookEntry> findById(Long id);

    List<BookEntry> findByTitleAndStatus(Optional<Title> title, Status status);
//    List<BookEntry> findByTitleIdAndReaderId(Optional<Title> titleId, Optional<Reader> readerId);
    List<BookEntry> findByReaderAndStatus(Optional<Reader> readerId,  Status status);
}
