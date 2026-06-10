package com.nzamba.tp_gestion_de_commandes.initializer;

import com.nzamba.tp_gestion_de_commandes.entity.*;
import com.nzamba.tp_gestion_de_commandes.repository.*;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@Profile("dev") // S'exécute uniquement si le profil 'dev' est actif
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {

    private final UtilisateurRepository utilisateurRepository;
    private final ProduitRepository produitRepository;
    private final ClientRepository clientRepository;
    private final CommandeRepository commandeRepository;
    private final PasswordEncoder passwordEncoder; // Essentiel pour le hachage des mots de passe

    @Override
    public void run(String... args) throws Exception {
        log.info("▶ Début de l'initialisation des données de test (Profil: dev)...");

        // 1. Initialisation des Utilisateurs (Rôles & Mots de passe hachés)
        if (utilisateurRepository.count() == 0) {
            Utilisateur admin = new Utilisateur();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRole("ADMIN");
            utilisateurRepository.save(admin);

            Utilisateur userNzamba = new Utilisateur();
            userNzamba.setUsername("nzamba");
            userNzamba.setPassword(passwordEncoder.encode("password123"));
            userNzamba.setRole("USER");
            
            // On sauvegarde individuellement et on écrase la variable locale 
            // avec l'instance contenant l'ID généré par la base de données
            userNzamba = utilisateurRepository.save(userNzamba);
            log.info("  ✓ Utilisateurs créés (admin / admin123) et (nzamba / password123)");
            
            // 2. Initialisation du Client lié à l'utilisateur métier
            Client client = new Client();
            client.setNom("Nzamba Dev");
            client.setEmail("nzamba@example.com");
            client.setUtilisateur(userNzamba); // Utilise l'instance managée saine
            
            clientRepository.save(client);
            log.info("  ✓ Profil Client rattaché créé pour 'nzamba'");    
        }

        // 3. Initialisation des Produits
        if (produitRepository.count() == 0) {
            Produit p1 = new Produit();
            p1.setNom("iPhone 15 Pro");
            p1.setPrix(1299.99);
            p1.setStock(15);

            Produit p2 = new Produit();
            p2.setNom("MacBook Air M3");
            p2.setPrix(1499.00);
            p2.setStock(8);

            Produit p3 = new Produit();
            p3.setNom("Souris Logi MX Master 3S");
            p3.setPrix(109.00);
            p3.setStock(50);

            produitRepository.saveAll(List.of(p1, p2, p3));
            log.info("  ✓ Catalogue produits initialisé (iPhone, MacBook, Souris)");
        }

        // 4. Initialisation d'une première Commande de test
        if (commandeRepository.count() == 0 && clientRepository.count() > 0) {
            Client client = clientRepository.findAll().get(0);
            
            Commande commande = new Commande();
            commande.setClient(client);
            commande.setDate(LocalDateTime.now());
            commande.setStatut(StatutCommande.CREATED);

            commandeRepository.save(commande);
            log.info("  ✓ Une commande de test créée pour le client : {}", client.getNom());
        }

        log.info("🏁 Fin de l'initialisation des données de test. Prêt pour les requêtes !");
    }
}