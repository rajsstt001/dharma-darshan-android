package com.example.ui.screens

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Fullscreen
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.VolunteerActivism
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.LiveDarshanStream
import com.example.ui.theme.DivineGold
import com.example.ui.theme.LightCreamBackground
import com.example.ui.theme.PureWhite
import com.example.ui.theme.SacredMaroon
import com.example.ui.theme.SaffronOrange
import com.example.ui.theme.TextDark
import com.example.ui.theme.TextMuted

@Composable
fun LiveDarshanScreen(
    stream: LiveDarshanStream,
    onBack: () -> Unit,
    onDonateClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var isPlaying by remember { mutableStateOf(true) }
    var chatInput by remember { mutableStateOf("") }
    val comments = remember {
        mutableStateListOf(
            "Ramesh Sharma: Har Har Mahadev! 🙏",
            "Priya Verma: Jai Kashi Vishwanath ji! ✨",
            "Anil Kulkarni: Om Namah Shivaya 🚩",
            "Sita Gupta: Seeking blessings for my family 🌸"
        )
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(LightCreamBackground)
    ) {
        // Video Player Box (Netflix style)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(230.dp)
                .background(Color.Black)
        ) {
            Image(
                painter = painterResource(id = stream.thumbnailRes),
                contentDescription = stream.templeName,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            // Top Bar Overlay
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .align(Alignment.TopCenter),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.Red)
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text(text = "🔴 LIVE DARSHAN", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = PureWhite)
                }

                IconButton(onClick = {}) {
                    Icon(imageVector = Icons.Default.Share, contentDescription = "Share", tint = PureWhite)
                }
            }

            // Bottom Player Info Overlay
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Black.copy(alpha = 0.6f))
                    .padding(8.dp)
                    .align(Alignment.BottomCenter)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(text = stream.templeName, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = PureWhite)
                        Text(text = "${stream.currentAarti} • 👥 ${stream.viewersCount} watching live", fontSize = 11.sp, color = DivineGold)
                    }

                    Row {
                        IconButton(onClick = { isPlaying = !isPlaying }) {
                            Icon(imageVector = Icons.Default.PlayArrow, contentDescription = "Play/Pause", tint = PureWhite)
                        }
                        IconButton(onClick = {}) {
                            Icon(imageVector = Icons.Default.Fullscreen, contentDescription = "Fullscreen", tint = PureWhite)
                        }
                    }
                }
            }
        }

        // Live Actions & Chat Section
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = onDonateClick,
                    colors = ButtonDefaults.buttonColors(containerColor = SacredMaroon, contentColor = PureWhite),
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(imageVector = Icons.Default.VolunteerActivism, contentDescription = null, modifier = Modifier.size(16.dp), tint = DivineGold)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "Offer Donation", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = PureWhite)
                }

                Button(
                    onClick = { comments.add(0, "You: Offered Flower 🌸") },
                    colors = ButtonDefaults.buttonColors(containerColor = com.example.ui.theme.KrishnaYellow, contentColor = com.example.ui.theme.DarkCharcoal),
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(imageVector = Icons.Default.Favorite, contentDescription = null, modifier = Modifier.size(16.dp), tint = com.example.ui.theme.DarkCharcoal)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "Offer Flower", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = com.example.ui.theme.DarkCharcoal)
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Aarti Schedule
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = PureWhite)
            ) {
                Column(modifier = Modifier.padding(10.dp)) {
                    Text(text = "⏰ Daily Aarti Schedule", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = SacredMaroon)
                    Text(text = "• Mangala Aarti: 03:00 AM - 04:00 AM\n• Bhog Aarti: 11:30 AM - 12:00 PM\n• Sandhya Aarti: 07:00 PM - 08:15 PM", fontSize = 11.sp, color = TextDark)
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(text = "💬 Devotee Live Chat & Chants", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = SacredMaroon)
            Spacer(modifier = Modifier.height(6.dp))

            // Chat list
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(PureWhite)
                    .padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                items(comments) { msg ->
                    Text(text = msg, fontSize = 12.sp, color = TextDark)
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Chat input
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = chatInput,
                    onValueChange = { chatInput = it },
                    placeholder = { Text("Chant or send prayer...", fontSize = 12.sp) },
                    modifier = Modifier.weight(1f),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = SaffronOrange),
                    shape = RoundedCornerShape(20.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                IconButton(
                    onClick = {
                        if (chatInput.isNotBlank()) {
                            comments.add(0, "You: $chatInput")
                            chatInput = ""
                        }
                    },
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(SaffronOrange)
                ) {
                    Icon(imageVector = Icons.Default.Send, contentDescription = "Send", tint = PureWhite, modifier = Modifier.size(18.dp))
                }
            }
        }
    }
}
