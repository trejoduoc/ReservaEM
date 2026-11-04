package com.sebatrejo.reservaem.data

data class Recinto(
    val id: String = "",
    val name: String = "",
    val description: String = "",
    val category: String = "" // Ej: "Cancha de Pasto", "Gimnasio"
)