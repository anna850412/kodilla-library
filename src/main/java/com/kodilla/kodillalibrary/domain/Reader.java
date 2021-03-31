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
