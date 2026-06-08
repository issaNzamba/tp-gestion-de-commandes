package com.nzamba.tp_gestion_de_commandes.dto;

import lombok.Data;

@Data
public class LigneCommandeRequest {
    private Long produitId;
    private int quantite;
}