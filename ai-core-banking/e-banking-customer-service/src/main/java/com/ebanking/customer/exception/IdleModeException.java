package com.ebanking.customer.exception;

/**
 * Thrown when an operation is attempted while the system is in Idle mode.
 */
public class IdleModeException extends RuntimeException {

    public IdleModeException() {
        super("Le système est en mode maintenance. Seules les opérations de lecture sont autorisées.");
    }

    public IdleModeException(String message) {
        super(message);
    }
}
