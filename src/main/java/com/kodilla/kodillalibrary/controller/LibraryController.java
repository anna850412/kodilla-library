package com.kodilla.kodillalibrary.controller;

import com.kodilla.kodillalibrary.domain.*;
import com.kodilla.kodillalibrary.exception.BookEntryNotExistException;
import com.kodilla.kodillalibrary.exception.ReturnBookNotExistException;
import com.kodilla.kodillalibrary.exception.TitleEntryNotExistException;
import com.kodilla.kodillalibrary.mapper.BookEntryMapper;
import com.kodilla.kodillalibrary.mapper.ReaderMapper;
import com.kodilla.kodillalibrary.mapper.TitleEntryMapper;
import com.kodilla.kodillalibrary.service.DbService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public void createBookEntry(@RequestBody BookEntryDto bookEntryDto) throws BookEntryNotExistException, TitleEntryNotExistException {

        TitleEntry titleEntry = service.findTitleEntryById(bookEntryDto.getTitleEntryId()).orElseThrow(
                ()-> new TitleEntryNotExistException("Title entry does not exist"));
        BookEntry bookEntry = bookEntryMapper.mapToBookEntry(bookEntryDto, titleEntry);
        service.saveBookEntry(bookEntry);
    }

//    @PutMapping(value = "updateStatus", consumes = MediaType.APPLICATION_JSON_VALUE)
//    public void updateStatus(@RequestBody BookEntryDto bookEntryDto) throws TitleEntryNotExistException {
//        BookEntry bookEntry = bookEntryMapper.mapToBookEntry(bookEntryDto);
//        BookEntry savedStatus = service.saveBookEntry(bookEntry);
//    }

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
    public void rentABook(@RequestBody BookRentalDto bookRentalDto) throws BookEntryNotExistException {
        service.rentBook(bookRentalDto);
    }

    @PutMapping(value = "returnBook", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void returnOfBook(@RequestBody ReturnBookDto returnBookDto) throws ReturnBookNotExistException {
        service.returnBook(returnBookDto);
    }
}
