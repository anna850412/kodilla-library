package com.kodilla.kodillalibrary.domain;

import com.sun.istack.NotNull;
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
@Entity
@Table(name = "READERS")
public class Reader {

    @Id
    @GeneratedValue
    @NotNull
    @Column(name = "READER_ID", unique = true)
    private Long id;

    @NotNull
    @Column(name = "NAME")
    private String name;

    @NotNull
    @Column(name = "SURNAME")
    private String surname;

    @NotNull
    @Column(name = "ACCOUNT_CREATION_DATE")
    private LocalDate creationDate;


    @OneToMany(
            targetEntity = Borrowing.class,
            mappedBy = "reader",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    private List<Borrowing> borrowings = new ArrayList<>();

    public Reader(String name, String surname, LocalDate creationDate, List<Borrowing> borrowings) {
        this.name = name;
        this.surname = surname;
        this.creationDate = creationDate;
        this.borrowings = borrowings;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getSurname() != null ? getSurname().hashCode() : 0);
        result = 31 * result + (getCreationDate() != null ? getCreationDate().hashCode() : 0);
        result = 31 * result + (getBorrowings() != null ? getBorrowings().hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Reader)) return false;

        Reader reader = (Reader) o;

        if (getId() != null ? !getId().equals(reader.getId()) : reader.getId() != null) return false;
        if (getName() != null ? !getName().equals(reader.getName()) : reader.getName() != null) return false;
        if (getSurname() != null ? !getSurname().equals(reader.getSurname()) : reader.getSurname() != null)
            return false;
        if (getCreationDate() != null ? !getCreationDate().equals(reader.getCreationDate()) : reader.getCreationDate() != null)
            return false;
        return getBorrowings() != null ? getBorrowings().equals(reader.getBorrowings()) : reader.getBorrowings() == null;
    }

}
