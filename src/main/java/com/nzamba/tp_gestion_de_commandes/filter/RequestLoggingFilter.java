package com.nzamba.tp_gestion_de_commandes.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class RequestLoggingFilter extends OncePerRequestFilter {
    private static final Logger logger = LoggerFactory.getLogger(RequestLoggingFilter.class);
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        
        // 1. Laisser Spring Security et les autres filtres traiter la requête en premier
        try {
            filterChain.doFilter(request, response);
        } finally {
            // 2. Récupérer l'utilisateur connecté depuis le SecurityContextHolder (post-authentification)
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = (authentication != null && authentication.isAuthenticated() 
                    && !"anonymousUser".equals(authentication.getPrincipal())) 
                    ? authentication.getName() 
                    : "anonyme";

            // 3. Capturer le timestamp actuel
            String timestamp = LocalDateTime.now().format(formatter);

            // 4. Logger les informations demandées (URI, Méthode, Utilisateur, Timestamp)
            logger.info("[{}] Réf: {} {} - Utilisateur: {} - Statut Réponse: {}", 
                    timestamp,
                    request.getMethod(), 
                    request.getRequestURI(), 
                    username,
                    response.getStatus());
        }
    }
}