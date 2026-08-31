package org.smf.enquetes.exceptions;

import java.util.UUID;

public class UtilisateurNotFoundException extends RuntimeException {
    public UtilisateurNotFoundException(UUID id) {
        super("Utilisateur non trouvé avec l'id : " + id);
    }

    public UtilisateurNotFoundException(String email) {
        super("Utilisateur non trouvé avec l'email : " + email);
    }
}