package com.kodilla.kodillalibrary.mapper;

import com.kodilla.kodillalibrary.domain.BookEntry;
import com.kodilla.kodillalibrary.domain.BookEntryDto;
import org.springframework.stereotype.Service;

import java.awt.print.Book;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookEntryMapper {
    public BookEntry mapToBookEntry(final BookEntryDto bookEntryDto) {
        return new BookEntry(
                bookEntryDto.getId(),
        bookEntryDto.getTitle(),
        bookEntryDto.getStatus(),
        bookEntryDto.getBorrowedBooks()
        );
    }
    public BookEntryDto mapToBookEntryDto(final BookEntry bookEntry){
        return new BookEntryDto(
                bookEntry.getId(),
                bookEntry.getTitle(),
                bookEntry.getStatus(),
                bookEntry.getBorrowedBooks()
        );
    }
    public List<BookEntryDto> mapToBookEntryDtoList(final List<BookEntry> bookEntryList){
        return bookEntryList.stream()
                .map(this::mapToBookEntryDto)
                .collect(Collectors.toList());
    }
    public List<BookEntry> mapToBookEntryList(final List<BookEntryDto> bookEntryDtoList){
        return bookEntryDtoList.stream()
                .map(this::mapToBookEntry)
                .collect(Collectors.toList());
    }
}
