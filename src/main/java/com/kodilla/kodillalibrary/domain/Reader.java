package com.kodilla.kodillalibrary.domain;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

//@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "READERS")
public class Reader {


    private Long id;
    private String Name;
    private String Surname;
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
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    @NotNull
    @Column(name = "SURNAME")
    public String getSurname() {
        return Surname;
    }

    public void setSurname(String surname) {
        Surname = surname;
    }

    @NotNull
    @Column(name = "ACCOUNT_CREATION_DATE")
    public LocalDate getDateOfAccountCreation() {
        return dateOfAccountCreation;
    }

    public void setDateOfAccountCreation(LocalDate dateOfAccountCreation) {
        this.dateOfAccountCreation = dateOfAccountCreation;
    }

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "JOIN_READERS_BORROWED_BOOKS",
            joinColumns = {@JoinColumn(name = "READERS_ID", referencedColumnName = "READER_ID")},
            inverseJoinColumns = {@JoinColumn(name = "BORROWED_BOOKS_ID", referencedColumnName = "BORROWED_BOOKS_ID")}
    )
    public List<BorrowedBooks> getBorrowedBooks() {
        return borrowedBooks;
    }

    public void setBorrowedBooks(List<BorrowedBooks> borrowedBooks) {
        this.borrowedBooks = borrowedBooks;
    }
}
