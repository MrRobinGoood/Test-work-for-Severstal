package ru.bartenev.severstal.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> handleNotFoundException(NotFoundException e) {
        return new ResponseEntity<>(new AppError(e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DeliveryNotFoundException.class)
    public ResponseEntity<?> handleDeliveryNotFound(DeliveryNotFoundException e) {
        return new ResponseEntity<>(new AppError(e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DeliveryStatusNotFoundException.class)
    public ResponseEntity<?> handleDeliveryStatusNotFound(DeliveryStatusNotFoundException e) {
        return new ResponseEntity<>(new AppError(e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ProviderNotFoundException.class)
    public ResponseEntity<?> handleProviderNotFound(ProviderNotFoundException e) {
        return new ResponseEntity<>(new AppError(e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AddressNotFoundException.class)
    public ResponseEntity<?> handleAddressNotFound(AddressNotFoundException e) {
        return new ResponseEntity<>(new AppError(e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PurchaseObjectNotFoundException.class)
    public ResponseEntity<?> handlePurchaseObjectNotFound(PurchaseObjectNotFoundException e) {
        return new ResponseEntity<>(new AppError(e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidParametersException.class)
    public ResponseEntity<?> handleInvalidParametersException(InvalidParametersException e) {
        return new ResponseEntity<>(new AppError(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PurchaseObjectConflictException.class)
    public ResponseEntity<?> handleMessageConflictException(PurchaseObjectConflictException e) {
        return new ResponseEntity<>(new AppError(e.getMessage()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ComplaintConflictException.class)
    public ResponseEntity<?> handleMessageConflictException(ComplaintConflictException e) {
        return new ResponseEntity<>(new AppError(e.getMessage()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(DeliveryConflictException.class)
    public ResponseEntity<?> handleMessageConflictException(DeliveryConflictException e) {
        return new ResponseEntity<>(new AppError(e.getMessage()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return new ResponseEntity<>(new AppError(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        return new ResponseEntity<>(new AppError(e.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
