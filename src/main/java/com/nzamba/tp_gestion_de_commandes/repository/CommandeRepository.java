package com.nzamba.tp_gestion_de_commandes.repository;

import com.nzamba.tp_gestion_de_commandes.entity.Commande;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface CommandeRepository extends JpaRepository<Commande, Long> {

    // 1. Chiffre d'affaires global (Utilisation de c.lignes selon votre entité)
    @Query("SELECT SUM(l.prixUnitaire * l.quantite) FROM Commande c JOIN c.lignes l WHERE c.statut != 'CANCELLED'")
    Double computeTotalChiffreAffaires();

    // 2. Total des dépenses d'un client spécifique
    @Query("SELECT COALESCE(SUM(l.prixUnitaire * l.quantite), 0.0) FROM Commande c JOIN c.lignes l WHERE c.client.id = :clientId AND c.statut != 'CANCELLED'")
    Double computeTotalDepensesParClient(@Param("clientId") Long clientId);

    // 3. Top 5 des produits les plus vendus
    @Query("SELECT l.produit, SUM(l.quantite) as totalVendu FROM Commande c JOIN c.lignes l " +
           "WHERE c.statut != 'CANCELLED' GROUP BY l.produit ORDER BY totalVendu DESC")
    List<Object[]> findTopProduitsRaw();

    // 4. Chiffre d'affaires agrégé par Mois
    @Query("SELECT FUNCTION('MONTH', c.date) as mois, FUNCTION('YEAR', c.date) as annee, SUM(l.prixUnitaire * l.quantite) as ca " +
           "FROM Commande c JOIN c.lignes l WHERE c.statut != 'CANCELLED' " +
           "GROUP BY FUNCTION('YEAR', c.date), FUNCTION('MONTH', c.date) ORDER BY annee DESC, mois DESC")
    List<Map<String, Object>> getChiffreAffairesParMois();
}