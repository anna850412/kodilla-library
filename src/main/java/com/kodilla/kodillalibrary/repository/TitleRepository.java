package com.kodilla.kodillalibrary.repository;

import com.kodilla.kodillalibrary.domain.Title;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
@Transactional
@Repository
public interface TitleRepository extends CrudRepository<Title, Long> {
    @Override
    Title save(Title title);
    @Override
    List<Title> findAll();

    Optional<Title> findById(Long id);
}
