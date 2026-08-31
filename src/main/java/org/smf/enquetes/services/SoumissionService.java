package org.smf.enquetes.services;

import lombok.RequiredArgsConstructor;
import org.smf.enquetes.dtos.request.ReponseRequestDTO;
import org.smf.enquetes.dtos.request.SoumissionRequestDTO;
import org.smf.enquetes.dtos.response.SoumissionResponseDTO;
import org.smf.enquetes.entities.Enquete;
import org.smf.enquetes.entities.Question;
import org.smf.enquetes.entities.Reponse;
import org.smf.enquetes.entities.Soumission;
import org.smf.enquetes.exceptions.EnqueteNotFoundException;
import org.smf.enquetes.exceptions.QuestionNotFoundException;
import org.smf.enquetes.exceptions.ReponseInvalideException; // NOUVEL IMPORT
import org.smf.enquetes.mappers.SoumissionMapper;
import org.smf.enquetes.repositories.EnqueteRepository;
import org.smf.enquetes.repositories.QuestionRepository;
import org.smf.enquetes.repositories.SoumissionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SoumissionService {

    private final SoumissionRepository soumissionRepository;
    private final EnqueteRepository enqueteRepository;
    private final QuestionRepository questionRepository;
    private final SoumissionMapper soumissionMapper;

    // --- LA LISTE DES VALEURS AUTORISÉES ---
    private static final List<String> VALEURS_LIKERT_5 = List.of(
            "Tres insatisfait",
            "Insatisfait",
            "Ni satisfait ni insatisfait",
            "Satisfait",
            "Tres satisfait"
    );

    @Transactional
    public SoumissionResponseDTO enregistrerSoumission(SoumissionRequestDTO request) {
        Enquete enquete = enqueteRepository.findById(request.enqueteId())
                .orElseThrow(() -> new EnqueteNotFoundException(request.enqueteId()));

        // --- NOUVELLE VÉRIFICATION DE SÉCURITÉ BACKEND ---
        LocalDate aujourdhui = LocalDate.now();
        if (!enquete.isActive() || (enquete.getDateCloture() != null && enquete.getDateCloture().isBefore(aujourdhui))) {
            throw new ReponseInvalideException("Impossible de soumettre : cette enquête est clôturée.");
        }
        // ------------------------------------------------

        Soumission soumission = new Soumission();
        soumission.setEnquete(enquete);
        soumission.setDateSoumission(LocalDateTime.now());
        soumission.setReponses(mapReponses(request.reponses(), soumission));
        Soumission savedSoumission = soumissionRepository.save(soumission);
        return soumissionMapper.toResponseDTO(savedSoumission);
    }

    @Transactional(readOnly = true)
    public List<SoumissionResponseDTO> getSoumissionsParEnquete(UUID enqueteId) {
        return soumissionRepository.findByEnqueteId(enqueteId).stream()
                .map(soumissionMapper::toResponseDTO)
                .toList();
    }

    private List<Reponse> mapReponses(List<ReponseRequestDTO> reponsesDto, Soumission soumission) {
        return reponsesDto.stream().map(reponseDto -> {
            Question question = questionRepository.findById(reponseDto.questionId())
                    .orElseThrow(() -> new QuestionNotFoundException(reponseDto.questionId()));

            // VÉRIFICATION DE SÉCURITÉ
            if ("ECHELLE_LIKERT_5".equals(question.getType())) {
                // On utilise la version sans accent pour éviter les bugs
                if (!VALEURS_LIKERT_5.contains(reponseDto.valeur())) {
                    throw new ReponseInvalideException(reponseDto.valeur());
                }
            } else if ("ECHELLE_NOTE_10".equals(question.getType())) {
                try {
                    int note = Integer.parseInt(reponseDto.valeur());
                    if (note < 0 || note > 10) throw new NumberFormatException();
                } catch (NumberFormatException e) {
                    throw new ReponseInvalideException("La note doit être comprise entre 0 et 10.");
                }
            }

            Reponse reponse = new Reponse();
            reponse.setValeur(reponseDto.valeur());
            reponse.setQuestion(question);
            reponse.setSoumission(soumission);
            return reponse;
        }).toList();
    }
}