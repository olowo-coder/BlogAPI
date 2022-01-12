package com.example.blog.exception;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ErrorMessage {
    private Date timestamp;
    private List<String> errorMessage;

    public ErrorMessage(Date timestamp, List<String> errorMessage) {
        this.timestamp = timestamp;
        this.errorMessage = errorMessage;
    }
}
