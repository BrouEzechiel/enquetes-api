package org.smf.enquetes.dtos.request;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record EnqueteRequestDTO(
        String titre,
        String description,
        LocalDate dateCloture,
        UUID departementId, // Pour lier l'enquête au service d'imagerie, par exemple
        List<SectionRequestDTO> sections // On remplace les questions par les sections
) {}