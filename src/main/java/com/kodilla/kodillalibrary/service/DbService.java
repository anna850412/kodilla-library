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

    public TitleEntry findTitleEntryByTileAndAuthor(String title, String author) {
        return titleEntryRepository.findByTitleAndAuthor(title, author);
    }

    public void setBookEntryStatus(Status status, Long id) throws BookNotExistException {
        bookEntryRepository.findById(id)
                .orElseThrow(() -> new BookNotExistException("Book does not exist"))
                .setStatus(status);
    }

    //    sprawdzenie ilości egzemplarzy danego tytułu dostępnych do wypożyczenia,
    public Long getNumberOfAvailableBooksByTitleEntry(String title, String author) {
        TitleEntry titleEntry = titleEntryRepository.findByTitleAndAuthor(title, author);
        return bookEntryRepository.findByTitleEntryAndStatus(titleEntry, Status.AVAILABLE).stream().count();
    }

    public Long getNumberOfAvailableBooksById(Long id) {
        return bookEntryRepository.findById(id).stream().count();
    }

    //    wypożyczenie książki,
    public void findAvailableBooksToBeBorrowedByTitle(TitleEntry titleEntry) throws BookNotExistException {
        bookEntryRepository.findByTitleEntryAndStatus(titleEntry, Status.AVAILABLE).stream()
                .findFirst()
                .orElseThrow(() -> new BookNotExistException("Book does not exist"))
                .setStatus(Status.BORROWED);
    }

    @SneakyThrows
    public void rentBook(BookRentalDto bookRentalDto) {
        TitleEntry titleEntry = findTitleEntryById(bookRentalDto.getTitleId()).orElseThrow(() -> new TitleEntryNotExistException("Title Entry doesn't exist"));
        BookEntry availableBookEntry = bookEntryRepository.findByTitleEntryAndStatus(
                titleEntry, Status.AVAILABLE).stream()
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

    @SneakyThrows
    public void returnBook(ReturnBookDto returnBookDto) {
        //   TitleEntry titleEntry = findTitleEntryById(returnBookDto.getBookEntryId()).orElseThrow(() -> new TitleEntryNotExistException("Title Entry doesn't exist"));
        // todo to jest złe. nie mozesz szukac titleEntry po BookEntryID. po pierwsze a po drugie to TitleEntry Ci tu w ogole nie jest potrzebne bo przeciez zwracasz ksiazke bookEntry a nie tytuł title entry
        BookEntry bookEntry = bookEntryRepository.findById(returnBookDto.getBookEntryId()).orElseThrow(() -> new BookNotExistException("Nie ma ksisazki o takim id w naszej bibliotece"));

        Reader reader = readerRepository.findById(returnBookDto.getReaderId()).orElseThrow(() -> new ReaderNotExistException("Reader not exist on library"));

//        BookEntry rentedBookEntry = bookEntryRepository.findByTitleEntryAndStatus(
//                titleEntry, Status.BORROWED).
//                stream().
//                findFirst().orElseThrow(() -> new BookNotExistException("No available book entry for this title"));
        // todo po co to skoro wiesz jaka ksiazke oddajesz masz jej id w retudnBookDTO
//
        bookEntry.setStatus(Status.AVAILABLE);
        //   Borrowing borrowing = new Borrowing(rentedBookEntry, reader,
        // todo tak jak pisałem nie tworzysz nowego borrowing, tylko na tym co jest przypisane do ksiazki ustawiasz date zwrotu

//            reader.getBorrowings().get(0).getRentalDate() ,
//                bookEntry.getBorrowings().stream()
//                        .filter(borrowing1 -> borrowing1.getReader().getId().equals(returnBookDto.getReaderId()))
//                        .filter(borrowing1 -> borrowing1.getReturnDate().isBefore(LocalDate.now()))
//                        .findFirst().get().getRentalDate(),
        // todo na pewno nie get 0

        //           LocalDate.now()); //todo a to co za linika jest?

        Borrowing borrowing = bookEntry.getBorrowings()
                .stream()
                .filter(b -> b.getReturnDate() == null && b.getReader() == reader)
                .findFirst().get();

        borrowing.setReturnDate(LocalDate.now());

//        reader.getBorrowings().add(borrowing);
//        rentedBookEntry.getBorrowings().add(borrowing);
     //   saveReader(reader); //todo chyba nie potrzebne bo na readerze nic nie zmienialismy
        saveBookEntry(bookEntry);
        saveBorrowing(borrowing);
    }
}


