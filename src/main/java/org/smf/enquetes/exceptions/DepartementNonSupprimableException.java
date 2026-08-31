package org.smf.enquetes.exceptions;

import java.util.UUID;

public class DepartementNonSupprimableException extends RuntimeException {
    public DepartementNonSupprimableException(UUID departementId) {
        super("Impossible de supprimer le département " + departementId
                + " : des enquêtes existent déjà pour ce département.");
    }
}