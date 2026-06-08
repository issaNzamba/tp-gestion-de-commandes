package com.nzamba.tp_gestion_de_commandes.controller;

import com.nzamba.tp_gestion_de_commandes.entity.Utilisateur;
import com.nzamba.tp_gestion_de_commandes.repository.UtilisateurRepository;
import com.nzamba.tp_gestion_de_commandes.security.JwtUtil;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UtilisateurRepository utilisateurRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        if (utilisateurRepository.findByUsername(request.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body(Map.of("message", "Cet utilisateur existe déjà"));
        }

        // On force Java à utiliser l'entité du package ENTITY
    Utilisateur nouvelUtilisateur = new Utilisateur();        nouvelUtilisateur.setUsername(request.getUsername());
        
        // Hachage du mot de passe via BCrypt
        nouvelUtilisateur.setPassword(passwordEncoder.encode(request.getPassword()));
        
        // La sauvegarde va maintenant compiler parfaitement !
        utilisateurRepository.save(nouvelUtilisateur);
        
        return ResponseEntity.ok(Map.of("message", "Utilisateur créé avec succès !"));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(Map.of("token", jwt));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        // En mode Stateless Pur, le logout consiste principalement à vider le contexte de sécurité
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok(Map.of("message", "Déconnexion réussie (Pensez à détruire le token côté client)"));
    }
}

// --- DTOs internes temporaires pour simplifier le TP ---
@Data class RegisterRequest { private String username; private String password; }
@Data class LoginRequest { private String username; private String password; }