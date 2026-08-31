package org.smf.enquetes.dtos.response;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public record IndicateurQuestionDTO(
        UUID questionId,
        String texteQuestion,
        Map<String, Long> repartitionParValeur,
        Map<String, Double> pourcentageParValeur,
        long totalReponses,
        List<String> reponsesTextes // NOUVEAU : Pour stocker les commentaires libres
) {}