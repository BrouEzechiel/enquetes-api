package org.smf.enquetes.dtos.response;

import java.util.UUID;

public record ReponseResponseDTO(
        UUID id,
        UUID questionId,
        String valeur
) {}