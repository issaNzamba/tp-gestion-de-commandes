package com.nzamba.tp_gestion_de_commandes.repository;

// L'import crucial : il doit TOUT DE SUITE pointer vers le package entity !
import com.nzamba.tp_gestion_de_commandes.entity.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {
    Optional<Utilisateur> findByUsername(String username);
}