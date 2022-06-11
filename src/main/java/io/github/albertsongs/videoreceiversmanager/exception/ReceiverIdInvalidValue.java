package io.github.albertsongs.videoreceiversmanager.exception;

public class ReceiverIdInvalidValue extends RuntimeException{
    public ReceiverIdInvalidValue(String id) {
        super("The receiver ID in the request body must be equal to the ID from the path or be empty (id: " + id + ")");
    }
}
