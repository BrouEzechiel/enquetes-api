package org.smf.enquetes.dtos.request;

import org.smf.enquetes.enums.Role;

import java.util.UUID;

public record UtilisateurRequestDTO(
        String nom,
        String prenom,
        String email,
        String motDePasse,
        Role role,
        UUID departementId // ID du département auquel l'utilisateur est affecté
) {}