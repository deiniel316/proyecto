package com.example.appbienestar.ui.screens

import com.example.appbienestar.data.Cita
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.appbienestar.viewModel.CitaViewModel

@Composable
fun CitasScreen(viewModel: CitaViewModel = viewModel()) {
    val context = LocalContext.current
    val citas by viewModel.citas.collectAsState()
    val mensaje by viewModel.mensaje.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.cargarCitas()
    }

    if (mensaje.isNotEmpty()) {
        Toast.makeText(context, mensaje, Toast.LENGTH_LONG).show()
    }

    Column(Modifier.padding(16.dp)) {
        Text("📅 Citas Agendadas", style = MaterialTheme.typography.headlineMedium)

        LazyColumn(Modifier.padding(top = 8.dp)) {
            items(citas) { cita ->
                Card(
                    Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp)
                ) {
                    Column(Modifier.padding(12.dp)) {
                        Text("Cliente: ${cita.cliente}")
                        Text("Servicio: ${cita.servicio}")
                        Text("Fecha: ${cita.fechaHora}")
                        Text("Estado: ${cita.estado}")
                        Button(
                            onClick = { cita.id?.let { viewModel.cancelarCita(it) } },
                            modifier = Modifier.padding(top = 8.dp)
                        ) {
                            Text("Cancelar")
                        }
                    }
                }
            }
        }
    }
}



