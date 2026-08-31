package org.smf.enquetes.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.smf.enquetes.enums.Role;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Utilisateur {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String nom;

    @Column(nullable = false)
    private String prenom;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String motDePasse;

    // Ex: "ADMIN", "MEDECIN", "INFIRMIER"
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    // Relation : L'agent ou le médecin appartient à un département (ex: 9 radiologues, 3 agents d'accueil liés à l'imagerie)
    @ManyToOne
    @JoinColumn(name = "departement_id", nullable = false)
    private Departement departement;
}