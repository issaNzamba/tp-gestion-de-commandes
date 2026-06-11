package com.nzamba.tp_gestion_de_commandes.repository;

import com.nzamba.tp_gestion_de_commandes.entity.LigneCommande;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LigneCommandeRepository extends JpaRepository<LigneCommande, Long> {
}