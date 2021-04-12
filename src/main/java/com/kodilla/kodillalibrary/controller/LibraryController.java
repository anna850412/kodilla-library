package com.kodilla.kodillalibrary.controller;

import com.kodilla.kodillalibrary.domain.*;
import com.kodilla.kodillalibrary.exception.BookNotExistException;
import com.kodilla.kodillalibrary.exception.ReturnBookNotExistException;
import com.kodilla.kodillalibrary.mapper.BookEntryMapper;
import com.kodilla.kodillalibrary.mapper.ReaderMapper;
import com.kodilla.kodillalibrary.mapper.TitleEntryMapper;
import com.kodilla.kodillalibrary.service.DbService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/library")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class LibraryController {
    private final DbService service;
    private final BookEntryMapper bookEntryMapper;
    private final ReaderMapper readerMapper;
    private final TitleEntryMapper titleEntryMapper;

    @GetMapping(value = "findAllTitleEntries")
    public List<TitleEntryDto> findAll() {
        return titleEntryMapper.mapToTitleEntriesDtoList(service.findAllTitleEntries());
    }

    @PostMapping(value = "createReader", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void createReader(@RequestBody ReaderDto readerDto) {
        Reader reader = readerMapper.mapToReader(readerDto);
        service.saveReader(reader);
    }

    @PostMapping(value = "createTitle", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void createTitle(@RequestBody TitleEntryDto titleEntryDto) {
        TitleEntry titleEntry = titleEntryMapper.mapToTitleEntry(titleEntryDto);
        service.saveTitle(titleEntry);
    }

    @PostMapping(value = "createBookEntry", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void createBookEntry(@RequestBody BookEntryDto bookEntryDto) {
        BookEntry bookEntry = bookEntryMapper.mapToBookEntry(bookEntryDto);
        service.saveBookEntry(bookEntry);
    }

    @PutMapping(value = "updateStatus", consumes = MediaType.APPLICATION_JSON_VALUE)
    public BookEntryDto updateStatus(@RequestBody BookEntryDto bookEntryDto) {
        BookEntry bookEntry = bookEntryMapper.mapToBookEntry(bookEntryDto);
        BookEntry savedStatus = service.saveBookEntry(bookEntry);

        return bookEntryMapper.mapToBookEntryDto(savedStatus);
    }

//        @GetMapping(value = "howManyBookEntriesAreAvailableById")
//    public Long howManyBookEntriesAreAvailable(@RequestParam Long id) {
//            Optional<TitleEntry> titleById = service.findTitleEntryById(id);
//            return service.getNumberOfAvailableBooksById(titleById);
//        }

    @GetMapping(value = "howManyBookEntriesAreAvailableByTitleAndAuthor")
    public Long howManyBookEntriesAreAvailable(@RequestParam String title, String author) {
        return service.getNumberOfAvailableBooksByTitleEntry(title, author);
    }

    @PutMapping(value = "rentBook", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void rentABook(@RequestBody BookRentalDto bookRentalDto) throws BookNotExistException {
        service.rentBook(bookRentalDto);
    }

    @PutMapping(value = "returnBook", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void returnOfBook(@RequestBody ReturnBookDto returnBookDto) throws ReturnBookNotExistException {
        service.returnBook(returnBookDto);
    }
}
