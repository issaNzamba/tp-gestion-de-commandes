package com.nzamba.tp_gestion_de_commandes.controller;

import com.nzamba.tp_gestion_de_commandes.entity.Produit;
import com.nzamba.tp_gestion_de_commandes.service.ProduitService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/produits")
@RequiredArgsConstructor
public class ProduitController {

    private final ProduitService produitService;

    @GetMapping("/recherche")
    public ResponseEntity<Page<Produit>> rechercher(
            @RequestParam(required = false) Double prixMin,
            @RequestParam(required = false) Double prixMax,
            @RequestParam(required = false) Integer stockMax,
            Pageable pageable) {
        
        return ResponseEntity.ok(produitService.chercherProduits(prixMin, prixMax, stockMax, pageable));
    }
}