package com.kodilla.kodillalibrary.controller;

import com.kodilla.kodillalibrary.domain.*;
import com.kodilla.kodillalibrary.exception.BookNotExistException;
import com.kodilla.kodillalibrary.exception.TitleEntryNotExistException;
import com.kodilla.kodillalibrary.mapper.BookEntryMapper;
import com.kodilla.kodillalibrary.mapper.ReaderMapper;
import com.kodilla.kodillalibrary.mapper.TitleEntryMapper;
import com.kodilla.kodillalibrary.repository.BookEntryRepository;
import com.kodilla.kodillalibrary.repository.ReaderRepository;
import com.kodilla.kodillalibrary.repository.TitleEntryRepository;
import com.kodilla.kodillalibrary.service.DbService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DisplayName("Library Controller Test Suite")
public class LibraryControllerTestSuite {

    @MockBean
    private TitleEntryMapper titleEntryMapper;
    @MockBean
    private BookEntryMapper bookEntryMapper;
    @MockBean
    private TitleEntryRepository titleEntryRepository;
    @MockBean
    private BookEntryRepository bookEntryRepository;
    @MockBean
    private ReaderRepository readerRepository;
    @MockBean
    private ReaderMapper readerMapper;
    @MockBean
    private DbService service;
    @InjectMocks
    private LibraryController controller;

    @Test
    public void shouldFindAll() {
        //Given
        List<TitleEntryDto> entries = List.of(
                new TitleEntryDto(1L, "title1", "author1", LocalDate.now(), 3L),
                new TitleEntryDto(2L, "title2", "author2", LocalDate.now(), 3L)
        );
        when(titleEntryMapper.mapToTitleEntriesDtoList(anyList())).thenReturn(entries);

        //When
        List<TitleEntryDto> allEntries = controller.findAll();
        //Then
        Assertions.assertEquals(2, allEntries.size());
    }
    @Test
    void testCreateBookEntry() throws TitleEntryNotExistException {
        //Given
        List<BookEntry> bookEntries = new ArrayList<>();
        List<Borrowing> borrowedBooks = new ArrayList<>();
        TitleEntry titleEntry = new TitleEntry( "Title", "Author",
                LocalDate.of(2021, 4, 22), bookEntries);
        BookEntry bookEntry = new BookEntry(titleEntry, Status.AVAILABLE, borrowedBooks);

        //When
        controller.createBookEntry(bookEntryMapper.mapToBookEntryDto(bookEntry));
        //Then
        verify(service, times(1)).saveBookEntry(bookEntry);
    }
    @Test
    void testCreateReader() {
        //Given
        List<Borrowing> borrowedBooks = new ArrayList<>();
        Reader reader = new Reader("Ala", "Wilk",
                LocalDate.of(2018, 4, 23), borrowedBooks);
        ReaderDto readerDto = new ReaderDto(1L,"Ala", "Wilk",
                LocalDate.of(2018, 4, 23));
        when(readerMapper.mapToReader(readerDto)).thenReturn(reader);
        //When
        controller.createReader(readerMapper.mapToReaderDto(reader));
        //Then
        verify(service, times(1)).saveReader(reader);
    }

    @Test
    void testCreateTitle() {

        //Given
        List<BookEntry> bookEntries = new ArrayList<>();
        TitleEntry titleEntry = new TitleEntry(2L, "test Title", "test Author",
                LocalDate.of(2010, 9, 11), bookEntries);
        TitleEntryDto titleEntryDto = new TitleEntryDto(2L, "test Title", "test Author",
                LocalDate.of(2010, 9, 11), 11L);
        when(titleEntryMapper.mapToTitleEntry(ArgumentMatchers.any(TitleEntryDto.class))).thenReturn(titleEntry);
        when(titleEntryMapper.mapToTitleEntryDto(ArgumentMatchers.any(TitleEntry.class))).thenReturn(titleEntryDto);
        when(titleEntryRepository.save(titleEntry)).thenReturn(titleEntry);
        //When
        controller.createTitle(titleEntryMapper.mapToTitleEntryDto(titleEntry));
        //Then
        verify(service, times(1)).saveTitle(titleEntry);
    }

    //        @Disabled
    @Test
    void testUpdateStatus() {
        //Given
        List<BookEntry> bookEntries = new ArrayList<>();
        List<Borrowing> borrowedBooks = new ArrayList<>();
        TitleEntry titleEntry = new TitleEntry(1L, "Title", "Author",
                LocalDate.of(2010, 3, 21), bookEntries);
        BookEntry bookEntry = new BookEntry(titleEntry, Status.AVAILABLE, borrowedBooks);
        //When
        BookEntry savedStatus = bookEntryRepository.save(bookEntry);
        //Then
        Assertions.assertEquals(savedStatus, Status.AVAILABLE);
    }

    @Test
    void testHowManyBookEntriesAreAvailable() {
        //Given
        TitleEntryDto titleEntryDto = new TitleEntryDto(1L, "Title1", "Author",
                LocalDate.of(2010, 3, 2), 1L);
        LibraryController controller = new LibraryController(service, bookEntryMapper, readerMapper, titleEntryMapper);
        //When
        controller.howManyBookEntriesAreAvailable(titleEntryDto.getTitle(), titleEntryDto.getAuthor());
        //Then
        verify(service, times(1)).findTitleEntryById(titleEntryDto.getId());
    }

    @Test
    void testBookRental() throws BookNotExistException {
        //Given
        List<BookEntry> bookEntries = new ArrayList<>();
        BookRentalDto bookRentalDto = new BookRentalDto(1L,1L);
        TitleEntry titleEntry = new TitleEntry(111L,
                "Title", "Author", LocalDate.of(2010,11,4),bookEntries);
        List<Borrowing> borrowings = new ArrayList<>();
        titleEntryRepository.save(titleEntry);
        Reader reader = new Reader("Anna", "Kowalska", LocalDate.of(2020,11,11), borrowings);
        BookEntry availableBookEntry = new BookEntry(titleEntry,Status.AVAILABLE, borrowings);
        when(bookEntryRepository.findByTitleEntryAndStatus(titleEntry,Status.AVAILABLE)).thenReturn(Arrays.asList(availableBookEntry));
        when(readerRepository.findById(bookRentalDto.getReaderId())).thenReturn(Optional.of(reader));
        //When
        controller.rentABook(bookRentalDto);

        //Then
        verify(service, times(1)).saveReader(reader);

        //clean up
        titleEntryRepository.deleteById(111L);

    }

    @Test
    void testReturnOfBook() {
        //Given

        //When

        //Then
    }

}
