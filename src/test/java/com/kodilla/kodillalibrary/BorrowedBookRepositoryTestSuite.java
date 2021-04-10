package com.kodilla.kodillalibrary;

import com.kodilla.kodillalibrary.domain.*;
import com.kodilla.kodillalibrary.repository.Borrowings;
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
    private Borrowings borrowings;

    @Test
    void testBorrowedBookRepositoryWithNamedNativeQuery() {
        //Given
        Long bookEntryId1 = 1L;
        Long bookEntryId2 = 2L;
        List<BookEntry> bookEntries = new ArrayList<>();
        List<Borrowing> borrowedBooks = new ArrayList<>();
        TitleEntry title1 = new TitleEntry(bookEntryId1, "Title1", "Author1",
                LocalDate.of(2021, 01, 04), bookEntries);
        TitleEntry title2 = new TitleEntry(bookEntryId2, "Title2", "Author2",
                LocalDate.of(2017, 12, 14), bookEntries);
        BookEntry bookEntry1 = new BookEntry(title1, Status.RESERVED, borrowedBooks);
        BookEntry bookEntry2 = new BookEntry(title2, Status.AVAILABLE, borrowedBooks);
        bookEntries.add(bookEntry1);
        bookEntries.add(bookEntry2);

        Reader reader1 = new Reader( "Anna", "Kowalska",
                LocalDate.of(2019, 03, 12), borrowedBooks);
        Reader reader2 = new Reader( "Piotr", "Nowak",
                LocalDate.of(2018, 06, 23), borrowedBooks);
        Borrowing borrowedBook1 = new Borrowing(bookEntry1, reader1,
                LocalDate.of(2021, 01, 15), LocalDate.of(2021, 03, 05));
        Borrowing borrowedBook2 = new Borrowing(bookEntry2, reader2, LocalDate.of(2020, 07, 27),
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
}

