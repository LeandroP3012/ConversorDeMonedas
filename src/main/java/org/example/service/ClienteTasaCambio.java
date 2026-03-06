package org.example.service;

import java.io.IOException;

public interface ClienteTasaCambio {
    double obtenerTasaPar(String monedaOrigen, String monedaDestino) throws IOException, InterruptedException;
}
