package io.github.albertsongs.videoreceiversmanager.exception;

public class ReceiverNotFound extends RuntimeException {
    public ReceiverNotFound(String receiverId) {
        super("The receiver not found (id:" + receiverId + ")");
    }
}
