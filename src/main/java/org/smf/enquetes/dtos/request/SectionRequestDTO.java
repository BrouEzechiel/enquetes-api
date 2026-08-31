package org.smf.enquetes.dtos.request;

import java.util.List;

public record SectionRequestDTO(
        String nom,
        String description,
        Integer ordre,
        List<QuestionRequestDTO> questions
) {}