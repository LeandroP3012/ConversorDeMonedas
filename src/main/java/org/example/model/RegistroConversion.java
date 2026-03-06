package org.example.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class RegistroConversion {
    private static final DateTimeFormatter FORMATO = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    private final LocalDateTime fechaHora;
    private final String monedaOrigen;
    private final String monedaDestino;
    private final double montoOriginal;
    private final double tasaCambio;
    private final double montoConvertido;

    public RegistroConversion(LocalDateTime fechaHora,
                              String monedaOrigen,
                              String monedaDestino,
                              double montoOriginal,
                              double tasaCambio,
                              double montoConvertido) {
        this.fechaHora = fechaHora;
        this.monedaOrigen = monedaOrigen;
        this.monedaDestino = monedaDestino;
        this.montoOriginal = montoOriginal;
        this.tasaCambio = tasaCambio;
        this.montoConvertido = montoConvertido;
    }

    public String aTexto() {
        return String.format(
                "[%s] %.2f %s -> %.2f %s (tasa: %.6f)",
                fechaHora.format(FORMATO),
                montoOriginal,
                monedaOrigen,
                montoConvertido,
                monedaDestino,
                tasaCambio
        );
    }
}
