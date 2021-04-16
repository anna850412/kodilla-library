package com.kodilla.kodillalibrary.mapper;

import com.kodilla.kodillalibrary.domain.BookEntry;
import com.kodilla.kodillalibrary.domain.BookEntryDto;
import com.kodilla.kodillalibrary.domain.TitleEntry;
import com.kodilla.kodillalibrary.exception.TitleEntryNotExistException;
import com.kodilla.kodillalibrary.repository.TitleEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BookEntryMapper {
    public BookEntry mapToBookEntry(BookEntryDto bookEntryDto, TitleEntry titleEntry) {
        BookEntry bookEntry = new BookEntry();
        bookEntry.setTitleEntry(titleEntry);
        bookEntry.setStatus(bookEntryDto.getStatus());

        return bookEntry;
    }

    public BookEntryDto mapToBookEntryDto(final BookEntry bookEntry) {
        BookEntryDto bookEntryDto = new BookEntryDto();
        bookEntryDto.setTitleEntryId(bookEntry.getTitleEntry().getId());
        bookEntryDto.setStatus(bookEntry.getStatus());
        return bookEntryDto;

//        return new BookEntryDto(
//                bookEntry.getId(),
//                bookEntry.getTitleEntry(),
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
