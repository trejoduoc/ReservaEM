package com.sebatrejo.resem.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.sebatrejo.resem.data.ReservaItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminHomeScreen() {
    val db = FirebaseFirestore.getInstance()
    var reservas by remember { mutableStateOf<List<ReservaItem>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }

    // Escuchar reservas en tiempo real (Solo las PENDIENTES)
    LaunchedEffect(Unit) {
        db.collection("reservas")
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .addSnapshotListener { value, error ->
                if (error != null) {
                    isLoading = false
                    return@addSnapshotListener
                }

                val list = value?.documents?.mapIndexed { index, doc ->
                    ReservaItem(
                        id = doc.id,
                        recintoNombre = doc.getString("recintoNombre") ?: "Desconocido",
                        fecha = doc.getString("fecha") ?: "Sin fecha",
                        hora = doc.getString("hora") ?: "Sin hora",
                        estado = doc.getString("estado") ?: "PENDIENTE"
                    )
                }?.filter { it.estado == "PENDIENTE" } ?: emptyList()

                reservas = list
                isLoading = false
            }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Panel del Teniente", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.errorContainer
                )
            )
        }
    ) { padding ->
        if (isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else if (reservas.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                Text("No hay solicitudes pendientes 🎖️", fontSize = 18.sp)
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item {
                    Text(
                        text = "Solicitudes Pendientes",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }
                items(reservas) { reserva ->
                    AdminReservaCard(
                        reserva = reserva,
                        onApprove = { approveReserva(db, reserva.id) },
                        onReject = { rejectReserva(db, reserva.id) }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminReservaCard(reserva: ReservaItem, onApprove: () -> Unit, onReject: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(text = reserva.recintoNombre, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    Text(text = "${reserva.fecha} | ${reserva.hora}", color = Color.Gray)
                }
                Badge { Text("PENDIENTE") }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(onClick = onReject, colors = ButtonDefaults.textButtonColors(contentColor = Color.Red)) {
                    Icon(Icons.Default.Close, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Rechazar")
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(
                    onClick = onApprove,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50))
                ) {
                    Icon(Icons.Default.CheckCircle, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Aprobar")
                }
            }
        }
    }
}

// Funciones de actualización en Firestore
fun approveReserva(db: FirebaseFirestore, id: String) {
    db.collection("reservas").document(id).update("estado", "APROBADA")
}

fun rejectReserva(db: FirebaseFirestore, id: String) {
    db.collection("reservas").document(id).update("estado", "RECHAZADA")
}
