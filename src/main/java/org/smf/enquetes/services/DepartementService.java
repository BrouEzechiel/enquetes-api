package org.smf.enquetes.services;

import lombok.RequiredArgsConstructor;
import org.smf.enquetes.dtos.request.DepartementRequestDTO;
import org.smf.enquetes.dtos.response.DepartementResponseDTO;
import org.smf.enquetes.entities.Departement;
import org.smf.enquetes.exceptions.DepartementNomDejaUtiliseException;
import org.smf.enquetes.exceptions.DepartementNonSupprimableException;
import org.smf.enquetes.exceptions.DepartementNotFoundException;
import org.smf.enquetes.mappers.DepartementMapper;
import org.smf.enquetes.repositories.DepartementRepository;
import org.smf.enquetes.repositories.EnqueteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DepartementService {

    private final DepartementRepository departementRepository;
    private final EnqueteRepository enqueteRepository;
    private final DepartementMapper departementMapper;

    @Transactional
    public DepartementResponseDTO creerDepartement(DepartementRequestDTO request) {
        if (departementRepository.findByNom(request.nom()).isPresent()) {
            throw new DepartementNomDejaUtiliseException(request.nom());
        }

        Departement departement = new Departement();
        departement.setNom(request.nom());
        departement.setDescription(request.description());

        Departement savedDepartement = departementRepository.save(departement);
        return departementMapper.toResponseDTO(savedDepartement);
    }

    @Transactional(readOnly = true)
    public List<DepartementResponseDTO> obtenirTousLesDepartements() {
        return departementRepository.findAll().stream()
                .map(departementMapper::toResponseDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public DepartementResponseDTO obtenirDepartementParId(UUID id) {
        Departement departement = departementRepository.findById(id)
                .orElseThrow(() -> new DepartementNotFoundException(id));
        return departementMapper.toResponseDTO(departement);
    }

    @Transactional
    public DepartementResponseDTO modifierDepartement(UUID id, DepartementRequestDTO request) {
        Departement departement = departementRepository.findById(id)
                .orElseThrow(() -> new DepartementNotFoundException(id));

        departement.setNom(request.nom());
        departement.setDescription(request.description());

        Departement updatedDepartement = departementRepository.save(departement);
        return departementMapper.toResponseDTO(updatedDepartement);
    }

    @Transactional
    public void supprimerDepartement(UUID id) {
        if (!departementRepository.existsById(id)) {
            throw new DepartementNotFoundException(id);
        }
        if (enqueteRepository.existsByDepartementId(id)) {
            throw new DepartementNonSupprimableException(id);
        }
        departementRepository.deleteById(id);
    }
}