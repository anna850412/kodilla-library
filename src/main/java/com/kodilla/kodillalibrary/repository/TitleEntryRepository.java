package com.kodilla.kodillalibrary.repository;

import com.kodilla.kodillalibrary.domain.TitleEntry;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
@Transactional
@Repository
public interface TitleEntryRepository extends CrudRepository<TitleEntry, Long> {
    @Override
    TitleEntry save(TitleEntry titleEntry);
    @Override
    List<TitleEntry> findAll();
   Optional<TitleEntry> findByTitleAndAuthor(String title, String author);
    Optional<TitleEntry> findById(Long id);
}
