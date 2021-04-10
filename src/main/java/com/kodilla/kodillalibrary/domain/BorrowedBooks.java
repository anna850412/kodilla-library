package com.kodilla.kodillalibrary.domain;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

//zwrot książki mając readerId i bookEntryId

@NamedNativeQuery(
        name = "BorrowedBooks.returnBook",
        query = "UPDATE BOOK_ENTRIES " +
                "SET STATUS = Status.AVAILABLE " +
                "WHERE READERS.READER_ID = :readerId AND BOOK_ENTRY_ID = :bookEntryId",
        resultClass = BorrowedBooks.class
)
@NamedNativeQuery(
        name = "BorrowedBooks.bookRental",
        query = "UPDATE BOOK_ENTRIES " +
                "SET STATUS = Status.BORROWED " +
                "WHERE READERS.READER_ID = :readerId AND BOOK_ENTRIES.TITLE_ID = :titleId",
        resultClass = BorrowedBooks.class
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "BORROWED_BOOKS")
public class BorrowedBooks {
    @Id
    @GeneratedValue
    @NotNull
    @Column(name = "BORROWED_BOOKS_ID", unique = true)
    private Long id;
//    @Column(name = "BOOK_ENTRIES_ID")
//    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "borrowedBooks", fetch = FetchType.LAZY)
    @ManyToOne
    @JoinColumn(name ="borrowedBooks")
    private BookEntry bookEntry;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "READER_ID")
    private Reader reader;
    @Column(name = "DATE_OF_RENTAL")
    private LocalDate dateOfRental;
    @Column(name = "DATE_OF_RETURN")
    private LocalDate dateOfReturn;


}
