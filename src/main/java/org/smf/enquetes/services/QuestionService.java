package org.smf.enquetes.services;

import lombok.RequiredArgsConstructor;
import org.smf.enquetes.dtos.request.QuestionRequestDTO;
import org.smf.enquetes.dtos.response.QuestionResponseDTO;
import org.smf.enquetes.entities.Question;
import org.smf.enquetes.entities.Section;
import org.smf.enquetes.exceptions.QuestionNonSupprimableException;
import org.smf.enquetes.exceptions.QuestionNotFoundException;
import org.smf.enquetes.exceptions.SectionNotFoundException;
import org.smf.enquetes.mappers.QuestionMapper;
import org.smf.enquetes.repositories.QuestionRepository;
import org.smf.enquetes.repositories.ReponseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final SectionRepository sectionRepository;
    private final ReponseRepository reponseRepository;
    private final QuestionMapper questionMapper;

    @Transactional
    public QuestionResponseDTO ajouterQuestion(UUID sectionId, QuestionRequestDTO request) {
        Section section = sectionRepository.findById(sectionId)
                .orElseThrow(() -> new SectionNotFoundException(sectionId));

        Question question = new Question();
        question.setTexte(request.texte());
        question.setType(request.type());
        question.setOrdre(request.ordre());
        question.setOptions(request.options());
        question.setSection(section);
        question.setEnquete(section.getEnquete());

        Question savedQuestion = questionRepository.save(question);
        return questionMapper.toResponseDTO(savedQuestion);
    }

    @Transactional
    public QuestionResponseDTO modifierQuestion(UUID questionId, QuestionRequestDTO request) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new QuestionNotFoundException(questionId));

        question.setTexte(request.texte());
        question.setType(request.type());
        question.setOrdre(request.ordre());
        question.setOptions(request.options()); // Ajouté pour mettre à jour les options

        Question updatedQuestion = questionRepository.save(question);
        return questionMapper.toResponseDTO(updatedQuestion);
    }

    @Transactional
    public void supprimerQuestion(UUID questionId) {
        if (!questionRepository.existsById(questionId)) {
            throw new QuestionNotFoundException(questionId);
        }
        if (reponseRepository.existsByQuestionId(questionId)) {
            throw new QuestionNonSupprimableException(questionId);
        }
        questionRepository.deleteById(questionId);
    }
}