package org.smf.enquetes.dtos.response;

import java.util.UUID;

public record DepartementResponseDTO(
        UUID id,
        String nom,
        String description
) {}