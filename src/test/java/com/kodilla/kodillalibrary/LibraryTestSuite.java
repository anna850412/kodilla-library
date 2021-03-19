package com.kodilla.kodillalibrary;

import com.kodilla.kodillalibrary.domain.*;
import com.kodilla.kodillalibrary.repository.BookEntryRepository;
import com.kodilla.kodillalibrary.repository.BorrowedBooksRepository;
import com.kodilla.kodillalibrary.repository.ReaderRepository;
import com.kodilla.kodillalibrary.repository.TitleRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class LibraryTestSuite {
    @Autowired
    private ReaderRepository readerRepository;
    @Autowired
    private BorrowedBooksRepository borrowedBooksRepository;
    @Autowired
    private TitleRepository titleRepository;
    @Autowired
    private BookEntryRepository bookEntryRepository;

    @Test
    void testReaderRepositorySaveWithReader(){
        //Given
        List<BookEntry> bookEntries = new ArrayList<>();
        List<BorrowedBooks> borrowedBooks = new ArrayList<>();
        Reader reader1 = new Reader(1L,"Anna","Kowalska",
                LocalDate.of(2019,03,12), borrowedBooks);
        borrowedBooks.add(new BorrowedBooks(bookEntries, reader1,
                LocalDate.of(2021,01,15), LocalDate.of(2021,03,05)));
        Reader reader2 = new Reader(2L, "Piotr", "Nowak",
                LocalDate.of(2018, 06, 23),borrowedBooks);
        borrowedBooks.add(new BorrowedBooks(bookEntries, reader2, LocalDate.of(2020,07,27),
                LocalDate.of(2020,11,11)));
        BorrowedBooks borrowedBooks1 = new BorrowedBooks(bookEntries, reader1,
                LocalDate.of(2021, 03, 10), null);
        BorrowedBooks borrowedBooks2 = new BorrowedBooks(bookEntries, reader2,
                LocalDate.of(2021, 02, 10), LocalDate.of(2021, 03, 11));
        borrowedBooks.add(borrowedBooks1);
        borrowedBooks.add(borrowedBooks2);
//        reader1.setBorrowedBooks((List<BorrowedBooks>)borrowedBooks1);
//        reader2.setBorrowedBooks(borrowedBooks2);
        reader1.getBorrowedBooks().add(borrowedBooks1);
        reader2.getBorrowedBooks().add(borrowedBooks2);
        //When
//        readerRepository.save(reader1);
//        Long id1 = reader1.getId();
        readerRepository.save(reader2);
        Long id2 = reader2.getId();

        //Then
//        Assertions.assertNotEquals(0, id1);
        Assertions.assertNotEquals(0, id2);

        //Clean up
//        readerRepository.deleteById(id1);
        readerRepository.deleteById(id2);
    }

    @Test
    void testTitleRepositorySaveWithTitle(){
        //Given
        Title title1 = new Title("Title1", "Author1",
                LocalDate.of(2016,06,21));
        Title title2 = new Title("Title2", "Author2",
                LocalDate.of(2011,12,11));
        BookEntry bookEntry1 = new BookEntry(title1, Status.BORROWED);
        BookEntry bookEntry2 = new BookEntry(title2, Status.RESERVED);
        bookEntry1.setTitle(title1);
        bookEntry2.setTitle(title2);
        title1.getBookEntries().add(bookEntry1);
        title2.getBookEntries().add(bookEntry2);

        //When
        titleRepository.save(title1);
        Long id1 = title1.getId();
        titleRepository.save(title2);
        Long id2 = title2.getId();

        //Then
        Assertions.assertNotEquals(0, id1);
        Assertions.assertNotEquals(0, id2);
        //Clean up
        titleRepository.deleteById(id1);
        titleRepository.deleteById(id2);
    }

    @Test
    void testBookEntryRepositorySaveWithBookEntry(){
        //Given
        Title title1 = new Title("Title1", "Author1",
                LocalDate.of(2016,06,21));
        BookEntry bookEntry1 = new BookEntry(title1, Status.BORROWED);
        bookEntry1.setTitle(title1);
        title1.getBookEntries().add(bookEntry1);

        //When
        bookEntryRepository.save(bookEntry1);
        Long id1 = bookEntry1.getId();

        //Then
        Assertions.assertNotEquals(0, id1);

        //Clean up
        bookEntryRepository.deleteById(id1);

    }

    @Test
    void testTitleRepositoryFindById(){
        //Given
        Title title1 = new Title("Title1", "Author1",
                LocalDate.of(2016,06,21));

        //When
        titleRepository.save(title1);

        //Then
        Long id1 = title1.getId();
        Optional<Title> savedTitle1 = titleRepository.findById(id1);
        Assertions.assertTrue(savedTitle1.isPresent());

        //Clean up
        titleRepository.deleteById(id1);
    }
    @Test
    void testBookEntryRepositoryFindById(){
        //Given
        Title title = new Title("Title1", "Author1",
                LocalDate.of(2016,06,21));
        BookEntry bookEntry = new BookEntry(title, Status.BORROWED);
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
    void testTitleRepositoryFindAll(){
        //Given
        Title title1 = new Title("Title1", "Author1",
                LocalDate.of(2016,06,21));
        //When
        titleRepository.save(title1);

        //Then
        Long id = title1.getId();
        Optional<Title> savedTitle = titleRepository.findById(id);
        Assertions.assertTrue(savedTitle.isPresent());
        //Clean up
        titleRepository.deleteById(id);
    }

    @Test
    void testBorrowedBooksRepositorySaveWithBorrowedBooks(){
        //Given
        Title title1 = new Title("Title1", "Author1",
                LocalDate.of(2021, 01, 04));
        Title title2 = new Title("Title2", "Author2",
                LocalDate.of(2017, 12, 14));
        List<BookEntry> bookEntries = new ArrayList<>();
        BookEntry bookEntry1 = new BookEntry(title1, Status.RESERVED);
        BookEntry bookEntry2 = new BookEntry(title2, Status.AVAILABLE);
        bookEntries.add(bookEntry1);
        bookEntries.add(bookEntry2);
        List<BorrowedBooks> borrowedBooks = new ArrayList<>();
        Reader reader1 = new Reader(1L,"Anna","Kowalska",
                LocalDate.of(2019,03,12), borrowedBooks);
        Reader reader2 = new Reader(2L, "Piotr", "Nowak",
                LocalDate.of(2018, 06, 23),borrowedBooks);
        BorrowedBooks borrowedBook1 = new BorrowedBooks(bookEntries, reader1,
                LocalDate.of(2021,01,15), LocalDate.of(2021,03,05));
        BorrowedBooks borrowedBook2 = new BorrowedBooks(bookEntries, reader2, LocalDate.of(2020,07,27),
                LocalDate.of(2020,11,11));
        borrowedBooks.add(borrowedBook1);
        borrowedBooks.add(borrowedBook2);
//        borrowedBook1.setBookEntries((List<BookEntry>) bookEntry1);
//        borrowedBook2.setBookEntries((List<BookEntry>) bookEntry2);
//        borrowedBook1.getBookEntries().add(bookEntry1);
//        borrowedBook2.getBookEntries().add(bookEntry2);

        //When
        borrowedBooksRepository.save(borrowedBook1);
        Long id1 = borrowedBook1.getId();
        borrowedBooksRepository.save(borrowedBook2);
        Long id2 = borrowedBook2.getId();

        //Then
        Assertions.assertNotEquals(0, id1);
        Assertions.assertNotEquals(0, id2);

        //Clean up
        borrowedBooksRepository.deleteById(id1);
        borrowedBooksRepository.deleteById(id2);
    }
    @Test
    void testBookEntryRepositoryFindByTitleAndStatus(){
        //Given
        Title title1 = new Title("Title1", "Author1",
                LocalDate.of(2016,06,21));
        BookEntry bookEntry = new BookEntry(title1, Status.BORROWED);
        bookEntryRepository.save(bookEntry);
        Status status = bookEntry.getStatus();
        Optional<Title> title = Optional.ofNullable(bookEntry.getTitle());
        //When
        List<BookEntry> savedTitleAndStatus = bookEntryRepository.findByTitleAndStatus(title, status);

        //Then
        Assertions.assertEquals(1, savedTitleAndStatus.size());
        //Clean up
        Long id = savedTitleAndStatus.get(0).getId();
        bookEntryRepository.deleteById(id);
    }
    @Test
    void contextLoads() {

    }
}
