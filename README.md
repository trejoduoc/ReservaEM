# 🎖️ Gestión de Reservas - Escuela Militar

Sistema de gestión de reservas deportivas desarrollado como proyecto final para la asignatura de Aplicaciones Móviles. La aplicación permite a los alumnos solicitar el uso de recintos deportivos y al administrador (Teniente) gestionar dichas solicitudes en tiempo real.

## 🚀 Características Principales

### 👤 Módulo del Alumno
- **Autenticación Segura**: Acceso mediante correo electrónico y contraseña.
- **Catálogo de Recintos**: Visualización de los 14 recintos deportivos disponibles con iconos dinámicos según la disciplina.
- **Sistema de Reservas**: Formulario intuitivo para la selección de fecha y hora de uso.
- **Seguimiento en Tiempo Real**: Sección de "Mis Reservas" donde el alumno puede monitorear si su solicitud está `PENDIENTE`, `APROBADA` o `RECHAZADA`.

### 🎖️ Módulo del Administrador (Teniente)
- **Panel de Gestión**: Vista centralizada de todas las solicitudes pendientes de aprobación.
- **Control de Acceso**: Capacidad de aprobar o rechazar solicitudes con un solo clic.
- **Sincronización Instantánea**: Actualización automática de la lista de solicitudes gracias a la tecnología de tiempo real de Firebase.

---

## 🛠️ Stack Tecnológico

### Frontend
- **Lenguaje**: [Kotlin](https://kotlinlang.org/)
- **UI Framework**: [Jetpack Compose](https://developer.android.com/jetpack/compose) (UI Declarativa)
- **Arquitectura**: [MVVM](https://developer.android.com/topic/architecture) (Model-View-ViewModel)
- **Navegación**: Jetpack Navigation Component

### Backend (BaaS)
- **Plataforma**: [Firebase](https://firebase.google.com/)
- **Autenticación**: Firebase Auth (Email/Password)
- **Base de Datos**: Cloud Firestore (NoSQL)

---

## 🏗️ Estructura del Proyecto

```text
com.sebatrejo.resem
├── data/
│   └── Models.kt          # Modelos de datos globales (User, Recinto, Reserva)
├── ui/
│   ├── screens/
│   │   ├── LoginScreen.kt        # Interfaz de acceso y validación de roles
│   │   ├── AlumnoHomeScreen.kt   # Catálogo de recintos y navegación
│   │   ├── ReservaFormScreen.kt  # Formulario de solicitud de reserva
│   │   ├── MisReservasScreen.kt   # Estado de reservas del alumno
│   │   └── AdminHomeScreen.kt     # Panel de gestión del administrador
│   ├── viewmodel/
│   │   └── AuthViewModel.kt     # Lógica de negocio y gestión de sesión
│   └── theme/                     # Configuración de colores y tipografía (Material 3)
└── MainActivity.kt               # Orquestador de navegación y punto de entrada
```

---

## ⚙️ Configuración e Instalación

1. **Clonar el repositorio**:
   ```bash
   git clone https://github.com/trejoduoc/ReservaEM.git
   ```
2. **Firebase**:
   - Crear un proyecto en la consola de Firebase.
   - Habilitar **Authentication** (Email/Password) y **Cloud Firestore**.
   - Agregar una aplicación Android con el paquete `com.sebatrejo.resem`.
   - Descargar el archivo `google-services.json` y colocarlo en la carpeta `app/`.
3. **Ejecutar**:
   - Abrir el proyecto en **Android Studio (Koala o superior)**.
   - Sincronizar Gradle y ejecutar en un emulador con API 24 o superior.

---

## 🎓 Créditos
Desarrollado por **Seba Trejo** como parte de la formación académica en el área de desarrollo móvil.
