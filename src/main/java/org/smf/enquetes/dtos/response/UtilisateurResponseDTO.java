package org.smf.enquetes.dtos.response;

import org.smf.enquetes.enums.Role;

import java.util.UUID;

public record UtilisateurResponseDTO(
        UUID id,
        String nom,
        String prenom,
        String email,
        Role role, // Modifié
        DepartementResponseDTO departement // On renvoie les infos du département
) {}