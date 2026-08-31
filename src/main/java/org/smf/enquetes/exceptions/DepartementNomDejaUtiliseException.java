package org.smf.enquetes.exceptions;

public class DepartementNomDejaUtiliseException extends RuntimeException {
    public DepartementNomDejaUtiliseException(String nom) {
        super("Un département avec le nom '" + nom + "' existe déjà.");
    }
}