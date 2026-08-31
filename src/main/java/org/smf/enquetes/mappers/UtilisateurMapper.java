package org.smf.enquetes.mappers;

import org.smf.enquetes.dtos.response.DepartementResponseDTO;
import org.smf.enquetes.dtos.response.UtilisateurResponseDTO;
import org.smf.enquetes.entities.Utilisateur;
import org.springframework.stereotype.Component;

@Component
public class UtilisateurMapper {

    public UtilisateurResponseDTO toResponseDTO(Utilisateur utilisateur) {
        DepartementResponseDTO deptDto = new DepartementResponseDTO(
                utilisateur.getDepartement().getId(),
                utilisateur.getDepartement().getNom(),
                utilisateur.getDepartement().getDescription()
        );

        return new UtilisateurResponseDTO(
                utilisateur.getId(),
                utilisateur.getNom(),
                utilisateur.getPrenom(),
                utilisateur.getEmail(),
                utilisateur.getRole(),
                deptDto
        );
    }
}