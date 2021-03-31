package com.kodilla.kodillalibrary.domain;

import com.kodilla.kodillalibrary.repository.BorrowedBooksRepository;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.repository.Modifying;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

//zwrot książki mając readerId i bookEntryId

@NamedNativeQuery(
        name = "BorrowedBooks.returnBook",
        query = "UPDATE BORROWED_BOOKS " +
                "SET STATUS = Status.AVAILABLE " +
                "WHERE READER_ID = readerId AND BOOK_ENTRY_ID = bookEntryId",
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
    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "borrowedBooks", fetch = FetchType.LAZY)
    private List<BookEntry> bookEntries;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "READER_ID")
    private Reader readers;
    @Column(name = "DATE_OF_RENTAL")
    private LocalDate dateOfRental;
    @Column(name = "DATE_OF_RETURN")
    private LocalDate dateOfReturn;


}
