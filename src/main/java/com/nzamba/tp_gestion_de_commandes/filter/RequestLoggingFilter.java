package com.nzamba.tp_gestion_de_commandes.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Component
@Slf4j
public class RequestLoggingFilter extends OncePerRequestFilter {

    private static final String TRACE_ID_KEY = "traceId";
    private static final String USER_KEY = "username";

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        // Évite de tracer les endpoints techniques et la documentation pour ne pas saturer les logs
        return path.startsWith("/swagger-ui") || 
               path.startsWith("/v3/api-docs") || 
               path.startsWith("/h2-console") || 
               path.equals("/favicon.ico");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, 
                                    HttpServletResponse response, 
                                    FilterChain filterChain) throws ServletException, IOException {
        
        long startTime = System.currentTimeMillis();
        
        // 1. Génération d'un Trace-ID unique pour la requête
        String traceId = request.getHeader("X-Trace-ID");
        if (traceId == null || traceId.isEmpty()) {
            traceId = UUID.randomUUID().toString().substring(0, 8); // Format court pour lisibilité
        }
        
        // 2. Récupération de l'utilisateur (le filtre étant placé après JwtAuthenticationFilter)
        String username = "anonyme";
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && !"anonymousUser".equals(authentication.getPrincipal())) {
            username = authentication.getName();
        }

        // 3. Injection dans le MDC (Mapped Diagnostic Context) pour Slf4j/Logback
        MDC.put(TRACE_ID_KEY, traceId);
        MDC.put(USER_KEY, username);
        
        // Optionnel : Injecter le Trace-ID dans la réponse HTTP pour faciliter le debug côté client/Postman
        response.setHeader("X-Trace-ID", traceId);

        try {
            // Log d'entrée de requête
            log.info("-> Entrée : {} {}", request.getMethod(), request.getRequestURI());
            
            // Continuer la chaîne de filtres
            filterChain.doFilter(request, response);
            
        } finally {
            // 4. Calcul du temps d'exécution et log de sortie
            long duration = System.currentTimeMillis() - startTime;
            log.info("<- Sortie : {} {} - Statut: {} - Durée: {}ms", 
                    request.getMethod(), request.getRequestURI(), response.getStatus(), duration);
            
            // Crucial : Nettoyage du thread-local MDC pour éviter les fuites de mémoire
            MDC.clear();
        }
    }
}