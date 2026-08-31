package org.smf.enquetes.dtos.response;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record EnqueteResponseDTO(
        UUID id,
        String titre,
        String description,
        LocalDate dateCreation,
        LocalDate dateCloture,
        boolean active,
        DepartementResponseDTO departement, // Pour afficher le département concerné
        List<SectionResponseDTO> sections // Les sections avec leurs questions imbriquées
) {}