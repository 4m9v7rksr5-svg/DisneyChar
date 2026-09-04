# DisneyChar

**DisneyChar** es una aplicación de Android moderna y robusta diseñada para explorar el universo de personajes de Disney. 
Este proyecto sirve como una implementación de referencia para **Clean Architecture**

## 🚀 Características

- **Exploración de Personajes:** Listado dinámico obtenido de la API oficial de Disney.
- **Búsqueda en Tiempo Real:** Barra de búsqueda integrada con Material 3 que filtra resultados localmente mientras escribes.
- **Soporte Offline (Local First):** Cacheo inteligente de datos mediante **Room**, permitiendo el uso de la app sin conexión.
- **Gestión de Favoritos:** Sistema híbrido de persistencia que utiliza **Jetpack DataStore** para guardar preferencias de usuario (IDs de favoritos).
- **Monitor de Conectividad:** Detección automática del estado de red para alternar entre datos de la API y datos locales (favoritos).
- **Navegación ** Implementación de Jetpack Navigation con paso de argumentos y estados dinámicos en la UI.

---

## 🏗️ Arquitectura

La aplicación sigue los principios de **Clean Architecture**

### 1. Capa de Dominio (Domain)
- **Models:** Entidades puras de Kotlin (`DisneyCharacter`).
- **Use Cases:** Casos de uso específicos (`DisneyCharUseCase`, `DisneyCharSingleUseCase`, `SaveFavoriteUseCase`).
- **Interfaces:** Definición del contrato del repositorio (`DisneyRepository`).

### 2. Capa de Datos (Data)
- **Remote:** Consumo de la API de Disney usando **Retrofit** y DTOs mapeados.
- **Local:** Base de datos **Room** para caché y **DataStore** para preferencias.
- **Repository Implementation:** Orquesta el flujo de datos siguiendo el patrón "Single Source of Truth".
- **Mappers:** Conversores encargados de transformar datos entre DTOs, Entities y Domain Models.

### 3. Capa de Presentación (UI)
- **MVVM Pattern:** ViewModels que gestionan el estado mediante `StateFlow` reactivos.
- **Jetpack Compose:** Interfaz de usuario 100% declarativa con componentes de Material 3.
- **Modularización de UI:** Pantallas (`screens`) y componentes reutilizables (`components`) separados por archivos para máxima mantenibilidad.

---

## 🛠️ Stack Tecnológico

- **UI:** [Jetpack Compose](https://developer.android.com/jetpack/compose) (Material 3)
- **Inyección de Dependencias:** [Hilt](https://developer.android.com/training/dependency-injection/hilt-android)
- **Red:** [Retrofit 2](https://square.github.io/retrofit/) + [Gson](https://github.com/google/gson)
- **Base de Datos:** [Room](https://developer.android.com/training/data-storage/room)
- **Preferencias:** [Jetpack DataStore](https://developer.android.com/topic/libraries/architecture/datastore)
- **Carga de Imágenes:** [Coil](https://coil-kt.github.io/coil/)
- **Asincronía:** Coroutines & Kotlin Flows
- **Arquitectura:** Clean Architecture + MVVM

---

## 📦 Instalación y Uso

### Requisitos
- Android Studio Ladybug | 2024.2.1 o superior.
- Java JDK 11 o superior.
- Dispositivo Android con API 29+.

### Ejecución
1. Clona este repositorio:
   ```bash
   git clone https://github.com/edcode/disneychar.git
   ```
2. Abre el proyecto en Android Studio.
3. Sincroniza los archivos de Gradle.
4. Ejecuta la aplicación en tu emulador o dispositivo físico.

---

## ✒️ Autor
*   **Eduardo Alvarez** - *Desarrollo de Software y Arquitectura de Android*
