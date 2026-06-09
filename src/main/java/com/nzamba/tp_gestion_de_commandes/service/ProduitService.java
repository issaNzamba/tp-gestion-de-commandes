package com.nzamba.tp_gestion_de_commandes.service;

import com.nzamba.tp_gestion_de_commandes.entity.Produit;
import com.nzamba.tp_gestion_de_commandes.repository.ProduitRepository;
import com.nzamba.tp_gestion_de_commandes.repository.specification.ProduitSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProduitService {

    private final ProduitRepository produitRepository;

    public Page<Produit> chercherProduits(Double prixMin, Double prixMax, Integer stockMax, Pageable pageable) {
        // Nouvelle approche Spring Boot 3.5+ : On part d'une base neutre (toujours vraie)
        Specification<Produit> spec = Specification.allOf(
                ProduitSpecification.hasPrixMin(prixMin),
                ProduitSpecification.hasPrixMax(prixMax),
                ProduitSpecification.hasStockMax(stockMax)
        );

        return produitRepository.findAll(spec, pageable);
    }
}