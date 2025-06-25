package com.example.lab4.exception;

public class UserNotFoundException extends RuntimeException {
    private final Long missingUserId;

    public UserNotFoundException(Long missingUserId) {
        super("User with id " + missingUserId + " doesn't exist");
        this.missingUserId = missingUserId;
    }

    public Long getMissingUserId() {
        return missingUserId;
    }
}
