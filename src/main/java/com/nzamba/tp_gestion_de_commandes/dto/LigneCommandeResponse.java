package com.nzamba.tp_gestion_de_commandes.dto;

import lombok.Data;

@Data
public class LigneCommandeResponse {
    private Long id;
    private Long produitId;
    private String produitNom; // Optionnel mais super pratique pour l'affichage !
    private int quantite;
    private double prixUnitaire;
}