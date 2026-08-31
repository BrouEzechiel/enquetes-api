package org.smf.enquetes.dtos.request;

import java.util.List;
import java.util.UUID;

public record SoumissionRequestDTO(
        UUID enqueteId,  // Référence à l'enquête complétée
        List<ReponseRequestDTO> reponses
) {}