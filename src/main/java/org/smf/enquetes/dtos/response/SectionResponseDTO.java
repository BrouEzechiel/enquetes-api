package org.smf.enquetes.dtos.response;

import java.util.List;
import java.util.UUID;

public record SectionResponseDTO(
        UUID id,
        String nom,
        String description,
        Integer ordre,
        List<QuestionResponseDTO> questions
) {}