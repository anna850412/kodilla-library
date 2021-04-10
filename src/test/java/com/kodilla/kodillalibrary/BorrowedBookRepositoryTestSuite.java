package com.kodilla.kodillalibrary;

import com.kodilla.kodillalibrary.domain.*;
import com.kodilla.kodillalibrary.repository.BorrowedBooksRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class BorrowedBookRepositoryTestSuite {
    @Autowired
    private BorrowedBooksRepository borrowedBooksRepository;

    @Test
    void testBorrowedBookRepositoryWithNamedNativeQuery(){
        //Given
        Long bookEntryId1 = 1L;
        Long bookEntryId2 = 2L;
         List<BookEntry> bookEntries = new ArrayList<>();
        List<BorrowedBooks> borrowedBooks = new ArrayList<>();
        TitleEntry title1 = new TitleEntry(  1L,bookEntryId1, "Title1", "Author1",
                LocalDate.of(2021, 01, 04), bookEntries);
        TitleEntry title2 = new TitleEntry(2L, bookEntryId2,"Title2", "Author2",
                LocalDate.of(2017, 12, 14), bookEntries);
        BookEntry bookEntry1 = new BookEntry(1L,title1, Status.RESERVED, borrowedBooks);
        BookEntry bookEntry2 = new BookEntry(2L, title2, Status.AVAILABLE, borrowedBooks);
        bookEntries.add(bookEntry1);
        bookEntries.add(bookEntry2);

        Reader reader1 = new Reader(1L, "Anna", "Kowalska",
                LocalDate.of(2019, 03, 12), borrowedBooks);
        Reader reader2 = new Reader(2L, "Piotr", "Nowak",
                LocalDate.of(2018, 06, 23), borrowedBooks);
        BorrowedBooks borrowedBook1 = new BorrowedBooks(1L,bookEntries, reader1,
                LocalDate.of(2021, 01, 15), LocalDate.of(2021, 03, 05));
        BorrowedBooks borrowedBook2 = new BorrowedBooks(2L,bookEntries, reader2, LocalDate.of(2020, 07, 27),
                LocalDate.of(2020, 11, 11));

        borrowedBook1.setBookEntries((List<BookEntry>) bookEntry1);
        borrowedBook2.setBookEntries((List<BookEntry>) bookEntry2);
        borrowedBook1.getBookEntries().add(bookEntry1);
        borrowedBook2.getBookEntries().add(bookEntry2);
        borrowedBooksRepository.save(borrowedBook1);
        Long id1 = borrowedBook1.getId();
        borrowedBooksRepository.save(borrowedBook2);
        Long id2 = borrowedBook2.getId();
        //When
//         void returnOfBook1 = borrowedBooksRepository.returnBook(reader1.getId(), bookEntryId1);
//         void returnOfBook2 = borrowedBooksRepository.returnBook(reader2.getId(), bookEntryId2);

        //Then
        Assertions.assertNotEquals(0, id1);
        Assertions.assertNotEquals(0, id2);

        //Clean up
        borrowedBooksRepository.deleteById(id1);
        borrowedBooksRepository.deleteById(id2);
    }
    }

