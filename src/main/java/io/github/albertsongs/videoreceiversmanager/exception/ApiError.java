package io.github.albertsongs.videoreceiversmanager.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ApiError {
    private String status;
    private String message;
    private LocalDateTime timestamp;
}
