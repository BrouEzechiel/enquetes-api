package org.smf.enquetes.exceptions;

import java.util.UUID;

public class QuestionNotFoundException extends RuntimeException {
    public QuestionNotFoundException(UUID id) {
        super("Question non trouvée avec l'id : " + id);
    }
}