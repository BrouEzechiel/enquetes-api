package org.smf.enquetes.dtos.response;

import org.smf.enquetes.enums.Role;

public record AuthResponseDTO(
        String token,
        String type,
        String email,
        Role role // Modifié
) {}