package com.kodilla.kodillalibrary.mapper;

import com.kodilla.kodillalibrary.domain.BorrowedBooks;
import com.kodilla.kodillalibrary.domain.BorrowedBooksDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BorrowedBooksMapper {
    public BorrowedBooks mapToBorrowedBooks(final BorrowedBooksDto borrowedBooksDto){
        return new BorrowedBooks(
                borrowedBooksDto.getId(),
                borrowedBooksDto.getBookEntries(),
                borrowedBooksDto.getReaders(),
                borrowedBooksDto.getDateOfRental(),
                borrowedBooksDto.getDateOfReturn()
        );
    }
    public BorrowedBooksDto mapToBorrowedBooksDto(final BorrowedBooks borrowedBooks){
        return new BorrowedBooksDto(
                borrowedBooks.getId(),
                borrowedBooks.getBookEntries(),
                borrowedBooks.getReaders(),
                borrowedBooks.getDateOfRental(),
                borrowedBooks.getDateOfReturn()
        );
    }
    public List<BorrowedBooksDto> mapToBorrowedBooksDtoList(final List<BorrowedBooks> borrowedBooksList){
        return borrowedBooksList.stream()
                .map(this::mapToBorrowedBooksDto)
                .collect(Collectors.toList());
    }
    public List<BorrowedBooks> mapToBorrowedBooksList(final List<BorrowedBooksDto> borrowedBooksDtoList){
        return borrowedBooksDtoList.stream()
                .map(this::mapToBorrowedBooks)
                .collect(Collectors.toList());
    }

}
