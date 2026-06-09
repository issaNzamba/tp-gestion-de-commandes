package com.nzamba.tp_gestion_de_commandes.repository.specification;

import com.nzamba.tp_gestion_de_commandes.entity.Produit;
import org.springframework.data.jpa.domain.Specification;

public class ProduitSpecification {

    public static Specification<Produit> hasPrixMin(Double prixMin) {
        return (root, query, cb) -> prixMin == null ? cb.conjunction() : cb.greaterThanOrEqualTo(root.get("prix"), prixMin);
    }

    public static Specification<Produit> hasPrixMax(Double prixMax) {
        return (root, query, cb) -> prixMax == null ? cb.conjunction() : cb.lessThanOrEqualTo(root.get("prix"), prixMax);
    }

    public static Specification<Produit> hasStockMax(Integer stockMax) {
        return (root, query, cb) -> stockMax == null ? cb.conjunction() : cb.lessThanOrEqualTo(root.get("stock"), stockMax);
    }
}