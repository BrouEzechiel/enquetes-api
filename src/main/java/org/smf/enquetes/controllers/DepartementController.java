package org.smf.enquetes.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.smf.enquetes.dtos.request.DepartementRequestDTO;
import org.smf.enquetes.dtos.response.DepartementResponseDTO;
import org.smf.enquetes.services.DepartementService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/departements")
@RequiredArgsConstructor
public class DepartementController {

    private final DepartementService departementService;

    // 1. Lire tous les départements (Celle que vous aviez déjà)
    @GetMapping
    public ResponseEntity<List<DepartementResponseDTO>> obtenirTousLesDepartements() {
        return ResponseEntity.ok(departementService.obtenirTousLesDepartements());
    }

    // 2. Créer un département (Manquante : causait le 405 au clic sur Ajouter)
    @PostMapping
    public ResponseEntity<DepartementResponseDTO> creerDepartement(@Valid @RequestBody DepartementRequestDTO request) {
        DepartementResponseDTO nouveauDepartement = departementService.creerDepartement(request);
        URI location = URI.create("/api/v1/departements/" + nouveauDepartement.id());
        return ResponseEntity.created(location).body(nouveauDepartement);
    }

    // 3. Modifier un département (Pour plus tard si besoin)
    @PutMapping("/{id}")
    public ResponseEntity<DepartementResponseDTO> modifierDepartement(
            @PathVariable UUID id,
            @Valid @RequestBody DepartementRequestDTO request) {
        DepartementResponseDTO departementModifie = departementService.modifierDepartement(id, request);
        return ResponseEntity.ok(departementModifie);
    }

    // 4. Supprimer un département (Manquante : causait le 405 au clic sur la corbeille)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerDepartement(@PathVariable UUID id) {
        departementService.supprimerDepartement(id);
        return ResponseEntity.noContent().build();
    }
}