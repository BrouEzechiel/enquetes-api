package org.smf.enquetes.exceptions;

public class ReponseInvalideException extends RuntimeException {
    public ReponseInvalideException(String valeur) {
        super("La valeur '" + valeur + "' n'est pas autorisée pour ce type de question.");
    }
}