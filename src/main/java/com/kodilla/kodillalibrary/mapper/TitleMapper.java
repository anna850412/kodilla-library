package com.kodilla.kodillalibrary.mapper;

import com.kodilla.kodillalibrary.domain.Title;
import com.kodilla.kodillalibrary.domain.TitleDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TitleMapper {
    public Title mapToTitle(final TitleDto titleDto){
        Title title = new Title();
        title.setTitle(titleDto.getTitle());
        title.setAuthor(titleDto.getAuthor());
        title.setPublicationYear(titleDto.getPublicationYear());
        title.setBookEntryId(titleDto.getBookEntryId());

        return title;
    }
    public TitleDto mapToTitleDto(final Title title){
        TitleDto titleDto = new TitleDto();
        titleDto.setTitle(title.getTitle());
        titleDto.setAuthor(title.getAuthor());
        titleDto.setPublicationYear(title.getPublicationYear());
        titleDto.setBookEntryId(title.getBookEntryId());
        return titleDto;
    }
    public List<TitleDto> mapToTitlesDto(final List<Title> titles){
        return titles.stream()
                .map(this::mapToTitleDto)
                .collect(Collectors.toList());
    }
}
