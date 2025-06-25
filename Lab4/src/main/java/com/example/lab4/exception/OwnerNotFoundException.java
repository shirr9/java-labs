package com.example.lab4.exception;

public class OwnerNotFoundException extends RuntimeException {
    private final Long missingOwnerId;

    public OwnerNotFoundException(Long missingOwnerId) {
        super("Owner with id " + missingOwnerId + " doesn't exist");
        this.missingOwnerId = missingOwnerId;
    }

    public Long getMissingOwnerId() {
        return missingOwnerId;
    }
}
