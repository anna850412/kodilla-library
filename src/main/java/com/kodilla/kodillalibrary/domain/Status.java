package com.kodilla.kodillalibrary.domain;

public enum Status {
    AVAILABLE("av"),
    BORROWED("bo"),
    RESERVED("re");
    private String statusCode;

    private Status(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusCode() {
        return this.statusCode;
    }
}
