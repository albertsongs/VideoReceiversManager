package io.github.albertsongs.videoreceiversmanager.exception;

public class ObjectNotFound extends RuntimeException {
    public ObjectNotFound(String objectName, String receiverId) {
        super("The " + objectName + " not found (id:" + receiverId + ")");
    }
}
