package com.example.petservice.exception;
import java.util.Set;

public class PetsNotFoundException extends RuntimeException {
    private final Set<Long> missingIds;

    public PetsNotFoundException(Set<Long> missingIds) {
        super("Pets with ids " + missingIds + " don't exist");
        this.missingIds = missingIds;
    }

    public Set<Long> getMissingIds() {
        return missingIds;
    }
}
