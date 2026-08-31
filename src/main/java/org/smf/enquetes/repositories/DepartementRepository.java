package org.smf.enquetes.repositories;

import org.smf.enquetes.entities.Departement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface DepartementRepository extends JpaRepository<Departement, UUID> {
    Optional<Departement> findByNom(String nom);
}