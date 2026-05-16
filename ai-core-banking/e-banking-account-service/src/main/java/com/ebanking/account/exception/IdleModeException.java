package com.ebanking.account.exception;

public class IdleModeException extends RuntimeException {
    public IdleModeException() {
        super("Le système est en mode maintenance. Seules les opérations de lecture sont autorisées.");
    }
}
