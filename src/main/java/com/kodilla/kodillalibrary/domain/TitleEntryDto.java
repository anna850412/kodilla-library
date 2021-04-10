package com.kodilla.kodillalibrary.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@NoArgsConstructor
@Getter
@AllArgsConstructor
public class TitleEntryDto {
    private Long id;
    private String title;
    private String author;
    private LocalDate publicationYear;
    private Long BookEntryId;
}
