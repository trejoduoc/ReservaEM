# 🎖️ Documentación Técnica: Gestión de Reservas Escuela Militar

Este documento contiene toda la base conceptual y técnica del proyecto para ser utilizada en la elaboración de informes y presentaciones.

## 1. Descripción General
La aplicación es un sistema de gestión de reservas deportivas diseñado para la Escuela Militar. Su objetivo es optimizar la asignación de recintos deportivos, eliminando la gestión manual y proporcionando transparencia tanto al alumno como al administrador (Teniente).

## 2. Arquitectura Técnica
- **Frontend:** Android Nativo con **Jetpack Compose** (UI Declarativa).
- **Patrón de Diseño:** **MVVM** (Model-View-ViewModel) para separación de lógica y vista.
- **Backend:** **Firebase** (BaaS).
    - **Firebase Auth**: Gestión de identidad y autenticación de usuarios.
    - **Cloud Firestore**: Base de datos NoSQL en tiempo real para almacenar usuarios, recintos y reservas.
- **Lenguaje:** Kotlin 2.0.

## 3. Flujos de Usuario

### A. Flujo del Alumno (Consumidor)
1. **Autenticación**: Login seguro vía email/password.
2. **Selección**: Visualización de una lista de 14 recintos deportivos disponibles.
3. **Solicitud**: Formulario de reserva donde selecciona fecha y hora.
4. **Persistencia**: La solicitud se guarda en Firestore con estado `PENDIENTE`.
5. **Seguimiento**: Pantalla de "Mis Reservas" donde puede ver si su solicitud fue `APROBADA` o `RECHAZADA` en tiempo real.

### B. Flujo del Administrador / Teniente (Gestor)
1. **Acceso**: Login con rol `ADMIN`.
2. **Supervisión**: Panel de control que lista todas las solicitudes `PENDIENTES` en tiempo real.
3. **Toma de Decisión**: El administrador puede aprobar o rechazar la reserva con un solo clic.
4. **Actualización**: El cambio de estado se refleja instantáneamente en la app del alumno.

## 4. Modelo de Datos (Firestore)

### Colección `users`
- `uid`: String (ID único)
- `nombre`: String
- `rol`: String (`ADMIN` | `ALUMNO`)

### Colección `reservas`
- `id`: String (Autogenerado)
- `usuarioId`: String (Referencia al alumno)
- `recintoId`: String (ID del recinto)
- `recintoNombre`: String
- `fecha`: String (DD/MM/YYYY)
- `hora`: String (HH:mm)
- `estado`: String (`PENDIENTE` | `APROBADA` | `RECHAZADA`)
- `timestamp`: Timestamp (Fecha de creación)

## 5. Valor Agregado y Soluciones Implementadas
- **Tiempo Real**: Uso de `SnapshotListeners` para evitar recargas manuales.
- **Seguridad de Roles**: Redirección automática según el perfil del usuario.
- **UX Moderna**: Interfaz basada en Material Design 3 con componentes responsivos.
