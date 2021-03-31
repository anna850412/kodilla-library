package com.kodilla.kodillalibrary.mapper;

import com.kodilla.kodillalibrary.domain.BookEntry;
import com.kodilla.kodillalibrary.domain.BookEntryDto;
import org.springframework.stereotype.Service;

@Service
public class BookEntryMapper {
    public BookEntry mapToBookEntry(BookEntryDto bookEntryDto) {
        BookEntry bookEntry = new BookEntry();
        bookEntry.setTitle(bookEntryDto.getTitle());
        bookEntry.setStatus(bookEntryDto.getStatus());
        return bookEntry;

    }

    public BookEntryDto mapToBookEntryDto(final BookEntry bookEntry) {
        BookEntryDto bookEntryDto = new BookEntryDto();
        bookEntryDto.setTitle(bookEntry.getTitle());
        bookEntryDto.setStatus(bookEntry.getStatus());
        return bookEntryDto;

//        return new BookEntryDto(
//                bookEntry.getId(),
//                bookEntry.getTitle(),
//                bookEntry.getStatus(),
//                bookEntry.getBorrowedBooks()
//        );
    }
//    public List<BookEntryDto> mapToBookEntryDtoList(final List<BookEntry> bookEntryList){
//        return bookEntryList.stream()
//                .map(this::mapToBookEntryDto)
//                .collect(Collectors.toList());
//    }
//    public List<BookEntry> mapToBookEntryList(final List<BookEntryDto> bookEntryDtoList){
//        return bookEntryDtoList.stream()
//                .map(this::mapToBookEntry)
//                .collect(Collectors.toList());
//    }
}
