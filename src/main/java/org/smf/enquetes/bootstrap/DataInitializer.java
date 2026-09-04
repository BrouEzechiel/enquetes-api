package org.smf.enquetes.bootstrap;

import lombok.RequiredArgsConstructor;
import org.smf.enquetes.entities.Departement;
import org.smf.enquetes.entities.Utilisateur;
import org.smf.enquetes.enums.Role;
import org.smf.enquetes.repositories.DepartementRepository;
import org.smf.enquetes.repositories.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final DepartementRepository departementRepository;
    private final UtilisateurRepository utilisateurRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${application.admin.email:admin@smf.ci}")
    private String adminEmail;

    @Value("${application.admin.password:password123}")
    private String adminPassword;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        Departement imagerie = departementRepository.findByNom("Imagerie Médicale")
                .orElseGet(() -> {
                    Departement nouveauDepartement = new Departement();
                    nouveauDepartement.setNom("Imagerie Médicale");
                    nouveauDepartement.setDescription("Service de radiographie, échographie et scanner du SMF");
                    System.out.println("Création du département initial : Imagerie Médicale");
                    return departementRepository.save(nouveauDepartement);
                });

        Optional<Utilisateur> existingAdmin = utilisateurRepository.findByEmail(adminEmail);

        if (existingAdmin.isEmpty()) {
            Utilisateur admin = new Utilisateur();
            admin.setNom("Admin");
            admin.setPrenom("Système");
            admin.setEmail(adminEmail);
            admin.setMotDePasse(passwordEncoder.encode(adminPassword));
            admin.setRole(Role.ADMIN);
            admin.setDepartement(imagerie);
            utilisateurRepository.save(admin);

            System.out.println("Création du compte administrateur initial (" + adminEmail + ")");
        } else {
            // Synchronise et met à jour le mot de passe pour s'assurer qu'il correspond toujours
            Utilisateur admin = existingAdmin.get();
            admin.setMotDePasse(passwordEncoder.encode(adminPassword));
            utilisateurRepository.save(admin);
            System.out.println("Le compte administrateur existe déjà, mot de passe synchronisé (" + adminEmail + ").");
        }
    }
}