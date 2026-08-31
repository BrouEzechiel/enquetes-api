package org.smf.enquetes.repositories;

import org.smf.enquetes.entities.Enquete;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface EnqueteRepository extends JpaRepository<Enquete, UUID> {
    // Permet de récupérer uniquement les enquêtes actuellement ouvertes aux usagers
    List<Enquete> findByActiveTrue();

    boolean existsByDepartementId(UUID departementId);
}