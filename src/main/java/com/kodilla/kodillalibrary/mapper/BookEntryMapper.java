package com.kodilla.kodillalibrary.mapper;

import com.kodilla.kodillalibrary.domain.BookEntry;
import com.kodilla.kodillalibrary.domain.BookEntryDto;
import org.springframework.stereotype.Service;

@Service
public class BookEntryMapper {
    public BookEntry mapToBookEntry(BookEntryDto bookEntryDto) {
        BookEntry bookEntry = new BookEntry();
        bookEntry.setTitleEntry(bookEntryDto.getTitleEntry());
        bookEntry.setStatus(bookEntryDto.getStatus());

        return bookEntry;
    }

    public BookEntryDto mapToBookEntryDto(final BookEntry bookEntry) {
        BookEntryDto bookEntryDto = new BookEntryDto();
        bookEntryDto.setTitleEntry(bookEntry.getTitleEntry());
        bookEntryDto.setStatus(bookEntry.getStatus());

        return bookEntryDto;
    }

}
