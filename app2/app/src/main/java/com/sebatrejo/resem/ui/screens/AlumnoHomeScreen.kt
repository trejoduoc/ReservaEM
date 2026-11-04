package com.sebatrejo.resem.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sebatrejo.resem.data.Recinto
import com.sebatrejo.resem.ui.viewmodel.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlumnoHomeScreen(
    authViewModel: AuthViewModel,
    onRecintoSelected: (Recinto) -> Unit,
    onViewMyReservations: () -> Unit,
    onLogout: () -> Unit
) {
    val recintos = listOf(
        Recinto(id = "1", nombre = "Cancha de Fútbol 1", descripcion = "Césped sintético"),
        Recinto(id = "2", nombre = "Cancha de Fútbol 2", descripcion = "Césped sintético"),
        Recinto(id = "3", nombre = "Gimnasio Principal", descripcion = "Multiuso / Básquetbol"),
        Recinto(id = "4", nombre = "Cancha de Tenis 1", descripcion = "Arcilla"),
        Recinto(id = "5", nombre = "Cancha de Tenis 2", descripcion = "Arcilla"),
        Recinto(id = "6", nombre = "Piscina Olímpica", descripcion = "50 metros"),
        Recinto(id = "7", nombre = "Piscina Semi-olímpica", descripcion = "25 metros"),
        Recinto(id = "8", nombre = "Sala de Musculación", descripcion = "Pesas y Máquinas"),
        Recinto(id = "9", nombre = "Cancha de Vóleibol", descripcion = "Interior"),
        Recinto(id = "10", nombre = "Trote Exterior", descripcion = "Circuito perimetral"),
        Recinto(id = "11", nombre = "Sala de Yoga/Pilates", descripcion = "Espacio zen"),
        Recinto(id = "12", nombre = "Cancha de Handball", descripcion = "Interior"),
        Recinto(id = "13", nombre = "Zona de Calistenia", descripcion = "Barras exteriores"),
        Recinto(id = "14", nombre = "Sala de Boxeo", descripcion = "Sacos y ring")
    )

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Reserva de Recintos", fontWeight = FontWeight.Bold) },
                actions = {
                    IconButton(onClick = onLogout) {
                        Icon(Icons.Default.ExitToApp, contentDescription = "Cerrar Sesión")
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = onViewMyReservations,
                icon = { Icon(Icons.Default.List, contentDescription = null) },
                text = { Text("Mis Reservas") }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Text(
                    text = "¡Hola! Selecciona un recinto:",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(bottom = 12.dp)
                )
            }
            items(recintos) { recinto ->
                RecintoCard(recinto = recinto, onClick = { onRecintoSelected(recinto) })
            }
        }
    }
}

@Composable
fun RecintoCard(recinto: Recinto, onClick: () -> Unit) {
    val icon = when {
        recinto.nombre.contains("Fútbol", ignoreCase = true) -> Icons.Default.Place // Fallback seguro
        recinto.nombre.contains("Tenis", ignoreCase = true) -> Icons.Default.Place
        recinto.nombre.contains("Piscina", ignoreCase = true) -> Icons.Default.Place
        else -> Icons.Default.Place
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                modifier = Modifier.size(50.dp),
                shape = MaterialTheme.shapes.medium,
                color = MaterialTheme.colorScheme.primary
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(30.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = recinto.nombre,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = recinto.descripcion,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
