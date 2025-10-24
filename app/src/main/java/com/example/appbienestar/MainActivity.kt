package com.example.appbienestar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation  // ✅ NUEVO IMPORT
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.appbienestar.ui.screens.AgendarCitaScreen
import com.example.appbienestar.ui.screens.ServiciosScreen
import com.example.appbienestar.ui.theme.AppBienestarTheme
import com.example.appbienestar.viewModel.ClienteViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppBienestarTheme {
                // ✅ NUEVO: Estado para controlar si el usuario está logueado
                var isLoggedIn by remember { mutableStateOf(false) }

                // Navegación simple - si no está logueado, mostrar Login
                if (!isLoggedIn) {
                    LoginScreen(
                        onLoginSuccess = {
                            isLoggedIn = true  // Al loguearse, cambia a true
                        }
                    )
                } else {
                    // Si está logueado, mostrar la pantalla principal
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        var selectedScreen by remember { mutableStateOf("clientes") }

                        Scaffold(
                            bottomBar = {
                                NavigationBar {
                                    NavigationBarItem(
                                        selected = selectedScreen == "clientes",
                                        onClick = { selectedScreen = "clientes" },
                                        icon = { Text("👥") },
                                        label = { Text("Clientes") }
                                    )
                                    NavigationBarItem(
                                        selected = selectedScreen == "servicios",
                                        onClick = { selectedScreen = "servicios" },
                                        icon = { Text("💼") },
                                        label = { Text("Servicios") }
                                    )
                                    NavigationBarItem(
                                        selected = selectedScreen == "citas",
                                        onClick = { selectedScreen = "citas" },
                                        icon = { Text("📅") },
                                        label = { Text("Citas") }
                                    )
                                }
                            }
                        ) { padding ->
                            Box(Modifier.padding(padding)) {
                                when (selectedScreen) {
                                    "clientes" -> ClientesScreen(onLogout = { isLoggedIn = false })
                                    "servicios" -> ServiciosScreen()
                                    "citas" -> AgendarCitaScreen()
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

// ✅ NUEVO: Pantalla de Login temporal
@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "🔐 App Bienestar",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        TextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Usuario") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp)
        )

        Button(
            onClick = {
                // ✅ Login temporal - cualquier usuario/contraseña funciona
                if (username.isNotBlank() && password.isNotBlank()) {
                    onLoginSuccess()
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = username.isNotBlank() && password.isNotBlank()
        ) {
            Text("Iniciar Sesión")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Para probar: usa cualquier usuario y contraseña",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

// ✅ MODIFICADO: ClientesScreen ahora acepta parámetro onLogout
@Composable
fun ClientesScreen(
    onLogout: () -> Unit = {},
    viewModel: ClienteViewModel = viewModel()
) {
    // Cargar clientes cuando se inicia la pantalla
    LaunchedEffect(Unit) {
        viewModel.cargarClientes()
    }

    val clientes by viewModel.clientes.collectAsState(emptyList())
    val mensaje by viewModel.mensaje.collectAsState("")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // ✅ NUEVO: Header con botón de logout
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "🏥 App Bienestar",
                style = MaterialTheme.typography.headlineLarge,
            )
            TextButton(onClick = onLogout) {
                Text("Cerrar Sesión")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (mensaje.isNotEmpty()) {
            Text(
                text = mensaje,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        Text(
            text = "Lista de Clientes:",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        if (clientes.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("No hay clientes registrados")
            }
        } else {
            LazyColumn {
                items(clientes) { cliente ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(
                                text = "👤 ${cliente.nombreCompleto}",
                                style = MaterialTheme.typography.bodyLarge
                            )
                            Text(
                                text = "DPI: ${cliente.dpi}",
                                style = MaterialTheme.typography.bodyMedium
                            )
                            cliente.email?.let { email ->
                                Text(
                                    text = "Email: $email",
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ClientesScreenPreview() {
    AppBienestarTheme {
        AgendarCitaScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    AppBienestarTheme {
        LoginScreen(onLoginSuccess = {})
    }
}