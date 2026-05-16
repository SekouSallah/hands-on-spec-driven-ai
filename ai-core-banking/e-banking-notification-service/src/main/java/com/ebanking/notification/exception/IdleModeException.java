package com.ebanking.notification.exception;

public class IdleModeException extends RuntimeException {
    public IdleModeException() { super("Le système est en mode maintenance."); }
}
