package org.smf.enquetes.services;

import lombok.RequiredArgsConstructor;
import org.smf.enquetes.dtos.request.UtilisateurRequestDTO;
import org.smf.enquetes.dtos.response.UtilisateurResponseDTO;
import org.smf.enquetes.entities.Departement;
import org.smf.enquetes.entities.Utilisateur;
import org.smf.enquetes.exceptions.DepartementNotFoundException;
import org.smf.enquetes.exceptions.EmailDejaUtiliseException;
import org.smf.enquetes.exceptions.UtilisateurNotFoundException;
import org.smf.enquetes.mappers.UtilisateurMapper;
import org.smf.enquetes.repositories.DepartementRepository;
import org.smf.enquetes.repositories.UtilisateurRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UtilisateurService {

    private final UtilisateurRepository utilisateurRepository;
    private final DepartementRepository departementRepository;
    private final UtilisateurMapper utilisateurMapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UtilisateurResponseDTO creerUtilisateur(UtilisateurRequestDTO request) {
        if (utilisateurRepository.findByEmail(request.email()).isPresent()) {
            throw new EmailDejaUtiliseException(request.email());
        }

        Departement departement = departementRepository.findById(request.departementId())
                .orElseThrow(() -> new DepartementNotFoundException(request.departementId()));

        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setNom(request.nom());
        utilisateur.setPrenom(request.prenom());
        utilisateur.setEmail(request.email());
        utilisateur.setMotDePasse(passwordEncoder.encode(request.motDePasse()));
        utilisateur.setRole(request.role());
        utilisateur.setDepartement(departement);

        Utilisateur savedUtilisateur = utilisateurRepository.save(utilisateur);
        return utilisateurMapper.toResponseDTO(savedUtilisateur);
    }

    @Transactional
    public UtilisateurResponseDTO modifierUtilisateur(UUID id, UtilisateurRequestDTO request) {
        Utilisateur utilisateur = utilisateurRepository.findById(id)
                .orElseThrow(() -> new UtilisateurNotFoundException(id));

        // Vérifier si l'email est déjà utilisé par un autre utilisateur
        if (!utilisateur.getEmail().equals(request.email()) &&
                utilisateurRepository.findByEmail(request.email()).isPresent()) {
            throw new EmailDejaUtiliseException(request.email());
        }

        Departement departement = departementRepository.findById(request.departementId())
                .orElseThrow(() -> new DepartementNotFoundException(request.departementId()));

        utilisateur.setNom(request.nom());
        utilisateur.setPrenom(request.prenom());
        utilisateur.setEmail(request.email());
        utilisateur.setRole(request.role());
        utilisateur.setDepartement(departement);

        // Mettre à jour le mot de passe seulement s'il est fourni et non vide
        if (request.motDePasse() != null && !request.motDePasse().isBlank()) {
            utilisateur.setMotDePasse(passwordEncoder.encode(request.motDePasse()));
        }

        Utilisateur updatedUtilisateur = utilisateurRepository.save(utilisateur);
        return utilisateurMapper.toResponseDTO(updatedUtilisateur);
    }

    @Transactional(readOnly = true)
    public List<UtilisateurResponseDTO> obtenirTousLesUtilisateurs() {
        return utilisateurRepository.findAll().stream()
                .map(utilisateurMapper::toResponseDTO)
                .toList();
    }

    @Transactional
    public void supprimerUtilisateur(UUID id) {
        if (!utilisateurRepository.existsById(id)) {
            throw new UtilisateurNotFoundException(id);
        }
        utilisateurRepository.deleteById(id);
    }
}