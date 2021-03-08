package com.kodilla.kodillalibrary.service;

import com.kodilla.kodillalibrary.domain.BookEntry;
import com.kodilla.kodillalibrary.domain.Reader;
import com.kodilla.kodillalibrary.domain.Title;
import com.kodilla.kodillalibrary.repository.BookEntryRepository;
import com.kodilla.kodillalibrary.repository.BorrowedBooksRepository;
import com.kodilla.kodillalibrary.repository.ReaderRepository;
import com.kodilla.kodillalibrary.repository.TitleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DbService {
    private final BookEntryRepository bookEntryRepository;
    private final BorrowedBooksRepository borrowedBooksRepository;
    private final ReaderRepository readerRepository;
    private final TitleRepository titleRepository;

    public Title saveTitle(final Title title) {
        return titleRepository.save(title);
    }

    public Reader saveReader(final Reader reader) {
        return readerRepository.save(reader);
    }

    public BookEntry saveBookEntry(final BookEntry bookEntry) {
        return bookEntryRepository.save(bookEntry);
    }

    public void setBookEntryStatus(String status, Long id) {
        bookEntryRepository.findById(id).setStatus(status);
    }

    //    sprawdzenie ilości egzemplarzy danego tytułu dostępnych do wypożyczenia,
    public Long getNumberOfAvailableBooksByTitle(String title) {
        return bookEntryRepository.findByTitleAndStatus(title, "available").stream().count();
    }

    //    wypożyczenie książki,
    public void findAvailableBooksToBeBorrowedByTitle(String title) {
        bookEntryRepository.findByTitleAndStatus(title, "available").stream()
                .findFirst()
                .orElseGet(null)
                .setStatus("borrowed");
    }

    //    zwrot książki.
    public void findBorrowedBooksById(Long id) {
        bookEntryRepository.findById(id).setStatus("available");
    }
}
