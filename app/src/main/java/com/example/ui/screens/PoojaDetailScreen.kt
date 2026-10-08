package com.example.ui.screens

import android.content.Intent
import android.widget.Toast
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
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ConfirmationNumber
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.HourglassBottom
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Place
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.R
import com.example.data.model.PoojaService
import com.example.data.model.UpcomingPooja
import com.example.ui.theme.CardLightBorder
import com.example.ui.theme.DivineGold
import com.example.ui.theme.LightCreamBackground
import com.example.ui.theme.PureWhite
import com.example.ui.theme.SacredMaroon
import com.example.ui.theme.SaffronOrange
import com.example.ui.theme.TextDark
import com.example.ui.theme.TextMuted

data class PoojaDetailData(
    val id: String,
    val name: String,
    val templeName: String,
    val location: String = "Kashi Vishwanath Temple, Varanasi",
    val dateText: String = "March 8, 2026",
    val timeSlot: String = "06:00 PM IST",
    val durationText: String = "180 Minutes",
    val devoteesCountText: String = "12,450+ Joined",
    val countdownDays: Int = 12,
    val countdownHours: Int = 4,
    val countdownMins: Int = 45,
    val price: Double = 2100.0,
    val rating: Float = 4.9f,
    val reviewsCount: Int = 1200,
    val bannerRes: Int = 0,
    val badgeText: String = "MOST AUSPICIOUS",
    val aboutText: String = "Maha Shivratri at Kashi Vishwanath is considered one of the most powerful spiritual events on the planet. As the night of the divine convergence of Shiva and Shakti, this Mahapooja is designed to align your internal energies with the cosmic consciousness.\n\nOur Vedic scholars will perform the Rudrabhishek with sacred waters of the Ganges, Bilva leaves, and ancient mantras that have echoed through these corridors for thousands of years. Participate remotely to receive the Sankalpa and divine vibrations from the heart of spiritual India.",
    val highlights: List<String> = listOf(
        "Personal Sankalpa with your name and gotra.",
        "Live streaming in high-definition quality.",
        "Prasad energized at the Jyotirlinga delivered to you.",
        "Detailed digital certificates of participation."
    ),
    val priestName: String = "Pandit Somnath Vyas",
    val priestTitle: String = "Head Acharya, Kashi",
    val priestBio: String = "Acharya Somnath has been the principal priest for the Mahapooja for over 30 years, specializing in the Krishna Yajur Veda."
)

fun UpcomingPooja.toDetailData(): PoojaDetailData {
    return PoojaDetailData(
        id = id,
        name = name,
        templeName = templeName,
        location = location,
        dateText = dateText,
        timeSlot = timeSlot,
        devoteesCountText = "$devoteesParticipating+ Joined",
        countdownDays = countdownMinutes / 1440,
        countdownHours = (countdownMinutes % 1440) / 60,
        countdownMins = countdownMinutes % 60,
        price = startingPrice,
        rating = rating,
        reviewsCount = reviewsCount,
        bannerRes = bannerRes,
        aboutText = description,
        highlights = spiritualBenefits,
        priestName = priestName,
        priestTitle = priestTitle
    )
}

fun PoojaService.toDetailData(): PoojaDetailData {
    return PoojaDetailData(
        id = id,
        name = name,
        templeName = templeName,
        location = "$templeName • Special Shrine",
        dateText = "Selected Date Slot",
        timeSlot = "09:00 AM IST",
        durationText = "$durationMinutes Minutes",
        devoteesCountText = "8,920+ Joined",
        price = price,
        rating = rating,
        reviewsCount = 420,
        bannerRes = imageRes,
        aboutText = description,
        highlights = benefits
    )
}

@Composable
fun PoojaDetailScreen(
    detailData: PoojaDetailData,
    onBack: () -> Unit,
    onParticipateNow: (PoojaDetailData) -> Unit,
    onViewAcharyaProfile: ((String) -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var isBookmarked by remember { mutableStateOf(false) }
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    var showPhotoGalleryModal by remember { mutableStateOf(false) }
    var lightboxIndex by remember { mutableStateOf<Int?>(null) }

    // Devotee & Sankalp Booking Form State
    var devoteeName by remember { mutableStateOf("") }
    var gotra by remember { mutableStateOf("") }
    var rashi by remember { mutableStateOf("Select Rashi") }
    var familyMembersInput by remember { mutableStateOf("") }
    var sankalpWish by remember { mutableStateOf("Health, Wealth & Peace") }
    var selectedDateSlot by remember { mutableStateOf("Tomorrow (08:00 AM)") }
    var prasadAddress by remember { mutableStateOf("") }

    val rashis = listOf("Mesha (Aries)", "Vrishabha (Taurus)", "Mithuna (Gemini)", "Karka (Cancer)", "Simha (Leo)", "Kanya (Virgo)", "Tula (Libra)", "Vrishchika (Scorpio)", "Dhanu (Sagittarius)", "Makara (Capricorn)", "Kumbha (Aquarius)", "Meena (Pisces)")
    val sankalpOptions = listOf("Health, Wealth & Peace", "Career & Business Growth", "Remove Marriage Obstacles", "Protection from Evil Eyes & Doshas", "Child Welfare & Education")
    val dateSlots = listOf("Tomorrow (08:00 AM)", "Next Ekadashi (09:30 AM)", "Upcoming Pradosham (06:00 PM)", "Full Moon / Purnima (07:00 AM)")

    val tabs = listOf("About", "Spiritual Benefits", "Priest Details", "Gallery", "Reviews")
    val scrollState = rememberScrollState()

    val galleryPhotos = listOf(
        R.drawable.img_hero_divine_1785577730862,
        R.drawable.img_live_darshan_kashi_1785577744688,
        R.drawable.img_pandit_banner_1785577758893,
        R.drawable.img_hero_divine_1785577730862,
        R.drawable.img_live_darshan_kashi_1785577744688,
        R.drawable.img_pandit_banner_1785577758893
    )

    Scaffold(
        bottomBar = {
            // Sticky Bottom CTA Bar
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = PureWhite),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                Row(
                    modifier = Modifier
                        .padding(12.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Button(
                        onClick = { onParticipateNow(detailData) },
                        colors = ButtonDefaults.buttonColors(containerColor = SacredMaroon),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .weight(1f)
                            .height(50.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ConfirmationNumber,
                            contentDescription = null,
                            tint = DivineGold,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Participate Now (₹${detailData.price.toInt()})",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = PureWhite
                        )
                    }

                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(50.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .border(1.dp, CardLightBorder, RoundedCornerShape(12.dp))
                            .background(LightCreamBackground)
                            .clickable { isBookmarked = !isBookmarked }
                    ) {
                        Icon(
                            imageVector = if (isBookmarked) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = "Bookmark",
                            tint = if (isBookmarked) Color.Red else SacredMaroon,
                            modifier = Modifier.size(22.dp)
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
                .padding(bottom = innerPadding.calculateBottomPadding())
                .background(LightCreamBackground)
                .verticalScroll(scrollState)
        ) {
            // Hero Image Header Banner - No white gap above
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(260.dp)
            ) {
                val bannerImage = if (detailData.bannerRes != 0) detailData.bannerRes else R.drawable.img_hero_divine_1785577730862
                Image(
                    painter = painterResource(id = bannerImage),
                    contentDescription = detailData.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                // Dark Gradient
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color.Black.copy(alpha = 0.4f),
                                    Color.Transparent,
                                    Color.Black.copy(alpha = 0.92f)
                                )
                            )
                        )
                )

                // Floating Action Overlay (Top Right: Share & Favorite)
                Row(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(38.dp)
                            .clip(CircleShape)
                            .background(Color.Black.copy(alpha = 0.5f))
                            .clickable {
                                val sendIntent = Intent().apply {
                                    action = Intent.ACTION_SEND
                                    putExtra(Intent.EXTRA_TEXT, "Book ${detailData.name} at ${detailData.templeName} on DharmaDarshan! https://dharmadarshan.app/pooja")
                                    type = "text/plain"
                                }
                                context.startActivity(Intent.createChooser(sendIntent, "Share Mahapooja"))
                            }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = "Share",
                            tint = PureWhite,
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(38.dp)
                            .clip(CircleShape)
                            .background(Color.Black.copy(alpha = 0.5f))
                            .clickable { isBookmarked = !isBookmarked }
                    ) {
                        Icon(
                            imageVector = if (isBookmarked) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = "Favorite",
                            tint = if (isBookmarked) Color.Red else PureWhite,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }

                Column(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(16.dp)
                ) {
                    // Badge Tag
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(SaffronOrange)
                            .padding(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = detailData.badgeText,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = PureWhite
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = detailData.name,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = PureWhite,
                        fontFamily = FontFamily.Serif
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Outlined.Place,
                            contentDescription = null,
                            tint = DivineGold,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = detailData.location,
                            fontSize = 13.sp,
                            color = PureWhite.copy(alpha = 0.9f)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Key Info Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = PureWhite),
                border = androidx.compose.foundation.BorderStroke(1.dp, CardLightBorder)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    InfoDetailRow(
                        icon = Icons.Default.CalendarToday,
                        label = "DATE",
                        value = detailData.dateText
                    )
                    InfoDetailRow(
                        icon = Icons.Default.AccessTime,
                        label = "TIME",
                        value = detailData.timeSlot
                    )
                    InfoDetailRow(
                        icon = Icons.Default.HourglassBottom,
                        label = "DURATION",
                        value = detailData.durationText
                    )
                    InfoDetailRow(
                        icon = Icons.Default.People,
                        label = "DEVOTEES",
                        value = detailData.devoteesCountText
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Countdown Timer Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = SaffronOrange),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "STARTS IN",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = PureWhite,
                        letterSpacing = 1.sp
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        TimerDigitBlock(value = String.format("%02d", detailData.countdownDays.coerceAtLeast(12)), label = "DAYS")
                        Text(text = ":", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = PureWhite, modifier = Modifier.padding(horizontal = 6.dp))
                        TimerDigitBlock(value = String.format("%02d", detailData.countdownHours.coerceAtLeast(4)), label = "HRS")
                        Text(text = ":", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = PureWhite, modifier = Modifier.padding(horizontal = 6.dp))
                        TimerDigitBlock(value = String.format("%02d", detailData.countdownMins.coerceAtLeast(45)), label = "MINS")
                    }
                }
            }



            Spacer(modifier = Modifier.height(20.dp))

            // Scrollable Content Tabs Header
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                itemsIndexed(tabs) { idx, tab ->
                    val isSelected = selectedTabIndex == idx
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.clickable { selectedTabIndex = idx }
                    ) {
                        Text(
                            text = tab,
                            fontSize = 14.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                            color = if (isSelected) SacredMaroon else TextMuted
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Box(
                            modifier = Modifier
                                .width(if (isSelected) 30.dp else 0.dp)
                                .height(3.dp)
                                .background(SaffronOrange)
                        )
                    }
                }
            }

            Divider(modifier = Modifier.padding(horizontal = 16.dp), color = Color.LightGray.copy(alpha = 0.4f))

            Spacer(modifier = Modifier.height(16.dp))

            // Tab Dynamic Content
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                when (selectedTabIndex) {
                    0 -> { // About
                        Text(
                            text = "About the Mahapooja",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = SacredMaroon,
                            fontFamily = FontFamily.Serif
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = detailData.aboutText,
                            fontSize = 13.sp,
                            color = TextDark,
                            lineHeight = 20.sp
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Ritual Highlights & Inclusions",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = SacredMaroon
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        detailData.highlights.forEach { highlightText ->
                            HighlightCard(text = highlightText)
                        }
                    }

                    1 -> { // Spiritual Benefits
                        Text(
                            text = "✨ Spiritual & Earthly Benefits",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = SacredMaroon,
                            fontFamily = FontFamily.Serif
                        )
                        Spacer(modifier = Modifier.height(10.dp))

                        val benefitCards = listOf(
                            "Karma Purification" to "Eliminates past karmic blockages and fills your life with positive cosmic resonance.",
                            "Family Harmony & Health" to "Invokes divine protection for parents, spouse, and children against chronic ailments.",
                            "Prosperity & Financial Growth" to "Attracts Lakshmi Kripa and unlocks stalled career or business opportunities.",
                            "Removal of Planetary Doshas" to "Specially effective for Rahu-Ketu, Shani Sade Sati, and Manglik Dosha Shanti."
                        )

                        benefitCards.forEach { (title, desc) ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 5.dp),
                                shape = RoundedCornerShape(12.dp),
                                colors = CardDefaults.cardColors(containerColor = PureWhite),
                                border = androidx.compose.foundation.BorderStroke(1.dp, CardLightBorder)
                            ) {
                                Column(modifier = Modifier.padding(14.dp)) {
                                    Text(text = "🕉️ $title", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = SacredMaroon)
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(text = desc, fontSize = 12.sp, color = TextDark, lineHeight = 17.sp)
                                }
                            }
                        }
                    }

                    2 -> { // Priest Details
                        Text(
                            text = "Led by Acharya",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = SacredMaroon,
                            fontFamily = FontFamily.Serif
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = PureWhite),
                            border = androidx.compose.foundation.BorderStroke(1.dp, CardLightBorder)
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.img_pandit_banner_1785577758893),
                                    contentDescription = detailData.priestName,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .size(70.dp)
                                        .clip(CircleShape)
                                )

                                Spacer(modifier = Modifier.height(8.dp))

                                Text(
                                    text = detailData.priestName,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = SacredMaroon
                                )
                                Text(
                                    text = detailData.priestTitle,
                                    fontSize = 12.sp,
                                    color = SaffronOrange,
                                    fontWeight = FontWeight.SemiBold
                                )

                                Spacer(modifier = Modifier.height(8.dp))

                                Text(
                                    text = detailData.priestBio,
                                    fontSize = 12.sp,
                                    color = TextDark,
                                    textAlign = TextAlign.Center,
                                    lineHeight = 17.sp
                                )

                                Spacer(modifier = Modifier.height(12.dp))

                                OutlinedButton(
                                    onClick = {
                                        if (onViewAcharyaProfile != null) {
                                            onViewAcharyaProfile(detailData.priestName)
                                        } else {
                                            Toast.makeText(context, "Opening profile of ${detailData.priestName}", Toast.LENGTH_SHORT).show()
                                        }
                                    },
                                    shape = RoundedCornerShape(10.dp),
                                    border = androidx.compose.foundation.BorderStroke(1.dp, SacredMaroon)
                                ) {
                                    Text("View Profile", fontSize = 12.sp, color = SacredMaroon, fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                    }

                    3 -> { // Gallery
                        Text(
                            text = "Past Mahapooja Gallery",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = SacredMaroon,
                            fontFamily = FontFamily.Serif
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Card(
                                modifier = Modifier
                                    .weight(1f)
                                    .height(110.dp)
                                    .clickable { lightboxIndex = 0 },
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.img_hero_divine_1785577730862),
                                    contentDescription = null,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.fillMaxSize()
                                )
                            }

                            Card(
                                modifier = Modifier
                                    .weight(1f)
                                    .height(110.dp)
                                    .clickable { lightboxIndex = 1 },
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.img_live_darshan_kashi_1785577744688),
                                    contentDescription = null,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.fillMaxSize()
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        OutlinedButton(
                            onClick = { showPhotoGalleryModal = true },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(10.dp),
                            border = androidx.compose.foundation.BorderStroke(1.dp, SacredMaroon)
                        ) {
                            Text("VIEW ALL 24 PHOTOS", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = SacredMaroon)
                        }
                    }

                    4 -> { // Reviews
                        // Fixed Layout for Ratings section so it doesn't break vertically (Fix for Screenshot 3)
                        Column(modifier = Modifier.fillMaxWidth()) {
                            Text(
                                text = "Devotee Reviews & Ratings",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = SacredMaroon,
                                fontFamily = FontFamily.Serif
                            )

                            Spacer(modifier = Modifier.height(6.dp))

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Text(
                                    text = "${detailData.rating}",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = SacredMaroon
                                )
                                Row {
                                    repeat(5) {
                                        Icon(
                                            imageVector = Icons.Default.Star,
                                            contentDescription = null,
                                            tint = DivineGold,
                                            modifier = Modifier.size(16.dp)
                                        )
                                    }
                                }
                                Text(
                                    text = "(1,240 Devotee Ratings)",
                                    fontSize = 12.sp,
                                    color = TextMuted,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        val reviews = listOf(
                            "Arjun Kapoor" to "A truly transcendental experience. Participating in the live Sankalpa made me feel like I was physically present at Kashi. The organization was flawless.",
                            "Sunita Sharma" to "Received the blessed Prasad at home in Delhi within 3 days. Beautiful packaging and authentic Rudraksha mala included.",
                            "Ramesh Iyer" to "The chants during live streaming brought immense tranquility to our home. Highly blessed!"
                        )

                        reviews.forEach { (author, text) ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 5.dp),
                                shape = RoundedCornerShape(12.dp),
                                colors = CardDefaults.cardColors(containerColor = PureWhite),
                                border = androidx.compose.foundation.BorderStroke(1.dp, CardLightBorder)
                            ) {
                                Column(modifier = Modifier.padding(12.dp)) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(text = author, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = TextDark)
                                        Row {
                                            repeat(5) {
                                                Icon(imageVector = Icons.Default.Star, contentDescription = null, tint = DivineGold, modifier = Modifier.size(12.dp))
                                            }
                                        }
                                    }
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(text = text, fontSize = 12.sp, color = TextDark, lineHeight = 17.sp)
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(30.dp))
            }
        }
    }

    // Modal Dialog for 24 Photos Gallery
    if (showPhotoGalleryModal) {
        Dialog(
            onDismissRequest = { showPhotoGalleryModal = false },
            properties = DialogProperties(usePlatformDefaultWidth = false)
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth(0.95f)
                    .fillMaxHeight(0.85f),
                shape = RoundedCornerShape(20.dp),
                color = PureWhite
            ) {
                Column(modifier = Modifier.fillMaxSize()) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(SaffronOrange)
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Mahapooja Photo Gallery (24)",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = PureWhite
                        )
                        IconButton(onClick = { showPhotoGalleryModal = false }) {
                            Icon(imageVector = Icons.Default.Close, contentDescription = "Close", tint = PureWhite)
                        }
                    }

                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        contentPadding = PaddingValues(12.dp),
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        itemsIndexed(galleryPhotos) { idx, photoRes ->
                            Card(
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier
                                    .height(130.dp)
                                    .clickable { lightboxIndex = idx }
                            ) {
                                Image(
                                    painter = painterResource(id = photoRes),
                                    contentDescription = "Gallery Photo $idx",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.fillMaxSize()
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    // Full Screen Lightbox Photo Viewer
    if (lightboxIndex != null) {
        Dialog(
            onDismissRequest = { lightboxIndex = null },
            properties = DialogProperties(usePlatformDefaultWidth = false)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.95f)),
                contentAlignment = Alignment.Center
            ) {
                val curIdx = lightboxIndex ?: 0
                val curRes = galleryPhotos[curIdx % galleryPhotos.size]

                Image(
                    painter = painterResource(id = curRes),
                    contentDescription = "Fullscreen Photo",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.fillMaxWidth().padding(16.dp)
                )

                IconButton(
                    onClick = { lightboxIndex = null },
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(24.dp)
                        .background(Color.Black.copy(alpha = 0.6f), CircleShape)
                ) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = "Close", tint = PureWhite)
                }

                if (curIdx > 0) {
                    IconButton(
                        onClick = { lightboxIndex = curIdx - 1 },
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .padding(16.dp)
                            .background(Color.Black.copy(alpha = 0.6f), CircleShape)
                    ) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Prev", tint = PureWhite)
                    }
                }

                if (curIdx < galleryPhotos.size - 1) {
                    IconButton(
                        onClick = { lightboxIndex = curIdx + 1 },
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .padding(16.dp)
                            .background(Color.Black.copy(alpha = 0.6f), CircleShape)
                    ) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowForward, contentDescription = "Next", tint = PureWhite)
                    }
                }
            }
        }
    }
}

@Composable
private fun HighlightCard(text: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = PureWhite),
        border = androidx.compose.foundation.BorderStroke(1.dp, CardLightBorder)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(24.dp)
                    .clip(CircleShape)
                    .background(SaffronOrange.copy(alpha = 0.15f))
            ) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null,
                    tint = SacredMaroon,
                    modifier = Modifier.size(16.dp)
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = text,
                fontSize = 13.sp,
                color = TextDark,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
private fun InfoDetailRow(
    icon: ImageVector,
    label: String,
    value: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(42.dp)
                .clip(CircleShape)
                .background(SaffronOrange.copy(alpha = 0.12f))
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = SacredMaroon,
                modifier = Modifier.size(20.dp)
            )
        }

        Spacer(modifier = Modifier.width(14.dp))

        Column {
            Text(
                text = label,
                fontSize = 10.sp,
                color = TextMuted,
                fontWeight = FontWeight.Bold,
                letterSpacing = 0.5.sp
            )
            Text(
                text = value,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = TextDark
            )
        }
    }
}

@Composable
private fun TimerDigitBlock(value: String, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = value,
            fontSize = 24.sp,
            fontWeight = FontWeight.ExtraBold,
            color = PureWhite
        )
        Text(
            text = label,
            fontSize = 9.sp,
            fontWeight = FontWeight.Bold,
            color = PureWhite.copy(alpha = 0.85f)
        )
    }
}
