package com.nzamba.tp_gestion_de_commandes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.nzamba.tp_gestion_de_commandes.repository") // Si nécessaire, mettez-la ICI
public class TpGestionDeCommandesApplication {
    public static void main(String[] args) {
        SpringApplication.run(TpGestionDeCommandesApplication.class, args);
    }
}