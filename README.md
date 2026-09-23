# Taller 5 — Lista con Navegación, Búsqueda y Pila de Navegación

Programación para Dispositivos Móviles — Universidad de Caldas

App Android en **Kotlin + Jetpack Compose** con:

- Lista de 14 animales (4 categorías) con `LazyColumn`, `Card` y `key = { it.id }`.
- Búsqueda en tiempo real por título.
- Navegación lista → detalle pasando el `id` con Navigation Compose.
- Pantalla de detalle con botón "Volver" (`popBackStack()`).
- Corrección del bug de navegación duplicada con `launchSingleTop = true`.
- Bonus A: filtro por categoría con chips combinado con la búsqueda.
- Bonus B: se conserva la posición del scroll al volver del detalle.

## Entregables

- Código fuente: `app/src/main/java/com/example/taller5/MainActivity.kt`
- Análisis técnico y predicción: [ANALISIS.md](ANALISIS.md)
- Capturas y videos: carpeta [evidencias](evidencias)

## Cómo ejecutarlo

1. Abrir la carpeta en Android Studio.
2. Esperar la sincronización de Gradle.
3. Ejecutar con ▶ en un emulador o celular (Android 7.0 o superior).
