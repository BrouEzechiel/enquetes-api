package org.smf.enquetes.services;

import lombok.RequiredArgsConstructor;
import org.smf.enquetes.dtos.request.EnqueteRequestDTO;
import org.smf.enquetes.dtos.request.SectionRequestDTO;
import org.smf.enquetes.dtos.response.EnqueteResponseDTO;
import org.smf.enquetes.entities.*;
import org.smf.enquetes.exceptions.DepartementNotFoundException;
import org.smf.enquetes.exceptions.EnqueteNonSupprimableException;
import org.smf.enquetes.exceptions.EnqueteNotFoundException;
import org.smf.enquetes.mappers.EnqueteMapper;
import org.smf.enquetes.repositories.DepartementRepository;
import org.smf.enquetes.repositories.EnqueteRepository;
import org.smf.enquetes.repositories.SoumissionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EnqueteService {

    private final EnqueteRepository enqueteRepository;
    private final DepartementRepository departementRepository;
    private final SoumissionRepository soumissionRepository;
    private final EnqueteMapper enqueteMapper;

    @Transactional
    public EnqueteResponseDTO creerEnquete(EnqueteRequestDTO request) {
        Departement departement = departementRepository.findById(request.departementId())
                .orElseThrow(() -> new DepartementNotFoundException(request.departementId()));

        Enquete enquete = new Enquete();
        enquete.setTitre(request.titre());
        enquete.setDescription(request.description());
        enquete.setDateCreation(LocalDate.now());
        enquete.setDateCloture(request.dateCloture());
        enquete.setActive(true);
        enquete.setDepartement(departement);
        enquete.setSections(mapSections(request, enquete));

        Enquete savedEnquete = enqueteRepository.save(enquete);

        return enqueteMapper.toResponseDTO(savedEnquete);
    }

    @Transactional(readOnly = true)
    public List<EnqueteResponseDTO> getEnquetesActives() {
        LocalDate aujourdhui = LocalDate.now();

        return enqueteRepository.findByActiveTrue().stream()
                // On s'assure que la date de clôture n'est pas passée
                .filter(enquete -> enquete.getDateCloture() == null || !enquete.getDateCloture().isBefore(aujourdhui))
                .map(enqueteMapper::toResponseDTO)
                .toList();
    }

    @Transactional
    public void supprimerEnquete(UUID enqueteId) {
        if (!enqueteRepository.existsById(enqueteId)) {
            throw new EnqueteNotFoundException(enqueteId);
        }
        if (soumissionRepository.existsByEnqueteId(enqueteId)) {
            throw new EnqueteNonSupprimableException(enqueteId);
        }
        enqueteRepository.deleteById(enqueteId);
    }

    private List<Section> mapSections(EnqueteRequestDTO request, Enquete enquete) {
        return request.sections().stream().map(sectionDto -> {
            Section section = new Section();
            section.setNom(sectionDto.nom());
            section.setDescription(sectionDto.description());
            section.setOrdre(sectionDto.ordre());
            section.setEnquete(enquete);
            section.setQuestions(mapQuestions(sectionDto, section, enquete));
            return section;
        }).toList();
    }

    private List<Question> mapQuestions(SectionRequestDTO sectionDto, Section section, Enquete enquete) {
        return sectionDto.questions().stream().map(questionDto -> {
            Question question = new Question();
            question.setTexte(questionDto.texte());
            question.setType(questionDto.type());
            question.setOrdre(questionDto.ordre());
            question.setSection(section);
            question.setEnquete(enquete);
            return question;
        }).toList();
    }

    @Transactional(readOnly = true)
    public List<EnqueteResponseDTO> getToutesLesEnquetes() {
        return enqueteRepository.findAll().stream()
                .map(enqueteMapper::toResponseDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public EnqueteResponseDTO getEnqueteParId(UUID id) {
        Enquete enquete = enqueteRepository.findById(id)
                .orElseThrow(() -> new EnqueteNotFoundException(id));
        return enqueteMapper.toResponseDTO(enquete);
    }
}