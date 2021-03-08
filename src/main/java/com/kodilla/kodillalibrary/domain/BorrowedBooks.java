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
    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "borrowedBooks")
    private List<Reader> readers = new ArrayList<>();
    @Column(name = "DATE_OF_RENTAL")
    private LocalDate dateOfRental;
    @Column(name = "DATE_OF_RETURN")
    private LocalDate dateOfReturn;

}
