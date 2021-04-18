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

    public TitleEntry(String title, String author, LocalDate publicationYear, List<BookEntry> bookEntries) {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TitleEntry)) return false;

        TitleEntry that = (TitleEntry) o;

        if (getId() != null ? !getId().equals(that.getId()) : that.getId() != null) return false;
        if (getTitle() != null ? !getTitle().equals(that.getTitle()) : that.getTitle() != null) return false;
        if (getAuthor() != null ? !getAuthor().equals(that.getAuthor()) : that.getAuthor() != null) return false;
        if (getPublicationYear() != null ? !getPublicationYear().equals(that.getPublicationYear()) : that.getPublicationYear() != null)
            return false;
        return getBookEntries() != null ? getBookEntries().equals(that.getBookEntries()) : that.getBookEntries() == null;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getTitle() != null ? getTitle().hashCode() : 0);
        result = 31 * result + (getAuthor() != null ? getAuthor().hashCode() : 0);
        result = 31 * result + (getPublicationYear() != null ? getPublicationYear().hashCode() : 0);
        result = 31 * result + (getBookEntries() != null ? getBookEntries().hashCode() : 0);
        return result;
    }
}
