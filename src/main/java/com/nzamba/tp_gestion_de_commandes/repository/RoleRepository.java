package com.nzamba.tp_gestion_de_commandes.repository;

import com.nzamba.tp_gestion_de_commandes.security.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByNom(String nom);
}