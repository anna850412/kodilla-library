package com.kodilla.kodillalibrary.service;

import com.kodilla.kodillalibrary.domain.BookEntry;
import com.kodilla.kodillalibrary.domain.Reader;
import com.kodilla.kodillalibrary.domain.Status;
import com.kodilla.kodillalibrary.domain.Title;
import com.kodilla.kodillalibrary.exception.BookNotExistException;
import com.kodilla.kodillalibrary.exception.BorrowedBookNotExistException;
import com.kodilla.kodillalibrary.repository.BookEntryRepository;
import com.kodilla.kodillalibrary.repository.BorrowedBooksRepository;
import com.kodilla.kodillalibrary.repository.ReaderRepository;
import com.kodilla.kodillalibrary.repository.TitleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DbService {
    private final BookEntryRepository bookEntryRepository;
    private final BorrowedBooksRepository borrowedBooksRepository;
    private final ReaderRepository readerRepository;
    private final TitleRepository titleRepository;

    public List<Title> findAll() {
        return titleRepository.findAll();
    }

    public Title saveTitle(final Title title) {
        return titleRepository.save(title);
    }

    public Reader saveReader(final Reader reader) {
        return readerRepository.save(reader);
    }

    public BookEntry saveBookEntry(final BookEntry bookEntry) {
        return bookEntryRepository.save(bookEntry);
    }

    public Optional<Title> findTitleById(Long id) {
        return titleRepository.findById(id);
    }

    public void setBookEntryStatus(Status status, Long id) throws BookNotExistException{
        bookEntryRepository.findById(id).stream()
                .findFirst()
                .orElseThrow(()->new BookNotExistException("Book does not exist"))
                .setStatus(status);

    }

    //    sprawdzenie ilości egzemplarzy danego tytułu dostępnych do wypożyczenia,
    public Long getNumberOfAvailableBooksByTitle(Optional<Title> title) {
        return bookEntryRepository.findByTitleAndStatus(title, Status.AVAILABLE).stream().count();
    }

    //    wypożyczenie książki,
    public void findAvailableBooksToBeBorrowedByTitle(Optional<Title> title) throws BookNotExistException {
        bookEntryRepository.findByTitleAndStatus(title, Status.AVAILABLE).stream()
                .findFirst()
                .orElseThrow(()->new BookNotExistException("Book does not exist"))
                .setStatus(Status.BORROWED);
    }

    //    zwrot książki.
    public void returnBorrowedBooksById(Long id) throws BorrowedBookNotExistException{
        bookEntryRepository.findById(id).stream()
                .findFirst()
                .orElseThrow(()->new BorrowedBookNotExistException("Borrowed book does not exist"))
                .setStatus(Status.AVAILABLE);
    }
}
