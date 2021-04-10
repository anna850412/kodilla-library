package com.kodilla.kodillalibrary.service;

import com.kodilla.kodillalibrary.domain.*;
import com.kodilla.kodillalibrary.exception.BookNotExistException;
import com.kodilla.kodillalibrary.repository.BookEntryRepository;
import com.kodilla.kodillalibrary.repository.BorrowedBooksRepository;
import com.kodilla.kodillalibrary.repository.ReaderRepository;
import com.kodilla.kodillalibrary.repository.TitleEntryRepository;
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

    public BorrowedBooks saveBorrowedBooks(BorrowedBooks borrowedBooks) {
        return borrowedBooksRepository.save(borrowedBooks);
    }

    public Optional<BookEntry> findBookEntryById(Long id){
        return bookEntryRepository.findById(id);
    }

    public Optional<TitleEntry> findTitleEntryById(Long id) {
        return titleEntryRepository.findById(id);
    }

    public Optional<TitleEntry> findTitleEntryByTileAndAuthor(String title, String author) {
        return titleEntryRepository.findByTitleAndAuthor(title, author);
    }

    public void setBookEntryStatus(Status status, Long id) throws BookNotExistException {
        bookEntryRepository.findById(id).stream()
                .findFirst()
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

    //    public void bookRental(BookRentalDto bookRentalDto){
//        if(readerRepository.existsById(bookRentalDto.getReaderId())&&
//        bookEntryRepository.existsById(bookRentalDto.getTitleId())){
//     jak skorzystać z metody wyszukania po title i status?
//                bookEntryRepository.findByTitleAndStatus(bookRentalDto.getTitle(),Status.AVAILABLE)
//        borrowedBooksRepository.bookRental(bookRentalDto.getReaderId(), bookRentalDto.getTitleId());
// jak teraz zmienić status na Borrowed (jak w findAvailableBooksToBeBorrowedByTitle)
// bookEntryRepository.findByTitleAndStatus(bookRentalDto.getTitle(), )
//        }
//    }
    public void bookRental(BookRentalDto bookRentalDto) {
        TitleEntry title = findTitleEntryById(bookRentalDto.getTitleId()).get();
        Optional<BookEntry> availableBookEntry = bookEntryRepository.findByTitleEntryAndStatus(
                Optional.of(title), Status.AVAILABLE).stream()
                .findFirst();
        if (availableBookEntry.isPresent()
                && readerRepository.existsById(bookRentalDto.getReaderId())
                && bookEntryRepository.existsById(bookRentalDto.getTitleId())) {

            BookEntry bookEntry = availableBookEntry.get();
            bookEntry.setStatus(Status.BORROWED);
            bookEntryRepository.save(bookEntry);
        }
    }

    public void returnBook(ReturnBookDto returnBookDto) {
        Optional<TitleEntry> title = findTitleEntryById(returnBookDto.getBookEntryId());
        Optional<Reader> reader = Optional.of(readerRepository.findById(returnBookDto.getReaderId()).get());
        Optional<BookEntry> rentedBookEntry = bookEntryRepository.findByTitleEntryAndStatus(
                title, Status.BORROWED
        ).stream()
//                bookEntryRepository.findByReaderAndStatus(
//                reader, Status.BORROWED).stream()
                .findFirst();
        if (rentedBookEntry.isPresent()
                && readerRepository.existsById(returnBookDto.getReaderId())
                && bookEntryRepository.existsById(returnBookDto.getBookEntryId())) {
            BookEntry bookEntry = rentedBookEntry.get();
            bookEntry.setStatus(Status.AVAILABLE);
            bookEntryRepository.save(bookEntry);
        }
    }

//    public void returnBorrowedBooksById(Long id) throws BorrowedBookNotExistException {
//        bookEntryRepository.findById(id).stream()
//                .findFirst()
//                .orElseThrow(() -> new BorrowedBookNotExistException("Borrowed book does not exist"))
//                .setStatus(Status.AVAILABLE);
//    }
//    public void returnBook(ReturnBookDto returnBookDto) {
//        if (readerRepository.existsById(returnBookDto.getReaderId()) &&
//                bookEntryRepository.existsById(returnBookDto.getBookEntryId())) {
//            borrowedBooksRepository.returnBook(returnBookDto.getReaderId(), returnBookDto.getBookEntryId());
//        }
    //    wypożyczenie książki po titleId i readerId
//    public void findAvailableBooksToBeBorrowedByTitleIdAndReaderId(Optional<Title> titleId, Optional<Reader> readerId)
//            throws BookNotExistException {
//        bookEntryRepository.findByTitleIdAndReaderId(titleId, readerId).stream()
//                .findFirst()
//                .orElseThrow(() -> new BookNotExistException("Book does not exist"))
//                .setStatus(Status.BORROWED);
//    }
}
