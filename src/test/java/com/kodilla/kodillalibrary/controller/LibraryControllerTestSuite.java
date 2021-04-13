package com.kodilla.kodillalibrary.controller;

import com.kodilla.kodillalibrary.domain.TitleEntryDto;
import com.kodilla.kodillalibrary.mapper.TitleEntryMapper;
import com.kodilla.kodillalibrary.service.DbService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class LibraryControllerTestSuite {

    @MockBean
    private TitleEntryMapper titleEntryMapper;

    @MockBean
    private DbService service;

    @InjectMocks
    private LibraryController controller;

    @Test
    public void shouldFindAll() {
        //Given
        List<TitleEntryDto> entries = List.of(
                new TitleEntryDto(1L, "title1", "author1", LocalDate.now(), 3L),
                new TitleEntryDto(2L, "title2", "author2", LocalDate.now(), 3L)
        );
        when(titleEntryMapper.mapToTitleEntriesDtoList(anyList())).thenReturn(entries);

        //When
        List<TitleEntryDto> allEntries = controller.findAll();
        //Then
        Assertions.assertEquals(2, allEntries.size());
    }
}
