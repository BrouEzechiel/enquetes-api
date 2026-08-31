package org.smf.enquetes.mappers;

import org.smf.enquetes.dtos.response.ReponseResponseDTO;
import org.smf.enquetes.entities.Reponse;
import org.springframework.stereotype.Component;

@Component
public class ReponseMapper {

    public ReponseResponseDTO toResponseDTO(Reponse reponse) {
        return new ReponseResponseDTO(
                reponse.getId(),
                reponse.getQuestion().getId(),
                reponse.getValeur()
        );
    }
}