package ru.bartenev.severstal.exception;

public class AddressNotFoundException extends NotFoundException {
    public AddressNotFoundException(String message) {
        super(message);
    }
}