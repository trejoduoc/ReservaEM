package com.sebatrejo.resem.ui.screens

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*
import com.google.firebase.auth.FirebaseAuth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReservaFormScreen(recintoNombre: String, recintoId: String, onReservationComplete: () -> Unit) {
    val context = LocalContext.current
    val db = FirebaseFirestore.getInstance()
    val auth = FirebaseAuth.getInstance()
    val currentUserId = auth.currentUser?.uid ?: "anonymous"
    var fecha by remember { mutableStateOf("Selecciona fecha") }
    var hora by remember { mutableStateOf("Selecciona hora") }
    var isSubmitting by remember { mutableStateOf(false) }

    // Calendario para seleccionar fecha
    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            fecha = "$dayOfMonth/${month + 1}/$year"
        },
        Calendar.getInstance().get(Calendar.YEAR),
        Calendar.getInstance().get(Calendar.MONTH),
        Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
    )

    // Reloj para seleccionar hora
    val timePickerDialog = TimePickerDialog(
        context,
        { _, hour, minute ->
            hora = String.format("%02d:%02d", hour, minute)
        },
        Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
        Calendar.getInstance().get(Calendar.MINUTE),
        true
    )

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Detalle de Reserva", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Reservando:",
                fontSize = 18.sp,
                color = Color.Gray
            )
            Text(
                text = recintoNombre,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 32.dp)
            )

            // Botón Fecha
            Button(
                onClick = { datePickerDialog.show() },
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.medium
            ) {
                Text(text = fecha)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Botón Hora
            Button(
                onClick = { timePickerDialog.show() },
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.medium
            ) {
                Text(text = hora)
            }

            Spacer(modifier = Modifier.height(48.dp))

            if (isSubmitting) {
                CircularProgressIndicator()
            } else {
                Button(
                    onClick = {
                        if (fecha != "Selecciona fecha" && hora != "Selecciona hora") {
                            isSubmitting = true
                            val reserva = hashMapOf(
                                "recintoId" to recintoId,
                                "recintoNombre" to recintoNombre,
                                "fecha" to fecha,
                                "hora" to hora,
                                "estado" to "PENDIENTE",
                                "usuarioId" to currentUserId, // Ahora usa la variable real, sin comillas
                                "timestamp" to com.google.firebase.Timestamp.now()
                            )

                            db.collection("reservas").add(reserva)
                                .addOnSuccessListener {
                                    isSubmitting = false
                                    onReservationComplete()
                                }
                                .addOnFailureListener {
                                    isSubmitting = false
                                }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                    shape = MaterialTheme.shapes.medium
                ) {
                    Text("Enviar Solicitud", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
