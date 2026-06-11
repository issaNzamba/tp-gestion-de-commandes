package com.nzamba.tp_gestion_de_commandes.repository;

import com.nzamba.tp_gestion_de_commandes.security.Token;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<Token, Long> {
}