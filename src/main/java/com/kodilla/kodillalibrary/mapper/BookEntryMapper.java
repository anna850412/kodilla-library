package com.kodilla.kodillalibrary.mapper;

import com.kodilla.kodillalibrary.domain.BookEntry;
import com.kodilla.kodillalibrary.domain.BookEntryDto;
import com.kodilla.kodillalibrary.domain.TitleEntry;
import com.kodilla.kodillalibrary.exception.TitleEntryNotExistException;
import com.kodilla.kodillalibrary.repository.TitleEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BookEntryMapper {
    //todo co do autowired nie jestem pewny byc moze trzeba zainicjalizowac poprzez new
    @Autowired
    private TitleEntryRepository titleEntryRepository;

    public BookEntry mapToBookEntry(BookEntryDto bookEntryDto) throws TitleEntryNotExistException {
        BookEntry bookEntry = new BookEntry();
        TitleEntry titleEntry = titleEntryRepository.findById(bookEntryDto.getTitleEntryId()).orElseThrow(() -> new TitleEntryNotExistException("Book does not exist"));
        bookEntry.setTitleEntry(titleEntry);
        bookEntry.setStatus(bookEntryDto.getStatus());

        return bookEntry;
    }

}
