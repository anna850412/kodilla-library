package com.kodilla.kodillalibrary.service;

import com.kodilla.kodillalibrary.domain.BookEntry;
import com.kodilla.kodillalibrary.domain.Reader;
import com.kodilla.kodillalibrary.domain.Status;
import com.kodilla.kodillalibrary.domain.Title;
import com.kodilla.kodillalibrary.repository.BookEntryRepository;
import com.kodilla.kodillalibrary.repository.BorrowedBooksRepository;
import com.kodilla.kodillalibrary.repository.ReaderRepository;
import com.kodilla.kodillalibrary.repository.TitleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DbService {
    private final BookEntryRepository bookEntryRepository;
    private final BorrowedBooksRepository borrowedBooksRepository;
    private final ReaderRepository readerRepository;
    private final TitleRepository titleRepository;

    public List<Title> findAll(){
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
    public Title findTitleById(Long id){
        return titleRepository.findById(id);
    }

    public void setBookEntryStatus(Status status, Long id) {
        bookEntryRepository.findById(id).setStatus(status);
    }

    //    sprawdzenie ilości egzemplarzy danego tytułu dostępnych do wypożyczenia,
    public Long getNumberOfAvailableBooksByTitle(Title title) {
        return bookEntryRepository.findByTitleAndStatus(title, Status.AVAILABLE).stream().count();
    }

    //    wypożyczenie książki,
    public void findAvailableBooksToBeBorrowedByTitle(Title title) {
        bookEntryRepository.findByTitleAndStatus(title, Status.AVAILABLE).stream()
                .findFirst()
                .orElseGet(null)
                .setStatus(Status.BORROWED);
    }

    //    zwrot książki.
    public void findBorrowedBooksById(Long id) {
        bookEntryRepository.findById(id).setStatus(Status.AVAILABLE);
    }
}
