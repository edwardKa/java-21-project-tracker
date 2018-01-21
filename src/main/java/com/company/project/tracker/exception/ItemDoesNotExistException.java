package com.company.project.tracker.exception;

public class ItemDoesNotExistException extends RuntimeException {

    public ItemDoesNotExistException() {
        super();
    }

    public ItemDoesNotExistException(String message) {
        super(message);
    }
}
