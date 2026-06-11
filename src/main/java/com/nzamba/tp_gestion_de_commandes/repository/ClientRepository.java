package com.nzamba.tp_gestion_de_commandes.repository;

import com.nzamba.tp_gestion_de_commandes.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}