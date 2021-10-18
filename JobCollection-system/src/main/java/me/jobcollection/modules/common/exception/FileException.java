package me.jobcollection.modules.common.exception;

import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

/**
 * @author Hongrry
 * @create 2021-10-05 15:29
 */
public class FileException extends RuntimeException {
    public FileException(String msg) {
        super(msg);
    }

    public FileException(HttpStatus status, String msg) {
        super(msg);
    }
}
