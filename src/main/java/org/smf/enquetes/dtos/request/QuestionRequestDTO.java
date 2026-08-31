package org.smf.enquetes.dtos.request;

import org.smf.enquetes.enums.CategorieKpi;
import java.util.List;

public record QuestionRequestDTO(
        String texte,
        String type,
        Integer ordre,
        CategorieKpi categorieKpi,
        List<String> options // NOUVEAU
) {}