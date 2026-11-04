# 🎖️ Proyecto: Gestión de Reservas Escuela Militar

## 🛠️ Estado Técnico actual
- **Arquitectura:** Jetpack Compose (Modern UI) + MVVM.
- **Paquete:** `com.sebatrejo.reservaem`
- **Firebase:** 
  - Auth conectado (Email/Password).
  - Firestore conectado (Colección `users` con roles ADMIN y ALUMNO).
- **Navegación:** NavHost implementado con rutas: `login`, `alumno_home`, `admin_home`.
- **Componentes creados:**
  - `User.kt`, `Recinto.kt`, `Reserva.kt` (Modelos en paquete `data`).
  - `AuthViewModel.kt` (Lógica de acceso y roles en paquete `ui.viewmodel`).
  - `LoginScreen.kt` (Interfaz de inicio de sesión en paquete `ui.screens`).
  - `MainActivity.kt` (Orquestador de navegación).

## ✅ Logros alcanzados
- [x] Configuración de Gradle y Kotlin 2.0 estable.
- [x] Conexión exitosa con Firebase Auth y Firestore.
- [x] Flujo de Login funcional con redirección según rol (Admin/Alumno).
- [x] App corre sin errores en emulador/dispositivo.

## 🚀 Pendientes para la próxima sesión
1. **Pantalla de Alumno:** Crear `AlumnoHomeScreen.kt` con la lista de los 14 recintos deportivos.
2. **Formulario de Reserva:** Crear pantalla para seleccionar fecha/hora y enviar solicitud a Firestore.
3. **Panel del Teniente:** Crear `AdminHomeScreen.kt` para aprobar o rechazar solicitudes pendientes.
4. **Mis Reservas:** Vista para que el alumno vea el estado de sus pedidos.
