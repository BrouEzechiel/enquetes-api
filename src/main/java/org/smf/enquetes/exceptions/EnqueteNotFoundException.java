package org.smf.enquetes.exceptions;

import java.util.UUID;

public class EnqueteNotFoundException extends RuntimeException {
    public EnqueteNotFoundException(UUID id) {
        super("Enquête non trouvée avec l'id : " + id);
    }
}