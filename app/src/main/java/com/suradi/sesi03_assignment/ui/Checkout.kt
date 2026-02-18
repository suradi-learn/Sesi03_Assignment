package com.suradi.sesi03_assignment.ui


import androidx.compose.foundation.clickable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
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


private val CoffeeDark = Color(0xFF4E342E)
private val CoffeeMid = Color(0xFF6D4C41)
private val CoffeeBg = Color(0xFFF3E9E2)
private val CoffeeChipOn = Color(0xFF5D4037)
private val CoffeeChipOff = Color(0xFFE7D6CC)
private val ErrorRed = Color(0xFFB00020)

private enum class ShippingMethod { REGULAR, EXPRESS, SAME_DAY }

@Composable
fun CheckoutView() {

    // 1) STATE input
    var nama by remember { mutableStateOf("") }
    var telepon by remember { mutableStateOf("") }
    var alamat by remember { mutableStateOf("") }
    var catatan by remember { mutableStateOf("") }
    var metode by remember { mutableStateOf(ShippingMethod.REGULAR) }
    var agreeTnc by remember { mutableStateOf(false) }

    // 2) STATE dialog
    var showDialog by remember { mutableStateOf(false) }

    // 3) VALIDASI
    val namaValid = isValidNama(nama)
    val teleponValid = isValidTelepon(telepon)
    val alamatValid = isValidAlamat(alamat)

    val formValid = namaValid && teleponValid && alamatValid && agreeTnc

    // 4) Layout Utama
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(CoffeeBg)
            .verticalScroll(rememberScrollState()) // supaya bagian bawah bisa terlihat di hp layar kecil
    ) {

        // Header 
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .background(CoffeeDark)
                .padding(16.dp),
            contentAlignment = Alignment.BottomStart
        ) {
            Text(
                text = "Checkout",
                color = Color.White,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            // Nama Penerima
            Text("Nama Penerima", fontWeight = FontWeight.Bold, color = CoffeeDark,)
            OutlinedTextField(
                value = nama,
                onValueChange = { nama = it },
                placeholder = { Text("Nama lengkap") },
                modifier = Modifier.fillMaxWidth(),
                isError = nama.isNotBlank() && !namaValid
            )
            if (nama.isNotBlank() && !namaValid) {
                Text("Nama minimal 2 karakter", color = ErrorRed)
            }

            // Telepon
            Text("Telepon", fontWeight = FontWeight.Bold, color = CoffeeDark)
            OutlinedTextField(
                value = telepon,
                onValueChange = { telepon = it },
                placeholder = { Text("08xxxxxxxxxx") },
                modifier = Modifier.fillMaxWidth(),
                isError = telepon.isNotBlank() && !teleponValid
            )
            Text("Hanya digit, 10–15", color = CoffeeDark.copy(alpha = 0.7f))
            if (telepon.isNotBlank() && !teleponValid) {
                Text("Nomor telepon tidak valid", color = ErrorRed)
            }

            // Alamat (multiline)
            Text("Alamat", fontWeight = FontWeight.Bold, color = CoffeeDark)
            OutlinedTextField(
                value = alamat,
                onValueChange = { alamat = it },
                placeholder = { Text("Jalan, No, RT/RW, Kelurahan, Kecamatan...") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(110.dp),
                isError = alamat.isNotBlank() && !alamatValid,
                singleLine = false,
                maxLines = 4
            )
            if (alamat.isNotBlank() && !alamatValid) {
                Text("Alamat minimal 10 karakter", color = ErrorRed)
            }

            // Metode Pengiriman (pilih salah satu)
            Text("Metode Pengiriman", fontWeight = FontWeight.Bold, color = CoffeeDark)

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                ShippingChip(
                    text = "Regular",
                    selected = metode == ShippingMethod.REGULAR,
                    onClick = { metode = ShippingMethod.REGULAR }
                )
                ShippingChip(
                    text = "Express",
                    selected = metode == ShippingMethod.EXPRESS,
                    onClick = { metode = ShippingMethod.EXPRESS }
                )
                ShippingChip(
                    text = "Same Day",
                    selected = metode == ShippingMethod.SAME_DAY,
                    onClick = { metode = ShippingMethod.SAME_DAY }
                )
            }

            // Catatan (opsional)
            Text("Catatan (opsional)", fontWeight = FontWeight.Bold, color = CoffeeDark)
            OutlinedTextField(
                value = catatan,
                onValueChange = { catatan = it },
                modifier = Modifier.fillMaxWidth()
            )

            // Checkbox S&K
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Checkbox(
                    checked = agreeTnc,
                    onCheckedChange = { agreeTnc = it }
                )
                Text(
                    text = "Saya setuju dengan Syarat & Ketentuan",
                    color = CoffeeDark
                )
            }

            // Tombol Buat Pesanan
            Button(
                onClick = { showDialog = true },
                enabled = formValid,
                colors = ButtonDefaults.buttonColors(
                    containerColor = CoffeeMid,
                    disabledContainerColor = CoffeeMid.copy(alpha = 0.35f)
                ),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text("Buat Pesanan", color = Color.White)
            }

            Spacer(modifier = Modifier.height(6.dp))
        }
    }

    // Dialog Ringkasan
    if (showDialog) {
        val metodeText = when (metode) {
            ShippingMethod.REGULAR -> "Regular"
            ShippingMethod.EXPRESS -> "Express"
            ShippingMethod.SAME_DAY -> "Same Day"
        }

        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = {
                Text("Ringkasan Pesanan", color = CoffeeDark, fontWeight = FontWeight.Bold)
            },
            text = {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Text("Nama: $nama")
                        Text("Telepon: $telepon")
                        Text("Alamat: $alamat")
                        Text("Pengiriman: $metodeText")
                        Text("Catatan: ${catatan.ifBlank { "(kosong)" }}")
                    }
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

@Composable
private fun ShippingChip(
    text: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    val bg = if (selected) CoffeeChipOn else CoffeeChipOff
    val fg = if (selected) Color.White else CoffeeDark

    Box(
        modifier = Modifier
            .background(bg, RoundedCornerShape(18.dp))
            .clickable { onClick() }              // ini yang bisa buat di klik jadi berhasil
            .padding(horizontal = 14.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = fg,
            fontWeight = FontWeight.SemiBold
        )
    }
}

// Fungsi validasi
private fun isValidNama(nama: String): Boolean = nama.trim().length >= 2

private fun isValidTelepon(telp: String): Boolean {
    val t = telp.trim()
    if (t.isEmpty()) return false
    if (!t.all { it.isDigit() }) return false
    return t.length in 10..15
}

private fun isValidAlamat(alamat: String): Boolean = alamat.trim().length >= 10

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun CheckoutPreview() {
    CheckoutView()
}

