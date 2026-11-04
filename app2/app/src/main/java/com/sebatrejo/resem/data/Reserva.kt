package com.sebatrejo.reservaem.data

enum class ReservaStatus { PENDIENTE, APROBADA, RECHAZADA }

data class Reserva(
    val id: String = "",
    val userId: String = "",
    val recintoId: String = "",
    val date: String = "", // Formato "yyyy-MM-dd"
    val timeSlot: String = "", // Ej: "08:00 - 10:00"
    val status: ReservaStatus = ReservaStatus.PENDIENTE,
    val requestDate: Long = System.currentTimeMillis()
)