package org.smf.enquetes.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.smf.enquetes.enums.CategorieKpi;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String texte;

    @Column(nullable = false)
    private String type;

    private Integer ordre;

    @Enumerated(EnumType.STRING)
    @Column(name = "categorie_kpi")
    private CategorieKpi categorieKpi = CategorieKpi.AUCUNE;

    // NOUVEAU : Permet de stocker les choix pour les questions de type QCM
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "question_options", joinColumns = @JoinColumn(name = "question_id"))
    @Column(name = "option_texte")
    private List<String> options = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "section_id")
    private Section section;

    @ManyToOne
    @JoinColumn(name = "enquete_id")
    private Enquete enquete;
}