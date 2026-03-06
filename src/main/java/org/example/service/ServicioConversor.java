package org.example.service;

import org.example.model.RegistroConversion;

import java.io.IOException;
import java.util.List;

public interface ServicioConversor {
    RegistroConversion convertir(String monedaOrigen, String monedaDestino, double monto)
            throws IOException, InterruptedException;

    List<RegistroConversion> obtenerHistorial();
}
