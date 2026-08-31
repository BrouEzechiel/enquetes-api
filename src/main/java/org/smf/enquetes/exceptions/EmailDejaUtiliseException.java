package org.smf.enquetes.exceptions;

public class EmailDejaUtiliseException extends RuntimeException {
    public EmailDejaUtiliseException(String email) {
        super("Un utilisateur avec l'email '" + email + "' existe déjà.");
    }
}