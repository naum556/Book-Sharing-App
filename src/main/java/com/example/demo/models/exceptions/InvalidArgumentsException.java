package com.example.demo.models.exceptions;



public class InvalidArgumentsException extends RuntimeException {
    public InvalidArgumentsException() {
        super("Invalid argument");
    }
}
