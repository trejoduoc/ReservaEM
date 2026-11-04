package com.sebatrejo.resem.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sebatrejo.resem.data.Recinto

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlumnoHomeScreen(onRecintoSelected: (Recinto) -> Unit, onViewMyReservations: () -> Unit) {
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
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = onViewMyReservations,
                icon = { Icon(Icons.Default.Place, contentDescription = null) },
                text = { Text("Mis Reservas") }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Text(
                    text = "Selecciona el recinto que deseas reservar:",
                    fontSize = 16.sp,
                    modifier = Modifier.padding(bottom = 8.dp)
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
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Place,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(40.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = recinto.nombre,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = recinto.descripcion,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
            }
        }
    }
}
