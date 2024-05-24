package ru.bartenev.severstal.exception;

public class DeliveryStatusNotFoundException extends NotFoundException {
    public DeliveryStatusNotFoundException(String message) {
        super(message);
    }
}