package org.smf.enquetes.exceptions;

import java.util.UUID;

public class SectionNonSupprimableException extends RuntimeException {
    public SectionNonSupprimableException(UUID sectionId) {
        super("Impossible de supprimer la section " + sectionId
                + " : des réponses de patients existent déjà pour ses questions.");
    }
}