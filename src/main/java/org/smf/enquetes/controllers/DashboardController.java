package org.smf.enquetes.controllers;

import org.smf.enquetes.entities.Reponse;
import org.smf.enquetes.enums.CategorieKpi;
import org.smf.enquetes.repositories.ReponseRepository;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/statistiques")
public class DashboardController {

    private final ReponseRepository reponseRepository;

    public DashboardController(ReponseRepository reponseRepository) {
        this.reponseRepository = reponseRepository;
    }

    @GetMapping("/kpi")
    public Map<String, Integer> getIndicateursKpi() {
        Map<String, Integer> kpis = new HashMap<>();

        // On calcule le taux pour chaque indicateur majeur exigé par le protocole
        kpis.put("tauxAccueil", calculerTauxSatisfaction(CategorieKpi.ACCUEIL));
        kpis.put("tauxAttente", calculerTauxSatisfaction(CategorieKpi.ATTENTE));
        kpis.put("tauxPriseEnCharge", calculerTauxSatisfaction(CategorieKpi.PRISE_EN_CHARGE));
        kpis.put("tauxRecommandation", calculerTauxSatisfaction(CategorieKpi.RECOMMANDATION));

        return kpis;
    }

    private int calculerTauxSatisfaction(CategorieKpi categorie) {
        List<Reponse> reponses = reponseRepository.findByQuestionCategorieKpi(categorie);
        if (reponses.isEmpty()) {
            return 0; // Pas encore de données
        }

        // On compte comme positif tout ce qui contient "satisfait" (en ignorant la casse et les accents),
        // en s'assurant d'exclure les "insatisfait" et les "ni satisfait ni...".
        long avisPositifs = reponses.stream()
                .filter(r -> r.getValeur() != null)
                .filter(r -> {
                    String val = r.getValeur().toLowerCase();
                    // Doit contenir "satisfait" mais pas "insatisfait" et pas "ni"
                    return val.contains("satisfait") && !val.contains("insatisfait") && !val.contains("ni");
                })
                .count();

        // Calcul du pourcentage (règle de 3)
        return (int) Math.round((double) avisPositifs / reponses.size() * 100);
    }
}