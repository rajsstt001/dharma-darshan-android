package com.example.ui.screens

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.SelfImprovement
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.BhajanTrack
import com.example.ui.theme.CardLightBorder
import com.example.ui.theme.DivineGold
import com.example.ui.theme.LightCreamBackground
import com.example.ui.theme.PureWhite
import com.example.ui.theme.SacredMaroon
import com.example.ui.theme.SaffronOrange
import com.example.ui.theme.TextDark
import com.example.ui.theme.TextMuted

@Composable
fun AstrologyScreen(
    onAskAiGuru: (String) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    var name by remember { mutableStateOf("Rajesh Sharma") }
    var dob by remember { mutableStateOf("15 Aug 1995") }
    var tob by remember { mutableStateOf("07:45 AM") }
    var pob by remember { mutableStateOf("Varanasi, UP") }
    var aiAnalysis by remember { mutableStateOf("") }

    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(LightCreamBackground)
            .padding(16.dp)
            .verticalScroll(scrollState)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onBack) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back", tint = SacredMaroon)
            }
            Text(text = "🔮 Vedic Astrology & Kundli", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = SacredMaroon)
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Birth Details Form
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = PureWhite)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "📜 Enter Kundli Birth Details", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = SacredMaroon)
                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = name, onValueChange = { name = it }, label = { Text("Full Name") }, singleLine = true,
                    modifier = Modifier.fillMaxWidth(), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = SaffronOrange)
                )
                Spacer(modifier = Modifier.height(6.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = dob, onValueChange = { dob = it }, label = { Text("Date of Birth") }, singleLine = true,
                        modifier = Modifier.weight(1f), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = SaffronOrange)
                    )
                    OutlinedTextField(
                        value = tob, onValueChange = { tob = it }, label = { Text("Time of Birth") }, singleLine = true,
                        modifier = Modifier.weight(1f), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = SaffronOrange)
                    )
                }
                Spacer(modifier = Modifier.height(6.dp))
                OutlinedTextField(
                    value = pob, onValueChange = { pob = it }, label = { Text("Place of Birth") }, singleLine = true,
                    modifier = Modifier.fillMaxWidth(), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = SaffronOrange)
                )

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = {
                        aiAnalysis = "✨ Kundli Summary for $name:\n• Ascendant: Leo (Simha Lagna)\n• Sun Sign: Leo, Moon Sign: Aries (Mesha Rashi)\n• Key Strengths: Leadership, Courage, Spiritual Intuition\n• Favorable Remedy: Chanting Gayatri Mantra and offering Jal to Surya Dev in the morning."
                    },
                    modifier = Modifier.fillMaxWidth().height(48.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = SaffronOrange),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(imageVector = Icons.Default.AutoAwesome, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(text = "Generate AI Kundli Chart", fontSize = 14.sp, fontWeight = FontWeight.Bold)
                }
            }
        }

        if (aiAnalysis.isNotEmpty()) {
            Spacer(modifier = Modifier.height(12.dp))
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = SacredMaroon)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = "🔮 AI Kundli Insight", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = DivineGold)
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(text = aiAnalysis, fontSize = 12.sp, color = PureWhite)
                }
            }
        }
    }
}

@Composable
fun MeditationAndBhajanScreen(
    bhajans: List<BhajanTrack>,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    var isTimerRunning by remember { mutableStateOf(false) }
    var selectedBhajan by remember { mutableStateOf<BhajanTrack?>(bhajans.firstOrNull()) }
    var isAudioPlaying by remember { mutableStateOf(false) }

    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val scalePulse by infiniteTransition.animateFloat(
        initialValue = 0.95f,
        targetValue = 1.12f,
        animationSpec = infiniteRepeatable(
            animation = tween(4000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulseScale"
    )

    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(LightCreamBackground)
            .padding(16.dp)
            .verticalScroll(scrollState)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onBack) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back", tint = SacredMaroon)
            }
            Text(text = "🧘 Meditation & Bhajans", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = SacredMaroon)
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Animated Breathing Ring
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = PureWhite)
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "🪷 Diaphragmatic Breath Timer", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = SacredMaroon)
                Spacer(modifier = Modifier.height(16.dp))

                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(130.dp)
                        .scale(if (isTimerRunning) scalePulse else 1f)
                        .clip(CircleShape)
                        .background(SaffronOrange.copy(alpha = 0.2f))
                        .border(3.dp, SaffronOrange, CircleShape)
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = if (isTimerRunning) "Inhale... Exhale..." else "Tap to Start",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = SacredMaroon
                        )
                        Text(text = "10:00 Mins", fontSize = 10.sp, color = TextMuted)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { isTimerRunning = !isTimerRunning },
                    colors = ButtonDefaults.buttonColors(containerColor = if (isTimerRunning) SacredMaroon else SaffronOrange),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(text = if (isTimerRunning) "Pause Meditation" else "Begin Meditation Session", fontSize = 13.sp)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "🎵 Devotional Bhajans & Mantras", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = SacredMaroon)
        Spacer(modifier = Modifier.height(8.dp))

        bhajans.forEach { track ->
            val isSel = selectedBhajan?.id == track.id
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
                    .clickable {
                        selectedBhajan = track
                        isAudioPlaying = true
                    },
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = if (isSel) SaffronOrange.copy(alpha = 0.1f) else PureWhite)
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = {
                            selectedBhajan = track
                            isAudioPlaying = if (isSel) !isAudioPlaying else true
                        },
                        modifier = Modifier.clip(CircleShape).background(SaffronOrange)
                    ) {
                        Icon(
                            imageVector = if (isSel && isAudioPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                            contentDescription = "Play",
                            tint = PureWhite
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(text = track.title, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = SacredMaroon)
                        Text(text = "${track.artist} • ${track.durationText}", fontSize = 11.sp, color = TextMuted)
                    }
                }
            }
        }

        if (selectedBhajan != null) {
            Spacer(modifier = Modifier.height(12.dp))
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = PureWhite)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(text = "📜 Lyrics: ${selectedBhajan!!.title}", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = SacredMaroon)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = selectedBhajan!!.lyrics, fontSize = 12.sp, color = TextDark)
                }
            }
        }
    }
}
