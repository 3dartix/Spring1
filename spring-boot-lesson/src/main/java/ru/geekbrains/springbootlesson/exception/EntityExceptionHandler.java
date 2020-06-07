package ru.geekbrains.springbootlesson.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class EntityExceptionHandler extends ResponseEntityExceptionHandler {

//    @ExceptionHandler(value = { Exception.class })
//    public ResponseEntity<Object> handleAnyException(Exception ex, WebRequest request) {
//        return new ResponseEntity<>(
//                ex.getMessage(), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
//    }

    @ExceptionHandler(NotFondException.class)
    public ResponseEntity<Object> notFoundExceptionHandler(NotFondException exception, WebRequest request) {
        String errorDescription = exception.getLocalizedMessage();

        if(errorDescription == null) {
            errorDescription = exception.toString();
        }

        ErrorMessage error = new ErrorMessage(new Date(), errorDescription);

        return new ResponseEntity<>(error, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> IllegalArgumentExceptionHandler(IllegalArgumentException exception, WebRequest request) {
        String errorDescription = exception.getLocalizedMessage();

        if(errorDescription == null) {
            errorDescription = exception.toString();
        }

        ErrorMessage error = new ErrorMessage(new Date(), errorDescription);

        return new ResponseEntity<>(error, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

//    public ErrorMessage handleBadRequest(HttpServletRequest req, Exception ex) {
//        return new ErrorMessage(new Date(), ex.getMessage(), req.getRequestURI());
//    }

//    @ExceptionHandler
//    public ResponseEntity<String> notFoundExceptionHandler(NotFondException exception) {
//        return new ResponseEntity<>("Not found", HttpStatus.NOT_FOUND);
//    }

//    @ExceptionHandler
//    public ResponseEntity<String> IllegalArgumentExceptionHandler(IllegalArgumentException exception) {
//        return new ResponseEntity<>(exception.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
//    }

}
