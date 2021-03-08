package com.kodilla.kodillalibrary.controller;

import com.kodilla.kodillalibrary.domain.*;
import com.kodilla.kodillalibrary.mapper.BookEntryMapper;
import com.kodilla.kodillalibrary.mapper.ReaderMapper;
import com.kodilla.kodillalibrary.mapper.TitleMapper;
import com.kodilla.kodillalibrary.service.DbService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/v1/library")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class LibraryController {
    private final DbService service;
    private final BookEntryMapper bookEntryMapper;
    private final ReaderMapper readerMapper;
    private final TitleMapper titleMapper;

    @PostMapping(value = "createReader", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void createReader(@RequestBody ReaderDto readerDto){
        Reader reader = readerMapper.mapToReader(readerDto);
        service.saveReader(reader);
    }
    @PostMapping(value = "createTitle", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void createTitle(@RequestBody TitleDto titleDto){
        Title title = titleMapper.mapToTitle(titleDto);
        service.saveTitle(title);
    }
    @PostMapping(value = "createBookEntry", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void createBookEntry(@RequestBody BookEntryDto bookEntryDto){
        BookEntry bookEntry = bookEntryMapper.mapToBookEntry(bookEntryDto);
        service.saveBookEntry(bookEntry);
    }
    @PutMapping(value = "updateStatus")
    public void findBookEntryByStatus(@RequestParam String status, Long id){
       service.setBookEntryStatus(status, id);
    }
//    @PutMapping(value = "updateStatus", consumes = MediaType.APPLICATION_JSON_VALUE)
//    public BookEntryDto updateStatus(@RequestBody BookEntryDto bookEntryDto){
//        BookEntry bookEntry = bookEntryMapper.mapToBookEntry(bookEntryDto);
//        BookEntry savedStatus = service.saveBookEntry(bookEntry);
//        return bookEntryMapper.mapToBookEntryDto(savedStatus);
//    }

    @GetMapping(value = "howManyBookEntriesAreAvailable")
    public Long howManyBookEntriesAreAvailable(@RequestParam String title){
        return service.getNumberOfAvailableBooksByTitle(title);
    }
    @PutMapping(value = "bookRental")
    public void rentABook(@RequestParam String title){
        service.findAvailableBooksToBeBorrowedByTitle(title);
    }
    @PutMapping(value = "returnOfBook")
    public void returnOfBook(@RequestParam Long id){
        service.findBorrowedBooksById(id);
    }
}
