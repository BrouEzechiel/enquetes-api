package org.smf.enquetes.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Soumission {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private LocalDateTime dateSoumission = LocalDateTime.now();

    // Relation avec l'enquête qui a été remplie
    @ManyToOne
    @JoinColumn(name = "enquete_id", nullable = false)
    private Enquete enquete;

    // Une soumission contient plusieurs réponses
    @OneToMany(mappedBy = "soumission", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Reponse> reponses;
}