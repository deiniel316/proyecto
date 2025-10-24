package com.example.appbienestar.ui.screens


import android.app.DatePickerDialog
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.appbienestar.data.Cita
import com.example.appbienestar.viewModel.CitaViewModel
import java.util.*

@Composable
fun AgendarCitaScreen(viewModel: CitaViewModel = viewModel()) {
    val context = LocalContext.current

    var cliente by remember { mutableStateOf("") }
    var servicio by remember { mutableStateOf("") }
    var fecha by remember { mutableStateOf("") }

    val mensaje by viewModel.mensaje.collectAsState()

    if (mensaje.isNotEmpty()) {
        Toast.makeText(context, mensaje, Toast.LENGTH_SHORT).show()
    }

    // Date picker dialog
    val calendario = Calendar.getInstance()
    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            fecha = "$dayOfMonth/${month + 1}/$year"
        },
        calendario.get(Calendar.YEAR),
        calendario.get(Calendar.MONTH),
        calendario.get(Calendar.DAY_OF_MONTH)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "🩺 Agendar nueva cita",
            style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(bottom = 20.dp)
        )

        OutlinedTextField(
            value = cliente,
            onValueChange = { cliente = it },
            label = { Text("Nombre del cliente") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = servicio,
            onValueChange = { servicio = it },
            label = { Text("Servicio") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = fecha,
            onValueChange = {},
            label = { Text("Fecha de la cita") },
            modifier = Modifier.fillMaxWidth(),
            readOnly = true,
            trailingIcon = {
                IconButton(onClick = { datePickerDialog.show() }) {
                    Icon(Icons.Default.DateRange, contentDescription = "Seleccionar fecha")
                }
            }
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                if (cliente.isBlank() || servicio.isBlank() || fecha.isBlank()) {
                    Toast.makeText(context, "❌ Completa todos los campos", Toast.LENGTH_SHORT).show()
                } else {
                    val nuevaCita = Cita(
                        cliente = cliente,
                        servicio = servicio,
                        fechaHora = fecha,
                        estado = "Pendiente"
                    )
                    viewModel.agendarCita(nuevaCita)
                    cliente = ""
                    servicio = ""
                    fecha = ""
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("✅ Agendar Cita")
        }
    }
}