package com.nzamba.tp_gestion_de_commandes.entity;

// ❌ SUPPRIMEZ l'import de security.Utilisateur qui s'y trouvait

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.CascadeType;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String email;

    @OneToOne(cascade = CascadeType.ALL)
    private Utilisateur utilisateur; // Utilise maintenant l'entité du package entity !
}