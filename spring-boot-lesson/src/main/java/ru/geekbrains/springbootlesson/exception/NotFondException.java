package ru.geekbrains.springbootlesson.exception;

public class NotFondException extends RuntimeException {
    public NotFondException (String message){
        super(message);
    }
}
