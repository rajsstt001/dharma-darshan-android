package com.example.ui.screens

import android.widget.Toast
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
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Help
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.LocalOffer
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.PrivacyTip
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.SupportAgent
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
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

// ============================================================================
// 1. SAVED WISHLIST SCREEN
// ============================================================================
data class WishlistItem(
    val id: String,
    val title: String,
    val category: String, // "Pooja", "Astrologer", "Prasad", "Temple"
    val priceText: String,
    val imageRes: Int,
    val rating: Double
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SavedWishlistScreen(
    onBack: () -> Unit,
    onNavigateToItem: (String, String) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var selectedCategoryIndex by remember { mutableIntStateOf(0) }
    val categories = listOf("All Saved", "Poojas", "Astrologers", "Store Products")

    val wishlist = remember {
        mutableStateListOf(
            WishlistItem("1", "Maha Rudrabhishek Pooja", "Poojas", "₹2,100", R.drawable.ic_launcher_background, 4.95),
            WishlistItem("2", "Acharya Kamalendra (Vedic)", "Astrologers", "₹15/min", R.drawable.ic_launcher_background, 4.90),
            WishlistItem("3", "Kashi Vishwanath Special Prasad", "Store Products", "₹501", R.drawable.ic_launcher_background, 4.88),
            WishlistItem("4", "Shani Shanti Mahayajna", "Poojas", "₹3,500", R.drawable.ic_launcher_background, 4.92)
        )
    }

    val filteredList = wishlist.filter { item ->
        when (selectedCategoryIndex) {
            1 -> item.category == "Poojas"
            2 -> item.category == "Astrologers"
            3 -> item.category == "Store Products"
            else -> true
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(LightCreamBackground)
    ) {
        // Category Tabs
        ScrollableTabRow(
            selectedTabIndex = selectedCategoryIndex,
            containerColor = PureWhite,
            contentColor = SacredMaroon,
            edgePadding = 16.dp,
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[selectedCategoryIndex]),
                    color = SaffronOrange,
                    height = 3.dp
                )
            }
        ) {
            categories.forEachIndexed { index, title ->
                Tab(
                    selected = selectedCategoryIndex == index,
                    onClick = { selectedCategoryIndex = index },
                    text = {
                        Text(
                            text = title,
                            fontSize = 13.sp,
                            fontWeight = if (selectedCategoryIndex == index) FontWeight.Bold else FontWeight.Normal,
                            color = if (selectedCategoryIndex == index) SacredMaroon else TextMuted
                        )
                    }
                )
            }
        }

        if (filteredList.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("❤️", fontSize = 48.sp)
                    Spacer(modifier = Modifier.height(12.dp))
                    Text("Your Wishlist is Empty", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = TextDark)
                    Spacer(modifier = Modifier.height(6.dp))
                    Text("Save your favorite Poojas, Astrologers & Sacred Prasadam to access them quickly anytime.", fontSize = 13.sp, color = TextMuted, textAlign = TextAlign.Center)
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(filteredList) { item ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = PureWhite),
                        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(12.dp)
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(64.dp)
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(KrishnaYellow.copy(alpha = 0.3f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(if (item.category == "Poojas") "🪔" else if (item.category == "Astrologers") "🧘" else "🎁", fontSize = 32.sp)
                            }

                            Spacer(modifier = Modifier.width(12.dp))

                            Column(modifier = Modifier.weight(1f)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Box(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(4.dp))
                                            .background(SacredMaroon.copy(alpha = 0.1f))
                                            .padding(horizontal = 6.dp, vertical = 2.dp)
                                    ) {
                                        Text(item.category, fontSize = 10.sp, fontWeight = FontWeight.Bold, color = SacredMaroon)
                                    }
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Icon(imageVector = Icons.Default.Star, contentDescription = null, tint = SaffronOrange, modifier = Modifier.size(12.dp))
                                    Text("${item.rating}", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = DarkCharcoal)
                                }

                                Spacer(modifier = Modifier.height(4.dp))

                                Text(
                                    text = item.title,
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = DarkCharcoal
                                )

                                Spacer(modifier = Modifier.height(4.dp))

                                Text(
                                    text = item.priceText,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = SuccessGreen
                                )
                            }

                            IconButton(
                                onClick = {
                                    wishlist.remove(item)
                                    Toast.makeText(context, "Removed from wishlist", Toast.LENGTH_SHORT).show()
                                }
                            ) {
                                Icon(imageVector = Icons.Default.Delete, contentDescription = "Remove", tint = Color.Gray)
                            }
                        }
                    }
                }
            }
        }
    }
}

// ============================================================================
// 2. REFER AND EARN SCREEN
// ============================================================================
@Composable
fun ReferAndEarnScreen(
    walletBalance: Int,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val referralCode = "DHARMA108"

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(LightCreamBackground)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Banner Card
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = SacredMaroon)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Brush.linearGradient(
                                colors = listOf(SacredMaroon, DarkCharcoal)
                            )
                        )
                        .padding(20.dp)
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                        Text("🎁 REFER & EARN ₹108 WALLET CASH", fontSize = 16.sp, fontWeight = FontWeight.ExtraBold, color = KrishnaYellow, textAlign = TextAlign.Center)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Invite your friends & family to DharmaDarshan. When they register, you both get ₹108 added to your Sacred Wallet!",
                            fontSize = 13.sp,
                            color = PureWhite,
                            textAlign = TextAlign.Center,
                            lineHeight = 18.sp
                        )
                    }
                }
            }
        }

        // Code Copy Card
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = PureWhite),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Your Unique Referral Code", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = TextMuted)
                    Spacer(modifier = Modifier.height(10.dp))

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(LightCreamBackground)
                            .border(1.5.dp, SaffronOrange, RoundedCornerShape(12.dp))
                            .padding(horizontal = 16.dp, vertical = 12.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = referralCode, fontSize = 20.sp, fontWeight = FontWeight.ExtraBold, color = SacredMaroon, letterSpacing = 2.sp)

                            Row(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(KrishnaYellow)
                                    .clickable {
                                        Toast.makeText(context, "Referral Code Copied!", Toast.LENGTH_SHORT).show()
                                    }
                                    .padding(horizontal = 12.dp, vertical = 6.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(imageVector = Icons.Default.ContentCopy, contentDescription = "Copy", tint = DarkCharcoal, modifier = Modifier.size(14.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("COPY", fontSize = 12.sp, fontWeight = FontWeight.ExtraBold, color = DarkCharcoal)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = {
                            Toast.makeText(context, "Sharing DharmaDarshan invite link...", Toast.LENGTH_SHORT).show()
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = SaffronOrange),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(imageVector = Icons.Default.Share, contentDescription = null, tint = PureWhite, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Share via WhatsApp / Apps", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = PureWhite)
                        }
                    }
                }
            }
        }

        // How it works guide
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = PureWhite)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("How it Works", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = DarkCharcoal)
                    Spacer(modifier = Modifier.height(12.dp))

                    ReferStepItem(num = "1", title = "Share Code", desc = "Send your code DHARMA108 to friends & family.")
                    ReferStepItem(num = "2", title = "Friend Joins", desc = "Your friend installs DharmaDarshan and logs in.")
                    ReferStepItem(num = "3", title = "Get Rewards", desc = "₹108 is instantly added to both your wallets!")
                }
            }
        }
    }
}

@Composable
fun ReferStepItem(num: String, title: String, desc: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        verticalAlignment = Alignment.Top
    ) {
        Box(
            modifier = Modifier
                .size(28.dp)
                .clip(CircleShape)
                .background(KrishnaYellow),
            contentAlignment = Alignment.Center
        ) {
            Text(num, fontSize = 13.sp, fontWeight = FontWeight.ExtraBold, color = DarkCharcoal)
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(title, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = TextDark)
            Text(desc, fontSize = 12.sp, color = TextMuted)
        }
    }
}

// ============================================================================
// 3. COUPONS AND OFFERS SCREEN
// ============================================================================
data class CouponItem(
    val code: String,
    val title: String,
    val description: String,
    val discountTag: String,
    val validTill: String
)

@Composable
fun CouponsAndOffersScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    val coupons = listOf(
        CouponItem("DHARMA108", "Flat 20% Off on Maha Pooja Bookings", "Applicable on all temple pooja rituals & prasad orders.", "20% OFF", "Valid till 31 Aug 2026"),
        CouponItem("GURU50", "Flat ₹50 OFF First Astrology Consultation", "Get ₹50 discount on chat or call with top astrologers.", "₹50 OFF", "Valid till 15 Aug 2026"),
        CouponItem("KASHI100", "₹100 Off on Kashi Vishwanath Chadhava", "Valid on special milk & bilvapatra offerings.", "₹100 OFF", "Valid for today"),
        CouponItem("BHAKTI15", "15% Cashback in Sacred Wallet", "Get 15% cashback on all spiritual store purchases.", "15% CASHBACK", "Valid till 31 Dec 2026")
    )

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(LightCreamBackground)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(coupons) { coupon ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = PureWhite),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(SaffronOrange.copy(alpha = 0.15f))
                                .padding(horizontal = 10.dp, vertical = 4.dp)
                        ) {
                            Text(coupon.discountTag, fontSize = 12.sp, fontWeight = FontWeight.ExtraBold, color = SacredMaroon)
                        }

                        Text(coupon.validTill, fontSize = 11.sp, color = TextMuted)
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(coupon.title, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = DarkCharcoal)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(coupon.description, fontSize = 12.sp, color = TextMuted)

                    Spacer(modifier = Modifier.height(14.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .background(LightCreamBackground)
                            .border(1.dp, GoldAccent.copy(alpha = 0.4f), RoundedCornerShape(10.dp))
                            .padding(horizontal = 12.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(imageVector = Icons.Default.LocalOffer, contentDescription = null, tint = SacredMaroon, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(coupon.code, fontSize = 14.sp, fontWeight = FontWeight.ExtraBold, color = SacredMaroon, letterSpacing = 1.sp)
                        }

                        TextButton(
                            onClick = {
                                Toast.makeText(context, "Coupon Code ${coupon.code} Copied & Applied!", Toast.LENGTH_SHORT).show()
                            }
                        ) {
                            Text("APPLY / COPY", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = SaffronOrange)
                        }
                    }
                }
            }
        }
    }
}

// ============================================================================
// 4. HELP AND CUSTOMER SUPPORT SCREEN
// ============================================================================
@Composable
fun HelpAndSupportScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var expandedFaqIndex by remember { mutableIntStateOf(-1) }

    val faqs = listOf(
        "How do I receive Pooja Prasadam at my home?" to "After the priest performs your live name & gotra pooja at the temple, sacred prasadam (vibhooti, dry fruits & threads) is blessed and dispatched via courier within 48 hours to your registered address.",
        "How does Live Consultation with Astrologers work?" to "Select an online astrologer, recharge your wallet, and tap Chat or Call. You will be connected instantly to discuss your birth chart & remedies.",
        "Are all Priests and Temples verified?" to "Yes, 100% of priests on DharmaDarshan are verified scholars from traditional Gurukuls and renowned mathas.",
        "What is the Refund & Cancellation Policy?" to "Pooja bookings can be rescheduled or cancelled up to 24 hours prior to ritual time with 100% wallet refund."
    )

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(LightCreamBackground)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Quick Call & WhatsApp Cards
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Card(
                    modifier = Modifier
                        .weight(1f)
                        .clickable {
                            Toast.makeText(context, "Opening WhatsApp Support...", Toast.LENGTH_SHORT).show()
                        },
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = PureWhite)
                ) {
                    Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("💬", fontSize = 28.sp)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("WhatsApp Us", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = TextDark)
                        Text("24x7 Quick Chat", fontSize = 11.sp, color = SuccessGreen)
                    }
                }

                Card(
                    modifier = Modifier
                        .weight(1f)
                        .clickable {
                            Toast.makeText(context, "Calling Support Helpline 1800-108-DHARMA...", Toast.LENGTH_SHORT).show()
                        },
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = PureWhite)
                ) {
                    Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(imageVector = Icons.Default.Phone, contentDescription = null, tint = SaffronOrange, modifier = Modifier.size(28.dp))
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Toll-Free Call", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = TextDark)
                        Text("1800-108-DHARMA", fontSize = 11.sp, color = TextMuted)
                    }
                }
            }
        }

        // FAQs Section
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = PureWhite)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Frequently Asked Questions", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = DarkCharcoal)
                    Spacer(modifier = Modifier.height(12.dp))

                    faqs.forEachIndexed { index, pair ->
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    expandedFaqIndex = if (expandedFaqIndex == index) -1 else index
                                }
                                .padding(vertical = 10.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = pair.first,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = TextDark,
                                    modifier = Modifier.weight(1f)
                                )
                                Icon(
                                    imageVector = if (expandedFaqIndex == index) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                                    contentDescription = null,
                                    tint = TextMuted
                                )
                            }

                            AnimatedVisibility(visible = expandedFaqIndex == index) {
                                Text(
                                    text = pair.second,
                                    fontSize = 12.sp,
                                    color = TextMuted,
                                    modifier = Modifier.padding(top = 8.dp),
                                    lineHeight = 17.sp
                                )
                            }
                        }
                        if (index < faqs.size - 1) Divider(color = Color.LightGray.copy(alpha = 0.3f))
                    }
                }
            }
        }
    }
}

// ============================================================================
// 5. ABOUT DHARMA DARSHAN SCREEN
// ============================================================================
@Composable
fun AboutDharmaDarshanScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(LightCreamBackground)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = PureWhite)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .clip(CircleShape)
                            .background(KrishnaYellow),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.img_app_icon_1785577715696),
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(CircleShape)
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Text("DharmaDarshan", fontSize = 24.sp, fontWeight = FontWeight.ExtraBold, color = DarkCharcoal)
                    Text("Version 2.4.0 • Built with Sacred Faith", fontSize = 12.sp, color = TextMuted)

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "DharmaDarshan is India's leading spiritual technology ecosystem connecting millions of devotees worldwide to authentic Vedic temples, verified Vedic priests, real-time astrology guidance, and pure prasadam delivery.",
                        fontSize = 13.sp,
                        color = TextDark,
                        textAlign = TextAlign.Center,
                        lineHeight = 19.sp
                    )
                }
            }
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = PureWhite)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Our Core Pillars", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = DarkCharcoal)
                    Spacer(modifier = Modifier.height(12.dp))

                    PillarRow("🙏 Authentic Rituals", "Live temple poojas performed directly in your Name & Gotra.")
                    PillarRow("🧘 Verified Astrologers", "Top Vedic, Nadi & Lal Kitab astrologers with verified credentials.")
                    PillarRow("📦 Pure Prasadam", "Sanctified temple blessings delivered safely to your doorstep.")
                }
            }
        }
    }
}

@Composable
fun PillarRow(title: String, desc: String) {
    Column(modifier = Modifier.padding(vertical = 6.dp)) {
        Text(title, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = SacredMaroon)
        Text(desc, fontSize = 12.sp, color = TextMuted)
    }
}

// ============================================================================
// 6. PRIVACY POLICY SCREEN
// ============================================================================
@Composable
fun PrivacyPolicyScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(LightCreamBackground)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = PureWhite)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("🔒 Privacy Policy", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = DarkCharcoal)
                    Text("Last updated: August 2026", fontSize = 11.sp, color = TextMuted)

                    Spacer(modifier = Modifier.height(14.dp))

                    LegalSection("1. Information We Collect", "We collect personal details such as your Name, Mobile Number, Date of Birth, Time of Birth, and Location strictly to generate accurate astrological Kundli predictions and perform Vedic pooja rituals in your Name and Gotra.")

                    LegalSection("2. Confidentiality & Security", "All 1-on-1 consultations with astrologers and personal birth chart details are encrypted and kept 100% confidential. We never sell or share your personal data with third-party advertisers.")

                    LegalSection("3. Payment Security", "Financial transactions and wallet top-ups are processed using RBI-compliant, bank-grade encrypted payment gateways.")

                    LegalSection("4. Your Rights", "You can request data deletion or update your profile information anytime through app settings or by contacting support.")
                }
            }
        }
    }
}

// ============================================================================
// 7. TERMS AND CONDITIONS SCREEN
// ============================================================================
@Composable
fun TermsAndConditionsScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(LightCreamBackground)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = PureWhite)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("📜 Terms & Conditions", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = DarkCharcoal)
                    Text("Effective Date: August 2026", fontSize = 11.sp, color = TextMuted)

                    Spacer(modifier = Modifier.height(14.dp))

                    LegalSection("1. Acceptance of Terms", "By accessing and using the DharmaDarshan application, you agree to abide by these Terms of Service.")

                    LegalSection("2. Astrological Guidance Disclaimer", "Astrology and spiritual guidance services are based on traditional Vedic calculations. Results and remedies are for spiritual well-being and personal guidance.")

                    LegalSection("3. Pooja & Temple Services", "Rituals are conducted by verified Vedic priests at specified temples. Video updates or live streams are made available based on temple connectivity.")

                    LegalSection("4. Wallet & Refunds", "Wallet balances can be used across all services. Cancellations requested prior to ritual execution are eligible for instant wallet credit.")
                }
            }
        }
    }
}

@Composable
fun LegalSection(heading: String, text: String) {
    Column(modifier = Modifier.padding(bottom = 12.dp)) {
        Text(heading, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = SacredMaroon)
        Spacer(modifier = Modifier.height(4.dp))
        Text(text, fontSize = 12.sp, color = TextDark, lineHeight = 17.sp)
    }
}
