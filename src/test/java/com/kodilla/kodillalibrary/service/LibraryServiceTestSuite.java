package com.kodilla.kodillalibrary.service;

import com.kodilla.kodillalibrary.controller.LibraryController;
import com.kodilla.kodillalibrary.domain.*;
import com.kodilla.kodillalibrary.exception.BookNotExistException;
import com.kodilla.kodillalibrary.exception.TitleEntryNotExistException;
import com.kodilla.kodillalibrary.mapper.BookEntryMapper;
import com.kodilla.kodillalibrary.mapper.ReaderMapper;
import com.kodilla.kodillalibrary.mapper.TitleEntryMapper;
import com.kodilla.kodillalibrary.repository.BookEntryRepository;
import com.kodilla.kodillalibrary.repository.Borrowings;
import com.kodilla.kodillalibrary.repository.ReaderRepository;
import com.kodilla.kodillalibrary.repository.TitleEntryRepository;
import com.kodilla.kodillalibrary.service.DbService;
import org.hibernate.validator.constraints.Mod10Check;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatcher;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest

@DisplayName("Library Service Test Suite")
public class LibraryServiceTestSuite {
    @MockBean
    private ReaderRepository readerRepository;
    @MockBean
    private Borrowings borrowings;
    @MockBean
    private TitleEntryRepository titleEntryRepository;
    @MockBean
    private BookEntryRepository bookEntryRepository;
    @InjectMocks
    private DbService service;
    @MockBean
    private BookEntryMapper bookEntryMapper;
    @MockBean
    private ReaderMapper readerMapper;
    @MockBean
    private TitleEntryMapper titleEntryMapper;

        @Test
        void testSaveBookEntry() throws TitleEntryNotExistException {
            //Given
            List<BookEntry> bookEntries = new ArrayList<>();
            List<Borrowing> borrowedBooks = new ArrayList<>();
            TitleEntry titleEntry = new TitleEntry(1L, "Title", "Author",
                    LocalDate.of(2021, 4, 22), bookEntries);
            BookEntry bookEntry = new BookEntry(titleEntry, Status.AVAILABLE, borrowedBooks);

            //When
            service.saveBookEntry(bookEntry);
            //Then
//            verify(service, times(1)).saveBookEntry(bookEntry);
            assertEquals(1, service.findBookEntryById(bookEntry.getId()));
        }

        @Test
        void testFindAllTitleEntries() {
            //Given
            List<BookEntry> bookEntries = new ArrayList<>();
            List<TitleEntry> allEntries = new ArrayList<>();
            TitleEntry titleEntry1 = new TitleEntry("title1", "author1", LocalDate.now(), bookEntries);
            TitleEntry titleEntry2 = new TitleEntry("title2", "author2", LocalDate.now(), bookEntries);
            allEntries.add(titleEntry1);
            allEntries.add(titleEntry2);
            //When
            allEntries = titleEntryRepository.findAll();
            //Then
            assertEquals(2, allEntries.size());
        }


        @Test
        void testSaveReader() {
            //Given
            List<Borrowing> borrowedBooks = new ArrayList<>();
            Reader reader = new Reader("Ala", "Wilk",
                    LocalDate.of(2018, 4, 23), borrowedBooks);
            ReaderDto readerDto = new ReaderDto(1L, "Ala", "Wilk",
                    LocalDate.of(2018, 4, 23));
            when(readerMapper.mapToReader(readerDto)).thenReturn(reader);
            //When
//            controller.createReader(readerMapper.mapToReaderDto(reader));
            //Then
            verify(service, times(1)).saveReader(reader);
        }

        @Test
        void testSaveTitle() {

            //Given
            List<BookEntry> bookEntries = new ArrayList<>();
            TitleEntry titleEntry = new TitleEntry(2L, "test Title", "test Author",
                    LocalDate.of(2010, 9, 11), bookEntries);
            TitleEntryDto titleEntryDto = new TitleEntryDto(2L, "test Title", "test Author",
                    LocalDate.of(2010, 9, 11), 11L);
            when(titleEntryMapper.mapToTitleEntry(ArgumentMatchers.any(TitleEntryDto.class))).thenReturn(titleEntry);
            when(titleEntryMapper.mapToTitleEntryDto(ArgumentMatchers.any(TitleEntry.class))).thenReturn(titleEntryDto);
            when(titleEntryRepository.save(titleEntry)).thenReturn(titleEntry);
            //When
//            controller.createTitle(titleEntryMapper.mapToTitleEntryDto(titleEntry));
            //Then
            verify(service, times(1)).saveTitle(titleEntry);
        }

        //        @Disabled
//        @Test
//        void testUpdateStatus() {
//            //Given
//            List<BookEntry> bookEntries = new ArrayList<>();
//            List<Borrowing> borrowedBooks = new ArrayList<>();
//            TitleEntry titleEntry = new TitleEntry(1L, "Title", "Author",
//                    LocalDate.of(2010, 3, 21), bookEntries);
//            BookEntry bookEntry = new BookEntry(titleEntry, Status.AVAILABLE, borrowedBooks);
//            //When
//            BookEntry savedStatus = bookEntryRepository.save(bookEntry);
//            //Then
//            assertEquals(savedStatus, Status.AVAILABLE);
//        }
        @Test
        void testSaveBorrowings(){
            //Given
            //When
            //Then
        }
        @Test
        void testGetNumberOfAvailableBooksByTitleEntry() {
            //Given
            TitleEntryDto titleEntryDto = new TitleEntryDto(1L, "Title1", "Author",
                    LocalDate.of(2010, 3, 2), 1L);
            LibraryController controller = new LibraryController(service, bookEntryMapper, readerMapper, titleEntryMapper);
            //When
            controller.howManyBookEntriesAreAvailable(titleEntryDto.getTitle(), titleEntryDto.getAuthor());
            //Then
            verify(service, times(1)).findTitleEntryById(titleEntryDto.getId());
        }

        @Test
        void testRentBook() throws BookNotExistException {
            //Given
            List<BookEntry> bookEntries = new ArrayList<>();
            BookRentalDto bookRentalDto = new BookRentalDto(1L, 1L);
            TitleEntry titleEntry = new TitleEntry(111L,
                    "Title", "Author", LocalDate.of(2010, 11, 4), bookEntries);
            List<Borrowing> borrowings = new ArrayList<>();
            titleEntryRepository.save(titleEntry);
            Reader reader = new Reader("Anna", "Kowalska", LocalDate.of(2020, 11, 11), borrowings);
            BookEntry availableBookEntry = new BookEntry(titleEntry, Status.AVAILABLE, borrowings);
            when(bookEntryRepository.findByTitleEntryAndStatus(titleEntry, Status.AVAILABLE)).thenReturn(Arrays.asList(availableBookEntry));
            when(readerRepository.findById(bookRentalDto.getReaderId())).thenReturn(Optional.of(reader));
            //When
//            controller.rentABook(bookRentalDto);

            //Then
            verify(service, times(1)).saveReader(reader);

            //clean up
            titleEntryRepository.deleteById(111L);

        }

        @Test
        void testReturnBook() {
            //Given

            //When

            //Then
        }
    }



