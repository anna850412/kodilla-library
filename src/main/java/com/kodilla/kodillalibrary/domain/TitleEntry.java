package com.kodilla.kodillalibrary.domain;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "TITLE_ENTRIES")
public class TitleEntry {
    @Id
    @GeneratedValue
    @NotNull
    @Column(name = "TITLE_ENTRY_ID", unique = true)
    private Long id;

    @NotNull
    @Column(name = "TITLE")
    private String title;

    @NotNull
    @Column(name = "AUTHOR")
    private String author;

    @NotNull
    @Column(name = "PUBLICATIONYEAR")
    private LocalDate publicationYear;

    @OneToMany(
            targetEntity = BookEntry.class,
            mappedBy = "titleEntry",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    private List<BookEntry> bookEntries;

}
