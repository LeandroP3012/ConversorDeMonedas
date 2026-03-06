package org.example.model;

import java.util.List;

public interface MonedasSoportadas {
    String ARS = "ARS";
    String BOB = "BOB";
    String BRL = "BRL";
    String CLP = "CLP";
    String COP = "COP";
    String PEN = "PEN";
    String USD = "USD";

    List<String> CODIGOS = List.of(ARS, BOB, BRL, CLP, COP, PEN, USD);

    static boolean estaSoportada(String codigo) {
        return CODIGOS.contains(codigo);
    }
}
