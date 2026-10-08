package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.AttachFile
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.model.Astrologer
import com.example.ui.theme.DarkCharcoal
import com.example.ui.theme.DivineGold
import com.example.ui.theme.GoldAccent
import com.example.ui.theme.KrishnaYellow
import com.example.ui.theme.LightCreamBackground
import com.example.ui.theme.PureWhite
import com.example.ui.theme.SacredMaroon
import com.example.ui.theme.SaffronOrange
import com.example.ui.theme.SuccessGreen
import com.example.ui.theme.TextDark
import com.example.ui.theme.TextMuted
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

data class ChatMessageItem(
    val id: String,
    val senderName: String,
    val message: String,
    val time: String,
    val isUser: Boolean,
    val isSystem: Boolean = false
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LiveChatConsultationScreen(
    astrologer: Astrologer,
    walletBalance: Int,
    onEndChat: () -> Unit,
    onNavigateToWallet: () -> Unit,
    modifier: Modifier = Modifier
) {
    val coroutineScope = rememberCoroutineScope()
    val listState = rememberLazyListState()

    var textInput by remember { mutableStateOf("") }
    var isAstrologerTyping by remember { mutableStateOf(false) }
    var showEndChatDialog by remember { mutableStateOf(false) }
    var remainingSeconds by remember { mutableIntStateOf(900) } // 15 mins default timer

    // Timer countdown effect
    LaunchedEffect(Unit) {
        while (remainingSeconds > 0) {
            delay(1000)
            remainingSeconds -= 1
        }
    }

    val minutes = remainingSeconds / 60
    val seconds = remainingSeconds % 60
    val timerText = String.format("%02d:%02d", minutes, seconds)

    val messages = remember {
        mutableStateListOf(
            ChatMessageItem(
                id = "1",
                senderName = "System",
                message = "🔮 Sacred Chat Consultation Started with ${astrologer.name} (${astrologer.skills.firstOrNull() ?: "Vedic Astrology"}). Rate: ₹${astrologer.pricePerMin}/min.",
                time = "Just now",
                isUser = false,
                isSystem = true
            ),
            ChatMessageItem(
                id = "2",
                senderName = astrologer.name,
                message = "Pranam! 🙏 I am ${astrologer.name}. I have loaded your birth details (Kundli). How may I guide your life journey today?",
                time = "10:00 AM",
                isUser = false
            )
        )
    }

    val presetQuestionChips = listOf(
        "💼 Career & Job Switch timing?",
        "❤️ Marriage & Soulmate compatibility?",
        "🪐 Shani Sade Sati remedies?",
        "💰 Wealth & Financial growth tips",
        "🏠 Buying property & Home muhurat"
    )

    fun handleSendMessage(queryText: String) {
        if (queryText.isBlank()) return
        val userMsg = ChatMessageItem(
            id = System.currentTimeMillis().toString(),
            senderName = "You",
            message = queryText.trim(),
            time = "Just now",
            isUser = true
        )
        messages.add(userMsg)
        textInput = ""

        coroutineScope.launch {
            listState.animateScrollToItem(messages.size - 1)
        }

        // Simulate Astrologer reply
        isAstrologerTyping = true
        coroutineScope.launch {
            delay(1800)
            isAstrologerTyping = false

            val replyText = when {
                queryText.contains("Career", ignoreCase = true) || queryText.contains("Job", ignoreCase = true) ->
                    "Based on your 10th House alignment and Sun's current Mahadasha, favorable career growth opportunities will manifest between October and December. I recommend chanting 'Om Suryaya Namah' daily."
                queryText.contains("Marriage", ignoreCase = true) || queryText.contains("Soulmate", ignoreCase = true) ->
                    "Your Venus (Shukra) position in the 7th House indicates strong marital harmony. The planetary alignment becomes highly auspicious after your upcoming birthday."
                queryText.contains("Shani", ignoreCase = true) || queryText.contains("Remedies", ignoreCase = true) ->
                    "For Shani Sade Sati peace, offer mustard oil to Lord Shani on Saturdays and light a sesame oil diya under a Peepal tree in the evening."
                else ->
                    "Hari Om 🙏 Your planetary transit (Gochar) shows auspicious positive shifts. To strengthen your Lucky Gemstone & Kundli houses, maintain morning prayers and offer जल to Sun God."
            }

            messages.add(
                ChatMessageItem(
                    id = (System.currentTimeMillis() + 1).toString(),
                    senderName = astrologer.name,
                    message = replyText,
                    time = "Just now",
                    isUser = false
                )
            )

            listState.animateScrollToItem(messages.size - 1)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Box(modifier = Modifier.size(42.dp)) {
                            Image(
                                painter = painterResource(id = if (astrologer.photoRes != 0) astrologer.photoRes else R.drawable.astrologer_deepali_portrait_1786370825016),
                                contentDescription = astrologer.name,
                                modifier = Modifier
                                    .size(42.dp)
                                    .clip(CircleShape)
                                    .border(1.5.dp, KrishnaYellow, CircleShape)
                            )
                            Box(
                                modifier = Modifier
                                    .size(12.dp)
                                    .clip(CircleShape)
                                    .background(SuccessGreen)
                                    .border(1.5.dp, PureWhite, CircleShape)
                                    .align(Alignment.BottomEnd)
                            )
                        }

                        Spacer(modifier = Modifier.width(10.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = astrologer.name,
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = DarkCharcoal,
                                    maxLines = 1
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Icon(
                                    imageVector = Icons.Default.CheckCircle,
                                    contentDescription = "Verified",
                                    tint = SuccessGreen,
                                    modifier = Modifier.size(14.dp)
                                )
                            }
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(4.dp))
                                        .background(DarkCharcoal.copy(alpha = 0.1f))
                                        .padding(horizontal = 6.dp, vertical = 2.dp)
                                ) {
                                    Text(
                                        text = "⏱️ $timerText",
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = SacredMaroon
                                    )
                                }
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "₹${astrologer.pricePerMin}/min",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = SuccessGreen
                                )
                            }
                        }

                        // End Chat Button
                        Button(
                            onClick = { showEndChatDialog = true },
                            colors = ButtonDefaults.buttonColors(containerColor = SacredMaroon),
                            shape = RoundedCornerShape(16.dp),
                            contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 10.dp, vertical = 4.dp),
                            modifier = Modifier.height(32.dp)
                        ) {
                            Text("End", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = PureWhite)
                        }
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { showEndChatDialog = true }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back", tint = DarkCharcoal)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = KrishnaYellow)
            )
        },
        bottomBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(PureWhite)
                    .navigationBarsPadding()
                    .border(1.dp, Color.LightGray.copy(alpha = 0.4f))
                    .padding(horizontal = 12.dp, vertical = 8.dp)
            ) {
                // Preset Question Chips
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.padding(bottom = 8.dp)
                ) {
                    items(presetQuestionChips) { chip ->
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(16.dp))
                                .background(LightCreamBackground)
                                .border(1.dp, GoldAccent.copy(alpha = 0.4f), RoundedCornerShape(16.dp))
                                .clickable { handleSendMessage(chip.substringAfter(" ")) }
                                .padding(horizontal = 12.dp, vertical = 6.dp)
                        ) {
                            Text(text = chip, fontSize = 12.sp, fontWeight = FontWeight.Medium, color = DarkCharcoal)
                        }
                    }
                }

                // Input Bar Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = textInput,
                        onValueChange = { textInput = it },
                        placeholder = { Text("Ask your doubt to ${astrologer.name}...", fontSize = 13.sp, color = TextMuted) },
                        singleLine = true,
                        shape = RoundedCornerShape(24.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = KrishnaYellow,
                            unfocusedBorderColor = Color.LightGray
                        ),
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),
                        keyboardActions = KeyboardActions(onSend = { handleSendMessage(textInput) }),
                        modifier = Modifier
                            .weight(1f)
                            .height(50.dp)
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    IconButton(
                        onClick = { handleSendMessage(textInput) },
                        modifier = Modifier
                            .size(46.dp)
                            .clip(CircleShape)
                            .background(
                                if (textInput.isNotBlank()) SaffronOrange else Color.LightGray
                            )
                    ) {
                        Icon(
                            imageVector = Icons.Default.Send,
                            contentDescription = "Send",
                            tint = PureWhite,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
        },
        containerColor = LightCreamBackground,
        modifier = modifier
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // User Birth Kundli Info Banner
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = PureWhite),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Row(
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("📜", fontSize = 20.sp)
                        Spacer(modifier = Modifier.width(8.dp))
                        Column {
                            Text("Attached Kundli: Raj Chauhan", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = DarkCharcoal)
                            Text("01 Jul 1999 • 07:30 AM • Varanasi, UP", fontSize = 10.sp, color = TextMuted)
                        }
                    }
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(KrishnaYellow.copy(alpha = 0.3f))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text("View Kundli", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = SacredMaroon)
                    }
                }
            }

            // Messages LazyColumn
            LazyColumn(
                state = listState,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 12.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(messages) { msg ->
                    if (msg.isSystem) {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(DarkCharcoal.copy(alpha = 0.08f))
                                    .padding(horizontal = 12.dp, vertical = 6.dp)
                            ) {
                                Text(
                                    text = msg.message,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = TextMuted
                                )
                            }
                        }
                    } else if (msg.isUser) {
                        // User message bubble (Right aligned)
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {
                            Card(
                                shape = RoundedCornerShape(16.dp, 16.dp, 2.dp, 16.dp),
                                colors = CardDefaults.cardColors(containerColor = KrishnaYellow),
                                modifier = Modifier.fillMaxWidth(0.8f)
                            ) {
                                Column(modifier = Modifier.padding(12.dp)) {
                                    Text(
                                        text = msg.message,
                                        fontSize = 14.sp,
                                        color = DarkCharcoal,
                                        lineHeight = 18.sp
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = msg.time,
                                        fontSize = 10.sp,
                                        color = TextMuted,
                                        modifier = Modifier.align(Alignment.End)
                                    )
                                }
                            }
                        }
                    } else {
                        // Astrologer message bubble (Left aligned)
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Start
                        ) {
                            Image(
                                painter = painterResource(id = if (astrologer.photoRes != 0) astrologer.photoRes else R.drawable.astrologer_deepali_portrait_1786370825016),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(32.dp)
                                    .clip(CircleShape)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Card(
                                shape = RoundedCornerShape(16.dp, 16.dp, 16.dp, 2.dp),
                                colors = CardDefaults.cardColors(containerColor = PureWhite),
                                modifier = Modifier.fillMaxWidth(0.85f),
                                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                            ) {
                                Column(modifier = Modifier.padding(12.dp)) {
                                    Text(
                                        text = msg.senderName,
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = SacredMaroon
                                    )
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text(
                                        text = msg.message,
                                        fontSize = 14.sp,
                                        color = TextDark,
                                        lineHeight = 20.sp
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = msg.time,
                                        fontSize = 10.sp,
                                        color = TextMuted,
                                        modifier = Modifier.align(Alignment.End)
                                    )
                                }
                            }
                        }
                    }
                }

                if (isAstrologerTyping) {
                    item {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(start = 40.dp, top = 4.dp)
                        ) {
                            Text(
                                text = "${astrologer.name} is examining your chart...",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = SaffronOrange
                            )
                        }
                    }
                }
            }
        }

        // CONFIRM END CHAT DIALOG
        if (showEndChatDialog) {
            AlertDialog(
                onDismissRequest = { showEndChatDialog = false },
                title = { Text("End Consultation Session?", fontWeight = FontWeight.Bold) },
                text = { Text("Are you sure you want to end your consultation with ${astrologer.name}? Total session charge will be deducted from your wallet.") },
                confirmButton = {
                    Button(
                        onClick = {
                            showEndChatDialog = false
                            onEndChat()
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = SacredMaroon)
                    ) {
                        Text("End Session", color = PureWhite)
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showEndChatDialog = false }) {
                        Text("Continue Chat", color = DarkCharcoal)
                    }
                }
            )
        }
    }
}
