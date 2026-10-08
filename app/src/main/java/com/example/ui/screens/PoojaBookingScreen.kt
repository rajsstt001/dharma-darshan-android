package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.LiveTv
import androidx.compose.material.icons.filled.Payment
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.PoojaService
import com.example.ui.theme.CardLightBorder
import com.example.ui.theme.DivineGold
import com.example.ui.theme.LightCreamBackground
import com.example.ui.theme.PureWhite
import com.example.ui.theme.SacredMaroon
import com.example.ui.theme.SaffronOrange
import com.example.ui.theme.TextDark
import com.example.ui.theme.TextMuted

@Composable
fun PoojaBookingScreen(
    pooja: PoojaService,
    onBack: () -> Unit,
    onConfirmBooking: (bookingType: String, targetName: String, poojaName: String, date: String, slot: String, name: String, gotra: String, sankalp: String, amount: Double) -> Unit,
    onJoinLiveDarshan: () -> Unit,
    modifier: Modifier = Modifier
) {
    var step by remember { mutableStateOf(1) } // 1: Details, 2: Payment, 3: Success

    var selectedDate by remember { mutableStateOf("Aug 5, 2026") }
    var selectedSlot by remember { mutableStateOf("09:00 AM - 10:30 AM") }
    var devoteeName by remember { mutableStateOf("Rajesh Sharma") }
    var gotra by remember { mutableStateOf("Kashyap") }
    var sankalpName by remember { mutableStateOf("Sharma Family Prosperity") }
    var paymentMethod by remember { mutableStateOf("UPI / GPay") }

    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(LightCreamBackground)
            .padding(16.dp)
            .verticalScroll(scrollState)
    ) {
        // Header Bar
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back", tint = SacredMaroon)
            }
            Column {
                Text(
                    text = "Book ${pooja.name}",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = SacredMaroon
                )
                Text(
                    text = pooja.templeName,
                    fontSize = 12.sp,
                    color = TextMuted
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Multi Step Progress Indicator
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            StepChip(number = "1", label = "Devotee Details", isActive = step == 1, isCompleted = step > 1)
            StepChip(number = "2", label = "Payment", isActive = step == 2, isCompleted = step > 2)
            StepChip(number = "3", label = "Confirmed", isActive = step == 3, isCompleted = false)
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (step == 1) {
            // STEP 1: Date, Slot & Devotee Gotra
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = PureWhite)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = "📅 Select Date & Time Slot", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = SacredMaroon)
                    Spacer(modifier = Modifier.height(8.dp))

                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        listOf("Aug 5, 2026", "Aug 6, 2026", "Aug 7, 2026").forEach { d ->
                            val isSel = selectedDate == d
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(if (isSel) SaffronOrange else LightCreamBackground)
                                    .clickable { selectedDate = d }
                                    .padding(horizontal = 12.dp, vertical = 8.dp)
                            ) {
                                Text(text = d, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = if (isSel) PureWhite else TextDark)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(text = "⏰ Choose Time Slot", fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = TextDark)
                    Spacer(modifier = Modifier.height(6.dp))
                    listOf("07:00 AM - 08:30 AM", "09:00 AM - 10:30 AM", "05:00 PM - 06:30 PM").forEach { slot ->
                        val isSel = selectedSlot == slot
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(if (isSel) SaffronOrange.copy(alpha = 0.12f) else PureWhite)
                                .clickable { selectedSlot = slot }
                                .padding(8.dp)
                        ) {
                            RadioButton(
                                selected = isSel,
                                onClick = { selectedSlot = slot },
                                colors = RadioButtonDefaults.colors(selectedColor = SaffronOrange)
                            )
                            Text(text = slot, fontSize = 13.sp, color = TextDark, fontWeight = if (isSel) FontWeight.Bold else FontWeight.Normal)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Devotee Sankalp Form
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = PureWhite)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = "📿 Devotee & Gotra Information", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = SacredMaroon)
                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = devoteeName,
                        onValueChange = { devoteeName = it },
                        label = { Text("Devotee Full Name", fontSize = 12.sp, maxLines = 1, overflow = TextOverflow.Ellipsis) },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = SaffronOrange)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = gotra,
                        onValueChange = { gotra = it },
                        label = { Text("Gotra (e.g. Kashyap, Vashishtha)", fontSize = 12.sp, maxLines = 1, overflow = TextOverflow.Ellipsis) },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = SaffronOrange)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = sankalpName,
                        onValueChange = { sankalpName = it },
                        label = { Text("Sankalp Purpose / Name for Chanting", fontSize = 12.sp, maxLines = 1, overflow = TextOverflow.Ellipsis) },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = SaffronOrange)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { step = 2 },
                modifier = Modifier.fillMaxWidth().height(48.dp),
                colors = ButtonDefaults.buttonColors(containerColor = SacredMaroon),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(text = "Proceed to Payment (₹${pooja.price.toInt()})", fontSize = 15.sp, fontWeight = FontWeight.Bold)
            }

        } else if (step == 2) {
            // STEP 2: Payment
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = PureWhite)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = "💳 Select Payment Method", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = SacredMaroon)
                    Spacer(modifier = Modifier.height(8.dp))

                    listOf("UPI / Google Pay / PhonePe", "Credit / Debit Card", "Net Banking", "Wallet Balance (₹2,450)").forEach { pm ->
                        val isSel = paymentMethod == pm
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(if (isSel) SaffronOrange.copy(alpha = 0.12f) else PureWhite)
                                .clickable { paymentMethod = pm }
                                .padding(10.dp)
                        ) {
                            RadioButton(
                                selected = isSel,
                                onClick = { paymentMethod = pm },
                                colors = RadioButtonDefaults.colors(selectedColor = SaffronOrange)
                            )
                            Text(text = pm, fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = TextDark)
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .background(LightCreamBackground)
                            .padding(12.dp)
                    ) {
                        Column {
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Text(text = "Pooja Amount:", fontSize = 12.sp, color = TextMuted)
                                Text(text = "₹${pooja.price.toInt()}", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = TextDark)
                            }
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Text(text = "Temple Dakshina & Taxes:", fontSize = 12.sp, color = TextMuted)
                                Text(text = "₹0 (Included)", fontSize = 12.sp, color = Color(0xFF2E7D32), fontWeight = FontWeight.Bold)
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Text(text = "Total Payable:", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = SacredMaroon)
                                Text(text = "₹${pooja.price.toInt()}", fontSize = 16.sp, fontWeight = FontWeight.ExtraBold, color = SacredMaroon)
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    onConfirmBooking("TEMPLE_POOJA", pooja.templeName, pooja.name, selectedDate, selectedSlot, devoteeName, gotra, sankalpName, pooja.price)
                    step = 3
                },
                modifier = Modifier.fillMaxWidth().height(48.dp),
                colors = ButtonDefaults.buttonColors(containerColor = SaffronOrange),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(text = "Pay ₹${pooja.price.toInt()} & Confirm Booking", fontSize = 15.sp, fontWeight = FontWeight.Bold)
            }

        } else if (step == 3) {
            // STEP 3: Booking Success
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = PureWhite)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = null,
                        tint = Color(0xFF2E7D32),
                        modifier = Modifier.size(64.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "🎉 Booking Confirmed!",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = SacredMaroon
                    )
                    Text(
                        text = "Your sankalp has been recorded for ${pooja.name}",
                        fontSize = 12.sp,
                        color = TextMuted
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(LightCreamBackground)
                            .padding(12.dp)
                    ) {
                        Column {
                            Text(text = "🛕 Temple: ${pooja.templeName}", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = TextDark)
                            Text(text = "📅 Date & Time: $selectedDate ($selectedSlot)", fontSize = 12.sp, color = TextDark)
                            Text(text = "🙏 Devotee: $devoteeName (Gotra: $gotra)", fontSize = 12.sp, color = TextDark)
                            Text(text = "✨ Sankalp: $sankalpName", fontSize = 12.sp, color = SacredMaroon, fontWeight = FontWeight.SemiBold)
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            onClick = onJoinLiveDarshan,
                            colors = ButtonDefaults.buttonColors(containerColor = SaffronOrange),
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Icon(imageVector = Icons.Default.LiveTv, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(text = "Live Aarti", fontSize = 12.sp)
                        }

                        Button(
                            onClick = onBack,
                            colors = ButtonDefaults.buttonColors(containerColor = SacredMaroon),
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Icon(imageVector = Icons.Default.Download, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(text = "Receipt", fontSize = 12.sp)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun StepChip(number: String, label: String, isActive: Boolean, isCompleted: Boolean) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(24.dp)
                .clip(CircleShape)
                .background(
                    when {
                        isCompleted -> Color(0xFF2E7D32)
                        isActive -> SaffronOrange
                        else -> CardLightBorder
                    }
                )
        ) {
            Text(
                text = if (isCompleted) "✓" else number,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = PureWhite
            )
        }
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = label,
            fontSize = 11.sp,
            fontWeight = if (isActive || isCompleted) FontWeight.Bold else FontWeight.Normal,
            color = if (isActive || isCompleted) SacredMaroon else TextMuted
        )
    }
}
