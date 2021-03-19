package com.kodilla.kodillalibrary.exception;

import java.util.function.Supplier;

public class BookNotExistException extends Exception {
    public BookNotExistException(final String message) {
        super(message);
    }
}
