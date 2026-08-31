package org.smf.enquetes.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Departement {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    // Ex: "Imagerie médicale", "Cardiologie", etc.
    @Column(nullable = false, unique = true)
    private String nom;

    private String description;

    // Un département regroupe plusieurs utilisateurs (personnel médical, agents d'accueil)
    @OneToMany(mappedBy = "departement", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Utilisateur> utilisateurs;

    // Un département peut avoir plusieurs enquêtes qui lui sont propres
    @OneToMany(mappedBy = "departement", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Enquete> enquetes;
}