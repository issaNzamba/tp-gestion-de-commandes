package com.nzamba.tp_gestion_de_commandes.service;

import com.nzamba.tp_gestion_de_commandes.dto.*;
import com.nzamba.tp_gestion_de_commandes.entity.*;
import com.nzamba.tp_gestion_de_commandes.exception.EntityNotFoundException;
import com.nzamba.tp_gestion_de_commandes.exception.StockInsuffisantException;
import com.nzamba.tp_gestion_de_commandes.repository.*;
import com.nzamba.tp_gestion_de_commandes.repository.ProduitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommandeService {

    private final CommandeRepository commandeRepository;
    private final ProduitRepository produitRepository;
    private final ClientRepository clientRepository;

    @Transactional
    public CommandeResponse creerCommande(CommandeRequest request) {
        // 1. Recherche du client
        Client client = clientRepository.findById(request.getClientId())
                .orElseThrow(() -> new EntityNotFoundException("Client introuvable"));

        // 2. Initialisation de la commande
        Commande commande = new Commande();
        commande.setClient(client);
        commande.setDate(LocalDateTime.now());
        commande.setStatut(StatutCommande.CREATED);

        List<LigneCommande> lignes = new ArrayList<>();
        
        // 3. Boucle sur les lignes de la requête
        for (LigneCommandeRequest lr : request.getLignes()) {
            Produit produit = produitRepository.findById(lr.getProduitId())
                    .orElseThrow(() -> new EntityNotFoundException("Produit introuvable"));

            // Vérification du stock avec l'exception dédiée
            if (produit.getStock() < lr.getQuantite()) {
                throw new StockInsuffisantException("Stock insuffisant pour " + produit.getNom());
            }

            // Déduction du stock
            produit.setStock(produit.getStock() - lr.getQuantite());
            produitRepository.save(produit);

            // Construction de la ligne de commande
            LigneCommande ligne = new LigneCommande();
            ligne.setProduit(produit);
            ligne.setQuantite(lr.getQuantite());
            ligne.setPrixUnitaire(produit.getPrix());
            ligne.setCommande(commande);
            lignes.add(ligne);
        }

        commande.setLignes(lignes);
        
        // 4. Sauvegarde
        commande = commandeRepository.save(commande);
        
        // 5. Mapping et retour
        return mapToResponse(commande);
    }

    /**
     * Implémentation manuelle du mapping demandée par l'exemple
     */
    private CommandeResponse mapToResponse(Commande c) {
        CommandeResponse response = new CommandeResponse();
        response.setId(c.getId());
        response.setDate(c.getDate());
        response.setStatut(c.getStatut());
        response.setClientId(c.getClient().getId());

        // Calcul du total global et transformation des lignes en DTOs
        double totalGlobal = 0.0;
        List<LigneCommandeResponse> lignesDto = new ArrayList<>();

        for (LigneCommande lc : c.getLignes()) {
            LigneCommandeResponse lDto = new LigneCommandeResponse();
            lDto.setId(lc.getId());
            lDto.setProduitId(lc.getProduit().getId());
            lDto.setProduitNom(lc.getProduit().getNom());
            lDto.setQuantite(lc.getQuantite());
            lDto.setPrixUnitaire(lc.getPrixUnitaire());
            
            lignesDto.add(lDto);
            totalGlobal += lc.getPrixUnitaire() * lc.getQuantite();
        }

        response.setLignes(lignesDto);
        response.setTotal(totalGlobal);
        
        return response;
    }

    /**
     * Récupérer une commande par son ID
     */
    public CommandeResponse getById(Long id) {
        Commande commande = commandeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Commande introuvable avec l'ID : " + id));
        return mapToResponse(commande);
    }

    /**
     * Récupérer TOUTES les commandes (Pour l'administrateur)
     */
    public List<CommandeResponse> getAllCommandes() {
        return commandeRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }
}