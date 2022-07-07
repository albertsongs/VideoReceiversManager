package io.github.albertsongs.videoreceiversmanager.exception;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ApiError {
    private String status;
    private String message;
    private String timestamp;

    @JsonCreator
    public ApiError(@JsonProperty("status") String status,
                    @JsonProperty("message") String message,
                    @JsonProperty("timestamp") String timestamp) {
        this.status = status;
        this.message = message;
        this.timestamp = timestamp;
    }
}
