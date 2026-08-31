package org.smf.enquetes.dtos.response;

import org.smf.enquetes.enums.CategorieKpi;
import java.util.List;
import java.util.UUID;

public record QuestionResponseDTO(
        UUID id,
        String texte,
        String type,
        Integer ordre,
        CategorieKpi categorieKpi,
        List<String> options // NOUVEAU
) {}