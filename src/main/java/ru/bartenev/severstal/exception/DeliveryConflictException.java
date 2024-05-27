package ru.bartenev.severstal.exception;

public class DeliveryConflictException extends ConflictException {
    public DeliveryConflictException(String message) {
        super(message);
    }
}