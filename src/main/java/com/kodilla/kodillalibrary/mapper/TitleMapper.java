package com.kodilla.kodillalibrary.mapper;

import com.kodilla.kodillalibrary.domain.Title;
import com.kodilla.kodillalibrary.domain.TitleDto;
import org.springframework.stereotype.Service;

@Service
public class TitleMapper {
    public Title mapToTitle(final TitleDto titleDto){
        Title title = new Title();
        title.setTitle(title.getTitle());
        title.setAuthor(titleDto.getAuthor());
        title.setPublicationYear(titleDto.getPublicationYear());
        return title;

//        return new Title(
//                titleDto.getId(),
//                titleDto.getTitle(),
//                titleDto.getAuthor(),
//                titleDto.getPublicationYear(),
//                titleDto.getBookEntries()
//        );
    }
//    public TitleDto mapToTitleDto(final Title title){
//
//        return new TitleDto(
//                title.getId(),
//                title.getTitle(),
//                title.getAuthor(),
//                title.getPublicationYear(),
//                title.getBookEntries()
//        );
//    }
}
