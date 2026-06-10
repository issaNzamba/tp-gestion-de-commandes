package com.nzamba.tp_gestion_de_commandes.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.JoinColumn;
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

    // Correction ici : suppression du cascade = CascadeType.ALL pour éviter le conflit de persistance
    @OneToOne
    @JoinColumn(name = "utilisateur_id") // Optionnel mais recommandé pour nommer proprement votre clé étrangère
    private Utilisateur utilisateur; 
}