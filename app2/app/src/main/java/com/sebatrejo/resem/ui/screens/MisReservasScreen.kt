package com.sebatrejo.resem.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.sebatrejo.resem.data.ReservaItem


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MisReservasScreen(onBack: () -> Unit) {
    val db = FirebaseFirestore.getInstance()
    val auth = FirebaseAuth.getInstance()
    val currentUserId = auth.currentUser?.uid ?: "anonymous"

    var misReservas by remember { mutableStateOf<List<ReservaItem>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        db.collection("reservas")
            .whereEqualTo("usuarioId", currentUserId)
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .addSnapshotListener { value, error ->
                if (error != null) {
                    isLoading = false
                    return@addSnapshotListener
                }

                misReservas = value?.documents?.map { doc ->
                    ReservaItem(
                        id = doc.id,
                        recintoNombre = doc.getString("recintoNombre") ?: "Desconocido",
                        fecha = doc.getString("fecha") ?: "Sin fecha",
                        hora = doc.getString("hora") ?: "Sin hora",
                        estado = doc.getString("estado") ?: "PENDIENTE"
                    )
                } ?: emptyList()
                isLoading = false
            }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Mis Reservas", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }
    ) { padding ->
        if (isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else if (misReservas.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                Text("Aún no tienes reservas realizadas 🕒", fontSize = 18.sp)
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(misReservas) { reserva ->
                    ReservaStatusCard(reserva = reserva)
                }
            }
        }
    }
}

@Composable
fun ReservaStatusCard(reserva: ReservaItem) {
    val statusColor = when (reserva.estado) {
        "APROBADA" -> Color(0xFF4CAF50)
        "RECHAZADA" -> Color.Red
        else -> Color(0xFFFFC107)
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp).fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = reserva.recintoNombre, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Text(text = "${reserva.fecha} | ${reserva.hora}", fontSize = 14.sp, color = Color.Gray)
            }

            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = reserva.estado,
                    color = statusColor,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
                if (reserva.estado == "APROBADA") {
                    Icon(Icons.Default.CheckCircle, contentDescription = null, tint = statusColor, modifier = Modifier.size(20.dp))
                }
            }
        }
    }
}
