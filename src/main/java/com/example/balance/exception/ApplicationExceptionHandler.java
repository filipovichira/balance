package com.example.balance.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    protected ResponseEntity<Exception> handleAccountNotFoundException() {
        return new ResponseEntity<>(new Exception("Not found"), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AlreadyExistsException.class)
    protected ResponseEntity<Exception> handleAlreadyExistsException() {
        return new ResponseEntity<>(new Exception("Already exists"), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(BalanceExceededException.class)
    protected ResponseEntity<Exception> handleBalanceExceededException() {
        return new ResponseEntity<>(new Exception("Exceeded balance for account"), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    protected ResponseEntity<Exception> handleIllegalArgumentException() {
        return new ResponseEntity<>(new Exception("Incorrect parameters"), HttpStatus.BAD_REQUEST);
    }

    private static class Exception {

        private final String message;

        public Exception(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }
}
