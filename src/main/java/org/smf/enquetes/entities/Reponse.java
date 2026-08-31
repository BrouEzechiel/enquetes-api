package org.smf.enquetes.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reponse {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    // La valeur de la réponse (peut être du texte libre, ou un choix multiple stocké en texte)
    @Column(nullable = false, columnDefinition = "TEXT")
    private String valeur;

    // Relation avec la question à laquelle on répond
    @ManyToOne
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    // Relation avec la soumission globale
    @ManyToOne
    @JoinColumn(name = "soumission_id", nullable = false)
    @JsonIgnore // Évite la boucle infinie côté JSON
    private Soumission soumission;
}