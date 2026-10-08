package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.LiveTv
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Place
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.model.Astrologer
import com.example.data.model.BlogPost
import com.example.data.model.ChadhavaItem
import com.example.data.model.LiveDarshanStream
import com.example.data.model.PanchangInfo
import com.example.data.model.PoojaService
import com.example.data.model.Temple
import com.example.data.model.UpcomingPooja
import com.example.data.model.VedicToolItem
import com.example.ui.components.DharmaGradientButton
import com.example.ui.components.OnlineGlowDot
import com.example.ui.components.PanchangCard
import com.example.ui.components.SectionHeader
import com.example.ui.theme.DarkCharcoal
import com.example.ui.theme.DivineGold
import com.example.ui.theme.GoldAccent
import com.example.ui.theme.KrishnaYellow
import com.example.ui.theme.LightCreamBackground
import com.example.ui.theme.PureWhite
import com.example.ui.theme.SuccessGreen
import com.example.ui.theme.TextDark
import com.example.ui.theme.TextMuted

@Composable
fun HomeScreen(
    panchang: PanchangInfo,
    upcomingPoojas: List<UpcomingPooja>,
    recommendedPoojas: List<PoojaService>,
    liveStreams: List<LiveDarshanStream>,
    chadhavaItems: List<ChadhavaItem>,
    temples: List<Temple>,
    astrologers: List<Astrologer> = emptyList(),
    vedicTools: List<VedicToolItem> = emptyList(),
    aiServices: List<VedicToolItem> = emptyList(),
    blogPosts: List<BlogPost> = emptyList(),
    onSelectUpcomingPooja: (UpcomingPooja) -> Unit,
    onSelectRecommendedPooja: (PoojaService) -> Unit,
    onViewAllPoojas: (PoojaListFilter) -> Unit = {},
    onJoinLiveStream: (LiveDarshanStream) -> Unit,
    onAddChadhava: (ChadhavaItem) -> Unit,
    onNavigateToAiGuru: () -> Unit,
    onNavigateToTemples: () -> Unit,
    onNavigateToPandits: () -> Unit,
    onNavigateToAstrologers: () -> Unit = {},
    onNavigateToLiveAstrologers: (String?) -> Unit = {},
    onSelectAstrologer: (Astrologer) -> Unit = {},
    onStartChatConsultation: (Astrologer) -> Unit = {},
    onNavigateToVedicTools: (VedicToolItem) -> Unit = {},
    onNavigateToBlogs: () -> Unit = {},
    onNavigateToStore: () -> Unit = {},
    onSelectBlog: (BlogPost) -> Unit = {},
    onNavigateToPanchang: () -> Unit = {},
    onOpenDrawer: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    var searchQuery by remember { mutableStateOf("") }
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(LightCreamBackground)
            .verticalScroll(scrollState)
    ) {
        // ----------------------------------------------------
        // 1. GLOBAL SEARCH SECTION (ON TOP)
        // ----------------------------------------------------
        GlobalSearchSection(
            query = searchQuery,
            onQueryChange = { searchQuery = it }
        )

        Spacer(modifier = Modifier.height(4.dp))

        // ----------------------------------------------------
        // 2. TOP BANNER CAROUSEL (BELOW SEARCH)
        // ----------------------------------------------------
        TopBannerCarousel(
            onBannerClick = { index ->
                when (index) {
                    0 -> onViewAllPoojas(PoojaListFilter.UPCOMING)
                    1 -> onNavigateToAstrologers()
                    2 -> if (liveStreams.isNotEmpty()) onJoinLiveStream(liveStreams.first())
                    else -> onNavigateToTemples()
                }
            }
        )

        Spacer(modifier = Modifier.height(10.dp))

        // ----------------------------------------------------
        // 3. TOP VERIFIED ASTROLOGERS CAROUSEL (Section 2)
        // ----------------------------------------------------
        if (astrologers.isNotEmpty()) {
            SectionHeader(
                title = "🔮 Top Verified Astrologers",
                subtitle = "Instant Chat & Call guidance with Vedic Experts",
                actionText = "View All",
                onActionClick = onNavigateToAstrologers
            )
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(astrologers) { ast ->
                    HomeAstrologerCard(
                        astrologer = ast,
                        onCardClick = { onSelectAstrologer(ast) },
                        onChatClick = { onStartChatConsultation(ast) },
                        onCallClick = { onSelectAstrologer(ast) }
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

        // ----------------------------------------------------
        // 3B. LIVE ASTROLOGERS SECTION (BELOW TOP VERIFIED ASTROLOGERS)
        // ----------------------------------------------------
        val sampleHomeLiveAstrologers = listOf(
            Triple("live_1", "Tarot Deepali", R.drawable.astrologer_deepali_portrait_1786370825016),
            Triple("live_2", "Tarot Shubh", R.drawable.img_pandit_banner_1785577758893),
            Triple("live_3", "Acharya Kamalendra", R.drawable.astrologer_kamalendra_portrait_1786370841660),
            Triple("live_4", "Sunita Shastri", R.drawable.astrologer_deepali_portrait_1786370825016),
            Triple("live_5", "Dr. Rajeshwar", R.drawable.img_pandit_banner_1785577758893)
        )

        SectionHeader(
            title = "🔴 Live Astrologers",
            subtitle = "Watch live sessions, ask questions & send offerings",
            actionText = "Watch Live",
            onActionClick = { onNavigateToLiveAstrologers(null) }
        )

        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(sampleHomeLiveAstrologers) { (id, name, photoRes) ->
                HomeLiveAstrologerCard(
                    id = id,
                    name = name,
                    photoRes = photoRes,
                    onClick = { onNavigateToLiveAstrologers(id) }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // ----------------------------------------------------
        // 4. TRENDING POOJAS (Section 3)
        // ----------------------------------------------------
        SectionHeader(
            title = "🔥 Trending Pooja's",
            subtitle = "Pre-scheduled temple rituals — book your Sankalp",
            actionText = "View All",
            onActionClick = { onViewAllPoojas(PoojaListFilter.UPCOMING) }
        )
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            items(upcomingPoojas) { pooja ->
                UpcomingPoojaCard(
                    pooja = pooja,
                    onParticipateClick = { onSelectUpcomingPooja(pooja) }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // ----------------------------------------------------
        // 5. RECOMMENDED POOJAS (Section 4)
        // ----------------------------------------------------
        SectionHeader(
            title = "✨ Recommended Poojas",
            subtitle = "Select your preferred date & time slot",
            actionText = "View All",
            onActionClick = { onViewAllPoojas(PoojaListFilter.RECOMMENDED) }
        )
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            items(recommendedPoojas) { pooja ->
                RecommendedPoojaMatchingCard(
                    pooja = pooja,
                    onBookClick = { onSelectRecommendedPooja(pooja) }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // ----------------------------------------------------
        // 6. AI & VEDIC TOOLS (Section 5)
        // ----------------------------------------------------
        val combinedTools = (vedicTools + aiServices)
        if (combinedTools.isNotEmpty()) {
            SectionHeader(
                title = "✨ AI & Vedic Tools",
                subtitle = "Instant Kundli, Rashifal & AI Astro Guidance",
                actionText = "Explore All",
                onActionClick = { onNavigateToVedicTools(combinedTools.first()) }
            )

            // Two-Row Horizontal Icon Grid
            val topRow = combinedTools.filterIndexed { index, _ -> index % 2 == 0 }
            val bottomRow = combinedTools.filterIndexed { index, _ -> index % 2 != 0 }

            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(topRow.size) { i ->
                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        val item1 = topRow[i]
                        VedicToolGridItem(tool = item1, onClick = { onNavigateToVedicTools(item1) })

                        if (i < bottomRow.size) {
                            val item2 = bottomRow[i]
                            VedicToolGridItem(tool = item2, onClick = { onNavigateToVedicTools(item2) })
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

        // ----------------------------------------------------
        // 7. LIVE DARSHAN (Section 6)
        // ----------------------------------------------------
        SectionHeader(
            title = "🔴 Live Darshan",
            subtitle = "Direct live streams from sacred shrines",
            actionText = "Watch Live",
            onActionClick = { if (liveStreams.isNotEmpty()) onJoinLiveStream(liveStreams.first()) }
        )
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            items(liveStreams) { stream ->
                LiveStreamCard(
                    stream = stream,
                    onJoinClick = { onJoinLiveStream(stream) }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // ----------------------------------------------------
        // 8. SPIRITUAL ARTICLES (Section 7)
        // ----------------------------------------------------
        if (blogPosts.isNotEmpty()) {
            SectionHeader(
                title = "📜 Spiritual Articles",
                subtitle = "Daily spiritual insights, rituals & festival guides",
                actionText = "Read All",
                onActionClick = onNavigateToBlogs
            )
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                items(blogPosts) { blog ->
                    ArticleMatchingCard(
                        blog = blog,
                        onClick = { onSelectBlog(blog) }
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

        // ----------------------------------------------------
        // 9. TODAY'S PANCHANG
        // ----------------------------------------------------
        SectionHeader(
            title = "📅 Today's Panchang",
            subtitle = "Shubh Muhurat, Rahu Kaal & Tithi insights",
            actionText = "Full Details",
            onActionClick = onNavigateToPanchang
        )
        PanchangCard(panchang = panchang, onOpenAiGuru = onNavigateToPanchang)

        Spacer(modifier = Modifier.height(16.dp))

        // ----------------------------------------------------
        // 10. OFFERINGS
        // ----------------------------------------------------
        SectionHeader(
            title = "🪷 Offerings",
            subtitle = "Offer flowers, milk, prasad & diyas directly to deities",
            actionText = "View All",
            onActionClick = onNavigateToStore
        )
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(chadhavaItems) { item ->
                ChadhavaItemCard(
                    item = item,
                    onAddToCart = { onAddChadhava(item) }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // ----------------------------------------------------
        // 11. FEATURED TEMPLES
        // ----------------------------------------------------
        if (temples.isNotEmpty()) {
            SectionHeader(
                title = "🛕 Featured Temples",
                subtitle = "Explore holy pilgrimages & online darshan",
                actionText = "View Temples",
                onActionClick = onNavigateToTemples
            )
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(temples) { temple ->
                    TempleCard(
                        temple = temple,
                        onClick = onNavigateToTemples
                    )
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

// ---------------------------------------------------------------------
// TOP BANNER CAROUSEL
// ---------------------------------------------------------------------
@Composable
private fun TopBannerCarousel(onBannerClick: (Int) -> Unit) {
    val banners = listOf(
        BannerItem("🪔 Sravana Somvar Special Puja", "Kashi Vishwanath • 50% Off", R.drawable.img_live_darshan_kashi_1785577744688),
        BannerItem("🔮 Talk to India's Top Astrologers", "1st Consultation @ ₹1/min", R.drawable.img_pandit_banner_1785577758893),
        BannerItem("🔴 Live Bhasma Aarti Ujjain", "Direct HD Stream • Watch Free", R.drawable.img_hero_divine_1785577730862)
    )

    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(banners.size) { index ->
            val b = banners[index]
            Card(
                modifier = Modifier
                    .width(300.dp)
                    .height(125.dp)
                    .clickable { onBannerClick(index) },
                shape = RoundedCornerShape(18.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    Image(
                        painter = painterResource(id = b.imgRes),
                        contentDescription = b.title,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.horizontalGradient(
                                    colors = listOf(Color.Black.copy(alpha = 0.85f), Color.Black.copy(alpha = 0.35f))
                                )
                            )
                            .padding(14.dp)
                    ) {
                        Column(modifier = Modifier.align(Alignment.CenterStart)) {
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(KrishnaYellow)
                                    .padding(horizontal = 8.dp, vertical = 2.dp)
                            ) {
                                Text("SPECIAL OFFER", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = DarkCharcoal)
                            }
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = b.title,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = PureWhite,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            Text(
                                text = b.subtitle,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = KrishnaYellow,
                                maxLines = 1
                            )
                        }
                    }
                }
            }
        }
    }
}

private data class BannerItem(val title: String, val subtitle: String, val imgRes: Int)

// ---------------------------------------------------------------------
// GLOBAL SEARCH SECTION
// ---------------------------------------------------------------------
@Composable
private fun GlobalSearchSection(
    query: String,
    onQueryChange: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(KrishnaYellow.copy(alpha = 0.25f))
            .padding(horizontal = 16.dp, vertical = 10.dp)
    ) {
        OutlinedTextField(
            value = query,
            onValueChange = onQueryChange,
            placeholder = {
                Text(
                    text = "Search Temples, Pandits, or Poojas",
                    fontSize = 12.sp,
                    color = TextMuted,
                    maxLines = 1
                )
            },
            leadingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = "Search", tint = GoldAccent) },
            trailingIcon = { Icon(imageVector = Icons.Default.Mic, contentDescription = "Voice", tint = GoldAccent) },
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = PureWhite,
                unfocusedContainerColor = PureWhite,
                focusedBorderColor = KrishnaYellow,
                unfocusedBorderColor = Color.LightGray
            ),
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(6.dp))

        // Trending chips
        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            val chips = listOf("Trending 🔥", "Kashi Vishwanath", "Maha Rudrabhishek", "Tirupati Laddu", "Ganesh Pooja", "Siddhivinayak")
            items(chips) { chip ->
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(PureWhite)
                        .border(1.dp, KrishnaYellow, RoundedCornerShape(12.dp))
                        .clickable { onQueryChange(chip) }
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text(text = chip, fontSize = 11.sp, color = DarkCharcoal, fontWeight = FontWeight.SemiBold)
                }
            }
        }
    }
}

// ---------------------------------------------------------------------
// HOME ASTROLOGER CARD (With Online Pulse Dot & Gradient Buttons)
// ---------------------------------------------------------------------
@Composable
private fun HomeAstrologerCard(
    astrologer: Astrologer,
    onCardClick: () -> Unit,
    onChatClick: () -> Unit,
    onCallClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .width(230.dp)
            .clickable { onCardClick() },
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = PureWhite),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(contentAlignment = Alignment.BottomEnd) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(54.dp)
                            .clip(CircleShape)
                            .background(KrishnaYellow.copy(alpha = 0.3f))
                            .border(2.dp, KrishnaYellow, CircleShape)
                    ) {
                        if (astrologer.photoRes != 0) {
                            Image(
                                painter = painterResource(id = astrologer.photoRes),
                                contentDescription = astrologer.name,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize().clip(CircleShape)
                            )
                        } else {
                            Text(text = if (astrologer.gender == "Female") "👩‍💼" else "🧘‍♂️", fontSize = 26.sp)
                        }
                    }

                    if (astrologer.isOnline) {
                        OnlineGlowDot(size = 9.dp)
                    }
                }

                Spacer(modifier = Modifier.width(10.dp))

                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = astrologer.name,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextDark,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.weight(1f, fill = false)
                        )
                        if (astrologer.isVerified) {
                            Spacer(modifier = Modifier.width(2.dp))
                            Icon(imageVector = Icons.Default.CheckCircle, contentDescription = null, tint = SuccessGreen, modifier = Modifier.size(14.dp))
                        }
                    }
                    Text(
                        text = astrologer.skills.firstOrNull() ?: "Vedic Astro",
                        fontSize = 11.sp,
                        color = TextMuted,
                        maxLines = 1
                    )
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(imageVector = Icons.Default.Star, contentDescription = null, tint = GoldAccent, modifier = Modifier.size(12.dp))
                        Spacer(modifier = Modifier.width(2.dp))
                        Text(text = "${astrologer.rating}", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = TextDark)
                        Text(text = " • Exp ${astrologer.experienceYears}y", fontSize = 10.sp, color = TextMuted)
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "₹${astrologer.pricePerMin.toInt()}/min",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = SuccessGreen
                )

                DharmaGradientButton(
                    text = "Chat",
                    onClick = onChatClick,
                    icon = Icons.Default.Chat,
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                )
            }
        }
    }
}

// ---------------------------------------------------------------------
// UPCOMING POOJA CARD
// ---------------------------------------------------------------------
@Composable
fun UpcomingPoojaCard(
    pooja: UpcomingPooja,
    onParticipateClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .width(280.dp)
            .clickable { onParticipateClick() },
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = PureWhite),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(130.dp)
            ) {
                if (pooja.bannerRes != 0) {
                    Image(
                        painter = painterResource(id = pooja.bannerRes),
                        contentDescription = pooja.name,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                } else {
                    Box(modifier = Modifier.fillMaxSize().background(KrishnaYellow.copy(alpha = 0.4f)))
                }
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.75f))
                            )
                        )
                )

                Box(
                    modifier = Modifier
                        .padding(10.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(KrishnaYellow)
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                        .align(Alignment.TopStart)
                ) {
                    Text(text = "⏳ Starts in 02h 45m", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = DarkCharcoal)
                }

                Box(
                    modifier = Modifier
                        .padding(10.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(PureWhite)
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                        .align(Alignment.BottomEnd)
                ) {
                    Text(text = "👥 ${pooja.devoteesParticipating} Joined", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = DarkCharcoal)
                }
            }

            Column(modifier = Modifier.padding(12.dp)) {
                Text(
                    text = pooja.name,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextDark,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "🛕 ${pooja.templeName} • ${pooja.location}",
                    fontSize = 11.sp,
                    color = TextMuted,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "📅 ${pooja.dateText} | ${pooja.timeSlot}",
                    fontSize = 10.sp,
                    color = GoldAccent,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(text = "Sankalp Fee", fontSize = 9.sp, color = TextMuted)
                        Text(text = "₹${pooja.startingPrice.toInt()}", fontSize = 15.sp, fontWeight = FontWeight.ExtraBold, color = SuccessGreen)
                    }

                    DharmaGradientButton(
                        text = "Participate Now",
                        onClick = onParticipateClick
                    )
                }
            }
        }
    }
}

// ---------------------------------------------------------------------
// RECOMMENDED POOJA CARD (REDESIGNED MATCHING UPCOMING POOJAS STYLE)
// ---------------------------------------------------------------------
@Composable
private fun RecommendedPoojaMatchingCard(
    pooja: PoojaService,
    onBookClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .width(280.dp)
            .clickable { onBookClick() },
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = PureWhite),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(130.dp)
            ) {
                if (pooja.imageRes != 0) {
                    Image(
                        painter = painterResource(id = pooja.imageRes),
                        contentDescription = pooja.name,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                } else {
                    Box(modifier = Modifier.fillMaxSize().background(KrishnaYellow.copy(alpha = 0.4f)))
                }
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.75f))
                            )
                        )
                )

                // Category Tag
                Box(
                    modifier = Modifier
                        .padding(10.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(KrishnaYellow)
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                        .align(Alignment.TopStart)
                ) {
                    Text(text = "✨ ${pooja.deity}", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = DarkCharcoal)
                }

                // Rating Badge
                Box(
                    modifier = Modifier
                        .padding(10.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(PureWhite)
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                        .align(Alignment.BottomEnd)
                ) {
                    Text(text = "⭐ ${pooja.rating}", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = DarkCharcoal)
                }
            }

            Column(modifier = Modifier.padding(12.dp)) {
                Text(
                    text = pooja.name,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextDark,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "🛕 ${pooja.templeName}",
                    fontSize = 11.sp,
                    color = TextMuted,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "⏱️ ${pooja.durationMinutes} mins duration",
                    fontSize = 10.sp,
                    color = GoldAccent,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(text = "Starting Price", fontSize = 9.sp, color = TextMuted)
                        Text(text = "₹${pooja.price.toInt()}", fontSize = 15.sp, fontWeight = FontWeight.ExtraBold, color = SuccessGreen)
                    }

                    DharmaGradientButton(
                        text = "Book Pooja",
                        onClick = onBookClick
                    )
                }
            }
        }
    }
}

// ---------------------------------------------------------------------
// VEDIC TOOL GRID ITEM (Circular Icon, Short Title)
// ---------------------------------------------------------------------
@Composable
private fun VedicToolGridItem(
    tool: VedicToolItem,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .width(150.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = PureWhite),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(38.dp)
                    .clip(CircleShape)
                    .background(KrishnaYellow.copy(alpha = 0.3f))
            ) {
                Text(text = tool.iconEmoji, fontSize = 20.sp)
            }
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(
                    text = tool.title,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextDark,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = tool.category.name,
                    fontSize = 9.sp,
                    color = TextMuted,
                    maxLines = 1
                )
            }
        }
    }
}

// ---------------------------------------------------------------------
// LIVE STREAM CARD
// ---------------------------------------------------------------------
@Composable
fun LiveStreamCard(
    stream: LiveDarshanStream,
    onJoinClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .width(260.dp)
            .clickable { onJoinClick() },
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = PureWhite),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(130.dp)
            ) {
                if (stream.thumbnailRes != 0) {
                    Image(
                        painter = painterResource(id = stream.thumbnailRes),
                        contentDescription = stream.templeName,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                } else {
                    Box(modifier = Modifier.fillMaxSize().background(KrishnaYellow.copy(alpha = 0.4f)))
                }
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.75f))
                            )
                        )
                )

                // LIVE Badge
                Box(
                    modifier = Modifier
                        .padding(10.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.Red)
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                        .align(Alignment.TopStart)
                ) {
                    Text(text = "🔴 LIVE DARSHAN", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = PureWhite)
                }

                Box(
                    modifier = Modifier
                        .padding(10.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(PureWhite.copy(alpha = 0.9f))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                        .align(Alignment.BottomEnd)
                ) {
                    Text(text = "👁️ ${stream.viewersCount} Viewing", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = DarkCharcoal)
                }
            }

            Column(modifier = Modifier.padding(12.dp)) {
                Text(
                    text = stream.templeName,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextDark,
                    maxLines = 1
                )
                Text(
                    text = stream.currentAarti,
                    fontSize = 11.sp,
                    color = TextMuted,
                    maxLines = 1
                )

                Spacer(modifier = Modifier.height(10.dp))

                DharmaGradientButton(
                    text = "Watch Live Darshan",
                    onClick = onJoinClick,
                    icon = Icons.Default.LiveTv,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

// ---------------------------------------------------------------------
// ARTICLE CARD (REDESIGNED MATCHING UPCOMING POOJAS STYLE)
// ---------------------------------------------------------------------
@Composable
private fun ArticleMatchingCard(
    blog: BlogPost,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .width(280.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = PureWhite),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(130.dp)
            ) {
                if (blog.imageRes != 0) {
                    Image(
                        painter = painterResource(id = blog.imageRes),
                        contentDescription = blog.title,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                } else {
                    Box(modifier = Modifier.fillMaxSize().background(KrishnaYellow.copy(alpha = 0.4f)))
                }
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.75f))
                            )
                        )
                )

                Box(
                    modifier = Modifier
                        .padding(10.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(KrishnaYellow)
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                        .align(Alignment.TopStart)
                ) {
                    Text(text = "📜 ${blog.category}", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = DarkCharcoal)
                }

                Box(
                    modifier = Modifier
                        .padding(10.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(PureWhite)
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                        .align(Alignment.BottomEnd)
                ) {
                    Text(text = "⏱️ ${blog.readingTime}", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = DarkCharcoal)
                }
            }

            Column(modifier = Modifier.padding(12.dp)) {
                Text(
                    text = blog.title,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextDark,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "✍️ ${blog.author} • ${blog.publishedDate}",
                    fontSize = 11.sp,
                    color = TextMuted,
                    maxLines = 1
                )

                Spacer(modifier = Modifier.height(10.dp))

                DharmaGradientButton(
                    text = "Read Article",
                    onClick = onClick,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

// ---------------------------------------------------------------------
// SACRED CHADHAVA CARD
// ---------------------------------------------------------------------
@Composable
private fun ChadhavaItemCard(
    item: ChadhavaItem,
    onAddToCart: () -> Unit
) {
    Card(
        modifier = Modifier.width(155.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = PureWhite),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(52.dp)
                    .clip(CircleShape)
                    .background(KrishnaYellow.copy(alpha = 0.25f))
            ) {
                Text(text = item.iconEmoji, fontSize = 28.sp)
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = item.name,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = TextDark,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = "₹${item.price.toInt()}",
                fontSize = 13.sp,
                fontWeight = FontWeight.ExtraBold,
                color = SuccessGreen
            )

            Spacer(modifier = Modifier.height(8.dp))

            DharmaGradientButton(
                text = "Offer Now",
                onClick = onAddToCart,
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 6.dp)
            )
        }
    }
}

// ---------------------------------------------------------------------
// FEATURED TEMPLE CARD
// ---------------------------------------------------------------------
@Composable
private fun TempleCard(
    temple: Temple,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .width(220.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = PureWhite),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
            ) {
                if (temple.coverImageRes != 0) {
                    Image(
                        painter = painterResource(id = temple.coverImageRes),
                        contentDescription = temple.name,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                } else {
                    Box(modifier = Modifier.fillMaxSize().background(KrishnaYellow.copy(alpha = 0.4f)))
                }
            }
            Column(modifier = Modifier.padding(10.dp)) {
                Text(text = temple.name, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = TextDark, maxLines = 1)
                Text(text = "📍 ${temple.city}, ${temple.state}", fontSize = 10.sp, color = TextMuted, maxLines = 1)
            }
        }
    }
}

// ---------------------------------------------------------------------
// HOME LIVE ASTROLOGER CARD (REDESIGNED)
// ---------------------------------------------------------------------
@Composable
private fun HomeLiveAstrologerCard(
    id: String,
    name: String,
    photoRes: Int,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .width(140.dp)
            .height(195.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = DarkCharcoal),
        border = androidx.compose.foundation.BorderStroke(1.5.dp, GoldAccent.copy(alpha = 0.6f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            // Full Portrait Background Image
            Image(
                painter = painterResource(id = photoRes),
                contentDescription = name,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            // Vignette Gradient Overlay
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Black.copy(alpha = 0.6f),
                                Color.Transparent,
                                Color.Black.copy(alpha = 0.85f)
                            )
                        )
                    )
            )

            // TOP ROW: LIVE Badge & Viewer Count
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // RED LIVE BADGE
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color.Red)
                        .padding(horizontal = 6.dp, vertical = 3.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(6.dp)
                                .clip(CircleShape)
                                .background(PureWhite)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "LIVE",
                            fontSize = 9.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = PureWhite
                        )
                    }
                }

                // VIEWER COUNT
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color.Black.copy(alpha = 0.5f))
                        .padding(horizontal = 6.dp, vertical = 3.dp)
                ) {
                    Text(
                        text = "👁️ 800+",
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold,
                        color = KrishnaYellow
                    )
                }
            }

            // BOTTOM INFO & WATCH LIVE CTA
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                Text(
                    text = name,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = PureWhite,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = "Tarot & Remedies",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = DivineGold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(6.dp))

                // Watch Button Badge
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(KrishnaYellow)
                        .padding(vertical = 4.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "▶ Join",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = DarkCharcoal
                    )
                }
            }
        }
    }
}
