package com.kodilla.kodillalibrary.domain;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "BOOK_ENTRIES")
public class BookEntry {
    @Id
    @GeneratedValue
    @NotNull
    @Column(name = "BOOK_ENTRY_ID", unique = true)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "TITLE_ENTRY_ID")
    private TitleEntry titleEntry;

    @Column(name = "STATUS")
    private Status status;

    @OneToMany(
            targetEntity = Borrowing.class,
            mappedBy = "bookEntry",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    private List<Borrowing> borrowings = new ArrayList<>();

    public BookEntry(TitleEntry titleEntry, Status status, List<Borrowing> borrowings) {
        this.titleEntry = titleEntry;
        this.status = status;
        this.borrowings = borrowings;
    }
}
