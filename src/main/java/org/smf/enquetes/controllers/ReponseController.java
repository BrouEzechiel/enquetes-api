package org.smf.enquetes.controllers;

import lombok.RequiredArgsConstructor;
import org.smf.enquetes.dtos.response.IndicateurQuestionDTO;
import org.smf.enquetes.dtos.response.ReponseResponseDTO;
import org.smf.enquetes.services.ReponseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * Expose les réponses en lecture seule à des fins d'analyse (MSIS,
 * indicateurs). Aucune modification n'est permise ici : les réponses
 * sont immuables après soumission du questionnaire par le patient.
 */
@RestController
@RequestMapping("/api/v1/reponses")
@RequiredArgsConstructor
public class ReponseController {

    private final ReponseService reponseService;

    @GetMapping("/question/{questionId}")
    public ResponseEntity<List<ReponseResponseDTO>> getReponsesParQuestion(@PathVariable UUID questionId) {
        return ResponseEntity.ok(reponseService.getReponsesParQuestion(questionId));
    }

    @GetMapping("/question/{questionId}/indicateurs")
    public ResponseEntity<IndicateurQuestionDTO> getIndicateursParQuestion(@PathVariable UUID questionId) {
        return ResponseEntity.ok(reponseService.calculerIndicateurs(questionId));
    }
}