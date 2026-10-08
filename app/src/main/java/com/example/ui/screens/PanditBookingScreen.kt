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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.SelfImprovement
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Pandit
import com.example.ui.components.DharmaGradientButton
import com.example.ui.components.OnlineGlowDot
import com.example.ui.components.RatingBadge
import com.example.ui.theme.CardLightBorder
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PanditBookingScreen(
    pandits: List<Pandit>,
    onBack: () -> Unit,
    onConfirmPanditBooking: (Pandit, String, Double) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedPandit by remember { mutableStateOf<Pandit?>(null) }
    var selectedPackage by remember { mutableStateOf("PREMIUM") } // BASIC, PREMIUM, VIP
    var searchQuery by remember { mutableStateOf("") }
    var selectedTagFilter by remember { mutableStateOf("All") }
    var showFilterSheet by remember { mutableStateOf(false) }

    // Specialization Tag Categories
    val specializationTags = listOf(
        "All",
        "🕉️ Vedic Rituals",
        "🔥 Havan & Homam",
        "💍 Marriage Ceremony",
        "🏠 Griha Pravesh",
        "🧭 Vastu Shanti",
        "👴 Pitru Karma",
        "🌙 Navagraha Pooja",
        "🔱 Rudrabhishek",
        "📖 Bhagwat Katha",
        "📿 Satyanarayan Katha",
        "🪔 Lakshmi Pooja",
        "🐘 Ganesh Pooja",
        "🌺 Durga Pooja",
        "🐒 Hanuman Pooja",
        "🌿 Shiva Pooja",
        "🎉 Festival Poojas",
        "📹 Online Pooja",
        "🛕 Temple Services",
        "✨ Personalized Rituals",
        "📜 Sanskrit & Mantra Chanting",
        "🕉️ General Pooja",
        "🧿 Dosha Nivaran",
        "🌙 Graha Shanti"
    )

    var expFilter by remember { mutableStateOf("All") }
    var ratingFilter by remember { mutableStateOf("All") }
    var selectedLanguages by remember { mutableStateOf(setOf<String>()) }
    var onlineOnly by remember { mutableStateOf(false) }

    val filteredPandits = remember(
        searchQuery, selectedTagFilter, expFilter, ratingFilter, selectedLanguages, onlineOnly, pandits
    ) {
        pandits.filter { p ->
            val matchesQuery = searchQuery.isBlank() ||
                    p.name.contains(searchQuery, ignoreCase = true) ||
                    p.city.contains(searchQuery, ignoreCase = true) ||
                    p.title.contains(searchQuery, ignoreCase = true) ||
                    p.specializations.any { it.contains(searchQuery, ignoreCase = true) }

            val matchesTag = if (selectedTagFilter == "All") true
            else p.specializations.any { spec -> spec.contains(selectedTagFilter.replace(Regex("[^a-zA-Z &]"), "").trim(), ignoreCase = true) || spec == selectedTagFilter }

            val matchesExp = when (expFilter) {
                "5-10 Yrs" -> p.experienceYears in 5..10
                "10-20 Yrs" -> p.experienceYears in 10..20
                "20+ Yrs" -> p.experienceYears >= 20
                else -> true
            }

            val matchesRating = when (ratingFilter) {
                "4.5+" -> p.rating >= 4.5f
                "4.8+" -> p.rating >= 4.8f
                else -> true
            }

            val matchesLang = if (selectedLanguages.isEmpty()) true
            else p.languages.any { selectedLanguages.contains(it) }

            matchesQuery && matchesTag && matchesExp && matchesRating && matchesLang
        }
    }

    if (selectedPandit != null) {
        val pandit = selectedPandit!!
        val packagePrice = when (selectedPackage) {
            "BASIC" -> pandit.priceBasic
            "PREMIUM" -> pandit.pricePremium
            else -> pandit.priceVip
        }
        val scrollState = rememberScrollState()

        Column(
            modifier = modifier
                .fillMaxSize()
                .background(LightCreamBackground)
                .padding(16.dp)
                .verticalScroll(scrollState)
        ) {
            // Pandit Profile Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = PureWhite),
                elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(contentAlignment = Alignment.BottomEnd) {
                            Image(
                                painter = painterResource(id = pandit.imageRes),
                                contentDescription = pandit.name,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .size(76.dp)
                                    .clip(CircleShape)
                                    .border(2.dp, KrishnaYellow, CircleShape)
                            )
                            OnlineGlowDot(size = 10.dp)
                        }
                        Spacer(modifier = Modifier.width(14.dp))
                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(text = pandit.name, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = TextDark)
                                Spacer(modifier = Modifier.width(4.dp))
                                Icon(imageVector = Icons.Default.CheckCircle, contentDescription = "Verified", tint = SuccessGreen, modifier = Modifier.size(16.dp))
                            }
                            Text(text = pandit.title, fontSize = 12.sp, color = GoldAccent, fontWeight = FontWeight.SemiBold)
                            Text(text = "📍 ${pandit.city} • ${pandit.experienceYears}+ Years Exp", fontSize = 11.sp, color = TextMuted)
                            Spacer(modifier = Modifier.height(4.dp))
                            RatingBadge(rating = pandit.rating, reviewCount = pandit.reviewCount)
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))
                    Divider(color = Color.LightGray.copy(alpha = 0.4f))
                    Spacer(modifier = Modifier.height(10.dp))

                    Text(text = "🗣️ Languages: ${pandit.languages.joinToString(", ")}", fontSize = 12.sp, color = TextDark)
                    Spacer(modifier = Modifier.height(6.dp))

                    Text(text = "📿 Specializations:", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = TextDark)
                    Spacer(modifier = Modifier.height(4.dp))
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        items(pandit.specializations) { spec ->
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(KrishnaYellow.copy(alpha = 0.2f))
                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                            ) {
                                Text(text = spec, fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = DarkCharcoal)
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Choose Ceremony Package
            Text(text = "📦 Choose Ceremony Package", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = TextDark)
            Spacer(modifier = Modifier.height(8.dp))

            PackageCard(
                title = "Basic Package",
                price = pandit.priceBasic,
                features = listOf("1 Senior Vedic Pandit", "Standard Samagri List provided", "60 mins Ceremony", "Digital Video Option"),
                isSelected = selectedPackage == "BASIC",
                onSelect = { selectedPackage = "BASIC" }
            )

            Spacer(modifier = Modifier.height(8.dp))

            PackageCard(
                title = "Premium Package (Most Popular)",
                price = pandit.pricePremium,
                features = listOf("2 Experienced Pandits", "Full Authentic Samagri Kit Included", "90 mins Ceremony", "Flowers & Havan Kund Included", "Prasad Pack Delivered"),
                isSelected = selectedPackage == "PREMIUM",
                onSelect = { selectedPackage = "PREMIUM" }
            )

            Spacer(modifier = Modifier.height(8.dp))

            PackageCard(
                title = "VIP Grand Package",
                price = pandit.priceVip,
                features = listOf("3 Acharya Level Priests", "Complete Premium Floral Decoration", "120 mins Grand Hawan & Chanting", "Customized Sankalp Video Recording", "VIP Prasad & Silver Diya Gift"),
                isSelected = selectedPackage == "VIP",
                onSelect = { selectedPackage = "VIP" }
            )

            Spacer(modifier = Modifier.height(20.dp))

            DharmaGradientButton(
                text = "Confirm Booking for ₹${packagePrice.toInt()}",
                onClick = { onConfirmPanditBooking(pandit, selectedPackage, packagePrice) },
                modifier = Modifier.fillMaxWidth().height(48.dp)
            )
        }

    } else {
        // List of Pandits
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(LightCreamBackground)
        ) {
            // Search & Filter Header Section
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(KrishnaYellow.copy(alpha = 0.25f))
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "📿 Verified Vedic Pandits",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = DarkCharcoal,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    OutlinedButton(
                        onClick = { showFilterSheet = true },
                        shape = RoundedCornerShape(20.dp),
                        modifier = Modifier.height(36.dp)
                    ) {
                        Icon(imageVector = Icons.Default.FilterList, contentDescription = "Filter", modifier = Modifier.size(16.dp), tint = DarkCharcoal)
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Filters", fontSize = 12.sp, color = DarkCharcoal, fontWeight = FontWeight.Bold)
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = { Text("Search pandit, pooja or city...", fontSize = 12.sp, color = TextMuted, maxLines = 1, overflow = TextOverflow.Ellipsis) },
                    leadingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = null, tint = SacredMaroon) },
                    trailingIcon = {
                        if (searchQuery.isNotEmpty()) {
                            IconButton(onClick = { searchQuery = "" }) {
                                Icon(imageVector = Icons.Default.Close, contentDescription = "Clear", tint = TextMuted)
                            }
                        }
                    },
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = PureWhite,
                        unfocusedContainerColor = PureWhite,
                        focusedBorderColor = SacredMaroon,
                        unfocusedBorderColor = PureWhite,
                        focusedTextColor = TextDark,
                        unfocusedTextColor = TextDark
                    ),
                    modifier = Modifier.fillMaxWidth().height(50.dp)
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Specialization Chips Bar in Single Line
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(specializationTags) { tag ->
                        val isSelected = selectedTagFilter == tag
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(16.dp))
                                .background(if (isSelected) DarkCharcoal else PureWhite)
                                .border(1.dp, if (isSelected) DarkCharcoal else PureWhite, RoundedCornerShape(16.dp))
                                .clickable { selectedTagFilter = tag }
                                .padding(horizontal = 14.dp, vertical = 6.dp)
                        ) {
                            Text(
                                text = tag,
                                fontSize = 12.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                color = if (isSelected) DivineGold else DarkCharcoal,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                }
            }

            // Pandits List
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp, vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(filteredPandits) { pandit ->
                    PanditCardItem(
                        pandit = pandit,
                        onCardClick = { selectedPandit = pandit },
                        onBookClick = { selectedPandit = pandit },
                        onCallClick = { selectedPandit = pandit }
                    )
                }
            }
        }
    }

    // Modal Filter Sheet
    if (showFilterSheet) {
        ModalBottomSheet(
            onDismissRequest = { showFilterSheet = false },
            sheetState = rememberModalBottomSheetState(),
            containerColor = LightCreamBackground
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "⚙️ Filter Vedic Pandits", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = TextDark)
                    IconButton(onClick = { showFilterSheet = false }) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "Close", tint = TextMuted)
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text("Experience Level", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = TextDark)
                Spacer(modifier = Modifier.height(4.dp))
                FilterChipRow(
                    options = listOf("All", "5-10 Yrs", "10-20 Yrs", "20+ Yrs"),
                    selected = expFilter,
                    onSelect = { expFilter = it }
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text("Minimum Rating", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = TextDark)
                Spacer(modifier = Modifier.height(4.dp))
                FilterChipRow(
                    options = listOf("All", "4.5+", "4.8+"),
                    selected = ratingFilter,
                    onSelect = { ratingFilter = it }
                )

                Spacer(modifier = Modifier.height(20.dp))

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    OutlinedButton(
                        onClick = {
                            expFilter = "All"
                            ratingFilter = "All"
                            selectedLanguages = emptySet()
                            selectedTagFilter = "All"
                            searchQuery = ""
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Reset All", color = TextDark)
                    }

                    DharmaGradientButton(
                        text = "Apply Filters",
                        onClick = { showFilterSheet = false },
                        modifier = Modifier.weight(1.5f)
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}

@Composable
private fun FilterChipRow(options: List<String>, selected: String, onSelect: (String) -> Unit) {
    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        items(options) { opt ->
            val isSel = selected == opt
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .background(if (isSel) KrishnaYellow else PureWhite)
                    .border(1.dp, if (isSel) KrishnaYellow else Color.LightGray, RoundedCornerShape(16.dp))
                    .clickable { onSelect(opt) }
                    .padding(horizontal = 12.dp, vertical = 6.dp)
            ) {
                Text(text = opt, fontSize = 12.sp, fontWeight = if (isSel) FontWeight.Bold else FontWeight.Normal, color = DarkCharcoal)
            }
        }
    }
}

@Composable
private fun PanditCardItem(
    pandit: Pandit,
    onCardClick: () -> Unit,
    onBookClick: () -> Unit,
    onCallClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onCardClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = PureWhite),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Avatar with Online Dot
                Box(contentAlignment = Alignment.BottomEnd, modifier = Modifier.clickable { onCardClick() }) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(62.dp)
                            .clip(CircleShape)
                            .background(KrishnaYellow.copy(alpha = 0.25f))
                            .border(2.dp, KrishnaYellow, CircleShape)
                    ) {
                        Image(
                            painter = painterResource(id = pandit.imageRes),
                            contentDescription = pandit.name,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize().clip(CircleShape)
                        )
                    }
                    OnlineGlowDot(size = 10.dp)
                }

                Spacer(modifier = Modifier.width(12.dp))

                // Details Column
                Column(modifier = Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = pandit.name, fontSize = 15.sp, fontWeight = FontWeight.Bold, color = TextDark, maxLines = 1)
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(imageVector = Icons.Default.CheckCircle, contentDescription = "Verified", tint = SuccessGreen, modifier = Modifier.size(15.dp))
                    }

                    Text(text = pandit.title, fontSize = 11.sp, color = GoldAccent, fontWeight = FontWeight.SemiBold, maxLines = 1)

                    Spacer(modifier = Modifier.height(2.dp))

                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(imageVector = Icons.Default.Star, contentDescription = null, tint = GoldAccent, modifier = Modifier.size(13.dp))
                            Spacer(modifier = Modifier.width(2.dp))
                            Text(text = "${pandit.rating}", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = TextDark)
                            Text(text = " (${pandit.reviewCount})", fontSize = 10.sp, color = TextMuted)
                        }

                        Text(text = "•", fontSize = 10.sp, color = TextMuted)

                        Text(text = "🎓 ${pandit.experienceYears}+ Yrs Exp", fontSize = 11.sp, color = TextMuted)
                    }

                    Spacer(modifier = Modifier.height(2.dp))
                    Text(text = "📍 ${pandit.city} • 🗣️ ${pandit.languages.joinToString(", ")}", fontSize = 10.sp, color = TextMuted, maxLines = 1)
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Specialization tags chips
            LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                items(pandit.specializations) { spec ->
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(LightCreamBackground)
                            .padding(horizontal = 8.dp, vertical = 3.dp)
                    ) {
                        Text(text = spec, fontSize = 10.sp, fontWeight = FontWeight.Medium, color = DarkCharcoal)
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Highlighted Price Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .background(KrishnaYellow.copy(alpha = 0.15f))
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Pooja Package Starting At:", fontSize = 12.sp, color = TextMuted, fontWeight = FontWeight.Medium)
                Text(text = "₹${pandit.priceBasic.toInt()}", fontSize = 15.sp, fontWeight = FontWeight.ExtraBold, color = SuccessGreen)
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Action Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                DharmaGradientButton(
                    text = "Book Pandit",
                    onClick = onBookClick,
                    icon = Icons.Default.SelfImprovement,
                    modifier = Modifier.weight(1f)
                )

                OutlinedButton(
                    onClick = onCallClick,
                    shape = RoundedCornerShape(24.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = DarkCharcoal),
                    border = androidx.compose.foundation.BorderStroke(1.dp, GoldAccent),
                    modifier = Modifier.height(42.dp)
                ) {
                    Icon(imageVector = Icons.Default.Call, contentDescription = "Consult", modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Consult", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
private fun PackageCard(
    title: String,
    price: Double,
    features: List<String>,
    isSelected: Boolean,
    onSelect: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .border(2.dp, if (isSelected) GoldAccent else CardLightBorder, RoundedCornerShape(14.dp))
            .clickable { onSelect() },
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = if (isSelected) KrishnaYellow.copy(alpha = 0.12f) else PureWhite)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = title, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = TextDark)
                Text(text = "₹${price.toInt()}", fontSize = 16.sp, fontWeight = FontWeight.ExtraBold, color = SuccessGreen)
            }
            Spacer(modifier = Modifier.height(6.dp))
            features.forEach { f ->
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(vertical = 2.dp)) {
                    Icon(imageVector = Icons.Default.Check, contentDescription = null, tint = GoldAccent, modifier = Modifier.size(14.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(text = f, fontSize = 12.sp, color = TextDark)
                }
            }
        }
    }
}
