package com.kodilla.kodillalibrary.mapper;

import com.kodilla.kodillalibrary.domain.Reader;
import com.kodilla.kodillalibrary.domain.ReaderDto;
import org.springframework.stereotype.Service;

@Service
public class ReaderMapper {
    public Reader mapToReader(final ReaderDto readerDto){
        Reader reader  = new Reader();
        reader.setName(readerDto.getName());
        reader.setSurname(reader.getSurname());
        reader.setDateOfAccountCreation(readerDto.getDateOfAccountCreation());
        return reader;
//        return new Reader(
//                readerDto.getId(),
//                readerDto.getName(),
//                readerDto.getSurname(),
//                readerDto.getDateOfAccountCreation(),
//                readerDto.getBorrowedBooks()
//        );
    }
//    public ReaderDto mapToReaderDto(final Reader reader){
//        return new ReaderDto(
//                reader.getId(),
//                reader.getName(),
//                reader.getSurname(),
//                reader.getDateOfAccountCreation(),
//                reader.getBorrowedBooks()
//        );
//    }
}
