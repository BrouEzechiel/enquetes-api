package org.smf.enquetes.exceptions;

import java.util.UUID;

public class QuestionNonSupprimableException extends RuntimeException {
    public QuestionNonSupprimableException(UUID questionId) {
        super("Impossible de supprimer la question " + questionId
                + " : des réponses de patients existent déjà pour cette question.");
    }
}