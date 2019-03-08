package ru.regorov.rrvs.util.exceptions;

public class RestError {
    private final ErrorType type;
    private final String details;

    public RestError(ErrorType type, String detail) {
        this.type = type;
        this.details = detail;
    }
}
