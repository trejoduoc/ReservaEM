package com.sebatrejo.reservaem

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sebatrejo.reservaem.ui.screens.LoginScreen
import com.sebatrejo.reservaem.ui.screens.AlumnoHomeScreen
import com.sebatrejo.reservaem.ui.viewmodel.AuthViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val authViewModel: AuthViewModel = viewModel()

            NavHost(
                navController = navController,
                startDestination = "login"
            ) {
                composable("login") {
                    LoginScreen(
                        viewModel = authViewModel,
                        onLoginSuccess = { role ->
                            if (role == "ADMIN") {
                                navController.navigate("admin_home")
                            } else {
                                navController.navigate("alumno_home")
                            }
                        }
                    )
                }

                composable("alumno_home") {
                    AlumnoHomeScreen(
                        onRecintoSelected = { recinto ->
                            // Por ahora solo imprimimos en consola, luego navegaremos al formulario de reserva
                            println("Recinto seleccionado: ${recinto.nombre}")
                        }
                    )
                }

                composable("admin_home") {
                    AdminHomePlaceholder()
                }
            }
        }
    }
}

@Composable
fun AdminHomePlaceholder() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "🎖️ Panel del Teniente: Próximamente Aprobaciones")
    }
}