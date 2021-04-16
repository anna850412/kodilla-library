package com.kodilla.kodillalibrary.mapper;

import com.kodilla.kodillalibrary.domain.TitleEntry;
import com.kodilla.kodillalibrary.domain.TitleEntryDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TitleEntryMapper {
    public TitleEntry mapToTitleEntry(final TitleEntryDto titleEntryDto) {
        TitleEntry titleEntry = new TitleEntry();
        titleEntry.setTitle(titleEntryDto.getTitle());
        titleEntry.setAuthor(titleEntryDto.getAuthor());
        titleEntry.setPublicationYear(titleEntryDto.getPublicationYear());

        return titleEntry;
    }

    public TitleEntryDto mapToTitleEntryDto(final TitleEntry titleEntry) {
        TitleEntryDto titleEntryDto = new TitleEntryDto();
        titleEntryDto.setTitle(titleEntry.getTitle());
        titleEntryDto.setAuthor(titleEntry.getAuthor());
        titleEntryDto.setPublicationYear(titleEntry.getPublicationYear());

        return titleEntryDto;
    }

    public List<TitleEntryDto> mapToTitleEntriesDtoList(final List<TitleEntry> titleEntries) {
        return titleEntries.stream()
                .map(this::mapToTitleEntryDto)
                .collect(Collectors.toList());
    }
}
