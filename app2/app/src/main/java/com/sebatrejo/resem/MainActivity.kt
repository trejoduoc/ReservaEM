package com.sebatrejo.resem

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
import com.sebatrejo.resem.ui.screens.LoginScreen
import com.sebatrejo.resem.ui.screens.AlumnoHomeScreen
import com.sebatrejo.resem.ui.screens.ReservaFormScreen
import com.sebatrejo.resem.ui.screens.AdminHomeScreen
import com.sebatrejo.resem.ui.screens.MisReservasScreen
import com.sebatrejo.resem.ui.viewmodel.AuthViewModel

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
                        authViewModel = authViewModel,
                        onRecintoSelected = { recinto ->
                            navController.navigate("reserva_form/${recinto.id}/${recinto.nombre}")
                        },
                        onViewMyReservations = {
                            navController.navigate("mis_reservas")
                        },
                        onLogout = {
                            authViewModel.logout()
                            navController.navigate("login") {
                                popUpTo("login") { inclusive = true }
                            }
                        }
                    )
                }

                composable("mis_reservas") {
                    MisReservasScreen(
                        onBack = { navController.popBackStack() }
                    )
                }

                composable("reserva_form/{recintoId}/{recintoNombre}") { backStackEntry ->
                    val id = backStackEntry.arguments?.getString("recintoId") ?: ""
                    val nombre = backStackEntry.arguments?.getString("recintoNombre") ?: ""
                    ReservaFormScreen(
                        recintoId = id,
                        recintoNombre = nombre,
                        onReservationComplete = {
                            navController.navigate("mis_reservas")
                        }
                    )
                }

                composable("admin_home") {
                    AdminHomeScreen()
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