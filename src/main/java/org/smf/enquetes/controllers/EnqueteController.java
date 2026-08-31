package org.smf.enquetes.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.smf.enquetes.dtos.request.EnqueteRequestDTO;
import org.smf.enquetes.dtos.response.EnqueteResponseDTO;
import org.smf.enquetes.services.EnqueteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/enquetes")
@RequiredArgsConstructor
public class EnqueteController {

    private final EnqueteService enqueteService;

    @PostMapping
    public ResponseEntity<EnqueteResponseDTO> creerEnquete(@Valid @RequestBody EnqueteRequestDTO request) {
        EnqueteResponseDTO nouvelleEnquete = enqueteService.creerEnquete(request);
        URI location = URI.create("/api/v1/enquetes/" + nouvelleEnquete.id());
        return ResponseEntity.created(location).body(nouvelleEnquete);
    }

    @GetMapping("/actives")
    public ResponseEntity<List<EnqueteResponseDTO>> getEnquetesActives() {
        return ResponseEntity.ok(enqueteService.getEnquetesActives());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerEnquete(@PathVariable UUID id) {
        enqueteService.supprimerEnquete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<EnqueteResponseDTO>> getToutesLesEnquetes() {
        return ResponseEntity.ok(enqueteService.getToutesLesEnquetes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EnqueteResponseDTO> getEnqueteParId(@PathVariable UUID id) {
        return ResponseEntity.ok(enqueteService.getEnqueteParId(id));
    }
}