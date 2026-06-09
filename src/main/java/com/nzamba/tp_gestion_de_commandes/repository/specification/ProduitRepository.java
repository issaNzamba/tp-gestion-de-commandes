package com.nzamba.tp_gestion_de_commandes.repository.specification;

import com.nzamba.tp_gestion_de_commandes.entity.Produit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ProduitRepository extends JpaRepository<Produit, Long>, JpaSpecificationExecutor<Produit> {
    // JpaSpecificationExecutor nous donne accès à findAll(Specification, Pageable)
}