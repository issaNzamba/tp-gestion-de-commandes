package com.nzamba.tp_gestion_de_commandes.dto;

import lombok.Data;
import java.util.List;

@Data
public class CommandeRequest {
    private Long clientId;
    private List<LigneCommandeRequest> lignes;
}