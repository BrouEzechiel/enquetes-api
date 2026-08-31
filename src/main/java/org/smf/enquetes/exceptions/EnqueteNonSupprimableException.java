package org.smf.enquetes.exceptions;

import java.util.UUID;

public class EnqueteNonSupprimableException extends RuntimeException {
    public EnqueteNonSupprimableException(UUID enqueteId) {
        super("Impossible de supprimer l'enquête " + enqueteId
                + " : des soumissions de patients existent déjà pour cette enquête.");
    }
}