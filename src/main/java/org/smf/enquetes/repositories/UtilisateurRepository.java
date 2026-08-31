package org.smf.enquetes.repositories;

import org.smf.enquetes.entities.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur, UUID> {
    // Cette méthode sera très utile plus tard pour la connexion (authentification)
    Optional<Utilisateur> findByEmail(String email);
}