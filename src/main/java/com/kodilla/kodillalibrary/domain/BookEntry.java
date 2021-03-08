package com.kodilla.kodillalibrary.domain;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name ="BOOK_ENTRIES")
public class BookEntry {
    @Id
    @GeneratedValue
    @NotNull
    @Column(name = "BOOK_ENTRY_ID",unique = true)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "TITLE_ID")
    private Title title;

    @Column(name = "STATUS")
    private String status;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "JOIN_BOOK_ENTRIES_BORROWED_BOOKS",
            joinColumns = {@JoinColumn(name = "BOOK_ENTRIES_ID", referencedColumnName = "BOOK_ENTRY_ID")},
            inverseJoinColumns = {@JoinColumn(name = "BORROWED_BOOKS_ID", referencedColumnName = "BORROWED_BOOKS_ID")}
    )
    private List<BorrowedBooks> borrowedBooks = new ArrayList<>();


}
