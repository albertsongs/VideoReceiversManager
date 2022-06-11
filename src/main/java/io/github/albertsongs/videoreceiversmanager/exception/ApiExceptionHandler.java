package io.github.albertsongs.videoreceiversmanager.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(ObjectNotFound.class)
    public ResponseEntity<Object> handleObjectNotFound(ObjectNotFound ex) {
        return new ResponseEntity<>(
                new ApiError(NOT_FOUND.getReasonPhrase(), ex.getMessage(), LocalDateTime.now()),
                NOT_FOUND);
    }

    @ExceptionHandler(ReceiverIdInvalidFormat.class)
    public ResponseEntity<Object> handleReceiverNotFound(ReceiverIdInvalidFormat ex) {
        return new ResponseEntity<>(
                new ApiError(BAD_REQUEST.getReasonPhrase(), ex.getMessage(), LocalDateTime.now()),
                BAD_REQUEST);
    }

    @ExceptionHandler(ReceiverIdInvalidValue.class)
    public ResponseEntity<Object> handleReceiverNotFound(ReceiverIdInvalidValue ex) {
        return new ResponseEntity<>(
                new ApiError(BAD_REQUEST.getReasonPhrase(), ex.getMessage(), LocalDateTime.now()),
                BAD_REQUEST);
    }
}

