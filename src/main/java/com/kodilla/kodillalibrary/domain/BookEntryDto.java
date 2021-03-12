package com.kodilla.kodillalibrary.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BookEntryDto {
    private Long id;
    private Title title;
    private Status status;
//    private List<BorrowedBooks> borrowedBooks = new ArrayList<>();
}
