package org.smf.enquetes.repositories;

import org.smf.enquetes.entities.Soumission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SoumissionRepository extends JpaRepository<Soumission, UUID> {
    // Permet de compter ou de récupérer toutes les participations pour une enquête donnée
    List<Soumission> findByEnqueteId(UUID enqueteId);
    boolean existsByEnqueteId(UUID enqueteId);
    long countByEnqueteId(UUID enqueteId);
}