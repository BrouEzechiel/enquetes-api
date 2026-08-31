package org.smf.enquetes.dtos.response;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record SoumissionResponseDTO(
        UUID id,
        UUID enqueteId,
        LocalDateTime dateSoumission,
        List<ReponseResponseDTO> reponses
) {}