package org.smf.enquetes.dtos.request;

import java.util.UUID;

public record ReponseRequestDTO(
        UUID questionId,  // Référence à la question répondue
        String valeur
) {}