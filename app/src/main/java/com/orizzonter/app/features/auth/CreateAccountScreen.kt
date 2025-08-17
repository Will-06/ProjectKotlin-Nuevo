package com.orizzonter.app.features.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.orizzonter.app.features.auth.data.AuthPreferences
import com.orizzonter.app.features.auth.data.LocalAuthRepository

@Composable
fun CreateAccountScreen(navController: NavController) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val context = LocalContext.current
    val prefs = remember { AuthPreferences(context) }
    val viewModel = remember { AuthViewModel(LocalAuthRepository(prefs)) }
    val authState by viewModel.authState.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        WaveShape(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .align(Alignment.TopCenter),
            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f),
            isTop = true
        )

        WaveShape(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .align(Alignment.BottomCenter),
            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f),
            isTop = false
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(24.dp))
                    .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.1f))
                    .border(
                        1.dp,
                        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.15f),
                        RoundedCornerShape(24.dp)
                    )
                    .padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Crear cuenta",
                    style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onBackground
                )

                Spacer(Modifier.height(32.dp))

                AuthInputField(value = name, onValueChange = { name = it }, placeholder = "Nombre completo")
                Spacer(Modifier.height(16.dp))
                AuthInputField(value = email, onValueChange = { email = it }, placeholder = "Correo electrónico")
                Spacer(Modifier.height(16.dp))
                AuthInputField(
                    value = password,
                    onValueChange = { password = it },
                    placeholder = "Contraseña",
                    isPassword = true
                )

                Spacer(Modifier.height(32.dp))

                RegisterButton(
                    onClick = {
                        if (email.isNotBlank() && password.isNotBlank()) {
                            viewModel.register(name, email, password)
                        }
                    }
                )

                when (authState) {
                    is AuthState.Registered -> {
                        LaunchedEffect(Unit) {
                            navController.navigate("home") {
                                popUpTo("create_account") { inclusive = true }
                            }
                        }
                    }
                    is AuthState.Error -> {
                        Text(
                            text = (authState as AuthState.Error).message,
                            color = MaterialTheme.colorScheme.error,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }
                    else -> {}
                }

                Spacer(Modifier.height(16.dp))

                TextButton(onClick = { navController.popBackStack() }) {
                    Text(
                        "¿Ya tienes cuenta? Inicia sesión",
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}

@Composable
private fun AuthInputField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    isPassword: Boolean = false
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text(placeholder) },
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        shape = RoundedCornerShape(14.dp),
        singleLine = true,
        visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None
    )
}

@Composable
private fun RegisterButton(onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.15f))
            .border(
                1.5.dp,
                MaterialTheme.colorScheme.primary.copy(alpha = 0.4f),
                RoundedCornerShape(16.dp)
            )
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Registrarse",
            color = MaterialTheme.colorScheme.primary,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
    }
}
