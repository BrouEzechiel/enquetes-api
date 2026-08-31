package org.smf.enquetes.mappers;

import org.smf.enquetes.dtos.response.*;
import org.smf.enquetes.entities.*;
import org.springframework.stereotype.Component;

@Component
public class EnqueteMapper {

    public EnqueteResponseDTO toResponseDTO(Enquete enquete) {
        return new EnqueteResponseDTO(
                enquete.getId(),
                enquete.getTitre(),
                enquete.getDescription(),
                enquete.getDateCreation(),
                enquete.getDateCloture(),
                enquete.isActive(),
                toDepartementDTO(enquete.getDepartement()),
                enquete.getSections().stream()
                        .map(this::toSectionDTO)
                        .toList()
        );
    }

    private DepartementResponseDTO toDepartementDTO(Departement departement) {
        return new DepartementResponseDTO(
                departement.getId(),
                departement.getNom(),
                departement.getDescription()
        );
    }

    private SectionResponseDTO toSectionDTO(Section section) {
        return new SectionResponseDTO(
                section.getId(),
                section.getNom(),
                section.getDescription(),
                section.getOrdre(),
                section.getQuestions().stream()
                        .map(this::toQuestionDTO)
                        .toList()
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