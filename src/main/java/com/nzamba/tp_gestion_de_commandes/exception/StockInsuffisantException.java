package com.nzamba.tp_gestion_de_commandes.exception;

public class StockInsuffisantException extends RuntimeException {
    public StockInsuffisantException(String message) {
        super(message);
    }
}