package com.nzamba.tp_gestion_de_commandes.controller;

import com.nzamba.tp_gestion_de_commandes.dto.CommandeRequest;
import com.nzamba.tp_gestion_de_commandes.dto.CommandeResponse;
import com.nzamba.tp_gestion_de_commandes.service.CommandeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/commandes")
@RequiredArgsConstructor
public class CommandeController {

    private final CommandeService commandeService;

    /**
     * Un utilisateur connecté (client ou admin) peut passer une commande
     */
    @PostMapping
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')") // Remplacé hasAnyRole par hasAnyAuthority
    public ResponseEntity<CommandeResponse> creer(@Valid @RequestBody CommandeRequest request) {
        CommandeResponse response = commandeService.creerCommande(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Un utilisateur ou un admin peut voir le détail d'une commande précise
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')") // Remplacé hasAnyRole par hasAnyAuthority
    public ResponseEntity<CommandeResponse> obtenir(@PathVariable Long id) {
        return ResponseEntity.ok(commandeService.getById(id));
    }

    /**
     * SEUL l'administrateur a le droit de lister toutes les commandes de la boutique
     */
    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')") // Remplacé hasRole par hasAuthority
    public ResponseEntity<List<CommandeResponse>> listerTout() {
        return ResponseEntity.ok(commandeService.getAllCommandes());
    }
}