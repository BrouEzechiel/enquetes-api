package org.smf.enquetes.bootstrap;

import lombok.RequiredArgsConstructor;
import org.smf.enquetes.entities.Departement;
import org.smf.enquetes.entities.Utilisateur;
import org.smf.enquetes.enums.Role;
import org.smf.enquetes.repositories.DepartementRepository;
import org.smf.enquetes.repositories.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Value; // Ajoute cet import
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final DepartementRepository departementRepository;
    private final UtilisateurRepository utilisateurRepository;
    private final PasswordEncoder passwordEncoder;

    // Tu ajoutes la variable pour l'email juste au-dessus de celle du mot de passe
    @Value("${application.admin.email:admin@smf.ci}")
    private String adminEmail;

    // NOUVEAU : Récupère le mot de passe depuis l'environnement ou utilise password123 par défaut
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

        if (utilisateurRepository.findByEmail("admin@smf.ci").isEmpty()) {
            Utilisateur admin = new Utilisateur();
            admin.setNom("Admin");
            admin.setPrenom("Système");
            admin.setEmail(adminEmail);

            // MODIFIÉ : Utilise la variable sécurisée au lieu de la chaîne en dur
            admin.setMotDePasse(passwordEncoder.encode(adminPassword));

            admin.setRole(Role.ADMIN);
            admin.setDepartement(imagerie);
            utilisateurRepository.save(admin);

            System.out.println("Création du compte administrateur initial (admin@smf.ci)");
        } else {
            System.out.println("Les données initiales sont déjà présentes en base.");
        }
    }
}