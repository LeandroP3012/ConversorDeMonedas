package org.example.config;

public final class ConfiguracionApi {
    private static final String VARIABLE_API_KEY = "EXCHANGE_RATE_API_KEY";
    private static final String URL_BASE = "https://v6.exchangerate-api.com/v6/";
    private static String apiKeyEjecucion;

    private ConfiguracionApi() {
    }

    public static String obtenerApiKey() {
        if (apiKeyEjecucion != null && !apiKeyEjecucion.isBlank()) {
            return apiKeyEjecucion;
        }

        String apiKey = System.getenv(VARIABLE_API_KEY);
        if (apiKey == null || apiKey.isBlank()) {
            throw new IllegalStateException(
                    "No se encontró la API key. Configura la variable EXCHANGE_RATE_API_KEY o ingrésala al iniciar.");
        }

        return apiKey;
    }

    public static void definirApiKeyEjecucion(String apiKey) {
        if (apiKey != null && !apiKey.isBlank()) {
            apiKeyEjecucion = apiKey.trim();
        }
    }

    public static String construirUrlPar(String monedaOrigen, String monedaDestino) {
        return URL_BASE + obtenerApiKey() + "/pair/" + monedaOrigen + "/" + monedaDestino;
    }
}
