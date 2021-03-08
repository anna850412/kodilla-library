package com.kodilla.kodillalibrary.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
@Getter
@AllArgsConstructor
public class BorrowedBooksDto {
    private Long id;
    private List<BookEntry> bookEntries = new ArrayList<>();
    private List<Reader> readers = new ArrayList<>();
    private LocalDate dateOfRental;
    private LocalDate dateOfReturn;
}
