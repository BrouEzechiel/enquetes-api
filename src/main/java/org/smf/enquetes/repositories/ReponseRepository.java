package org.smf.enquetes.repositories;

import org.smf.enquetes.entities.Reponse;
import org.smf.enquetes.enums.CategorieKpi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ReponseRepository extends JpaRepository<Reponse, UUID> {
    // Utile pour analyser les réponses à une question spécifique (ex: calculer une moyenne sur une échelle de Likert)
    List<Reponse> findByQuestionId(UUID questionId);

    // Comptage par valeur de réponse (ex: pour QCM/échelle de satisfaction)
    @Query("SELECT r.valeur, COUNT(r) FROM Reponse r WHERE r.question.id = :questionId GROUP BY r.valeur")
    List<Object[]> countByValeurForQuestion(@Param("questionId") UUID questionId);

    // Nouveau : vérifie l'existence de réponses pour toutes les questions d'une section
    boolean existsByQuestionSectionId(UUID sectionId);

    boolean existsByQuestionId(UUID questionId);

    // Trouve toutes les réponses associées aux questions d'une catégorie spécifique
    @Query("SELECT r FROM Reponse r WHERE r.question.categorieKpi = :categorie")
    List<Reponse> findByQuestionCategorieKpi(@Param("categorie") CategorieKpi categorie);
}