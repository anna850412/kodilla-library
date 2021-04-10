package com.kodilla.kodillalibrary.domain;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name ="BOOK_ENTRIES")
public class BookEntry {
    @Id
    @GeneratedValue
    @NotNull
    @Column(name = "BOOK_ENTRY_ID",unique = true)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "TITLE_ENTRY_ID")
    private TitleEntry titleEntry;

    @Column(name = "STATUS")
    private Status status;
//    @Column(name = "READER_ID")
//    private Reader readerId;

//    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    @JoinTable(
//            name = "JOIN_BOOK_ENTRIES_BORROWED_BOOKS",
//            joinColumns = {@JoinColumn(name = "BOOK_ENTRY_ID", referencedColumnName = "BOOK_ENTRY_ID")},
//            inverseJoinColumns = {@JoinColumn(name = "BORROWED_BOOKS_ID", referencedColumnName = "BORROWED_BOOKS_ID")}
//    )
    @OneToMany(
            targetEntity = BorrowedBooks.class,
            mappedBy = "bookEntries",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    private List<BorrowedBooks> borrowedBooks;

}
