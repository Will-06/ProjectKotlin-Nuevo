package com.orizzonter.app.features.auth

import android.util.Patterns
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.orizzonter.app.R
import com.orizzonter.app.features.auth.data.AuthPreferences
import com.orizzonter.app.features.auth.data.LocalAuthRepository

@Composable
fun LoginScreen(navController: NavController) {
    val context = LocalContext.current
    val prefs = remember { AuthPreferences(context) }
    val viewModel = remember { AuthViewModel(LocalAuthRepository(prefs)) }
    val authState by viewModel.authState.collectAsState()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(authState) {
        if (authState is AuthState.Success) {
            navController.navigate("home") {
                popUpTo("login") { inclusive = true }
            }
        }
    }

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
                    .padding(vertical = 32.dp, horizontal = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Iniciar sesión",
                    style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onBackground
                )

                Spacer(Modifier.height(32.dp))

                AuthTextField(
                    value = email,
                    onValueChange = {
                        email = it
                        errorMessage = null
                    },
                    placeholder = "Correo electrónico"
                )

                Spacer(Modifier.height(16.dp))

                AuthTextField(
                    value = password,
                    onValueChange = {
                        password = it
                        errorMessage = null
                    },
                    placeholder = "Contraseña",
                    isPassword = true
                )

                Spacer(Modifier.height(32.dp))

                LoginButton {
                    when {
                        email.isBlank() || password.isBlank() -> {
                            errorMessage = "Completa todos los campos"
                        }
                        !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                            errorMessage = "Correo no válido"
                        }
                        else -> {
                            errorMessage = null
                            viewModel.login(email, password)
                        }
                    }
                }

                if (authState is AuthState.Error) {
                    Text(
                        text = (authState as AuthState.Error).message,
                        color = Color.Red,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }

                errorMessage?.let {
                    Text(
                        text = it,
                        color = Color.Red,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }

                Spacer(Modifier.height(16.dp))

                TextButton(onClick = { navController.navigate("forgot_password") }) {
                    Text(
                        "¿Olvidaste tu contraseña?",
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Medium
                    )
                }

                Spacer(Modifier.height(24.dp))

                Text(
                    text = "O continúa con",
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = 14.sp
                )

                Spacer(Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    SocialLoginButton(R.drawable.google) {}
                    SocialLoginButton(R.drawable.facebook) {}
                    SocialLoginButton(R.drawable.twitter) {}
                }

                Spacer(Modifier.height(32.dp))

                Text(
                    text = "¿No tienes cuenta?",
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.Medium
                )

                TextButton(onClick = { navController.navigate("create_account") }) {
                    Text(
                        "Regístrate aquí",
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
private fun AuthTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    isPassword: Boolean = false
) {
    var passwordVisible by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text(placeholder) },
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        shape = RoundedCornerShape(14.dp),
        singleLine = true,
        visualTransformation = if (isPassword && !passwordVisible) PasswordVisualTransformation() else VisualTransformation.None,
        trailingIcon = {
            if (isPassword) {
                val image = if (passwordVisible)
                    Icons.Filled.Visibility
                else Icons.Filled.VisibilityOff

                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(imageVector = image, contentDescription = if (passwordVisible) "Ocultar" else "Mostrar")
                }
            }
        }
    )
}

@Composable
private fun LoginButton(onClick: () -> Unit) {
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
            text = "Iniciar sesión",
            color = MaterialTheme.colorScheme.primary,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun SocialLoginButton(iconResId: Int, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(56.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.05f))
            .border(
                1.dp,
                MaterialTheme.colorScheme.onSurface.copy(alpha = 0.15f),
                RoundedCornerShape(12.dp)
            )
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(iconResId),
            contentDescription = null,
            modifier = Modifier.size(28.dp)
        )
    }
}

@Composable
fun WaveShape(
    modifier: Modifier = Modifier,
    color: Color,
    isTop: Boolean = true
) {
    Canvas(modifier = modifier) {
        val width = size.width
        val height = size.height
        val waveHeight = height * 0.6f

        val path = Path().apply {
            if (isTop) {
                moveTo(0f, height)
                cubicTo(width * 0.25f, height - waveHeight, width * 0.75f, height + waveHeight, width, height)
                lineTo(width, 0f)
                lineTo(0f, 0f)
            } else {
                moveTo(0f, 0f)
                cubicTo(width * 0.25f, waveHeight, width * 0.75f, -waveHeight, width, 0f)
                lineTo(width, height)
                lineTo(0f, height)
            }
            close()
        }

        drawPath(path, color, style = Fill)
    }
}
