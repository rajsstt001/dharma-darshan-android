package com.example.ui.screens

import android.content.Context
import android.content.Intent
import android.widget.Toast
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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ContentCopy
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
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.R
import com.example.data.model.Astrologer
import com.example.ui.components.DharmaGradientButton
import com.example.ui.components.OnlineGlowDot
import com.example.ui.theme.DarkCharcoal
import com.example.ui.theme.GoldAccent
import com.example.ui.theme.KrishnaYellow
import com.example.ui.theme.LightCreamBackground
import com.example.ui.theme.PureWhite
import com.example.ui.theme.SacredMaroon
import com.example.ui.theme.SuccessGreen
import com.example.ui.theme.TextDark
import com.example.ui.theme.TextMuted

data class AstrologerReviewData(
    val author: String,
    val date: String,
    val rating: Int,
    val comment: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AstrologerDetailScreen(
    astrologer: Astrologer,
    isFollowed: Boolean = false,
    onToggleFollow: () -> Unit = {},
    onBack: () -> Unit,
    onStartConsultation: (Astrologer, String) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    var showShareDialog by remember { mutableStateOf(false) }
    var showAllReviewsDialog by remember { mutableStateOf(false) }
    var photoViewerIndex by remember { mutableStateOf<Int?>(null) }
    var isBioExpanded by remember { mutableStateOf(false) }

    val samplePhotos = listOf(
        R.drawable.img_hero_divine_1785577730862,
        R.drawable.img_live_darshan_kashi_1785577744688,
        R.drawable.img_pandit_banner_1785577758893,
        R.drawable.img_hero_divine_1785577730862
    )

    val allReviews = listOf(
        AstrologerReviewData("Rohan Sharma", "Yesterday", 5, "Extremely accurate predictions! The Lal Kitab remedy for my career worked within 2 weeks. Acharya ji analyzed my birth chart with great depth and explained every planetary aspect patiently."),
        AstrologerReviewData("Priya V.", "3 days ago", 5, "Very polite, patient and knowledgeable. Highly recommended for marriage compatibility check and Kundli matching. He answered all my questions without any rush."),
        AstrologerReviewData("Amitabh Chhabra", "1 week ago", 5, "Spot on prediction regarding my business expansion and property purchase. The gemstone recommendation gave me immense peace of mind."),
        AstrologerReviewData("Sunita Patel", "2 weeks ago", 4, "Clear remedies provided without any unnecessary fear-mongering. Very logical approach to Vedic astrology and Prashna Kundli."),
        AstrologerReviewData("Vikramaditya K.", "3 weeks ago", 5, "The Mahadasha analysis was exceptionally detailed. I was going through a tough Saturn transiting phase and his guidance helped me navigate smoothly."),
        AstrologerReviewData("Meenakshi Iyer", "1 month ago", 5, "Spoke with Acharya ji via call. His intuition and Graha Shanti mantras are very effective. Will definitely consult again!"),
        AstrologerReviewData("Gaurav Kapoor", "1 month ago", 5, "Best Vedic Astrologer on DharmaDarshan! Accurate timeline predictions for job change."),
        AstrologerReviewData("Deepika Nair", "2 months ago", 4, "Very soothing voice and authentic knowledge of Parashari and KP systems. Satisfied with consultation.")
    )

    Scaffold(
        bottomBar = {
            Surface(
                color = PureWhite,
                shadowElevation = 12.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 10.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    DharmaGradientButton(
                        text = "Chat (₹${astrologer.pricePerMin.toInt()}/m)",
                        onClick = { onStartConsultation(astrologer, "Chat") },
                        icon = Icons.Default.Chat,
                        modifier = Modifier.weight(1f)
                    )
                    DharmaGradientButton(
                        text = "Call (₹${astrologer.pricePerMin.toInt()}/m)",
                        onClick = { onStartConsultation(astrologer, "Call") },
                        icon = Icons.Default.Call,
                        gradient = Brush.horizontalGradient(
                            colors = listOf(Color(0xFF2E7D32), Color(0xFF4CAF50))
                        ),
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        },
        containerColor = LightCreamBackground,
        modifier = modifier
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(16.dp)
        ) {
            // Header Profile Card
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = PureWhite),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Profile Photo with Online pulse dot & Follow Pill
                    Box(contentAlignment = Alignment.BottomEnd) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .size(96.dp)
                                .clip(CircleShape)
                                .background(KrishnaYellow.copy(alpha = 0.2f))
                                .border(2.5.dp, KrishnaYellow, CircleShape)
                        ) {
                            if (astrologer.photoRes != 0) {
                                Image(
                                    painter = painterResource(id = astrologer.photoRes),
                                    contentDescription = astrologer.name,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .clip(CircleShape)
                                )
                            } else {
                                Text(
                                    text = if (astrologer.gender == "Female") "👩‍💼" else "🧘‍♂️",
                                    fontSize = 48.sp
                                )
                            }
                        }

                        if (astrologer.isOnline) {
                            OnlineGlowDot(size = 14.dp)
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = astrologer.name,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = TextDark,
                            textAlign = TextAlign.Center
                        )
                        if (astrologer.isVerified) {
                            Spacer(modifier = Modifier.width(4.dp))
                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = "Verified",
                                tint = SuccessGreen,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = astrologer.skills.joinToString(", "),
                        fontSize = 12.sp,
                        color = TextMuted,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Prominent Actions Row: Follow & Share
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Button(
                            onClick = onToggleFollow,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (isFollowed) DarkCharcoal else KrishnaYellow
                            ),
                            shape = RoundedCornerShape(20.dp),
                            modifier = Modifier
                                .weight(1f)
                                .height(38.dp)
                        ) {
                            Icon(
                                imageVector = if (isFollowed) Icons.Default.CheckCircle else Icons.Default.Add,
                                contentDescription = null,
                                tint = if (isFollowed) KrishnaYellow else DarkCharcoal,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = if (isFollowed) "Following ✓" else "Follow",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (isFollowed) KrishnaYellow else DarkCharcoal,
                                maxLines = 1
                            )
                        }

                        OutlinedButton(
                            onClick = { showShareDialog = true },
                            shape = RoundedCornerShape(20.dp),
                            modifier = Modifier
                                .weight(1f)
                                .height(38.dp),
                            border = androidx.compose.foundation.BorderStroke(1.dp, SacredMaroon)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Share,
                                contentDescription = null,
                                tint = SacredMaroon,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Share Profile",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = SacredMaroon,
                                maxLines = 1
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Stats Row
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(LightCreamBackground)
                            .padding(vertical = 10.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.Star,
                                    contentDescription = null,
                                    tint = GoldAccent,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(2.dp))
                                Text(
                                    text = "${astrologer.rating}",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = TextDark
                                )
                            }
                            Text(
                                text = "${astrologer.reviewCount} reviews",
                                fontSize = 11.sp,
                                color = TextMuted
                            )
                        }

                        Box(
                            modifier = Modifier
                                .width(1.dp)
                                .height(24.dp)
                                .background(Color.LightGray)
                        )

                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "${astrologer.experienceYears}+ Yrs",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextDark
                            )
                            Text(
                                text = "Experience",
                                fontSize = 11.sp,
                                color = TextMuted
                            )
                        }

                        Box(
                            modifier = Modifier
                                .width(1.dp)
                                .height(24.dp)
                                .background(Color.LightGray)
                        )

                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = if (astrologer.isOnline) "Online Now" else "Offline",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (astrologer.isOnline) SuccessGreen else TextMuted
                            )
                            Text(
                                text = "Status",
                                fontSize = 11.sp,
                                color = TextMuted
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Consultation Modes & Pricing
            Text(
                text = "Consultation Modes",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = TextDark,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                ConsultationPriceCard(
                    title = "Chat Consult",
                    price = "₹${astrologer.pricePerMin.toInt()}/min",
                    icon = Icons.Default.Chat,
                    onClick = { onStartConsultation(astrologer, "Chat") },
                    modifier = Modifier.weight(1f)
                )
                ConsultationPriceCard(
                    title = "Audio Call",
                    price = "₹${astrologer.pricePerMin.toInt()}/min",
                    icon = Icons.Default.Call,
                    onClick = { onStartConsultation(astrologer, "Call") },
                    modifier = Modifier.weight(1f)
                )
                ConsultationPriceCard(
                    title = "Video Call",
                    price = "₹${(astrologer.pricePerMin * 1.5).toInt()}/min",
                    icon = Icons.Default.Videocam,
                    onClick = { onStartConsultation(astrologer, "Video") },
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // About Bio (Shifted directly below Consultation Modes with 4 lines max & inline Read More)
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = PureWhite),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(
                        text = "📜 About ${astrologer.name}",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextDark
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = astrologer.bio,
                        fontSize = 13.sp,
                        color = TextDark.copy(alpha = 0.85f),
                        lineHeight = 18.sp,
                        maxLines = if (isBioExpanded) Int.MAX_VALUE else 4,
                        overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = if (isBioExpanded) "Read Less ▲" else "Read More ▼",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = SacredMaroon,
                        modifier = Modifier
                            .clickable { isBioExpanded = !isBioExpanded }
                            .padding(vertical = 2.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Spacer(modifier = Modifier.height(16.dp))

            // Photo Gallery Section
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = PureWhite),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(
                        text = "📸 Photo Gallery & Certificates",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextDark
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        itemsIndexed(samplePhotos) { index, photoRes ->
                            Box(
                                modifier = Modifier
                                    .size(85.dp)
                                    .clip(RoundedCornerShape(12.dp))
                                    .clickable { photoViewerIndex = index }
                            ) {
                                Image(
                                    painter = painterResource(id = photoRes),
                                    contentDescription = "Gallery Photo",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.fillMaxSize()
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Languages Spoken
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = PureWhite),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(
                        text = "🗣️ Languages Spoken",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextDark
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        astrologer.languages.forEach { lang ->
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(KrishnaYellow.copy(alpha = 0.15f))
                                    .padding(horizontal = 10.dp, vertical = 5.dp)
                            ) {
                                Text(
                                    text = lang,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = TextDark
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Expertise & Specializations
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = PureWhite),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(
                        text = "🔮 Expertise & Specializations",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextDark
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        astrologer.skills.forEach { skill ->
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(GoldAccent.copy(alpha = 0.15f))
                                    .padding(horizontal = 10.dp, vertical = 5.dp)
                            ) {
                                Text(
                                    text = skill,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = TextDark
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Customer Reviews (Shows 5 by default + See All Reviews option)
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = PureWhite),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "⭐ Customer Reviews",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextDark
                        )
                        Text(
                            text = "${astrologer.rating} ★ (${astrologer.reviewCount})",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = GoldAccent
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // 5 most recent reviews
                    allReviews.take(5).forEach { rev ->
                        ReviewItem(review = rev)
                        Spacer(modifier = Modifier.height(8.dp))
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    // See All Reviews Button
                    OutlinedButton(
                        onClick = { showAllReviewsDialog = true },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, SacredMaroon)
                    ) {
                        Text("SEE ALL REVIEWS (${astrologer.reviewCount})", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = SacredMaroon)
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }

    // Share Dialog / Modal Preview matching Screenshot 1
    if (showShareDialog) {
        Dialog(onDismissRequest = { showShareDialog = false }) {
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = PureWhite),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Share Profile", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = TextDark)
                        IconButton(onClick = { showShareDialog = false }) {
                            Icon(imageVector = Icons.Default.Close, contentDescription = "Close", tint = TextMuted)
                        }
                    }

                    // Share Card Preview matching Screenshot 1
                    Card(
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = PureWhite),
                        border = androidx.compose.foundation.BorderStroke(1.dp, Color.LightGray.copy(alpha = 0.5f)),
                        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    contentAlignment = Alignment.Center,
                                    modifier = Modifier
                                        .size(54.dp)
                                        .clip(CircleShape)
                                        .background(KrishnaYellow.copy(alpha = 0.2f))
                                        .border(1.5.dp, KrishnaYellow, CircleShape)
                                ) {
                                    if (astrologer.photoRes != 0) {
                                        Image(
                                            painter = painterResource(id = astrologer.photoRes),
                                            contentDescription = astrologer.name,
                                            contentScale = ContentScale.Crop,
                                            modifier = Modifier.fillMaxSize().clip(CircleShape)
                                        )
                                    } else {
                                        Text(if (astrologer.gender == "Female") "👩‍💼" else "🧘‍♂️", fontSize = 28.sp)
                                    }
                                }

                                Spacer(modifier = Modifier.width(12.dp))

                                Column(modifier = Modifier.weight(1f)) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text(astrologer.name, fontSize = 15.sp, fontWeight = FontWeight.Bold, color = TextDark, modifier = Modifier.weight(1f, fill = false), maxLines = 1, overflow = TextOverflow.Ellipsis)
                                        if (astrologer.isVerified) {
                                            Spacer(modifier = Modifier.width(4.dp))
                                            Icon(imageVector = Icons.Default.CheckCircle, contentDescription = null, tint = SuccessGreen, modifier = Modifier.size(16.dp))
                                        }
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Box(
                                            modifier = Modifier
                                                .clip(RoundedCornerShape(12.dp))
                                                .background(KrishnaYellow)
                                                .clickable { onToggleFollow() }
                                                .padding(horizontal = 8.dp, vertical = 3.dp)
                                        ) {
                                            Text(
                                                text = if (isFollowed) "Following ✓" else "Follow",
                                                fontSize = 10.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = DarkCharcoal,
                                                maxLines = 1
                                            )
                                        }
                                    }

                                    Spacer(modifier = Modifier.height(2.dp))

                                    Text(
                                        text = astrologer.skills.take(3).joinToString(", "),
                                        fontSize = 11.sp,
                                        color = TextMuted,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )

                                    Text(
                                        text = astrologer.languages.joinToString(", "),
                                        fontSize = 11.sp,
                                        color = TextMuted
                                    )

                                    Text(
                                        text = "Exp: ${astrologer.experienceYears} Years",
                                        fontSize = 11.sp,
                                        color = TextMuted
                                    )

                                    Spacer(modifier = Modifier.height(2.dp))

                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text("★★★★★", fontSize = 11.sp, color = GoldAccent)
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text(
                                            text = "₹${(astrologer.pricePerMin * 1.3).toInt()}",
                                            fontSize = 11.sp,
                                            color = TextMuted,
                                            style = androidx.compose.ui.text.TextStyle(textDecoration = androidx.compose.ui.text.style.TextDecoration.LineThrough)
                                        )
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text(
                                            text = "₹${astrologer.pricePerMin.toInt()}/min",
                                            fontSize = 11.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = SuccessGreen
                                        )
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(10.dp))
                            Box(modifier = Modifier.fillMaxWidth().height(1.dp).background(Color.LightGray.copy(alpha = 0.4f)))
                            Spacer(modifier = Modifier.height(8.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                Text("💼 10k+ orders", fontSize = 11.sp, fontWeight = FontWeight.Medium, color = TextDark)
                                Text("|", fontSize = 11.sp, color = Color.Gray)
                                Text("💬 50k+ mins", fontSize = 11.sp, fontWeight = FontWeight.Medium, color = TextDark)
                            }
                        }
                    }

                    // Share Message Preview Box matching screenshot
                    val shareMsg = "Hey! I am using DharmaDarshan to get predictions related to marriage/career. I would recommend you to connect with best Astrologer ${astrologer.name} at DharmaDarshan. https://dharmadarshan.app/astro?slug=${astrologer.name.replace(" ", "")}"

                    Card(
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = LightCreamBackground),
                        modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp)
                    ) {
                        Text(
                            text = shareMsg,
                            fontSize = 12.sp,
                            color = TextDark,
                            lineHeight = 16.sp,
                            modifier = Modifier.padding(10.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedButton(
                            onClick = {
                                val sendIntent: Intent = Intent().apply {
                                    action = Intent.ACTION_SEND
                                    putExtra(Intent.EXTRA_TEXT, shareMsg)
                                    type = "text/plain"
                                }
                                val shareIntent = Intent.createChooser(sendIntent, "Share Astrologer Profile")
                                context.startActivity(shareIntent)
                                showShareDialog = false
                            },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Icon(imageVector = Icons.Default.Share, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Share Apps", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }

                        Button(
                            onClick = {
                                Toast.makeText(context, "Profile link copied to clipboard!", Toast.LENGTH_SHORT).show()
                                showShareDialog = false
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = SacredMaroon),
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Icon(imageVector = Icons.Default.ContentCopy, contentDescription = null, tint = PureWhite, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Copy Link", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = PureWhite)
                        }
                    }
                }
            }
        }
    }

    // See All Reviews Modal Popup Dialog
    if (showAllReviewsDialog) {
        Dialog(
            onDismissRequest = { showAllReviewsDialog = false },
            properties = DialogProperties(usePlatformDefaultWidth = false)
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth(0.94f)
                    .fillMaxHeight(0.85f),
                shape = RoundedCornerShape(20.dp),
                color = PureWhite
            ) {
                Column(modifier = Modifier.fillMaxSize()) {
                    // Dialog Header
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(KrishnaYellow)
                            .padding(horizontal = 16.dp, vertical = 12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Customer Ratings & Reviews",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = DarkCharcoal
                        )
                        IconButton(onClick = { showAllReviewsDialog = false }) {
                            Icon(imageVector = Icons.Default.Close, contentDescription = "Close", tint = DarkCharcoal)
                        }
                    }

                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .verticalScroll(rememberScrollState())
                            .padding(16.dp)
                    ) {
                        // Rating Graph Header
                        Card(
                            shape = RoundedCornerShape(14.dp),
                            colors = CardDefaults.cardColors(containerColor = LightCreamBackground),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier
                                    .padding(16.dp)
                                    .fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                // Score summary
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier.padding(end = 16.dp)
                                ) {
                                    Text(
                                        text = "${astrologer.rating}",
                                        fontSize = 32.sp,
                                        fontWeight = FontWeight.ExtraBold,
                                        color = SacredMaroon
                                    )
                                    Row {
                                        repeat(5) {
                                            Icon(imageVector = Icons.Default.Star, contentDescription = null, tint = GoldAccent, modifier = Modifier.size(14.dp))
                                        }
                                    }
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = "${astrologer.reviewCount} Ratings",
                                        fontSize = 11.sp,
                                        color = TextMuted,
                                        fontWeight = FontWeight.Medium
                                    )
                                }

                                Box(modifier = Modifier.width(1.dp).height(80.dp).background(Color.LightGray))

                                Spacer(modifier = Modifier.width(16.dp))

                                // Rating Bars Graph 5..1
                                Column(modifier = Modifier.weight(1f)) {
                                    RatingBarRow(label = "5★", progress = 0.85f, countText = "85%")
                                    RatingBarRow(label = "4★", progress = 0.10f, countText = "10%")
                                    RatingBarRow(label = "3★", progress = 0.03f, countText = "3%")
                                    RatingBarRow(label = "2★", progress = 0.01f, countText = "1%")
                                    RatingBarRow(label = "1★", progress = 0.01f, countText = "1%")
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = "All Reviews (${allReviews.size})",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextDark,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )

                        allReviews.forEach { rev ->
                            ExpandableReviewItem(review = rev)
                            Spacer(modifier = Modifier.height(10.dp))
                        }
                    }
                }
            }
        }
    }

    // Photo Lightbox Viewer
    if (photoViewerIndex != null) {
        Dialog(
            onDismissRequest = { photoViewerIndex = null },
            properties = DialogProperties(usePlatformDefaultWidth = false)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.95f)),
                contentAlignment = Alignment.Center
            ) {
                val currentIndex = photoViewerIndex ?: 0
                val currentPhoto = samplePhotos[currentIndex]

                Image(
                    painter = painterResource(id = currentPhoto),
                    contentDescription = "Full Photo",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )

                // Top Close Button
                IconButton(
                    onClick = { photoViewerIndex = null },
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(24.dp)
                        .background(Color.Black.copy(alpha = 0.6f), CircleShape)
                ) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = "Close", tint = PureWhite)
                }

                // Left Arrow
                if (currentIndex > 0) {
                    IconButton(
                        onClick = { photoViewerIndex = currentIndex - 1 },
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .padding(16.dp)
                            .background(Color.Black.copy(alpha = 0.6f), CircleShape)
                    ) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Prev", tint = PureWhite)
                    }
                }

                // Right Arrow
                if (currentIndex < samplePhotos.size - 1) {
                    IconButton(
                        onClick = { photoViewerIndex = currentIndex + 1 },
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
private fun ConsultationPriceCard(
    title: String,
    price: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = PureWhite),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = modifier.clickable { onClick() }
    ) {
        Column(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = GoldAccent,
                modifier = Modifier.size(22.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = title,
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold,
                color = TextDark
            )
            Text(
                text = price,
                fontSize = 12.sp,
                fontWeight = FontWeight.ExtraBold,
                color = SuccessGreen
            )
        }
    }
}

@Composable
private fun RatingBarRow(label: String, progress: Float, countText: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 2.dp)
    ) {
        Text(text = label, fontSize = 10.sp, fontWeight = FontWeight.Bold, color = TextDark, modifier = Modifier.width(22.dp))
        Spacer(modifier = Modifier.width(4.dp))
        LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier
                .weight(1f)
                .height(6.dp)
                .clip(RoundedCornerShape(3.dp)),
            color = GoldAccent,
            trackColor = Color.LightGray.copy(alpha = 0.4f)
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(text = countText, fontSize = 10.sp, color = TextMuted, modifier = Modifier.width(28.dp))
    }
}

@Composable
private fun ReviewItem(review: AstrologerReviewData) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(LightCreamBackground)
            .padding(10.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = review.author,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = TextDark
            )
            Text(
                text = "★".repeat(review.rating),
                fontSize = 12.sp,
                color = GoldAccent
            )
        }
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = review.comment,
            fontSize = 12.sp,
            color = TextDark.copy(alpha = 0.85f),
            maxLines = 3,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
private fun ExpandableReviewItem(review: AstrologerReviewData) {
    var isExpanded by remember { mutableStateOf(false) }
    val isLong = review.comment.length > 100

    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = LightCreamBackground),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(text = review.author, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = TextDark)
                    Text(text = review.date, fontSize = 10.sp, color = TextMuted)
                }
                Row {
                    repeat(review.rating) {
                        Icon(imageVector = Icons.Default.Star, contentDescription = null, tint = GoldAccent, modifier = Modifier.size(14.dp))
                    }
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = review.comment,
                fontSize = 12.sp,
                color = TextDark,
                lineHeight = 17.sp,
                maxLines = if (isExpanded) Int.MAX_VALUE else 3,
                overflow = TextOverflow.Ellipsis
            )

            if (isLong) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = if (isExpanded) "Show less" else "Show more",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = SacredMaroon,
                    modifier = Modifier.clickable { isExpanded = !isExpanded }
                )
            }
        }
    }
}
