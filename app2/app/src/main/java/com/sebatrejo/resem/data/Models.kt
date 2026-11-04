package com.sebatrejo.resem.data

import com.google.firebase.Timestamp

data class User(
    val uid: String = "",
    val nombre: String = "",
    val rol: String = "ALUMNO"
)

data class Recinto(
    val id: String = "",
    val nombre: String = "",
    val descripcion: String = ""
)

data class Reserva(
    val id: String = "",
    val usuarioId: String = "",
    val recintoId: String = "",
    val recintoNombre: String = "",
    val fecha: String = "",
    val hora: String = "",
    val estado: String = "PENDIENTE",
    val timestamp: Timestamp? = null
)

data class ReservaItem(
    val id: String,
    val recintoNombre: String,
    val fecha: String,
    val hora: String,
    val estado: String
)
