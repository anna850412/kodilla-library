package com.kodilla.kodillalibrary.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class ReaderDto {
    private Long id;
    private String Name;
    private String Surname;
    private LocalDate dateOfAccountCreation;
//    private List<BorrowedBooks> borrowedBooks = new ArrayList<>();
}
