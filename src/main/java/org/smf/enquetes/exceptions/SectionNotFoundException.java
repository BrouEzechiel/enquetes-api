package org.smf.enquetes.exceptions;

import java.util.UUID;

public class SectionNotFoundException extends RuntimeException {
    public SectionNotFoundException(UUID id) {
        super("Section non trouvée avec l'id : " + id);
    }
}