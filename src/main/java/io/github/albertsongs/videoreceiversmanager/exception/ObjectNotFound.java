package io.github.albertsongs.videoreceiversmanager.exception;

public class ObjectNotFound extends RuntimeException {
    public ObjectNotFound(String objectName, String objectId) {
        super("The " + objectName + " not found (id:" + objectId + ")");
    }
}
