package com.example.ui.screens

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.CardGiftcard
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.ui.components.OnlineGlowDot
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

data class LiveAstrologerData(
    val id: String,
    val name: String,
    val topic: String,
    val skills: String,
    val languages: String,
    val rating: Float,
    val viewersCount: Int,
    val photoRes: Int,
    val isVerified: Boolean = true,
    val pricePerMin: Double = 23.0,
    val regularPrice: Double = 35.0
)

data class SpiritualGiftItem(
    val name: String,
    val priceText: String,
    val price: Double,
    val emoji: String
)

data class LiveComment(
    val sender: String,
    val message: String,
    val isGift: Boolean = false
)

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun LiveAstrologersScreen(
    initialAstrologerId: String? = null,
    onBack: () -> Unit,
    onStartConsultation: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    val sampleLiveAstrologers = remember {
        listOf(
            LiveAstrologerData(
                id = "live_1",
                name = "Tarot Deepali",
                topic = "LIVE: Shani Sade Sati Remedies & Career Guidance 🪐",
                skills = "Tarot Reading, Vedic Astrology",
                languages = "Hindi, English",
                rating = 4.9f,
                viewersCount = 813,
                photoRes = R.drawable.astrologer_deepali_portrait_1786370825016,
                pricePerMin = 11.0,
                regularPrice = 23.0
            ),
            LiveAstrologerData(
                id = "live_2",
                name = "Tarot Shubh",
                topic = "LIVE: Relationship & Marriage Compatibility Breakup Solutions 💍",
                skills = "Marriage Compatibility, KP System",
                languages = "Hindi, English",
                rating = 4.8f,
                viewersCount = 818,
                photoRes = R.drawable.img_pandit_banner_1785577758893,
                pricePerMin = 11.0,
                regularPrice = 23.0
            ),
            LiveAstrologerData(
                id = "live_3",
                name = "Acharya Kamalendra",
                topic = "LIVE: Rahu Ketu Transit Impact & Wealth Yogas 💰",
                skills = "Vedic Astrology, Lal Kitab",
                languages = "Hindi, Sanskrit",
                rating = 4.95f,
                viewersCount = 1240,
                photoRes = R.drawable.astrologer_kamalendra_portrait_1786370841660,
                pricePerMin = 15.0,
                regularPrice = 30.0
            ),
            LiveAstrologerData(
                id = "live_4",
                name = "Smt. Sunita Shastri",
                topic = "LIVE: Vastu Shastra for Home & Business Prosperity 🏠",
                skills = "Vastu Shastra, Gemstone Remedies",
                languages = "English, Hindi, Gujarati",
                rating = 4.88f,
                viewersCount = 940,
                photoRes = R.drawable.astrologer_deepali_portrait_1786370825016,
                pricePerMin = 12.0,
                regularPrice = 25.0
            ),
            LiveAstrologerData(
                id = "live_5",
                name = "Dr. Rajeshwar Prasad",
                topic = "LIVE: Instant Prashna Kundli & Business Growth 🌟",
                skills = "Jaimini Sutras, Astro Numerology",
                languages = "Hindi, Marathi",
                rating = 4.92f,
                viewersCount = 2100,
                photoRes = R.drawable.img_pandit_banner_1785577758893,
                pricePerMin = 18.0,
                regularPrice = 35.0
            )
        )
    }

    val initialIndex = remember(initialAstrologerId) {
        val idx = sampleLiveAstrologers.indexOfFirst { it.id == initialAstrologerId }
        if (idx >= 0) idx else 0
    }

    val pagerState = rememberPagerState(initialPage = initialIndex, pageCount = { sampleLiveAstrologers.size })
    var showGiftSheet by remember { mutableStateOf(false) }
    var activeGiftAstrologer by remember { mutableStateOf<LiveAstrologerData?>(null) }
    var walletBalance by remember { mutableStateOf(250.0) }

    val giftList = remember {
        listOf(
            SpiritualGiftItem("Namaste", "Free", 0.0, "🙏"),
            SpiritualGiftItem("Love Heart", "₹31", 31.0, "❤️"),
            SpiritualGiftItem("Red Rose", "₹41", 41.0, "🌹"),
            SpiritualGiftItem("Love Teddy", "₹61", 61.0, "🧸"),
            SpiritualGiftItem("Cookies", "₹71", 71.0, "🍪"),
            SpiritualGiftItem("Chocolate", "₹81", 81.0, "🍫"),
            SpiritualGiftItem("Cupcake", "₹101", 101.0, "🧁"),
            SpiritualGiftItem("2026 Gift", "₹121", 121.0, "🎁"),
            SpiritualGiftItem("Love Mug", "₹131", 131.0, "☕"),
            SpiritualGiftItem("Candle", "₹141", 141.0, "🕯️"),
            SpiritualGiftItem("Bouquet", "₹151", 151.0, "💐"),
            SpiritualGiftItem("Rudraksh", "₹171", 171.0, "📿")
        )
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        // Horizontal Pager for Seamless Swiping Between Live Astrologer Streams
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { pageIndex ->
            val astrologer = sampleLiveAstrologers[pageIndex]

            LiveStreamPageItem(
                astrologer = astrologer,
                onBack = onBack,
                onOpenGiftSheet = {
                    activeGiftAstrologer = astrologer
                    showGiftSheet = true
                },
                onStartConsultation = { onStartConsultation(astrologer.id) },
                pageIndex = pageIndex,
                totalPages = sampleLiveAstrologers.size,
                onNextStream = {
                    if (pageIndex < sampleLiveAstrologers.size - 1) {
                        coroutineScope.launch { pagerState.animateScrollToPage(pageIndex + 1) }
                    }
                },
                onPrevStream = {
                    if (pageIndex > 0) {
                        coroutineScope.launch { pagerState.animateScrollToPage(pageIndex - 1) }
                    }
                }
            )
        }

        // Bottom Sheet for Send Gift
        if (showGiftSheet) {
            ModalBottomSheet(
                onDismissRequest = { showGiftSheet = false },
                containerColor = PureWhite,
                shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                ) {
                    // Header with Wallet Balance
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Send a Gift",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextDark
                        )

                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(16.dp))
                                .background(KrishnaYellow.copy(alpha = 0.2f))
                                .border(1.dp, KrishnaYellow, RoundedCornerShape(16.dp))
                                .padding(horizontal = 12.dp, vertical = 6.dp)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text("👛", fontSize = 14.sp)
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "₹${walletBalance.toInt()}",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = DarkCharcoal
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // 4 Grid Columns for Gifts
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(4),
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        verticalArrangement = Arrangement.spacedBy(14.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        items(giftList) { gift ->
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier
                                    .clip(RoundedCornerShape(12.dp))
                                    .clickable {
                                        if (gift.price <= walletBalance) {
                                            walletBalance -= gift.price
                                            Toast
                                                .makeText(
                                                    context,
                                                    "Sent ${gift.name} ${gift.emoji} offering to ${activeGiftAstrologer?.name}!",
                                                    Toast.LENGTH_SHORT
                                                )
                                                .show()
                                            showGiftSheet = false
                                        } else {
                                            Toast
                                                .makeText(
                                                    context,
                                                    "Insufficient balance. Please recharge wallet!",
                                                    Toast.LENGTH_SHORT
                                                )
                                                .show()
                                        }
                                    }
                                    .padding(vertical = 8.dp, horizontal = 4.dp)
                            ) {
                                Box(
                                    contentAlignment = Alignment.Center,
                                    modifier = Modifier
                                        .size(54.dp)
                                        .clip(CircleShape)
                                        .background(LightCreamBackground)
                                ) {
                                    Text(gift.emoji, fontSize = 28.sp)
                                }

                                Spacer(modifier = Modifier.height(6.dp))

                                Text(
                                    text = gift.name,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = TextDark,
                                    textAlign = TextAlign.Center,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )

                                Text(
                                    text = gift.priceText,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = SacredMaroon
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))
                }
            }
        }
    }
}

@Composable
fun LiveStreamPageItem(
    astrologer: LiveAstrologerData,
    onBack: () -> Unit,
    onOpenGiftSheet: () -> Unit,
    onStartConsultation: () -> Unit,
    pageIndex: Int,
    totalPages: Int,
    onNextStream: () -> Unit,
    onPrevStream: () -> Unit
) {
    var isFollowing by remember { mutableStateOf(false) }
    var commentInput by remember { mutableStateOf("") }
    val context = LocalContext.current

    val liveComments = remember {
        mutableStateListOf(
            LiveComment("Harsha", "subh sir meri shadi kse ldke se hogi or kb tk fix hojayegi my dob 1/7/1999"),
            LiveComment("Ramesh", "sir yeh mekro kabhi block karte hai kabhi unblock baat ni karte breakup ho rakha kya chal raha hai sir ??"),
            LiveComment("Ankit S.", "Pranam Guru ji! Sent 108 Sacred Diyas 🪔", isGift = true),
            LiveComment("Pooja V.", "Namaste Shastri ji, very accurate prediction! 🙏")
        )
    }

    // Dynamic simulated user comments stream
    LaunchedEffect(astrologer.id) {
        val names = listOf("Karan", "Sneha", "Aditya", "Priya", "Vikram", "Gaurav", "Deepika")
        val comments = listOf(
            "Sita Ram 🙏",
            "When will my career transition happen?",
            "Pranam Guru ji! Please guide on Rahu Mahadasha.",
            "Sent Flower Garland 🌸",
            "Thank you so much for the remedies!"
        )
        while (true) {
            delay(3500)
            if (liveComments.size > 15) liveComments.removeAt(0)
            liveComments.add(LiveComment(names.random(), comments.random()))
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        // Video Stream Background Image
        Image(
            painter = painterResource(id = astrologer.photoRes),
            contentDescription = astrologer.name,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // Vignette & Dark Overlay Gradients
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Black.copy(alpha = 0.75f),
                            Color.Transparent,
                            Color.Black.copy(alpha = 0.9f)
                        )
                    )
                )
        )

        // ----------------------------------------------------
        // TOP OVERLAY BAR: Astrologer Info, Viewer Count, Follow, Actions
        // ----------------------------------------------------
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 105.dp, start = 12.dp, end = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                // Astrologer Avatar with Online Ring
                Box(contentAlignment = Alignment.Center) {
                    Box(
                        modifier = Modifier
                            .size(42.dp)
                            .clip(CircleShape)
                            .border(2.dp, KrishnaYellow, CircleShape)
                    ) {
                        Image(
                            painter = painterResource(id = astrologer.photoRes),
                            contentDescription = astrologer.name,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    }

                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .clip(RoundedCornerShape(6.dp))
                            .background(Color.Red)
                            .padding(horizontal = 4.dp, vertical = 1.dp)
                    ) {
                        Text("LIVE", fontSize = 8.sp, fontWeight = FontWeight.ExtraBold, color = PureWhite)
                    }
                }

                Spacer(modifier = Modifier.width(8.dp))

                Column {
                    Text(
                        text = astrologer.name,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = PureWhite,
                        maxLines = 1
                    )
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("👁️ ${astrologer.viewersCount}", fontSize = 10.sp, color = KrishnaYellow, fontWeight = FontWeight.Bold)
                    }
                }

                Spacer(modifier = Modifier.width(8.dp))

                // Follow Button
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .background(if (isFollowing) PureWhite.copy(alpha = 0.3f) else PureWhite)
                        .clickable { isFollowing = !isFollowing }
                        .padding(horizontal = 10.dp, vertical = 5.dp)
                ) {
                    Text(
                        text = if (isFollowing) "Following ✓" else "+ Follow",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = DarkCharcoal
                    )
                }
            }

            // Top Action Buttons (Functional Share)
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(
                    onClick = {
                        val shareIntent = android.content.Intent(android.content.Intent.ACTION_SEND).apply {
                            type = "text/plain"
                            putExtra(
                                android.content.Intent.EXTRA_TEXT,
                                "Watch ${astrologer.name} live on DivyaDham Astrology! Stream topic: ${astrologer.topic}"
                            )
                        }
                        context.startActivity(android.content.Intent.createChooser(shareIntent, "Share Live Stream"))
                    },
                    modifier = Modifier
                        .size(36.dp)
                        .background(Color.Black.copy(alpha = 0.5f), CircleShape)
                ) {
                    Icon(imageVector = Icons.Default.Share, contentDescription = "Share", tint = PureWhite, modifier = Modifier.size(18.dp))
                }
            }
        }

        // Floating Side Controls Overlay (Video call option / Queue button)
        Column(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(top = 175.dp, start = 14.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFF8D6E63))
                    .border(1.dp, PureWhite.copy(alpha = 0.6f), RoundedCornerShape(12.dp))
                    .clickable {
                        Toast.makeText(context, "Joined Waiting Queue for Consultation!", Toast.LENGTH_SHORT).show()
                    }
            ) {
                Text("🧘‍♂️", fontSize = 20.sp)
            }

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(SuccessGreen)
                    .border(1.5.dp, PureWhite, RoundedCornerShape(12.dp))
                    .clickable { onStartConsultation() }
            ) {
                Icon(imageVector = Icons.Default.Call, contentDescription = "Call", tint = PureWhite, modifier = Modifier.size(20.dp))
            }
        }

        // Swipe Navigation Indicators (Left/Right stream arrows)
        if (pageIndex > 0) {
            IconButton(
                onClick = onPrevStream,
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(start = 4.dp)
                    .background(Color.Black.copy(alpha = 0.3f), CircleShape)
            ) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Previous Stream", tint = PureWhite.copy(alpha = 0.8f))
            }
        }

        if (pageIndex < totalPages - 1) {
            IconButton(
                onClick = onNextStream,
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 4.dp)
                    .background(Color.Black.copy(alpha = 0.3f), CircleShape)
            ) {
                Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = "Next Stream", tint = PureWhite.copy(alpha = 0.8f))
            }
        }

        // ----------------------------------------------------
        // FLOATING LIVE CHAT & TOPIC BANNER
        // ----------------------------------------------------
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .fillMaxWidth()
                .padding(bottom = 80.dp, start = 12.dp, end = 12.dp)
        ) {
            // Live Stream Topic Banner
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.Black.copy(alpha = 0.65f))
                    .border(1.dp, GoldAccent.copy(alpha = 0.4f), RoundedCornerShape(12.dp))
                    .padding(horizontal = 12.dp, vertical = 6.dp)
            ) {
                Text(
                    text = astrologer.topic,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = DivineGold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Floating Comments Box (Transparent Overlay)
            Column(
                modifier = Modifier
                    .height(180.dp)
                    .fillMaxWidth(0.85f),
                verticalArrangement = Arrangement.Bottom
            ) {
                liveComments.takeLast(5).forEach { comment ->
                    Box(
                        modifier = Modifier
                            .padding(vertical = 3.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(
                                if (comment.isGift) SacredMaroon.copy(alpha = 0.75f)
                                else Color.Black.copy(alpha = 0.55f)
                            )
                            .padding(horizontal = 10.dp, vertical = 5.dp)
                    ) {
                        Row(verticalAlignment = Alignment.Top) {
                            Text(
                                text = "${comment.sender}: ",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = KrishnaYellow
                            )
                            Text(
                                text = comment.message,
                                fontSize = 12.sp,
                                color = PureWhite,
                                lineHeight = 16.sp
                            )
                        }
                    }
                }
            }
        }

        // ----------------------------------------------------
        // FLOATING SEND GIFT BUTTON (Shifted to right side above bottom footer)
        // ----------------------------------------------------
        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 80.dp, end = 16.dp)
                .size(46.dp)
                .clip(CircleShape)
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(SacredMaroon, SaffronOrange)
                    )
                )
                .border(1.5.dp, DivineGold, CircleShape)
                .clickable { onOpenGiftSheet() },
            contentAlignment = Alignment.Center
        ) {
            Text("🎁", fontSize = 20.sp)
        }

        // ----------------------------------------------------
        // BOTTOM ACTION BAR: Say Hi + Call CTA
        // ----------------------------------------------------
        Surface(
            color = Color.Black.copy(alpha = 0.88f),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .navigationBarsPadding()
                    .padding(horizontal = 12.dp, vertical = 12.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Say Hi Comment Input Bar (Expanded)
                OutlinedTextField(
                    value = commentInput,
                    onValueChange = { commentInput = it },
                    placeholder = { Text("Say hi...", fontSize = 12.sp, color = Color.LightGray) },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),
                    keyboardActions = KeyboardActions(
                        onSend = {
                            if (commentInput.isNotBlank()) {
                                liveComments.add(LiveComment("You", commentInput))
                                commentInput = ""
                            }
                        }
                    ),
                    trailingIcon = {
                        if (commentInput.isNotBlank()) {
                            IconButton(onClick = {
                                liveComments.add(LiveComment("You", commentInput))
                                commentInput = ""
                            }) {
                                Icon(Icons.Default.Send, contentDescription = "Send", tint = KrishnaYellow, modifier = Modifier.size(16.dp))
                            }
                        }
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = KrishnaYellow,
                        unfocusedBorderColor = Color.DarkGray,
                        focusedTextColor = PureWhite,
                        unfocusedTextColor = PureWhite
                    ),
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(22.dp)
                )

                // Call / Consult CTA Button
                Button(
                    onClick = onStartConsultation,
                    colors = ButtonDefaults.buttonColors(containerColor = KrishnaYellow),
                    shape = RoundedCornerShape(22.dp),
                    contentPadding = PaddingValues(horizontal = 14.dp, vertical = 8.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(imageVector = Icons.Default.Call, contentDescription = null, tint = DarkCharcoal, modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Column(horizontalAlignment = Alignment.Start) {
                            Text(
                                text = "₹${astrologer.pricePerMin.toInt()}/min",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = DarkCharcoal
                            )
                            Text(
                                text = "₹${astrologer.regularPrice.toInt()}/min",
                                fontSize = 9.sp,
                                color = DarkCharcoal.copy(alpha = 0.7f),
                                style = androidx.compose.ui.text.TextStyle(textDecoration = androidx.compose.ui.text.style.TextDecoration.LineThrough)
                            )
                        }
                    }
                }
            }
        }
    }
}
