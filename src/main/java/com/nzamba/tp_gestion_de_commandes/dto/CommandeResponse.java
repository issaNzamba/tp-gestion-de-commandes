package com.nzamba.tp_gestion_de_commandes.dto;

import com.nzamba.tp_gestion_de_commandes.entity.StatutCommande;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class CommandeResponse {
    private Long id;
    private LocalDateTime date;
    private StatutCommande statut;
    private Long clientId;
    private List<LigneCommandeResponse> lignes;
    private double total;
}