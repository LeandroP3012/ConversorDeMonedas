package org.example.service;

import org.example.model.RegistroConversion;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ServicioConversorMonedas implements ServicioConversor {
    private final ClienteTasaCambio clienteTasaCambio;
    private final List<RegistroConversion> historial;

    public ServicioConversorMonedas(ClienteTasaCambio clienteTasaCambio) {
        this.clienteTasaCambio = clienteTasaCambio;
        this.historial = new ArrayList<>();
    }

    @Override
    public RegistroConversion convertir(String monedaOrigen, String monedaDestino, double monto)
            throws IOException, InterruptedException {
        double tasa = clienteTasaCambio.obtenerTasaPar(monedaOrigen, monedaDestino);
        double montoConvertido = monto * tasa;

        RegistroConversion registro = new RegistroConversion(
                LocalDateTime.now(),
                monedaOrigen,
                monedaDestino,
                monto,
                tasa,
                montoConvertido
        );

        historial.add(registro);
        return registro;
    }

    @Override
    public List<RegistroConversion> obtenerHistorial() {
        return Collections.unmodifiableList(historial);
    }
}
