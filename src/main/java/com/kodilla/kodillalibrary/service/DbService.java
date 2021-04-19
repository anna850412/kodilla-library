package com.kodilla.kodillalibrary.service;

import com.kodilla.kodillalibrary.domain.*;
import com.kodilla.kodillalibrary.exception.BookEntryNotExistException;
import com.kodilla.kodillalibrary.exception.ReaderNotExistException;
import com.kodilla.kodillalibrary.exception.ReturnBookNotExistException;
import com.kodilla.kodillalibrary.exception.TitleEntryNotExistException;
import com.kodilla.kodillalibrary.repository.BookEntryRepository;
import com.kodilla.kodillalibrary.repository.BorrowingRepository;
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
    private final BorrowingRepository borrowingRepository;
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
        return borrowingRepository.save(borrowing);
    }

    public Optional<TitleEntry> findTitleEntryById(Long id) {
        return titleEntryRepository.findById(id);
    }

    public Long getNumberOfAvailableBooksByTitleEntry(String title, String author) {
        TitleEntry titleEntry = titleEntryRepository.findByTitleAndAuthor(title, author);
        return bookEntryRepository.findByTitleEntryAndStatus(titleEntry, Status.AVAILABLE).stream().count();
    }

    @SneakyThrows
    public void rentBook(BookRentalDto bookRentalDto) {
        TitleEntry titleEntry = findTitleEntryById(bookRentalDto.getTitleId()).orElseThrow(() -> new TitleEntryNotExistException("Title Entry not exist in our library"));
        BookEntry availableBookEntry = bookEntryRepository.findByTitleEntryAndStatus(
                titleEntry, Status.AVAILABLE).stream()
                .findFirst().orElseThrow(() -> new BookEntryNotExistException("No available book entry for this title"));
        Reader reader = readerRepository.findById(bookRentalDto.getReaderId()).orElseThrow(() -> new ReaderNotExistException("Reader not exist in our library"));
        availableBookEntry.setStatus(Status.BORROWED);
        Borrowing borrowing = new Borrowing(availableBookEntry, reader, LocalDate.now(), null);
        reader.getBorrowings().add(borrowing);
        availableBookEntry.getBorrowings().add(borrowing);
        saveReader(reader);
        saveBookEntry(availableBookEntry);
        saveBorrowing(borrowing);

    }

    @SneakyThrows
    public void returnBook(ReturnBookDto returnBookDto) {

        BookEntry bookEntry = bookEntryRepository.findById(returnBookDto.getBookEntryId()).orElseThrow(() -> new BookEntryNotExistException("Book Entry with this id not exist in our library "));
        Reader reader = readerRepository.findById(returnBookDto.getReaderId()).orElseThrow(() -> new ReaderNotExistException("Reader not exist in our library"));
        bookEntry.setStatus(Status.AVAILABLE);
        Borrowing borrowing =
                bookEntry.getBorrowings()
                        .stream()
                        .filter(b -> b.getReturnDate() == null && b.getReader() == reader)
                        .findFirst()
//                        .get();
                        .orElseThrow(() -> new ReturnBookNotExistException("Return book don't exist"));
        borrowing.setReturnDate(LocalDate.now());
        saveBookEntry(bookEntry);
        saveBorrowing(borrowing);
    }
}


