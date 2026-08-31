package org.smf.enquetes.mappers;

import org.smf.enquetes.dtos.response.ReponseResponseDTO;
import org.smf.enquetes.dtos.response.SoumissionResponseDTO;
import org.smf.enquetes.entities.Reponse;
import org.smf.enquetes.entities.Soumission;
import org.springframework.stereotype.Component;

@Component
public class SoumissionMapper {

    public SoumissionResponseDTO toResponseDTO(Soumission soumission) {
        return new SoumissionResponseDTO(
                soumission.getId(),
                soumission.getEnquete().getId(),
                soumission.getDateSoumission(),
                soumission.getReponses().stream()
                        .map(this::toReponseDTO)
                        .toList()
        );
    }

    private ReponseResponseDTO toReponseDTO(Reponse reponse) {
        return new ReponseResponseDTO(
                reponse.getId(),
                reponse.getQuestion().getId(),
                reponse.getValeur()
        );
    }
}