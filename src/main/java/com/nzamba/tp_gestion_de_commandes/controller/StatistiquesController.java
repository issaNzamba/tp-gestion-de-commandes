package com.nzamba.tp_gestion_de_commandes.controller;

import com.nzamba.tp_gestion_de_commandes.repository.CommandeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/statistiques")
@RequiredArgsConstructor
public class StatistiquesController {

    private final CommandeRepository commandeRepository;

    @GetMapping("/chiffre-affaires")
    public ResponseEntity<Double> getChiffreAffaires() {
        Double total = commandeRepository.computeTotalChiffreAffaires();
        return ResponseEntity.ok(total != null ? total : 0.0);
    }

    @GetMapping("/total-client/{clientId}")
    public ResponseEntity<Double> getTotalClient(@PathVariable Long clientId) {
        return ResponseEntity.ok(commandeRepository.computeTotalDepensesParClient(clientId));
    }

    @GetMapping("/top-produits")
    public ResponseEntity<List<Map<String, Object>>> getTopProduits() {
        List<Object[]> rawResults = commandeRepository.findTopProduitsRaw();
        List<Map<String, Object>> formattedResults = new ArrayList<>();
        
        // On ne garde que le Top 5 manuellement ou via la query
        rawResults.stream().limit(5).forEach(row -> {
            Map<String, Object> item = new LinkedHashMap<>();
            item.put("produit", row[0]);
            item.put("quantiteVendue", row[1]);
            formattedResults.add(item);
        });
        
        return ResponseEntity.ok(formattedResults);
    }

    @GetMapping("/chiffre-affaires-mensuel")
    public ResponseEntity<List<Map<String, Object>>> getChiffreAffairesMensuel() {
        return ResponseEntity.ok(commandeRepository.getChiffreAffairesParMois());
    }
}