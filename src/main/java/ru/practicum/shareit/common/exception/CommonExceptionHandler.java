package ru.practicum.shareit.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CommonExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> exceptionHandler(final NotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<String> exceptionHandler(final ForbiddenException exception) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(exception.getMessage());
    }
}
