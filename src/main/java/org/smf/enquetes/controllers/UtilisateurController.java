package org.smf.enquetes.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.smf.enquetes.dtos.request.UtilisateurRequestDTO;
import org.smf.enquetes.dtos.response.UtilisateurResponseDTO;
import org.smf.enquetes.services.UtilisateurService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/utilisateurs")
@RequiredArgsConstructor
public class UtilisateurController {

    private final UtilisateurService utilisateurService;

    @PostMapping
    public ResponseEntity<UtilisateurResponseDTO> creerUtilisateur(@Valid @RequestBody UtilisateurRequestDTO request) {
        UtilisateurResponseDTO nouvelUtilisateur = utilisateurService.creerUtilisateur(request);
        URI location = URI.create("/api/v1/utilisateurs/" + nouvelUtilisateur.id());
        return ResponseEntity.created(location).body(nouvelUtilisateur);
    }

    @GetMapping
    public ResponseEntity<List<UtilisateurResponseDTO>> obtenirTousLesUtilisateurs() {
        return ResponseEntity.ok(utilisateurService.obtenirTousLesUtilisateurs());
    }

    @PutMapping("/{id}")
    public ResponseEntity<UtilisateurResponseDTO> modifierUtilisateur(
            @PathVariable UUID id,
            @Valid @RequestBody UtilisateurRequestDTO request) {
        UtilisateurResponseDTO utilisateurModifie = utilisateurService.modifierUtilisateur(id, request);
        return ResponseEntity.ok(utilisateurModifie);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerUtilisateur(@PathVariable UUID id) {
        utilisateurService.supprimerUtilisateur(id);
        return ResponseEntity.noContent().build();
    }
}