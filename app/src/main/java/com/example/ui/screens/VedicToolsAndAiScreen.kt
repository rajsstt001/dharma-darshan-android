package com.example.ui.screens

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.VedicToolItem
import com.example.ui.theme.DarkCharcoal
import com.example.ui.theme.GoldAccent
import com.example.ui.theme.KrishnaYellow
import com.example.ui.theme.LightCreamBackground
import com.example.ui.theme.PureWhite
import com.example.ui.theme.TextDark
import com.example.ui.theme.TextMuted
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun VedicToolsAndAiScreen(
    tool: VedicToolItem? = null,
    onBack: () -> Unit,
    onAskAiGuru: suspend (String) -> String,
    modifier: Modifier = Modifier
) {
    val activeTool = remember(tool) {
        tool ?: VedicToolItem(
            id = "default_kundli",
            title = "Vedic Horoscope & Kundli",
            subtitle = "AI Powered Kundli Report",
            category = com.example.data.model.ToolCategory.VEDIC,
            iconEmoji = "🔮",
            description = "Detailed Janam Kundli matching, Dasha calculations, and planetary transit remedies."
        )
    }

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    var nameInput by remember { mutableStateOf("Rajesh Sharma") }
    var dobInput by remember { mutableStateOf("15/08/1992") }
    var tobInput by remember { mutableStateOf("07:30 AM") }
    var pobInput by remember { mutableStateOf("Varanasi, UP") }
    var specificQuestion by remember { mutableStateOf("Will my career grow in 2026?") }

    var isLoading by remember { mutableStateOf(false) }
    var resultOutput by remember { mutableStateOf("") }

    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(LightCreamBackground)
            .padding(16.dp)
            .verticalScroll(scrollState)
    ) {
            // Header Banner Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = DarkCharcoal),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Row(
                    modifier = Modifier.padding(20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = activeTool.iconEmoji, fontSize = 42.sp)
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text(
                            text = activeTool.title,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = KrishnaYellow
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = activeTool.description,
                            fontSize = 12.sp,
                            color = PureWhite.copy(alpha = 0.85f)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Birth Details Input Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = PureWhite),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Enter Birth Details for Accurate Prediction",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextDark
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = nameInput,
                        onValueChange = { nameInput = it },
                        label = { Text("Full Name") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = KrishnaYellow)
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedTextField(
                            value = dobInput,
                            onValueChange = { dobInput = it },
                            label = { Text("Date of Birth") },
                            modifier = Modifier.weight(1f),
                            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = KrishnaYellow)
                        )
                        OutlinedTextField(
                            value = tobInput,
                            onValueChange = { tobInput = it },
                            label = { Text("Time of Birth") },
                            modifier = Modifier.weight(1f),
                            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = KrishnaYellow)
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = pobInput,
                        onValueChange = { pobInput = it },
                        label = { Text("Place of Birth") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = KrishnaYellow)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = specificQuestion,
                        onValueChange = { specificQuestion = it },
                        label = { Text("Specific Question / Goal (Optional)") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = KrishnaYellow)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = {
                            coroutineScope.launch {
                                isLoading = true
                                resultOutput = ""
                                val prompt = "Generate Vedic / AI analysis for ${activeTool.title}. Name: $nameInput, DOB: $dobInput, TOB: $tobInput, POB: $pobInput. Question: $specificQuestion"
                                val res = onAskAiGuru(prompt)
                                isLoading = false
                                resultOutput = res
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = KrishnaYellow)
                    ) {
                        if (isLoading) {
                            CircularProgressIndicator(color = DarkCharcoal, modifier = Modifier.size(24.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Calculating Planetary Positions...", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = DarkCharcoal)
                        } else {
                            Icon(imageVector = Icons.Default.AutoAwesome, contentDescription = null, tint = DarkCharcoal)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("GENERATE DETAILED REPORT", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = DarkCharcoal)
                        }
                    }
                }
            }

            // Results Section
            AnimatedVisibility(visible = resultOutput.isNotEmpty()) {
                Column {
                    Spacer(modifier = Modifier.height(20.dp))
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = PureWhite),
                        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
                    ) {
                        Column(modifier = Modifier.padding(20.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text("✨", fontSize = 20.sp)
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "Your Divine Prediction Report",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = TextDark
                                )
                            }
                            Spacer(modifier = Modifier.height(12.dp))
                            Divider(color = KrishnaYellow)
                            Spacer(modifier = Modifier.height(12.dp))

                            Text(
                                text = resultOutput,
                                fontSize = 13.sp,
                                color = TextDark,
                                lineHeight = 20.sp
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(30.dp))
        }
    }
