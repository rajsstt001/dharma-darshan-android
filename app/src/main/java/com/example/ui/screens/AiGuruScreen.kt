package com.example.ui.screens

import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.AiChatMessage
import com.example.ui.theme.DivineGold
import com.example.ui.theme.LightCreamBackground
import com.example.ui.theme.PureWhite
import com.example.ui.theme.SacredMaroon
import com.example.ui.theme.SaffronOrange
import com.example.ui.theme.TextDark
import com.example.ui.theme.TextMuted
import kotlinx.coroutines.launch

@Composable
fun AiGuruScreen(
    onAskAiGuru: suspend (String) -> String,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val coroutineScope = rememberCoroutineScope()
    var inputQuery by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    val chatMessages = remember {
        mutableStateListOf(
            AiChatMessage(
                id = "1",
                sender = "GURU",
                text = "Hari Om Devotee 🙏 I am your Divine AI Guru. How may I guide your spiritual path, pooja selection, or horoscope today?"
            )
        )
    }

    val promptSuggestions = listOf(
        "Which pooja is recommended for career obstacles?",
        "What is the significance of today's panchang?",
        "How do I prepare for a Home Satyanarayan Pooja?",
        "Suggest a daily mantra for mental peace."
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(LightCreamBackground)
    ) {
        // Suggestions Chips
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(promptSuggestions) { prompt ->
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .background(PureWhite)
                        .clickable {
                            inputQuery = prompt
                        }
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Text(text = prompt, fontSize = 11.sp, fontWeight = FontWeight.Medium, color = SacredMaroon)
                }
            }
        }

        // Chat Conversation
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = 12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(chatMessages) { msg ->
                val isUser = msg.sender == "USER"
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = if (isUser) Alignment.CenterEnd else Alignment.CenterStart
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(0.85f)
                            .clip(
                                RoundedCornerShape(
                                    topStart = 16.dp,
                                    topEnd = 16.dp,
                                    bottomStart = if (isUser) 16.dp else 2.dp,
                                    bottomEnd = if (isUser) 2.dp else 16.dp
                                )
                            )
                            .background(if (isUser) SaffronOrange else PureWhite)
                            .padding(12.dp)
                    ) {
                        Text(
                            text = msg.text,
                            fontSize = 13.sp,
                            color = if (isUser) PureWhite else TextDark,
                            fontWeight = if (isUser) FontWeight.SemiBold else FontWeight.Normal
                        )
                    }
                }
            }
        }

        if (isLoading) {
            Row(
                modifier = Modifier.padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                CircularProgressIndicator(modifier = Modifier.size(16.dp), color = SaffronOrange)
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "AI Guru is consulting Vedic scriptures...", fontSize = 11.sp, color = TextMuted)
            }
        }

        // Input Field
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = inputQuery,
                onValueChange = { inputQuery = it },
                placeholder = { Text("Ask Divine Guru anything...", fontSize = 13.sp) },
                modifier = Modifier.weight(1f),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = SaffronOrange,
                    unfocusedContainerColor = PureWhite,
                    focusedContainerColor = PureWhite
                ),
                shape = RoundedCornerShape(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            IconButton(
                onClick = {
                    if (inputQuery.isNotBlank() && !isLoading) {
                        val userText = inputQuery
                        chatMessages.add(AiChatMessage(id = System.currentTimeMillis().toString(), sender = "USER", text = userText))
                        inputQuery = ""
                        isLoading = true

                        coroutineScope.launch {
                            val reply = onAskAiGuru(userText)
                            isLoading = false
                            chatMessages.add(AiChatMessage(id = System.currentTimeMillis().toString(), sender = "GURU", text = reply))
                        }
                    }
                },
                modifier = Modifier
                    .clip(CircleShape)
                    .background(SacredMaroon)
            ) {
                Icon(imageVector = Icons.Default.Send, contentDescription = "Send", tint = DivineGold)
            }
        }
    }
}
