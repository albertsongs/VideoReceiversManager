package io.github.albertsongs.videoreceiversmanager.exception;

public class RequiredFieldIsEmpty extends RuntimeException {
    public RequiredFieldIsEmpty(String fieldName) {
        super("Required field " + fieldName + " is empty");
    }
}
