package com.nzamba.tp_gestion_de_commandes.security;

// On importe la bonne et unique entité Utilisateur !
import com.nzamba.tp_gestion_de_commandes.entity.Utilisateur;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.ToString;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 500)
    private String valeur;

    private LocalDateTime dateExpiration;
    private boolean revoque;

    @ManyToOne
    @ToString.Exclude 
    private Utilisateur utilisateur; // Pointe maintenant sur com.nzamba.tp_gestion_de_commandes.entity.Utilisateur
}