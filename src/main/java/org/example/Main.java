package org.example;

import org.example.config.ConfiguracionApi;
import org.example.model.MonedasSoportadas;
import org.example.model.RegistroConversion;
import org.example.service.ClienteApiTipoCambio;
import org.example.service.ServicioConversor;
import org.example.service.ServicioConversorMonedas;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class Main {
    private final Scanner scanner;
    private final ServicioConversor servicioConversor;

    public Main() {
        this.scanner = new Scanner(System.in);
        this.servicioConversor = new ServicioConversorMonedas(new ClienteApiTipoCambio());
    }

    public static void main(String[] args) {
        new Main().iniciar();
    }

    private void iniciar() {
        System.out.println("*******************************************");
        System.out.println("Sea bienvenido/a al Conversor de Moneda");
        prepararApiKeySiHaceFalta();
        System.out.println("*******************************************");

        boolean enEjecucion = true;
        while (enEjecucion) {
            mostrarMenu();
            int opcion = leerEntero("Elija una opción válida: ");

            switch (opcion) {
                case 1 -> flujoConversion("USD", "ARS");
                case 2 -> flujoConversion("ARS", "USD");
                case 3 -> flujoConversion("USD", "BRL");
                case 4 -> flujoConversion("BRL", "USD");
                case 5 -> flujoConversion("USD", "COP");
                case 6 -> flujoConversion("COP", "USD");
                case 7 -> flujoConversion("USD", "PEN");
                case 8 -> flujoConversion("PEN", "USD");
                case 9 -> flujoConversionPersonalizada();
                case 10 -> mostrarHistorial();
                case 11 -> {
                    enEjecucion = false;
                    System.out.println("Gracias por usar el conversor. ¡Hasta luego!");
                }
                default -> System.out.println("Opción inválida. Inténtalo nuevamente.");
            }
        }
    }

    private void mostrarMenu() {
        System.out.println();
        System.out.println("1) Dólar (USD) => Peso argentino (ARS)");
        System.out.println("2) Peso argentino (ARS) => Dólar (USD)");
        System.out.println("3) Dólar (USD) => Real brasileño (BRL)");
        System.out.println("4) Real brasileño (BRL) => Dólar (USD)");
        System.out.println("5) Dólar (USD) => Peso colombiano (COP)");
        System.out.println("6) Peso colombiano (COP) => Dólar (USD)");
        System.out.println("7) Dólar (USD) => Sol peruano (PEN)");
        System.out.println("8) Sol peruano (PEN) => Dólar (USD)");
        System.out.println("9) Conversión personalizada (ARS, BOB, BRL, CLP, COP, PEN, USD)");
        System.out.println("10) Ver historial de conversiones");
        System.out.println("11) Salir");
    }

    private void prepararApiKeySiHaceFalta() {
        try {
            ConfiguracionApi.obtenerApiKey();
            System.out.println("API key detectada correctamente.");
        } catch (IllegalStateException error) {
            System.out.print("Ingresa tu API key de ExchangeRate-API: ");
            String apiKeyIngresada = scanner.nextLine().trim();
            ConfiguracionApi.definirApiKeyEjecucion(apiKeyIngresada);
            try {
                ConfiguracionApi.obtenerApiKey();
                System.out.println("API key cargada para esta ejecución.");
            } catch (IllegalStateException segundoError) {
                System.out.println("No se ingresó una API key válida. Podrás intentarlo al convertir.");
            }
        }
    }

    private void flujoConversion(String monedaOrigen, String monedaDestino) {
        double monto = leerDecimal("Ingresa el valor que deseas convertir: ");
        ejecutarConversion(monedaOrigen, monedaDestino, monto);
    }

    private void flujoConversionPersonalizada() {
        System.out.println("Monedas soportadas: " + String.join(", ", MonedasSoportadas.CODIGOS));

        String monedaOrigen = leerMoneda("Ingresa moneda origen: ");
        String monedaDestino = leerMoneda("Ingresa moneda destino: ");
        double monto = leerDecimal("Ingresa el valor que deseas convertir: ");

        ejecutarConversion(monedaOrigen, monedaDestino, monto);
    }

    private void ejecutarConversion(String monedaOrigen, String monedaDestino, double monto) {
        try {
            RegistroConversion registro = servicioConversor.convertir(monedaOrigen, monedaDestino, monto);
            System.out.println("Resultado: " + registro.aTexto());
        } catch (IOException error) {
            System.out.println("No fue posible completar la conversión: " + error.getMessage());
        } catch (InterruptedException error) {
            Thread.currentThread().interrupt();
            System.out.println("La solicitud fue interrumpida: " + error.getMessage());
        } catch (IllegalStateException error) {
            System.out.println("Error de configuración o API: " + error.getMessage());
        }
    }

    private void mostrarHistorial() {
        List<RegistroConversion> historial = servicioConversor.obtenerHistorial();
        if (historial.isEmpty()) {
            System.out.println("Aún no hay conversiones registradas.");
            return;
        }

        System.out.println("\n--- Historial de conversiones ---");
        for (int indice = 0; indice < historial.size(); indice++) {
            System.out.println((indice + 1) + ". " + historial.get(indice).aTexto());
        }
    }

    private int leerEntero(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String texto = scanner.nextLine();
            try {
                return Integer.parseInt(texto.trim());
            } catch (NumberFormatException error) {
                System.out.println("Debes ingresar un número entero.");
            }
        }
    }

    private double leerDecimal(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String texto = scanner.nextLine().trim().replace(',', '.');
            try {
                double valor = Double.parseDouble(texto);
                if (valor < 0) {
                    System.out.println("El valor no puede ser negativo.");
                    continue;
                }
                return valor;
            } catch (NumberFormatException error) {
                System.out.println("Debes ingresar un número válido.");
            }
        }
    }

    private String leerMoneda(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String codigo = scanner.nextLine().trim().toUpperCase(Locale.ROOT);
            if (MonedasSoportadas.estaSoportada(codigo)) {
                return codigo;
            }
            System.out.println("Código no soportado. Usa: " + String.join(", ", MonedasSoportadas.CODIGOS));
        }
    }
}
