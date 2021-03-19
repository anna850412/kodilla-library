package com.kodilla.kodillalibrary.exception;

public class BorrowedBookNotExistException extends Exception {
    public BorrowedBookNotExistException(final String message) {
        super(message);
    }
}
