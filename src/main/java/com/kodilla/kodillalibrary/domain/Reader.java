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

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "READERS")
public class Reader {


    private Long id;
    private String name;
    private String surname;
    private LocalDate dateOfAccountCreation;
    private List<BorrowedBooks> borrowedBooks = new ArrayList<>();

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getSurname() != null ? getSurname().hashCode() : 0);
        result = 31 * result + (getDateOfAccountCreation() != null ? getDateOfAccountCreation().hashCode() : 0);
        result = 31 * result + (getBorrowedBooks() != null ? getBorrowedBooks().hashCode() : 0);
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
        if (getDateOfAccountCreation() != null ? !getDateOfAccountCreation().equals(reader.getDateOfAccountCreation()) : reader.getDateOfAccountCreation() != null)
            return false;
        return getBorrowedBooks() != null ? getBorrowedBooks().equals(reader.getBorrowedBooks()) : reader.getBorrowedBooks() == null;
    }

    @Id
    @GeneratedValue
    @NotNull
    @Column(name = "READER_ID", unique = true)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @NotNull
    @Column(name = "NAME")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotNull
    @Column(name = "SURNAME")
    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @NotNull
    @Column(name = "ACCOUNT_CREATION_DATE")
    public LocalDate getDateOfAccountCreation() {
        return dateOfAccountCreation;
    }

    public void setDateOfAccountCreation(LocalDate dateOfAccountCreation) {
        this.dateOfAccountCreation = dateOfAccountCreation;
    }

    @OneToMany(
            targetEntity = BorrowedBooks.class,
            mappedBy = "readers",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    public List<BorrowedBooks> getBorrowedBooks() {
        return borrowedBooks;
    }

    public void setBorrowedBooks(List<BorrowedBooks> borrowedBooks) {
        this.borrowedBooks = borrowedBooks;
    }
}
