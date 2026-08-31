package org.smf.enquetes.dtos.request;

public record AuthRequestDTO(
        String email,
        String motDePasse
) {}