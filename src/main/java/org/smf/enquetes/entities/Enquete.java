package org.smf.enquetes.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Enquete {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String titre;

    @Column(length = 500)
    private String description;

    private LocalDate dateCreation;
    private LocalDate dateCloture;

    private boolean active = true;

    // Relation : Une enquête contient plusieurs sections
    @OneToMany(mappedBy = "enquete", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Section> sections = new ArrayList<>();

    // Relation : Une enquête est rattachée à un département spécifique
    @ManyToOne
    @JoinColumn(name = "departement_id", nullable = false)
    private Departement departement;

    // Méthode helper pour gérer la relation bidirectionnelle
    public void addSection(Section section) {
        sections.add(section);
        section.setEnquete(this);
    }

    public void removeSection(Section section) {
        sections.remove(section);
        section.setEnquete(null);
    }
}