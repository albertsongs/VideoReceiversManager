package io.github.albertsongs.videoreceiversmanager.exception;

public class ReceiverIdInvalidFormat extends RuntimeException {
    public ReceiverIdInvalidFormat(String message) {
        super("Invalid format of the receiver ID. The receiver ID must be in UUID format (see rfc4122). Details: " + message);
    }
}
