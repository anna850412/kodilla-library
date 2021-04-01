package com.kodilla.kodillalibrary.service;

import com.kodilla.kodillalibrary.domain.*;
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

    public BorrowedBooks saveBorrowedBooks(BorrowedBooks borrowedBooks) {
        return borrowedBooksRepository.save(borrowedBooks);
    }

    public Optional<Title> findTitleById(Long id) {
        return titleRepository.findById(id);
    }

    public void setBookEntryStatus(Status status, Long id) throws BookNotExistException {
        bookEntryRepository.findById(id).stream()
                .findFirst()
                .orElseThrow(() -> new BookNotExistException("Book does not exist"))
                .setStatus(status);

    }

    //    sprawdzenie ilości egzemplarzy danego tytułu dostępnych do wypożyczenia,
    public Long getNumberOfAvailableBooksByTitle(Optional<Title> title) {
        return bookEntryRepository.findByTitleAndStatus(title, Status.AVAILABLE).stream().count();
    }
//    public void returnBook(ReturnBookDto returnBookDto){
//       if (readerRepository.existsById(returnBookDto.getReaderId())&&
//               bookEntryRepository.existsById(returnBookDto.getBookEntryId())){
//           borrowedBooksRepository.returnBook(returnBookDto.getReaderId(), returnBookDto.getBookEntryId());
//       };
//    }

    //    wypożyczenie książki,
    public void findAvailableBooksToBeBorrowedByTitle(Optional<Title> title) throws BookNotExistException {
        bookEntryRepository.findByTitleAndStatus(title, Status.AVAILABLE).stream()
                .findFirst()
                .orElseThrow(() -> new BookNotExistException("Book does not exist"))
                .setStatus(Status.BORROWED);
    }
    public void bookRental(BookRentalDto bookRentalDto){
        if(readerRepository.existsById(bookRentalDto.getReaderId())&&
        bookEntryRepository.existsById(bookRentalDto.getTitleId())){
//     jak skorzystać z metody wyszukania po title i status?
            //     bookEntryRepository.findByTitleAndStatus(bookRentalDto.getTitle(),Status.AVAILABLE)
        borrowedBooksRepository.bookRental(bookRentalDto.getReaderId(), bookRentalDto.getTitleId());
// jak teraz zmienić status na Borrowed (jak w findAvailableBooksToBeBorrowedByTitle)
// bookEntryRepository.findByTitleAndStatus(bookRentalDto.getTitle(), )
        }
    }

    public void returnBorrowedBooksById(Long id) throws BorrowedBookNotExistException {
        bookEntryRepository.findById(id).stream()
                .findFirst()
                .orElseThrow(() -> new BorrowedBookNotExistException("Borrowed book does not exist"))
                .setStatus(Status.AVAILABLE);
    }
    //    wypożyczenie książki po titleId i readerId
//    public void findAvailableBooksToBeBorrowedByTitleIdAndReaderId(Optional<Title> titleId, Optional<Reader> readerId)
//            throws BookNotExistException {
//        bookEntryRepository.findByTitleIdAndReaderId(titleId, readerId).stream()
//                .findFirst()
//                .orElseThrow(() -> new BookNotExistException("Book does not exist"))
//                .setStatus(Status.BORROWED);
//    }
}
