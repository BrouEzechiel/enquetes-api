package org.smf.enquetes.services;

import lombok.RequiredArgsConstructor;
import org.smf.enquetes.dtos.response.IndicateurQuestionDTO;
import org.smf.enquetes.dtos.response.ReponseResponseDTO;
import org.smf.enquetes.entities.Question;
import org.smf.enquetes.entities.Reponse;
import org.smf.enquetes.exceptions.QuestionNotFoundException;
import org.smf.enquetes.mappers.ReponseMapper;
import org.smf.enquetes.repositories.QuestionRepository;
import org.smf.enquetes.repositories.ReponseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Expose les réponses en lecture seule à des fins d'analyse (MSIS,
 * indicateurs). Aucune méthode de modification/suppression n'est
 * exposée ici : les réponses sont immuables après soumission du
 * questionnaire par le patient, afin de préserver la rigueur
 * scientifique des données collectées.
 */
@Service
@RequiredArgsConstructor
public class ReponseService {

    private final ReponseRepository reponseRepository;
    private final QuestionRepository questionRepository;
    private final ReponseMapper reponseMapper;

    @Transactional(readOnly = true)
    public List<ReponseResponseDTO> getReponsesParQuestion(UUID questionId) {
        return reponseRepository.findByQuestionId(questionId).stream()
                .map(reponseMapper::toResponseDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public IndicateurQuestionDTO calculerIndicateurs(UUID questionId) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new QuestionNotFoundException(questionId));

        List<Object[]> resultats = reponseRepository.countByValeurForQuestion(questionId);
        Map<String, Long> repartition = new LinkedHashMap<>();
        long total = 0L;
        for (Object[] ligne : resultats) {
            String valeur = (String) ligne[0];
            Long count = (Long) ligne[1];
            repartition.put(valeur, count);
            total += count;
        }

        // Récupération des textes libres AVANT de figer totalFinal
        List<String> textes = List.of();
        if ("TEXTE_LIBRE".equals(question.getType())) {
            textes = reponseRepository.findByQuestionId(questionId).stream()
                    .map(Reponse::getValeur)
                    .toList();
            total = textes.size(); // Le vrai total de commentaires
        }

        final long totalFinal = total; // figée une seule fois, après tous les calculs
        Map<String, Double> pourcentages = new LinkedHashMap<>();
        repartition.forEach((valeur, count) -> {
            double pourcentage = totalFinal == 0 ? 0.0 : (count * 100.0) / totalFinal;
            pourcentages.put(valeur, Math.round(pourcentage * 100.0) / 100.0);
        });

        return new IndicateurQuestionDTO(
                question.getId(),
                question.getTexte(),
                repartition,
                pourcentages,
                totalFinal,
                textes
        );
    }
}