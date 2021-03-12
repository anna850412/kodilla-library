package com.kodilla.kodillalibrary.repository;

import com.kodilla.kodillalibrary.domain.Title;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TitleRepository extends CrudRepository <Title, Integer> {
    @Override
    Title save(Title title);
    @Override
    List<Title> findAll();

    Title findById(Long id);
}
