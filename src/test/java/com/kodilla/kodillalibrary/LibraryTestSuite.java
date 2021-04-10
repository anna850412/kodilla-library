package com.kodilla.kodillalibrary;

import com.kodilla.kodillalibrary.controller.LibraryController;
import com.kodilla.kodillalibrary.domain.*;
import com.kodilla.kodillalibrary.mapper.BookEntryMapper;
import com.kodilla.kodillalibrary.mapper.ReaderMapper;
import com.kodilla.kodillalibrary.mapper.TitleEntryMapper;
import com.kodilla.kodillalibrary.repository.BookEntryRepository;
import com.kodilla.kodillalibrary.repository.Borrowings;
import com.kodilla.kodillalibrary.repository.ReaderRepository;
import com.kodilla.kodillalibrary.repository.TitleEntryRepository;
import com.kodilla.kodillalibrary.service.DbService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
@SpringBootTest

@DisplayName("Library Test Suite")
public class LibraryTestSuite {
    @Autowired
    private ReaderRepository readerRepository;
    @Autowired
    private Borrowings borrowings;
    @Autowired
    private TitleEntryRepository titleEntryRepository;
    @Autowired
    private BookEntryRepository bookEntryRepository;
    @InjectMocks
    private LibraryController controller;
    @Mock
    private DbService service;
    @Autowired
    private BookEntryMapper bookEntryMapper;

    @Autowired
    private ReaderMapper readerMapper;
    @Autowired
    private TitleEntryMapper titleEntryMapper;
    @Mock
    private BookEntry bookEntry;
    @Mock
    private ReaderDto readerDto;

    @Mock
    private TitleEntryDto titleEntryDto;


    @Nested
//    @Disabled
    @DisplayName("Library Controller Tests")
    class LibraryControllerTests {

        @Test
        void testCreateBookEntry() {
            //Given
            List<BookEntry> bookEntries = new ArrayList<>();
            List<Borrowing> borrowedBooks = new ArrayList<>();
            TitleEntry titleEntry = new TitleEntry(1L, "Title", "Author",
                    LocalDate.of(2021, 4, 22), bookEntries);
            BookEntry bookEntry = new BookEntry(titleEntry, Status.AVAILABLE, borrowedBooks);
            LibraryController controller = new LibraryController(service, bookEntryMapper, readerMapper, titleEntryMapper);
            //When
            controller.createBookEntry(bookEntryMapper.mapToBookEntryDto(bookEntry));
            //Then
            verify(service, times(1)).saveBookEntry(bookEntry);
        }

        @Test
        void testFindAll() {
            //Given

            //When

            //Then
        }


        @Test
        void testCreateReader() {
            //Given
            List<Borrowing> borrowedBooks = new ArrayList<>();
            Reader reader = new Reader("Ala", "Wilk",
                    LocalDate.of(2018, 4, 23), borrowedBooks);
            LibraryController controller = new LibraryController(service, bookEntryMapper,
                    readerMapper, titleEntryMapper);
            //When
            controller.createReader(readerMapper.mapToReaderDto(reader));
            //Then
            verify(service, times(1)).saveReader(reader);
        }

        @Test
        void testCreateTitle() {

            //Given
            List<BookEntry> bookEntries = new ArrayList<>();
            TitleEntry titleEntry = new TitleEntry(2L, "test Title", "test Author",
                    LocalDate.of(2010, 9, 11), bookEntries);
            LibraryController controller = new LibraryController(service, bookEntryMapper, readerMapper, titleEntryMapper);
            //When
            controller.createTitle(titleEntryMapper.mapToTitleEntryDto(titleEntry));
            //Then
            verify(service, times(1)).saveTitle(titleEntry);
        }

        //        @Disabled
        @Test
        void testUpdateStatus() {
            //Given
            List<BookEntry> bookEntries = new ArrayList<>();
            List<Borrowing> borrowedBooks = new ArrayList<>();
            TitleEntry titleEntry = new TitleEntry(1L, "Title", "Author",
                    LocalDate.of(2010, 3, 21), bookEntries);
            BookEntry bookEntry = new BookEntry(titleEntry, Status.AVAILABLE, borrowedBooks);
            //When
            BookEntry savedStatus = bookEntryRepository.save(bookEntry);
            //Then
            Assertions.assertEquals(savedStatus, Status.AVAILABLE);
        }

        @Test
        void testHowManyBookEntriesAreAvailable() {
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
        void testBookRental() {
            //Given

            //When

            //Then
        }

        @Test
        void testReturnOfBook() {
            //Given

            //When

            //Then
        }
    }

    @Nested
    @Disabled
    @DisplayName("Repository Tests")
    class RepositoryTests {
        @Disabled
        @Test
        void testReaderRepositorySaveWithReader() {
            //Given
            BookEntry bookEntries = new BookEntry();
            List<Borrowing> borrowedBooks = new ArrayList<>();
            Reader reader1 = new Reader("Anna", "Kowalska",
                    LocalDate.of(2019, 03, 12), borrowedBooks);
            borrowedBooks.add(new Borrowing(bookEntries, reader1,
                    LocalDate.of(2021, 01, 15), LocalDate.of(2021, 03, 05)));
            Reader reader2 = new Reader("Piotr", "Nowak",
                    LocalDate.of(2018, 06, 23), borrowedBooks);
            borrowedBooks.add(new Borrowing(bookEntries, reader2, LocalDate.of(2020, 07, 27),
                    LocalDate.of(2020, 11, 11)));
            Borrowing borrowedBooks1 = new Borrowing(bookEntries, reader1,
                    LocalDate.of(2021, 03, 10), null);
            Borrowing borrowedBooks2 = new Borrowing(bookEntries, reader2,
                    LocalDate.of(2021, 02, 10), LocalDate.of(2021, 03, 11));
            borrowedBooks.add(borrowedBooks1);
            borrowedBooks.add(borrowedBooks2);
            reader1.setBorrowings((List<Borrowing>) borrowedBooks1);
            reader2.setBorrowings((List<Borrowing>) borrowedBooks2);
            reader1.getBorrowings().add(borrowedBooks1);
            reader2.getBorrowings().add(borrowedBooks2);
            //When
            readerRepository.save(reader1);
            Long id1 = reader1.getId();
            readerRepository.save(reader2);
            Long id2 = reader2.getId();

            //Then
            Assertions.assertNotEquals(0, id1);
            Assertions.assertNotEquals(0, id2);

            //Clean up
            readerRepository.deleteById(id1);
            readerRepository.deleteById(id2);
        }

        @Test
        void testTitleRepositorySaveWithTitle() {
            //Given
            List<Borrowing> borrowedBooks = new ArrayList<>();
            List<BookEntry> bookEntries = new ArrayList<>();
            TitleEntry title1 = new TitleEntry(1L, "Title1", "Author1",
                    LocalDate.of(2016, 06, 21), bookEntries);
            TitleEntry title2 = new TitleEntry(2L, "Title2", "Author2",
                    LocalDate.of(2011, 12, 11), bookEntries);
            BookEntry bookEntry1 = new BookEntry(title1, Status.BORROWED, borrowedBooks);
            BookEntry bookEntry2 = new BookEntry(title2, Status.RESERVED, borrowedBooks);
            bookEntry1.setTitleEntry(title1);
            bookEntry2.setTitleEntry(title2);
            title1.getBookEntries().add(bookEntry1);
            title2.getBookEntries().add(bookEntry2);

            //When
            titleEntryRepository.save(title1);
            Long id1 = title1.getId();
            titleEntryRepository.save(title2);
            Long id2 = title2.getId();

            //Then
            Assertions.assertNotEquals(0, id1);
            Assertions.assertNotEquals(0, id2);
            //Clean up
            titleEntryRepository.deleteById(id1);
            titleEntryRepository.deleteById(id2);
        }

        @Test
        void testBookEntryRepositorySaveWithBookEntry() {
            //Given
            List<BookEntry> bookEntries = new ArrayList<>();
            List<Borrowing> borrowedBooks = new ArrayList<>();
            TitleEntry title1 = new TitleEntry(1L, "Title1", "Author1",
                    LocalDate.of(2016, 06, 21), bookEntries);
            BookEntry bookEntry1 = new BookEntry(title1, Status.BORROWED, borrowedBooks);
            bookEntry1.setTitleEntry(title1);
            title1.getBookEntries().add(bookEntry1);

            //When
            bookEntryRepository.save(bookEntry1);
            Long id1 = bookEntry1.getId();

            //Then
            Assertions.assertNotEquals(0, id1);

            //Clean up
            bookEntryRepository.deleteById(id1);

        }

        @Test
        void testTitleRepositoryFindById() {
            //Given
            List<BookEntry> bookEntries = new ArrayList<>();
            TitleEntry title1 = new TitleEntry(1L, "Title1", "Author1",
                    LocalDate.of(2016, 06, 21), bookEntries);

            //When
            titleEntryRepository.save(title1);

            //Then
            Long id1 = title1.getId();
            Optional<TitleEntry> savedTitle1 = titleEntryRepository.findById(id1);
            Assertions.assertTrue(savedTitle1.isPresent());

            //Clean up
            titleEntryRepository.deleteById(id1);
        }

        @Test
        void testBookEntryRepositoryFindById() {
            //Given
            List<BookEntry> bookEntries = new ArrayList<>();
            List<Borrowing> borrowedBooks = new ArrayList<>();
            TitleEntry titleEntry = new TitleEntry(1L, "Title1", "Author1",
                    LocalDate.of(2016, 06, 21), bookEntries);
            BookEntry bookEntry = new BookEntry(titleEntry, Status.BORROWED, borrowedBooks);
            //When
            bookEntryRepository.save(bookEntry);

            //Then
            Long id = bookEntry.getId();
            Optional<BookEntry> savedBookEntry = bookEntryRepository.findById(id);
            Assertions.assertTrue(savedBookEntry.isPresent());
            //Clean up
            bookEntryRepository.deleteById(id);
        }

        @Test
        void testTitleRepositoryFindAll() {
            //Given
            List<BookEntry> bookEntries = new ArrayList<>();
            TitleEntry title1 = new TitleEntry(1L, "Title1", "Author1",
                    LocalDate.of(2016, 06, 21), bookEntries);
            //When
            titleEntryRepository.save(title1);

            //Then
            Long id = title1.getId();
            Optional<TitleEntry> savedTitle = titleEntryRepository.findById(id);
            Assertions.assertTrue(savedTitle.isPresent());
            //Clean up
            titleEntryRepository.deleteById(id);
        }

        @Test
        void testBorrowedBooksRepositorySaveWithBorrowedBooks() {
            //Given
            List<BookEntry> bookEntries = new ArrayList<>();
            List<Borrowing> borrowedBooks = new ArrayList<>();
            TitleEntry title1 = new TitleEntry(1L, "Title1", "Author1",
                    LocalDate.of(2021, 01, 04), bookEntries);
            TitleEntry title2 = new TitleEntry(2L, "Title2", "Author2",
                    LocalDate.of(2017, 12, 14), bookEntries);
            BookEntry bookEntry1 = new BookEntry(title1, Status.RESERVED, borrowedBooks);
            BookEntry bookEntry2 = new BookEntry(title2, Status.AVAILABLE, borrowedBooks);

            Reader reader1 = new Reader("Anna", "Kowalska",
                    LocalDate.of(2019, 03, 12), borrowedBooks);
            Reader reader2 = new Reader("Piotr", "Nowak",
                    LocalDate.of(2018, 06, 23), borrowedBooks);
            Borrowing borrowedBook1 = new Borrowing(bookEntry1, reader1,
                    LocalDate.of(2021, 01, 15), LocalDate.of(2021, 03, 05));
            Borrowing borrowedBook2 = new Borrowing(bookEntry2, reader2, LocalDate.of(2020, 07, 27),
                    LocalDate.of(2020, 11, 11));

            borrowedBook1.setBookEntry(bookEntry1);
            borrowedBook2.setBookEntry(bookEntry2);


            //When
            borrowings.save(borrowedBook1);
            Long id1 = borrowedBook1.getId();
            borrowings.save(borrowedBook2);
            Long id2 = borrowedBook2.getId();

            //Then
            Assertions.assertNotEquals(0, id1);
            Assertions.assertNotEquals(0, id2);

            //Clean up
            borrowings.deleteById(id1);
            borrowings.deleteById(id2);
        }

        @Test
        void testBookEntryRepositoryFindByTitleAndStatus() {
            //Given
            List<BookEntry> bookEntries = new ArrayList<>();
            List<Borrowing> borrowedBooks = new ArrayList<>();
            TitleEntry titleEntry1 = new TitleEntry(2L, "Title1", "Author1",
                    LocalDate.of(2016, 06, 21), bookEntries);
            BookEntry bookEntry = new BookEntry(titleEntry1, Status.BORROWED, borrowedBooks);
            bookEntryRepository.save(bookEntry);
            Status status = bookEntry.getStatus();
            Optional<TitleEntry> title = Optional.ofNullable(bookEntry.getTitleEntry());
            //When
            List<BookEntry> savedTitleAndStatus = bookEntryRepository.findByTitleEntryAndStatus(title, status);

            //Then
            Assertions.assertEquals(1, savedTitleAndStatus.size());
            //Clean up
            Long id = savedTitleAndStatus.get(0).getId();
            bookEntryRepository.deleteById(id);
        }

        @Test
        void contextLoads() {

        }
    }


}
