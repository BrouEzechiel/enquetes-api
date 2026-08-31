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
public class Section {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    // Ex: "Section B - Accueil et orientation"
    @Column(nullable = false)
    private String nom;

    private String description;

    // Pour garantir l'ordre d'affichage des sections (1, 2, 3...)
    @Column(nullable = false)
    private Integer ordre;

    // Relation : Une section appartient à une enquête
    @ManyToOne
    @JoinColumn(name = "enquete_id", nullable = false)
    @JsonIgnore
    private Enquete enquete;

    // Relation : Une section contient plusieurs questions
    @OneToMany(mappedBy = "section", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Question> questions;
}