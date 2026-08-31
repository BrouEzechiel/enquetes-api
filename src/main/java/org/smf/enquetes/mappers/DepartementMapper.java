package org.smf.enquetes.mappers;

import org.smf.enquetes.dtos.response.DepartementResponseDTO;
import org.smf.enquetes.entities.Departement;
import org.springframework.stereotype.Component;

@Component
public class DepartementMapper {

    public DepartementResponseDTO toResponseDTO(Departement departement) {
        return new DepartementResponseDTO(
                departement.getId(),
                departement.getNom(),
                departement.getDescription()
        );
    }
}