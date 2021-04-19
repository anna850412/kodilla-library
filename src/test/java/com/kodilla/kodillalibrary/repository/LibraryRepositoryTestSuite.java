package com.kodilla.kodillalibrary.repository;

import com.kodilla.kodillalibrary.domain.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@DisplayName("Repository Test Suites")
public class LibraryRepositoryTestSuite {
    @Autowired
    private BorrowingRepository borrowings;
    @Autowired
    private  TitleEntryRepository titleEntryRepository;
    @Autowired
    private BookEntryRepository bookEntryRepository;
    @Autowired
    private ReaderRepository readerRepository;

    @Test
    void testBorrowingRepositorySaveWithBorrowedBooks() {
        //Given
        List<BookEntry> bookEntries = new ArrayList<>();
        List<Borrowing> borrowedBooks = new ArrayList<>();
        TitleEntry title1 = new TitleEntry( "Title1", "Author1",
                LocalDate.of(2021, 1, 4), bookEntries);
        TitleEntry title2 = new TitleEntry( "Title2", "Author2",
                LocalDate.of(2017, 12, 14), bookEntries);
        BookEntry bookEntry1 = new BookEntry(title1, Status.RESERVED, borrowedBooks);
        BookEntry bookEntry2 = new BookEntry(title2, Status.AVAILABLE, borrowedBooks);
        bookEntries.add(bookEntry1);
        bookEntries.add(bookEntry2);
        Reader reader1 = new Reader( "Anna", "Kowalska",
                LocalDate.of(2019, 3, 12), borrowedBooks);
        Reader reader2 = new Reader( "Piotr", "Nowak",
                LocalDate.of(2018, 6, 23), borrowedBooks);
        Borrowing borrowedBook1 = new Borrowing(bookEntry1, reader1,
                LocalDate.of(2021, 1, 15), LocalDate.of(2021, 3, 5));
        Borrowing borrowedBook2 = new Borrowing(bookEntry2, reader2, LocalDate.of(2020, 7, 27),
                LocalDate.of(2020, 11, 11));
        borrowedBook1.setBookEntry(bookEntry1);
        borrowedBook2.setBookEntry(bookEntry2);
        borrowings.save(borrowedBook1);
        Long id1 = borrowedBook1.getId();
        borrowings.save(borrowedBook2);
        Long id2 = borrowedBook2.getId();
        //When
        //Then
        Assertions.assertNotEquals(0, id1);
        Assertions.assertNotEquals(0, id2);
        //Clean up
        borrowings.deleteById(id1);
        borrowings.deleteById(id2);
    }
    @Test
    void testReaderRepositorySaveWithReader() {
        //Given
        List<Borrowing> borrowedBooks = new ArrayList<>();
        Reader reader1 = new Reader("Anna", "Kowalska",
                LocalDate.of(2019, 3, 12), borrowedBooks);
        Reader reader2 = new Reader("Piotr", "Nowak",
                LocalDate.of(2018, 6, 23), borrowedBooks);
        //When
        readerRepository.save(reader1);
        Long id1 = reader1.getId();
        readerRepository.save(reader2);
        Long id2 = reader2.getId();
        //Then
        Assertions.assertNotEquals(0, id1);
        Assertions.assertNotEquals(0, id2);
        //Clean up
        readerRepository.deleteById(id1);
        readerRepository.deleteById(id2);
    }

    @Test
    void testTitleRepositorySaveWithTitle() {
        //Given
        List<Borrowing> borrowedBooks = new ArrayList<>();
        List<BookEntry> bookEntries = new ArrayList<>();
        TitleEntry title1 = new TitleEntry( "Title1", "Author1",
                LocalDate.of(2016, 6, 21), bookEntries);
        TitleEntry title2 = new TitleEntry( "Title2", "Author2",
                LocalDate.of(2011, 12, 11), bookEntries);
        BookEntry bookEntry1 = new BookEntry(title1, Status.BORROWED, borrowedBooks);
        BookEntry bookEntry2 = new BookEntry(title2, Status.RESERVED, borrowedBooks);
        bookEntry1.setTitleEntry(title1);
        bookEntry2.setTitleEntry(title2);
        //When
        titleEntryRepository.save(title1);
        Long id1 = title1.getId();
        titleEntryRepository.save(title2);
        Long id2 = title2.getId();
        //Then
        Assertions.assertNotEquals(0, id1);
        Assertions.assertNotEquals(0, id2);
        //Clean up
        titleEntryRepository.deleteById(id1);
        titleEntryRepository.deleteById(id2);
    }

    @Test
    void testBookEntryRepositorySaveWithBookEntry() {
        //Given
        List<BookEntry> bookEntries = new ArrayList<>();
        List<Borrowing> borrowedBooks = new ArrayList<>();
        TitleEntry title1 = new TitleEntry( "Title1", "Author1",
                LocalDate.of(2016, 6, 21), bookEntries);
        BookEntry bookEntry1 = new BookEntry(title1, Status.BORROWED, borrowedBooks);
        bookEntries.add(bookEntry1);
        bookEntry1.setTitleEntry(title1);
        //When
        bookEntryRepository.save(bookEntry1);
        Long id1 = bookEntry1.getId();
        //Then
        Assertions.assertNotEquals(0, id1);
        //Clean up
        bookEntryRepository.deleteById(id1);

    }

    @Test
    void testTitleRepositoryFindById() {
        //Given
        List<BookEntry> bookEntries = new ArrayList<>();
        TitleEntry title1 = new TitleEntry("Title1", "Author1",
                LocalDate.of(2016, 6, 21), bookEntries);
        //When
        titleEntryRepository.save(title1);
        //Then
        Long id1 = title1.getId();
        Optional<TitleEntry> savedTitle1 = titleEntryRepository.findById(id1);
        Assertions.assertTrue(savedTitle1.isPresent());
        //Clean up
        titleEntryRepository.deleteById(id1);
    }

    @Test
    void testBookEntryRepositoryFindById() {
        //Given
        List<BookEntry> bookEntries = new ArrayList<>();
        List<Borrowing> borrowedBooks = new ArrayList<>();
        TitleEntry titleEntry = new TitleEntry("Title1", "Author1",
                LocalDate.of(2016, 6, 21), bookEntries);
        BookEntry bookEntry = new BookEntry(titleEntry, Status.BORROWED, borrowedBooks);
        //When
        bookEntryRepository.save(bookEntry);
        //Then
        Long id = bookEntry.getId();
        Optional<BookEntry> savedBookEntry = bookEntryRepository.findById(id);
        Assertions.assertTrue(savedBookEntry.isPresent());
        //Clean up
        bookEntryRepository.deleteById(id);
    }

    @Test
    void testTitleRepositoryFindAll() {
        //Given
        List<BookEntry> bookEntries = new ArrayList<>();
        TitleEntry title1 = new TitleEntry("Title1", "Author1",
                LocalDate.of(2016, 6, 21), bookEntries);
        //When
        titleEntryRepository.save(title1);
        //Then
        Long id = title1.getId();
        Optional<TitleEntry> savedTitle = titleEntryRepository.findById(id);
        Assertions.assertTrue(savedTitle.isPresent());
        //Clean up
        titleEntryRepository.deleteById(id);
    }

    @Test
    void testBorrowedBooksRepositorySaveWithBorrowedBooks() {
        //Given
        List<BookEntry> bookEntries = new ArrayList<>();
        List<Borrowing> borrowedBooks = new ArrayList<>();
        TitleEntry title1 = new TitleEntry("Title1", "Author1",
                LocalDate.of(2021, 1, 4), bookEntries);
        TitleEntry title2 = new TitleEntry("Title2", "Author2",
                LocalDate.of(2017, 12, 14), bookEntries);
        BookEntry bookEntry1 = new BookEntry(title1, Status.RESERVED, borrowedBooks);
        BookEntry bookEntry2 = new BookEntry(title2, Status.AVAILABLE, borrowedBooks);
        bookEntries.add(bookEntry1);
        bookEntries.add(bookEntry2);
        Reader reader1 = new Reader("Anna", "Kowalska",
                LocalDate.of(2019, 3, 12), borrowedBooks);
        Reader reader2 = new Reader("Piotr", "Nowak",
                LocalDate.of(2018, 6, 23), borrowedBooks);
        Borrowing borrowedBook1 = new Borrowing(bookEntry1, reader1,
                LocalDate.of(2021, 1, 15), LocalDate.of(2021, 3, 5));
        Borrowing borrowedBook2 = new Borrowing(bookEntry2, reader2, LocalDate.of(2020, 7, 27),
                LocalDate.of(2020, 11, 11));
        borrowedBook1.setBookEntry(bookEntry1);
        borrowedBook2.setBookEntry(bookEntry2);
        //When
        borrowings.save(borrowedBook1);
        Long id1 = borrowedBook1.getId();
        borrowings.save(borrowedBook2);
        Long id2 = borrowedBook2.getId();
        //Then
        Assertions.assertNotEquals(0, id1);
        Assertions.assertNotEquals(0, id2);
        //Clean up
        borrowings.deleteById(id1);
        borrowings.deleteById(id2);
    }

    @Test
    void testBookEntryRepositoryFindByTitleAndStatus() {
        //Given
        List<BookEntry> bookEntries = new ArrayList<>();
        List<Borrowing> borrowedBooks = new ArrayList<>();
        TitleEntry titleEntry1 = new TitleEntry( "Title1", "Author1",
                LocalDate.of(2016, 6, 21), bookEntries);
        BookEntry bookEntry = new BookEntry(titleEntry1, Status.BORROWED, borrowedBooks);
        bookEntryRepository.save(bookEntry);
        Status status = bookEntry.getStatus();
        Optional<TitleEntry> title = Optional.ofNullable(bookEntry.getTitleEntry());
        //When
        List<BookEntry> savedTitleAndStatus = bookEntryRepository.findByTitleEntryAndStatus(title.get(), status);
        //Then
        Assertions.assertEquals(1, savedTitleAndStatus.size());
        //Clean up
        Long id = savedTitleAndStatus.get(0).getId();
        bookEntryRepository.deleteById(id);
    }

}


