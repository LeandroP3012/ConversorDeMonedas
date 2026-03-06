# Conversor de Monedas (Java)

Aplicación de consola que convierte montos entre monedas usando la API de ExchangeRate-API en tiempo real.

## ¿Qué hace?

- Muestra un menú por consola para elegir conversiones.
- Convierte con tasas actualizadas desde API (`/pair/{origen}/{destino}`).
- Permite conversiones directas y una opción personalizada.
- Guarda un historial de conversiones con fecha y hora.

Monedas incluidas:
`ARS`, `BOB`, `BRL`, `CLP`, `COP`, `PEN`, `USD`.

## ¿Cómo está implementado?

El proyecto está organizado con Programación Orientada a Objetos por responsabilidades:

- `config`: configuración de API key y construcción de URL.
- `model`: estructuras del dominio (registro de conversión y monedas soportadas).
- `service`: lógica de negocio y cliente HTTP para consumir la API.
- `Main`: menú de consola, lectura de datos y flujo principal.

Tecnologías usadas:

- Java 17
- `HttpClient`, `HttpRequest`, `HttpResponse`
- Gson (parseo de JSON)
- Maven

## Flujo de funcionamiento

1. La app inicia y valida la API key.
2. Si no existe variable de entorno, pide la clave por consola.
3. El usuario elige una opción del menú e ingresa el monto.
4. Se consulta la tasa de cambio a la API.
5. Se calcula y muestra el resultado.
6. Se registra la conversión en el historial.

## Configuración de API key

Opción recomendada (variable de entorno):

- Nombre: `EXCHANGE_RATE_API_KEY`
- Valor: tu clave de ExchangeRate-API

También puedes ingresar la clave al iniciar el programa (modo interactivo).

## Notas
- Si la API key es inválida, la API devolverá error y el programa lo mostrará en consola.
