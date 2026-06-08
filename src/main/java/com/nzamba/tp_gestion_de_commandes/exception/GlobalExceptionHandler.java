package com.nzamba.tp_gestion_de_commandes.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Intercepte l'erreur quand un client ou un produit n'existe pas en base
     * Renvoie un code HTTP 404 Not Found
     */
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleEntityNotFound(EntityNotFoundException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());        body.put("status", HttpStatus.NOT_FOUND.value());
        body.put("error", "Ressource introuvable");
        body.put("message", ex.getMessage());

        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    /**
     * Intercepte l'erreur quand les quantités demandées dépassent le stock disponible
     * Intercepte l'erreur quand les quantités demandées dépassent le stock disponible
     * Renvoie un code HTTP 400 Bad Request
     */
    @ExceptionHandler(StockInsuffisantException.class)
    public ResponseEntity<Map<String, Object>> handleStockInsuffisant(StockInsuffisantException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("error", "Échec de la commande");
        body.put("message", ex.getMessage());

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
}