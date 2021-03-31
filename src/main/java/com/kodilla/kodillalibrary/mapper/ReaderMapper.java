package com.kodilla.kodillalibrary.mapper;

import com.kodilla.kodillalibrary.domain.Reader;
import com.kodilla.kodillalibrary.domain.ReaderDto;
import org.springframework.stereotype.Service;

@Service
public class ReaderMapper {
    public Reader mapToReader(final ReaderDto readerDto) {
        Reader reader = new Reader();
        reader.setName(readerDto.getName());
        reader.setSurname(readerDto.getSurname());
        reader.setDateOfAccountCreation(readerDto.getDateOfAccountCreation());
        return reader;
    }

    public ReaderDto mapToReaderDto(final Reader reader) {
        ReaderDto readerDto = new ReaderDto();
        readerDto.setName(reader.getName());
        readerDto.setSurname(reader.getSurname());
        readerDto.setDateOfAccountCreation(reader.getDateOfAccountCreation());
        return readerDto;
    }
}
