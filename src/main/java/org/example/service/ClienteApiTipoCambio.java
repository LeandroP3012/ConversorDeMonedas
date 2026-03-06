package org.example.service;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.example.config.ConfiguracionApi;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ClienteApiTipoCambio implements ClienteTasaCambio {
    private final HttpClient clienteHttp;

    public ClienteApiTipoCambio() {
        this.clienteHttp = HttpClient.newHttpClient();
    }

    @Override
    public double obtenerTasaPar(String monedaOrigen, String monedaDestino) throws IOException, InterruptedException {
        String url = ConfiguracionApi.construirUrlPar(monedaOrigen, monedaDestino);

        HttpRequest solicitud = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> respuesta = clienteHttp.send(solicitud, HttpResponse.BodyHandlers.ofString());

        if (respuesta.statusCode() != 200) {
            throw new IllegalStateException("Error HTTP al consultar la API: " + respuesta.statusCode());
        }

        JsonElement elementoRaiz = JsonParser.parseString(respuesta.body());
        JsonObject objetoRaiz = elementoRaiz.getAsJsonObject();

        String resultado = objetoRaiz.get("result").getAsString();
        if (!"success".equalsIgnoreCase(resultado)) {
            String tipoError = objetoRaiz.has("error-type") ? objetoRaiz.get("error-type").getAsString() : "desconocido";
            throw new IllegalStateException("La API devolvió un error: " + tipoError);
        }

        return objetoRaiz.get("conversion_rate").getAsDouble();
    }
}
