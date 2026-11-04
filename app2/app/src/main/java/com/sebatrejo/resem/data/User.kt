package com.sebatrejo.reservaem.data

enum class UserRole { ALUMNO, ADMIN }

data class User(
    val uid: String = "",
    val name: String = "",
    val email: String = "",
    val role: UserRole = UserRole.ALUMNO,
    val promotion: String = "" // Ejemplo: "Promoción 2024"
)

