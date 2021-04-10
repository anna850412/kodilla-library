package com.kodilla.kodillalibrary;

import com.kodilla.kodillalibrary.controller.LibraryController;
import com.kodilla.kodillalibrary.domain.*;
import com.kodilla.kodillalibrary.mapper.BookEntryMapper;
import com.kodilla.kodillalibrary.mapper.BorrowedBooksMapper;
import com.kodilla.kodillalibrary.mapper.ReaderMapper;
import com.kodilla.kodillalibrary.mapper.TitleEntryMapper;
import com.kodilla.kodillalibrary.repository.BookEntryRepository;
import com.kodilla.kodillalibrary.repository.BorrowedBooksRepository;
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
    private BorrowedBooksRepository borrowedBooksRepository;
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
    private BorrowedBooksMapper borrowedBooksMapper;
    @Autowired
    private ReaderMapper readerMapper;
    @Autowired
    private TitleEntryMapper titleEntryMapper;
    @Mock
    private BookEntry bookEntry;
    @Mock
    private ReaderDto readerDto;
    @Mock
    private BorrowedBooksDto borrowedBooksDto;
    @Mock
    private TitleEntryDto titleEntryDto;

    /*   @Nested
       @DisplayName("SQL Tests")
       class ManyToMany {
           @Test
           void testSaveManyToMany() {
               //Given
   //            List<BookEntry> bookEntries1 = new ArrayList<>();
   //            List<BookEntry> bookEntries2 = new ArrayList<>();
               Long bookEntryId1 = 1L;
               Long bookEntryId2 = 2L;
               Long bookEntryId3 = 3L;

               Title title1 = new Title(1L,"Title1", "Author1",
                       LocalDate.of(2010, 3, 22),bookEntryId1);
               Title title2 = new Title(2L,"Title2", "Author2",
                       LocalDate.of(2015, 11, 22), bookEntryId2);
               Title title3 = new Title(3L,"Title3", "Author3",
                       LocalDate.of(2015, 11, 22), bookEntryId3);
               List<BorrowedBooks> borrowedBooks = new ArrayList<>();
               BookEntry bookEntry1 = new BookEntry(1L,title1, Status.AVAILABLE, borrowedBooks );
               BookEntry bookEntry2 = new BookEntry(2L,title2, Status.BORROWED, borrowedBooks);
               BookEntry bookEntry3 = new BookEntry(3L,title3, Status.RESERVED, borrowedBooks);
   //            bookEntries1.add(bookEntry1);
   //            bookEntries2.add(bookEntry2);
   //            bookEntries2.add(bookEntry3);
               Reader reader1 = new Reader(1L, "Kasia", "Wozniak",
                       LocalDate.of(2011, 7, 11), borrowedBooks);
               Reader reader2 = new Reader(2L, "Adam", "Kot",
                       LocalDate.of(2021, 2, 11), borrowedBooks);
               BorrowedBooks borrowedBooks1 = new BorrowedBooks(
                       1L, bookEntries1, reader1, LocalDate.of(2011, 8, 11),
                       LocalDate.of(2021, 1, 22));
               BorrowedBooks borrowedBooks2 = new BorrowedBooks(
                       2L, bookEntries2, reader2, LocalDate.of(2011, 8, 11),
                       LocalDate.of(2021, 1, 22));
               bookEntry1.getBorrowedBooks().add(borrowedBooks1);
               bookEntry2.getBorrowedBooks().add(borrowedBooks2);
               bookEntry3.getBorrowedBooks().add(borrowedBooks2);
               borrowedBooks1.getBookEntries().add(bookEntry1);
               borrowedBooks2.getBookEntries().add(bookEntry2);
               borrowedBooks2.getBookEntries().add(bookEntry3);
               //When
               bookEntryRepository.save(bookEntry1);
               Long id1 = bookEntry1.getId();
               bookEntryRepository.save(bookEntry2);
               Long id2 = bookEntry2.getId();
               bookEntryRepository.save(bookEntry3);
               Long id3 = bookEntry3.getId();
               borrowedBooksRepository.save(borrowedBooks1);
               Long id4 = borrowedBooks1.getId();
               borrowedBooksRepository.save(borrowedBooks2);
               Long id5 = borrowedBooks2.getId();
               //Then
               Assertions.assertNotEquals(0, id1);
               Assertions.assertNotEquals(0, id2);
               Assertions.assertNotEquals(0, id3);
               Assertions.assertNotEquals(0, id4);
               Assertions.assertNotEquals(0, id5);

           }
       }
       */
    @Nested
//    @Disabled
    @DisplayName("Library Controller Tests")
    class LibraryControllerTests {

        @Test
        void testCreateBookEntry() {
            //Given
            List<BookEntry> bookEntries = new ArrayList<>();
            List<BorrowedBooks> borrowedBooks = new ArrayList<>();
            TitleEntry titleEntry = new TitleEntry(1L, 1L, "Title", "Author",
                    LocalDate.of(2021, 4, 22), bookEntries);
            BookEntry bookEntry = new BookEntry(1L, titleEntry, Status.AVAILABLE, borrowedBooks);
            LibraryController controller = new LibraryController(service, bookEntryMapper, readerMapper, titleEntryMapper, borrowedBooksMapper);
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
            List<BorrowedBooks> borrowedBooks = new ArrayList<>();
            Reader reader = new Reader(1L, "Ala", "Wilk",
                    LocalDate.of(2018, 4, 23), borrowedBooks);
            LibraryController controller = new LibraryController(service, bookEntryMapper,
                    readerMapper, titleEntryMapper, borrowedBooksMapper);
            //When
            controller.createReader(readerMapper.mapToReaderDto(reader));
            //Then
            verify(service, times(1)).saveReader(reader);
        }

        @Test
        void testCreateTitle() {

            //Given
            List<BookEntry> bookEntries = new ArrayList<>();
            TitleEntry titleEntry = new TitleEntry(1L, 2L, "test Title", "test Author",
                    LocalDate.of(2010, 9, 11), bookEntries);
            LibraryController controller = new LibraryController(service, bookEntryMapper, readerMapper, titleEntryMapper, borrowedBooksMapper);
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
            List<BorrowedBooks> borrowedBooks = new ArrayList<>();
            TitleEntry titleEntry = new TitleEntry(1L, 1L, "Title", "Author",
                    LocalDate.of(2010, 3, 21), bookEntries);
            BookEntry bookEntry = new BookEntry(1L, titleEntry, Status.AVAILABLE, borrowedBooks);
            //When
            BookEntry savedStatus = bookEntryRepository.save(bookEntry);
            //Then
            Assertions.assertEquals(savedStatus, Status.AVAILABLE);
        }

        @Test
        void testHowManyBookEntriesAreAvailable() {
            //Given
            TitleEntryDto titleEntryDto = new TitleEntryDto(1L,"Title1","Author",
                    LocalDate.of(2010, 3, 2), 1L);
            LibraryController controller = new LibraryController(service, bookEntryMapper, readerMapper, titleEntryMapper, borrowedBooksMapper);
            //When
            controller.howManyBookEntriesAreAvailable(titleEntryDto.getTitle());
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
            List<BorrowedBooks> borrowedBooks = new ArrayList<>();
            Reader reader1 = new Reader(1L, "Anna", "Kowalska",
                    LocalDate.of(2019, 03, 12), borrowedBooks);
            borrowedBooks.add(new BorrowedBooks(1L, bookEntries, reader1,
                    LocalDate.of(2021, 01, 15), LocalDate.of(2021, 03, 05)));
            Reader reader2 = new Reader(2L, "Piotr", "Nowak",
                    LocalDate.of(2018, 06, 23), borrowedBooks);
            borrowedBooks.add(new BorrowedBooks(2L, bookEntries, reader2, LocalDate.of(2020, 07, 27),
                    LocalDate.of(2020, 11, 11)));
            BorrowedBooks borrowedBooks1 = new BorrowedBooks(1L, bookEntries, reader1,
                    LocalDate.of(2021, 03, 10), null);
            BorrowedBooks borrowedBooks2 = new BorrowedBooks(2L, bookEntries, reader2,
                    LocalDate.of(2021, 02, 10), LocalDate.of(2021, 03, 11));
            borrowedBooks.add(borrowedBooks1);
            borrowedBooks.add(borrowedBooks2);
            reader1.setBorrowedBooks((List<BorrowedBooks>) borrowedBooks1);
            reader2.setBorrowedBooks((List<BorrowedBooks>) borrowedBooks2);
            reader1.getBorrowedBooks().add(borrowedBooks1);
            reader2.getBorrowedBooks().add(borrowedBooks2);
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
            List<BorrowedBooks> borrowedBooks = new ArrayList<>();
            List<BookEntry> bookEntries = new ArrayList<>();
            TitleEntry title1 = new TitleEntry(1L, 1L, "Title1", "Author1",
                    LocalDate.of(2016, 06, 21), bookEntries);
            TitleEntry title2 = new TitleEntry(2L, 2L, "Title2", "Author2",
                    LocalDate.of(2011, 12, 11), bookEntries);
            BookEntry bookEntry1 = new BookEntry(1L, title1, Status.BORROWED, borrowedBooks);
            BookEntry bookEntry2 = new BookEntry(2L, title2, Status.RESERVED, borrowedBooks);
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
            List<BorrowedBooks> borrowedBooks = new ArrayList<>();
            TitleEntry title1 = new TitleEntry(1L, 1L, "Title1", "Author1",
                    LocalDate.of(2016, 06, 21), bookEntries);
            BookEntry bookEntry1 = new BookEntry(1L, title1, Status.BORROWED, borrowedBooks);
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
            TitleEntry title1 = new TitleEntry(1L, 1L, "Title1", "Author1",
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
            List<BorrowedBooks> borrowedBooks = new ArrayList<>();
            TitleEntry titleEntry = new TitleEntry(1L, 1L, "Title1", "Author1",
                    LocalDate.of(2016, 06, 21), bookEntries);
            BookEntry bookEntry = new BookEntry(1L, titleEntry, Status.BORROWED, borrowedBooks);
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
            TitleEntry title1 = new TitleEntry(1L, 1L, "Title1", "Author1",
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
            List<BorrowedBooks> borrowedBooks = new ArrayList<>();
            TitleEntry title1 = new TitleEntry(1L, 1L, "Title1", "Author1",
                    LocalDate.of(2021, 01, 04), bookEntries);
            TitleEntry title2 = new TitleEntry(2L, 2L, "Title2", "Author2",
                    LocalDate.of(2017, 12, 14), bookEntries);
            BookEntry bookEntry1 = new BookEntry(1L, title1, Status.RESERVED, borrowedBooks);
            BookEntry bookEntry2 = new BookEntry(2L, title2, Status.AVAILABLE, borrowedBooks);
//            bookEntries.add(bookEntry1);
//            bookEntries.add(bookEntry2);

            Reader reader1 = new Reader(1L, "Anna", "Kowalska",
                    LocalDate.of(2019, 03, 12), borrowedBooks);
            Reader reader2 = new Reader(2L, "Piotr", "Nowak",
                    LocalDate.of(2018, 06, 23), borrowedBooks);
            BorrowedBooks borrowedBook1 = new BorrowedBooks(1L, bookEntry1, reader1,
                    LocalDate.of(2021, 01, 15), LocalDate.of(2021, 03, 05));
            BorrowedBooks borrowedBook2 = new BorrowedBooks(2L, bookEntry2, reader2, LocalDate.of(2020, 07, 27),
                    LocalDate.of(2020, 11, 11));

            borrowedBook1.setBookEntries(bookEntry1);
            borrowedBook2.setBookEntries(bookEntry2);
//            borrowedBook1.getBookEntries().add(bookEntry1);
//            borrowedBook2.getBookEntries().add(bookEntry2);

            //When
            borrowedBooksRepository.save(borrowedBook1);
            Long id1 = borrowedBook1.getId();
            borrowedBooksRepository.save(borrowedBook2);
            Long id2 = borrowedBook2.getId();

            //Then
            Assertions.assertNotEquals(0, id1);
            Assertions.assertNotEquals(0, id2);

            //Clean up
            borrowedBooksRepository.deleteById(id1);
            borrowedBooksRepository.deleteById(id2);
        }

        @Test
        void testBookEntryRepositoryFindByTitleAndStatus() {
            //Given
            List<BookEntry> bookEntries = new ArrayList<>();
            List<BorrowedBooks> borrowedBooks = new ArrayList<>();
            TitleEntry titleEntry1 = new TitleEntry(1L, 2L, "Title1", "Author1",
                    LocalDate.of(2016, 06, 21), bookEntries);
            BookEntry bookEntry = new BookEntry(2L, titleEntry1, Status.BORROWED, borrowedBooks);
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
