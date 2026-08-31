package org.smf.enquetes.mappers;

import org.smf.enquetes.dtos.response.QuestionResponseDTO;
import org.smf.enquetes.dtos.response.SectionResponseDTO;
import org.smf.enquetes.entities.Question;
import org.smf.enquetes.entities.Section;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SectionMapper {

    public SectionResponseDTO toResponseDTO(Section section) {
        List<QuestionResponseDTO> questionsDto = (section.getQuestions() == null) ? List.of() :
                section.getQuestions().stream()
                        .map(this::toQuestionDTO)
                        .toList();

        return new SectionResponseDTO(
                section.getId(),
                section.getNom(),
                section.getDescription(),
                section.getOrdre(),
                questionsDto
        );
    }

    private QuestionResponseDTO toQuestionDTO(Question question) {
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