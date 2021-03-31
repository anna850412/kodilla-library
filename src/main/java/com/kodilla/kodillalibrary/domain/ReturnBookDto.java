package com.kodilla.kodillalibrary.domain;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ReturnBookDto {
    private Long readerId;
    private Long bookEntryId;
}
