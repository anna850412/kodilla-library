package com.kodilla.kodillalibrary.service;

import com.kodilla.kodillalibrary.domain.*;
import com.kodilla.kodillalibrary.exception.BookNotExistException;
import com.kodilla.kodillalibrary.exception.ReaderNotExistException;
import com.kodilla.kodillalibrary.exception.TitleEntryNotExistException;
import com.kodilla.kodillalibrary.repository.BookEntryRepository;
import com.kodilla.kodillalibrary.repository.Borrowings;
import com.kodilla.kodillalibrary.repository.ReaderRepository;
import com.kodilla.kodillalibrary.repository.TitleEntryRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DbService {
    private final BookEntryRepository bookEntryRepository;
    private final Borrowings borrowingBooksRepository;
    private final ReaderRepository readerRepository;
    private final TitleEntryRepository titleEntryRepository;

    public List<TitleEntry> findAllTitleEntries() {
        return titleEntryRepository.findAll();
    }

    public TitleEntry saveTitle(final TitleEntry title) {
        return titleEntryRepository.save(title);
    }

    public Reader saveReader(final Reader reader) {
        return readerRepository.save(reader);
    }

    public BookEntry saveBookEntry(final BookEntry bookEntry) {
        return bookEntryRepository.save(bookEntry);
    }

    public Borrowing saveBorrowing(Borrowing borrowing) {
        return borrowingBooksRepository.save(borrowing);
    }

    public Optional<BookEntry> findBookEntryById(Long id) {
        return bookEntryRepository.findById(id);
    }

    public Optional<TitleEntry> findTitleEntryById(Long id) {
        return titleEntryRepository.findById(id);
    }

    public Optional<TitleEntry> findTitleEntryByTileAndAuthor(String title, String author) {
        return titleEntryRepository.findByTitleAndAuthor(title, author);
    }

    public void setBookEntryStatus(Status status, Long id) throws BookNotExistException {
        bookEntryRepository.findById(id)
                .orElseThrow(() -> new BookNotExistException("Book does not exist"))
                .setStatus(status);
    }

    //    sprawdzenie ilości egzemplarzy danego tytułu dostępnych do wypożyczenia,
    public Long getNumberOfAvailableBooksByTitleEntry(String title, String author) {
        Optional<TitleEntry> titleEntry = titleEntryRepository.findByTitleAndAuthor(title, author);
        return bookEntryRepository.findByTitleEntryAndStatus(titleEntry, Status.AVAILABLE).stream().count();
    }

    //    wypożyczenie książki,
    public void findAvailableBooksToBeBorrowedByTitle(Optional<TitleEntry> title) throws BookNotExistException {
        bookEntryRepository.findByTitleEntryAndStatus(title, Status.AVAILABLE).stream()
                .findFirst()
                .orElseThrow(() -> new BookNotExistException("Book does not exist"))
                .setStatus(Status.BORROWED);
    }

    @SneakyThrows
    public void rentBook(BookRentalDto bookRentalDto) {
        TitleEntry titleEntry = findTitleEntryById(bookRentalDto.getTitleId()).orElseThrow(() -> new TitleEntryNotExistException("Title Entry doesn't exist"));
        BookEntry availableBookEntry = bookEntryRepository.findByTitleEntryAndStatus(
                Optional.of(titleEntry), Status.AVAILABLE).stream()
                .findFirst().orElseThrow(() -> new BookNotExistException("No available book entry for this title"));
        Reader reader = readerRepository.findById(bookRentalDto.getReaderId()).orElseThrow(() -> new ReaderNotExistException("Reader not exist on library"));

        availableBookEntry.setStatus(Status.BORROWED);
        Borrowing borrowing = new Borrowing(availableBookEntry, reader, LocalDate.now(), null);

        reader.getBorrowings().add(borrowing);
        availableBookEntry.getBorrowings().add(borrowing);
        saveReader(reader);
        saveBookEntry(availableBookEntry);
        saveBorrowing(borrowing); //todo te trzy rzeczy powinny isc w jednej transakcji.

    }

    public void returnBook(ReturnBookDto returnBookDto) {
        Optional<TitleEntry> title = findTitleEntryById(returnBookDto.getBookEntryId());
        Optional<Reader> reader = Optional.of(readerRepository.findById(returnBookDto.getReaderId()).get());
        Optional<BookEntry> rentedBookEntry = bookEntryRepository.findByTitleEntryAndStatus(title, Status.BORROWED).
                stream().
                findFirst();
        if (rentedBookEntry.isPresent()
                && readerRepository.existsById(returnBookDto.getReaderId())
                && bookEntryRepository.existsById(returnBookDto.getBookEntryId())) {
            BookEntry bookEntry = rentedBookEntry.get();
            bookEntry.setStatus(Status.AVAILABLE);
            bookEntryRepository.save(bookEntry); // todo robisz metoge saveBookEntry w dbService i z niej nie korzystasz
        }
    }

}
