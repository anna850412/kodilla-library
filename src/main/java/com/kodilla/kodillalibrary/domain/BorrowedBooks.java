package com.kodilla.kodillalibrary.domain;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "BORROWED_BOOKS")
public class BorrowedBooks {
    @Id
    @GeneratedValue
    @NotNull
    @Column(name = "BORROWED_BOOKS_ID", unique = true)
    private Long id;
    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "borrowedBooks")
    private List<BookEntry> bookEntries = new ArrayList<>();
//    @OneToMany(
//            targetEntity = Reader.class,
//            mappedBy = "borrowedBooks",
//            cascade = CascadeType.ALL,
//            fetch = FetchType.LAZY
//    )
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "READER_ID")
    private Reader readers;
    @Column(name = "DATE_OF_RENTAL")
    private final LocalDate dateOfRental = null;
    @Column(name = "DATE_OF_RETURN")
    private LocalDate dateOfReturn;

    public BorrowedBooks(List<BookEntry> bookEntries, Reader reader, LocalDate of, LocalDate of1) {
    }

    public BorrowedBooks(Long id, List<BookEntry> bookEntries, List<Reader> readers, LocalDate dateOfRental, LocalDate dateOfReturn) {
    }

}
