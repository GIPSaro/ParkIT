package giorgiaipsaropassione.ParkIT.exceptions;

import java.time.LocalDateTime;

public class ErrorResponse {

    private int statusCode;
    private String message;
    private LocalDateTime timestamp;
    private String details;

    public ErrorResponse(int statusCode, String message, LocalDateTime timestamp, String details) {
        this.statusCode = statusCode;
        this.message = message;
        this.timestamp = timestamp;
        this.details = details;
    }

    // Getters e setters
    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
