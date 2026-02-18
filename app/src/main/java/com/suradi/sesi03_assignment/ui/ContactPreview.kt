package com.suradi.sesi03_assignment.ui


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


private val CoffeeDark = Color(0xFF4E342E)     // coklat tua (header)
private val CoffeeMid  = Color(0xFF6D4C41)     // coklat sedang (button)
private val CoffeeBg   = Color(0xFFF3E9E2)     // cream coffee (background)
private val ErrorRed   = Color(0xFFB00020)

@Composable
fun ContactPreviewView() {

    // 1) STATE: data input
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }

    // 2) STATE: dialog
    var showDialog by remember { mutableStateOf(false) }

    // 3) VALIDASI: true/false
    val nameValid = isValidName(name)
    val emailValid = isValidEmail(email)

    // Tombol aktif hanya kalau dua-duanya valid
    val formValid = nameValid && emailValid

    // 4) LAYOUT UTAMA
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(CoffeeBg)
    ) {

        // Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(CoffeeDark)
                .padding(16.dp),
            contentAlignment = Alignment.BottomStart
        ) {
            Text(
                text = "Contact Preview",
                color = Color.White,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold
            )
        }

        // Content
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            // Label + Field: Nama
            Text(text = "Nama", fontWeight = FontWeight.Medium, color = CoffeeDark)

            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                placeholder = { Text("Masukkan nama") },
                modifier = Modifier.fillMaxWidth(),
                isError = name.isNotBlank() && !nameValid
            )

            // Pesan error untuk Nama
            if (name.isNotBlank() && !nameValid) {
                Text(
                    text = "Nama minimal 2 karakter",
                    color = ErrorRed
                )
            }

            // Label + Field: Email
            Text(text = "Email", fontWeight = FontWeight.Medium, color = CoffeeDark)

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                placeholder = { Text("nama@contoh.com") },
                modifier = Modifier.fillMaxWidth(),
                isError = email.isNotBlank() && !emailValid
            )

            // Pesan error untuk Email
            if (email.isNotBlank() && !emailValid) {
                Text(
                    text = "Alamat email tidak valid",
                    color = ErrorRed
                )
            }

            // Tombol Preview
            Button(
                onClick = { showDialog = true },
                enabled = formValid,
                colors = ButtonDefaults.buttonColors(containerColor = CoffeeMid),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text(text = "Preview", color = Color.White)
            }

            Spacer(modifier = Modifier.height(8.dp))

        }
    }

    // Dialog Ringkasan
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = {
                Text(text = "Ringkasan", color = CoffeeDark, fontWeight = FontWeight.Bold)
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text(text = "Nama: $name")
                    Text(text = "Email: $email")
                }
            },
            confirmButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("OK", color = CoffeeMid, fontWeight = FontWeight.Bold)
                }
            }
        )
    }
}

// Fungsi validasi
private fun isValidName(name: String): Boolean {
    return name.trim().length >= 2
}

private fun isValidEmail(email: String): Boolean {
    val e = email.trim()
    return e.contains("@") && e.length >= 5
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun ContactPreviewPreview() {
    ContactPreviewView()
}
