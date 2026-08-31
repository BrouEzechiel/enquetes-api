package org.smf.enquetes.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.smf.enquetes.dtos.request.SectionRequestDTO;
import org.smf.enquetes.dtos.response.SectionResponseDTO;
import org.smf.enquetes.services.SectionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/sections")
@RequiredArgsConstructor
public class SectionController {

    private final SectionService sectionService;

    @PostMapping("/enquete/{enqueteId}")
    public ResponseEntity<SectionResponseDTO> ajouterSection(
            @PathVariable UUID enqueteId,
            @Valid @RequestBody SectionRequestDTO request) {
        SectionResponseDTO nouvelleSection = sectionService.ajouterSection(enqueteId, request);
        URI location = URI.create("/api/v1/sections/" + nouvelleSection.id());
        return ResponseEntity.created(location).body(nouvelleSection);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SectionResponseDTO> modifierSection(
            @PathVariable UUID id,
            @Valid @RequestBody SectionRequestDTO request) {
        SectionResponseDTO sectionModifiee = sectionService.modifierSection(id, request);
        return ResponseEntity.ok(sectionModifiee);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerSection(@PathVariable UUID id) {
        sectionService.supprimerSection(id);
        return ResponseEntity.noContent().build();
    }
}