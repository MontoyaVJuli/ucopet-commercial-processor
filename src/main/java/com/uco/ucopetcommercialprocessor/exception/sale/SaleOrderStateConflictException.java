package com.uco.ucopetcommercialprocessor.exception.sale;

public class SaleOrderStateConflictException extends RuntimeException {
    public SaleOrderStateConflictException(String message) {
        super(message);
    }
}