package ru.bartenev.severstal.exception;

public class PurchaseObjectConflictException extends ConflictException {
    public PurchaseObjectConflictException(String message) {
        super(message);
    }
}