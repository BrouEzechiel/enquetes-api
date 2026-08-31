package org.smf.enquetes.services;

import lombok.RequiredArgsConstructor;
import org.smf.enquetes.dtos.request.SectionRequestDTO;
import org.smf.enquetes.dtos.response.SectionResponseDTO;
import org.smf.enquetes.entities.Enquete;
import org.smf.enquetes.entities.Section;
import org.smf.enquetes.exceptions.EnqueteNotFoundException;
import org.smf.enquetes.exceptions.SectionNonSupprimableException;
import org.smf.enquetes.exceptions.SectionNotFoundException;
import org.smf.enquetes.mappers.SectionMapper;
import org.smf.enquetes.repositories.EnqueteRepository;
import org.smf.enquetes.repositories.ReponseRepository;
import org.smf.enquetes.repositories.SectionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SectionService {

    private final SectionRepository sectionRepository;
    private final EnqueteRepository enqueteRepository;
    private final ReponseRepository reponseRepository; // ajouté
    private final SectionMapper sectionMapper;

    @Transactional
    public SectionResponseDTO ajouterSection(UUID enqueteId, SectionRequestDTO request) {
        Enquete enquete = enqueteRepository.findById(enqueteId)
                .orElseThrow(() -> new EnqueteNotFoundException(enqueteId));

        Section section = new Section();
        section.setNom(request.nom());
        section.setDescription(request.description());
        section.setOrdre(request.ordre());
        section.setEnquete(enquete);

        Section savedSection = sectionRepository.save(section);
        return sectionMapper.toResponseDTO(savedSection);
    }

    @Transactional
    public SectionResponseDTO modifierSection(UUID sectionId, SectionRequestDTO request) {
        Section section = sectionRepository.findById(sectionId)
                .orElseThrow(() -> new SectionNotFoundException(sectionId));

        section.setNom(request.nom());
        section.setDescription(request.description());
        section.setOrdre(request.ordre());

        Section updatedSection = sectionRepository.save(section);
        return sectionMapper.toResponseDTO(updatedSection);
    }

    @Transactional
    public void supprimerSection(UUID sectionId) {
        Section section = sectionRepository.findById(sectionId)
                .orElseThrow(() -> new SectionNotFoundException(sectionId));

        boolean aDesReponses = reponseRepository.existsByQuestionSectionId(sectionId);

        if (aDesReponses) {
            throw new SectionNonSupprimableException(sectionId);
        }

        sectionRepository.deleteById(sectionId);
    }
}