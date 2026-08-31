package org.smf.enquetes.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.smf.enquetes.dtos.request.SoumissionRequestDTO;
import org.smf.enquetes.dtos.response.SoumissionResponseDTO;
import org.smf.enquetes.services.SoumissionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/soumissions")
@RequiredArgsConstructor
public class SoumissionController {

    private final SoumissionService soumissionService;

    // Endpoint public pour les patients qui valident leur questionnaire
    @PostMapping
    public ResponseEntity<SoumissionResponseDTO> soumettreEnquete(@Valid @RequestBody SoumissionRequestDTO request) {
        SoumissionResponseDTO nouvelleSoumission = soumissionService.enregistrerSoumission(request);
        URI location = URI.create("/api/v1/soumissions/" + nouvelleSoumission.id());
        return ResponseEntity.created(location).body(nouvelleSoumission);
    }

    // Endpoint (futur sécurisé) pour le tableau de bord afin de voir les résultats
    @GetMapping("/enquete/{enqueteId}")
    public ResponseEntity<List<SoumissionResponseDTO>> getSoumissionsParEnquete(@PathVariable UUID enqueteId) {
        List<SoumissionResponseDTO> soumissions = soumissionService.getSoumissionsParEnquete(enqueteId);
        return ResponseEntity.ok(soumissions);
    }
}