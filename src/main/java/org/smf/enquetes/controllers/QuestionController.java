package org.smf.enquetes.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.smf.enquetes.dtos.request.QuestionRequestDTO;
import org.smf.enquetes.dtos.response.QuestionResponseDTO;
import org.smf.enquetes.services.QuestionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/questions")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    @PostMapping("/section/{sectionId}")
    public ResponseEntity<QuestionResponseDTO> ajouterQuestion(
            @PathVariable UUID sectionId,
            @Valid @RequestBody QuestionRequestDTO request) {
        QuestionResponseDTO nouvelleQuestion = questionService.ajouterQuestion(sectionId, request);
        URI location = URI.create("/api/v1/questions/" + nouvelleQuestion.id());
        return ResponseEntity.created(location).body(nouvelleQuestion);
    }

    @PutMapping("/{id}")
    public ResponseEntity<QuestionResponseDTO> modifierQuestion(
            @PathVariable UUID id,
            @Valid @RequestBody QuestionRequestDTO request) {
        QuestionResponseDTO questionModifiee = questionService.modifierQuestion(id, request);
        return ResponseEntity.ok(questionModifiee);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerQuestion(@PathVariable UUID id) {
        questionService.supprimerQuestion(id);
        return ResponseEntity.noContent().build();
    }
}