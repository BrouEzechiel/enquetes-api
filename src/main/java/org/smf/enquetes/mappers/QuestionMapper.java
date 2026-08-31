package org.smf.enquetes.mappers;

import org.smf.enquetes.dtos.response.QuestionResponseDTO;
import org.smf.enquetes.entities.Question;
import org.springframework.stereotype.Component;

@Component
public class QuestionMapper {
    public QuestionResponseDTO toResponseDTO(Question question) {
        return new QuestionResponseDTO(
                question.getId(),
                question.getTexte(),
                question.getType(),
                question.getOrdre(),
                question.getCategorieKpi(),
                question.getOptions() // NOUVEAU
        );
    }
}