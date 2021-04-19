package com.kodilla.kodillalibrary.service;

import com.kodilla.kodillalibrary.domain.*;
import com.kodilla.kodillalibrary.exception.BookEntryNotExistException;
import com.kodilla.kodillalibrary.exception.TitleEntryNotExistException;
import com.kodilla.kodillalibrary.repository.BookEntryRepository;
import com.kodilla.kodillalibrary.repository.BorrowingRepository;
import com.kodilla.kodillalibrary.repository.ReaderRepository;
import com.kodilla.kodillalibrary.repository.TitleEntryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest

@DisplayName("Library Service Test Suite")
public class LibraryServiceTestSuite {
    @Mock
    private ReaderRepository readerRepository;
    @Mock
    private BorrowingRepository borrowings;
    @Mock
    private TitleEntryRepository titleEntryRepository;
    @Mock
    private BookEntryRepository bookEntryRepository;
    @InjectMocks
    private DbService service;

    @Test
    void testSaveBookEntry() throws TitleEntryNotExistException {
        //Given
        List<BookEntry> bookEntries = new ArrayList<>();
        List<Borrowing> borrowedBooks = new ArrayList<>();
        TitleEntry titleEntry = new TitleEntry("Title", "Author",
                LocalDate.of(2021, 4, 22), bookEntries);
        BookEntry bookEntry = new BookEntry(titleEntry, Status.AVAILABLE, borrowedBooks);
        //When
        service.saveBookEntry(bookEntry);
        //Then
        verify(bookEntryRepository, times(1)).save(bookEntry);
    }

    @Test
    void testFindAllTitleEntries() {
        //Given
        List<BookEntry> bookEntries = new ArrayList<>();
        List<TitleEntry> allEntries = new ArrayList<>();
        TitleEntry titleEntry1 = new TitleEntry("title1", "author1", LocalDate.now(), bookEntries);
        TitleEntry titleEntry2 = new TitleEntry("title2", "author2", LocalDate.now(), bookEntries);
        allEntries.add(titleEntry1);
        allEntries.add(titleEntry2);
        //When
        when(service.findAllTitleEntries()).thenReturn(allEntries);
        allEntries = service.findAllTitleEntries();
        //Then
        assertEquals(2, allEntries.size());
    }


    @Test
    void testSaveReader() {
        //Given
        List<Borrowing> borrowedBooks = new ArrayList<>();
        Reader reader = new Reader("Ala", "Wilk",
                LocalDate.of(2018, 4, 23), borrowedBooks);
        //When
        service.saveReader(reader);
        //Then
        verify(readerRepository, times(1)).save(reader);
    }

    @Test
    void testSaveTitle() {
        //Given
        List<BookEntry> bookEntries = new ArrayList<>();
        TitleEntry titleEntry = new TitleEntry("test Title", "test Author",
                LocalDate.of(2010, 9, 11), bookEntries);
        //When
        service.saveTitle(titleEntry);
        //Then
        verify(titleEntryRepository, times(1)).save(titleEntry);
    }

    @Test
    void testSaveBorrowings() {
        //Given
        List<BookEntry> bookEntries = new ArrayList<>();
        List<Borrowing> borrowedBooks = new ArrayList<>();
        TitleEntry titleEntry = new TitleEntry("Title", "Author",
                LocalDate.of(2021, 4, 22), bookEntries);
        BookEntry bookEntry = new BookEntry(titleEntry, Status.AVAILABLE, borrowedBooks);
        Reader reader = new Reader("Ala", "Wilk",
                LocalDate.of(2018, 4, 23), borrowedBooks);
        Borrowing borrowing = new Borrowing(bookEntry, reader, LocalDate.of(2018, 5, 17), null);
        //When
        service.saveBorrowing(borrowing);
        //Then
        verify(borrowings, times(1)).save(borrowing);
    }

    @Test
    void testFindTitleEntryId() {
        //Given
        List<BookEntry> bookEntries = new ArrayList<>();
        TitleEntry titleEntry = new TitleEntry("Title", "Author",
                LocalDate.of(2021, 4, 22), bookEntries);
        Long titleEntryId = titleEntry.getId();
        //When
        service.findTitleEntryById(titleEntryId);
        //Then
        verify(titleEntryRepository, times(1)).findById(titleEntryId);
    }

    @Test
    void testGetNumberOfAvailableBooksByTitleEntry() {
        //Given
        List<BookEntry> bookEntries = new ArrayList<>();
        TitleEntryDto titleEntryDto = new TitleEntryDto(1L, "Title1", "Author",
                LocalDate.of(2010, 3, 2), 1L);
        TitleEntry titleEntry = new TitleEntry("Title1", "Author",
                LocalDate.of(2010, 3, 2), bookEntries);
        //When
        service.getNumberOfAvailableBooksByTitleEntry(titleEntry.getTitle(), titleEntry.getAuthor());
        //Then
        verify(titleEntryRepository, times(1)).findByTitleAndAuthor(titleEntry.getTitle(), titleEntry.getAuthor());
    }

    @Test
    void testRentBook() throws BookEntryNotExistException {
        //Given
        List<BookEntry> bookEntries = new ArrayList<>();
        BookRentalDto bookRentalDto = new BookRentalDto(1L, 1L);
        TitleEntry titleEntry = new TitleEntry("Title", "Author",
                LocalDate.of(2010, 11, 4), bookEntries);
        List<Borrowing> borrowings = new ArrayList<>();
        titleEntryRepository.save(titleEntry);
        Reader reader = new Reader("Anna", "Kowalska",
                LocalDate.of(2020, 11, 11), borrowings);
        BookEntry availableBookEntry = new BookEntry(titleEntry, Status.AVAILABLE, borrowings);
        when(bookEntryRepository.findByTitleEntryAndStatus(titleEntry, Status.AVAILABLE)).thenReturn(Arrays.asList(availableBookEntry));
        when(readerRepository.findById(bookRentalDto.getReaderId())).thenReturn(Optional.of(reader));
        when(titleEntryRepository.findById(bookRentalDto.getTitleId())).thenReturn(Optional.of(titleEntry));
        //When
        service.rentBook(bookRentalDto);
        //Then
        verify(bookEntryRepository, times(1)).findByTitleEntryAndStatus(titleEntry, Status.AVAILABLE);
        verify(readerRepository, times(1)).findById(bookRentalDto.getReaderId());
    }

    @Test
    void testReturnBook() {
        //Given
        List<Borrowing> borrowings = new ArrayList<>();
        List<BookEntry> bookEntries = new ArrayList<>();
        ReturnBookDto returnBookDto = new ReturnBookDto(1L, 1L);
        TitleEntry titleEntry = new TitleEntry("Title", "Author",
                LocalDate.of(2010, 11, 4), bookEntries);
//        BookEntry bookEntry = new BookEntry(titleEntry, Status.BORROWED, borrowings);
        BookEntry bookEntry = Mockito.mock(BookEntry.class);
        when(bookEntry.getTitleEntry()).thenReturn(titleEntry);
        when(bookEntry.getStatus()).thenReturn(Status.BORROWED);
        Reader reader = new Reader("Anna", "Kowalska",
                LocalDate.of(2020, 11, 11), borrowings);
        BookEntry borrowedBookEntry = new BookEntry(titleEntry, Status.BORROWED, borrowings);
      when(bookEntryRepository.findByTitleEntryAndStatus(titleEntry, Status.BORROWED)).thenReturn(Arrays.asList(borrowedBookEntry));
        when(bookEntryRepository.findById(returnBookDto.getBookEntryId())).thenReturn(Optional.of(bookEntry));
        when(readerRepository.findById(returnBookDto.getReaderId())).thenReturn(Optional.of(reader));
//        Borrowing borrowing = new Borrowing(bookEntry, reader, LocalDate.of(2020, 11, 11), null);
        Borrowing borrowing = Mockito.mock(Borrowing.class);
        when(borrowing.getBookEntry()).thenReturn(bookEntry);
        when(borrowing.getReader()).thenReturn(reader);
        when(borrowing.getRentalDate()).thenReturn(LocalDate.of(2020, 11, 11));
        when(borrowing.getReturnDate()).thenReturn(null);
        borrowings.add(borrowing);
        when(bookEntry.getBorrowings()).thenReturn(borrowings);
        //When
        service.returnBook(returnBookDto);
        //Then
        verify(borrowing, times(1)).setReturnDate(LocalDate.now());
        verify(bookEntry, times(1)).setStatus(Status.AVAILABLE);
        Assertions.assertEquals(1L, returnBookDto.getBookEntryId());
    }
}



