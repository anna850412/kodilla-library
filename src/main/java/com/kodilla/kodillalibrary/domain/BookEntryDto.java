package com.kodilla.kodillalibrary.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
public class BookEntryDto {
    private Long id;
    private Title title;
    private String status;
    private List<BorrowedBooks> borrowedBooks = new ArrayList<>();
}
