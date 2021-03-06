package com.kodilla.kodillalibrary.controller;

import com.kodilla.kodillalibrary.domain.*;
import com.kodilla.kodillalibrary.exception.BookEntryNotExistException;
import com.kodilla.kodillalibrary.exception.ReturnBookNotExistException;
import com.kodilla.kodillalibrary.exception.TitleEntryNotExistException;
import com.kodilla.kodillalibrary.mapper.BookEntryMapper;
import com.kodilla.kodillalibrary.mapper.ReaderMapper;
import com.kodilla.kodillalibrary.mapper.TitleEntryMapper;
import com.kodilla.kodillalibrary.service.DbService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DisplayName("Library Controller Test Suite")
public class LibraryControllerTestSuite {

    @Mock
    private TitleEntryMapper titleEntryMapper;
    @Mock
    private BookEntryMapper bookEntryMapper;
    @Mock
    private ReaderMapper readerMapper;
    @Mock
    private DbService service;
    @InjectMocks
    private LibraryController controller;

    @Test
    public void shouldFindAll() {
        //Given
        List<TitleEntryDto> entries = List.of(
                new TitleEntryDto(1L,"title1", "author1", LocalDate.now(), 3L),
                new TitleEntryDto(2L, "title2", "author2", LocalDate.now(), 3L)
        );
        when(titleEntryMapper.mapToTitleEntriesDtoList(anyList())).thenReturn(entries);
        //When
        List<TitleEntryDto> allEntries = controller.findAll();
        //Then
        Assertions.assertEquals(2, allEntries.size());
    }

    @Test
    void testCreateBookEntry() throws BookEntryNotExistException, TitleEntryNotExistException {
        //Given
        List<BookEntry> bookEntries = new ArrayList<>();
        List<Borrowing> borrowedBooks = new ArrayList<>();
        TitleEntry titleEntry = new TitleEntry("Title", "Author",
                LocalDate.of(2021, 4, 22), bookEntries);
        BookEntry bookEntry = new BookEntry(titleEntry, Status.AVAILABLE, borrowedBooks);
        BookEntryDto bookEntryDto = new BookEntryDto(bookEntry.getTitleEntry().getId(), Status.AVAILABLE);
        when(bookEntryMapper.mapToBookEntry(ArgumentMatchers.any(BookEntryDto.class), eq(titleEntry))).thenReturn(bookEntry);
        when(bookEntryMapper.mapToBookEntryDto(ArgumentMatchers.any(BookEntry.class))).thenReturn(bookEntryDto);
        service.saveTitle(titleEntry);
        when(service.findTitleEntryById(titleEntry.getId())).thenReturn(Optional.of(titleEntry));
        //When
        controller.createBookEntry(bookEntryDto);
        //Then
        verify(service, times(1)).saveBookEntry(bookEntry);
    }

    @Test
    void testCreateReader() {
        //Given
        Reader reader = Mockito.mock(Reader.class);
        ReaderDto readerDto = Mockito.mock(ReaderDto.class);
        when(readerMapper.mapToReader(ArgumentMatchers.any(ReaderDto.class))).thenReturn(reader);
        when(readerMapper.mapToReaderDto(ArgumentMatchers.any(Reader.class))).thenReturn(readerDto);
        //When
        controller.createReader(readerMapper.mapToReaderDto(reader));
        //Then
        verify(service, times(1)).saveReader(reader);
    }

    @Test
    void testCreateTitle() {

        //Given
        List<BookEntry> bookEntries = new ArrayList<>();
        TitleEntry titleEntry = new TitleEntry("test Title", "test Author",
                LocalDate.of(2010, 9, 11), bookEntries);
        TitleEntryDto titleEntryDto = new TitleEntryDto(1L, "test Title", "test Author",
                LocalDate.of(2010, 9, 11), 11L);
        when(titleEntryMapper.mapToTitleEntry(ArgumentMatchers.any(TitleEntryDto.class))).thenReturn(titleEntry);
        when(titleEntryMapper.mapToTitleEntryDto(ArgumentMatchers.any(TitleEntry.class))).thenReturn(titleEntryDto);
        //When
        controller.createTitle(titleEntryMapper.mapToTitleEntryDto(titleEntry));
        //Then
        verify(service, times(1)).saveTitle(titleEntry);
    }

    @Test
    void testHowManyBookEntriesAreAvailable() {
        //Given
        List<BookEntry> bookEntries = new ArrayList<>();
        TitleEntry titleEntry = new TitleEntry("Title1", "Author",
                LocalDate.of(2010, 3, 2), bookEntries);
        TitleEntryDto titleEntryDto = new TitleEntryDto(1L, "Title1", "Author",
                LocalDate.of(2010, 3, 2), 1L);
        when(titleEntryMapper.mapToTitleEntry(ArgumentMatchers.any(TitleEntryDto.class))).thenReturn(titleEntry);
        when(titleEntryMapper.mapToTitleEntryDto(ArgumentMatchers.any(TitleEntry.class))).thenReturn(titleEntryDto);
        //When
        controller.howManyBookEntriesAreAvailable(titleEntryDto.getTitle(), titleEntryDto.getAuthor());
        //Then
        verify(service, times(1)).getNumberOfAvailableBooksByTitleEntry(titleEntryDto.getTitle(), titleEntryDto.getAuthor());
    }

    @Test
    void testBookRental() throws BookEntryNotExistException {
        //Given
        BookRentalDto bookRentalDto = new BookRentalDto(1L, 1L);
        //When
        controller.rentABook(bookRentalDto);
        //Then
        verify(service, times(1)).rentBook(bookRentalDto);
    }

    @Test
    void testReturnOfBook() throws ReturnBookNotExistException {
        //Given
        ReturnBookDto returnBookDto = new ReturnBookDto(1L, 1L);
        //When
        controller.returnOfBook(returnBookDto);
        //Then
        verify(service, times(1)).returnBook(returnBookDto);
    }
}
