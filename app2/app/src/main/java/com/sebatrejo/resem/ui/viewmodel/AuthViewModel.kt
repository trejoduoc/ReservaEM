package com.sebatrejo.reservaem.ui.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class AuthViewModel : ViewModel() {
    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    var isLoading = mutableStateOf(false)
    var userRole = mutableStateOf<String?>(null)
    var authError = mutableStateOf<String?>(null)

    fun login(email: String, pass: String, onResult: (Boolean) -> Unit) {
        isLoading.value = true
        authError.value = null

        auth.signInWithEmailAndPassword(email, pass)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    checkUserRole(auth.currentUser?.uid ?: "", onResult)
                } else {
                    authError.value = "Error: ${task.exception?.message}"
                    isLoading.value = false
                    onResult(false)
                }
            }
    }

    private fun checkUserRole(uid: String, onResult: (Boolean) -> Unit) {
        db.collection("users").document(uid).get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful && task.result != null) {
                    val role = task.result?.getString("role") ?: "ALUMNO"
                    userRole.value = role
                    isLoading.value = false
                    onResult(true)
                } else {
                    authError.value = "Usuario no registrado en la base de datos."
                    isLoading.value = false
                    onResult(false)
                }
            }
    }

    fun logout() {
        auth.signOut()
        userRole.value = null
    }
}