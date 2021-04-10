package com.kodilla.kodillalibrary.domain;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

//zwrot książki mając readerId i bookEntryId

@NamedNativeQuery(
        name = "BorrowedBooks.returnBook",
        query = "UPDATE BOOK_ENTRIES " +
                "SET STATUS = Status.AVAILABLE " +
                "WHERE READERS.READER_ID = :readerId AND BOOK_ENTRY_ID = :bookEntryId",
        resultClass = Borrowing.class
)
@NamedNativeQuery(
        name = "BorrowedBooks.bookRental",
        query = "UPDATE BOOK_ENTRIES " +
                "SET STATUS = Status.BORROWED " +
                "WHERE READERS.READER_ID = :readerId AND BOOK_ENTRIES.TITLE_ID = :titleId",
        resultClass = Borrowing.class
)
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "BORROWINGS")
public class Borrowing {
    @Id
    @GeneratedValue
    @NotNull
    @Column(name = "BORROWING_ID", unique = true)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "BORROWED_BOOK")
    private BookEntry bookEntry;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "READER_ID")
    private Reader reader;

    @Column(name = "RENTAR_DATE")
    private LocalDate rentalDate;

    @Column(name = "RETURN_DATE")
    private LocalDate returnDate;

    public Borrowing(BookEntry bookEntry, Reader reader, LocalDate rentalDate, LocalDate returnDate) {
        this.bookEntry = bookEntry;
        this.reader = reader;
        this.rentalDate = rentalDate;
        this.returnDate = returnDate;
    }
}
