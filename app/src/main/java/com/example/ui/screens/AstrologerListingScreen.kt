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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Search
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Astrologer
import com.example.ui.components.DharmaGradientButton
import com.example.ui.components.OnlineGlowDot
import androidx.compose.ui.text.style.TextOverflow
import com.example.ui.theme.DarkCharcoal
import com.example.ui.theme.DivineGold
import com.example.ui.theme.GoldAccent
import com.example.ui.theme.KrishnaYellow
import com.example.ui.theme.LightCreamBackground
import com.example.ui.theme.PureWhite
import com.example.ui.theme.SacredMaroon
import com.example.ui.theme.SuccessGreen
import com.example.ui.theme.TextDark
import com.example.ui.theme.TextMuted

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AstrologerListingScreen(
    astrologers: List<Astrologer>,
    onBack: () -> Unit,
    onSelectAstrologer: (Astrologer) -> Unit,
    onStartConsultation: (Astrologer, String) -> Unit,
    modifier: Modifier = Modifier
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedSortOption by remember { mutableStateOf("Popular") }
    var showFilterSheet by remember { mutableStateOf(false) }

    // Filter states
    var expFilter by remember { mutableStateOf("All") } // "All", "1-5 Yrs", "5-10 Yrs", "10+ Yrs"
    var ratingFilter by remember { mutableStateOf("All") } // "All", "4.0+", "4.5+"
    var selectedLanguages by remember { mutableStateOf(setOf<String>()) }
    var priceFilter by remember { mutableStateOf("All") } // "All", "Under ₹15", "₹15 - ₹30", "₹30+"
    var selectedExpertises by remember { mutableStateOf(setOf<String>()) }
    var genderFilter by remember { mutableStateOf("All") } // "All", "Male", "Female"
    var onlineOnly by remember { mutableStateOf(false) }
    var verifiedOnly by remember { mutableStateOf(false) }

    var selectedCategoryTab by remember { mutableStateOf("All") }

    val categoryTabs = listOf(
        "All",
        "❤️ Love",
        "💍 Marriage",
        "💼 Career",
        "📚 Education",
        "💰 Finance",
        "🏢 Business",
        "🩺 Health",
        "👶 Child",
        "👨‍👩‍👧 Family",
        "🌍 Abroad",
        "🔮 Horoscope",
        "🌟 Kundli",
        "🪐 Astrology",
        "🧿 Dosha",
        "💎 Gemstones",
        "📿 Rudraksha",
        "🏠 Vastu",
        "🕉️ Spiritual",
        "🔢 Numerology",
        "🎴 Tarot"
    )

    val sortOptions = listOf("Popular", "Rating", "Price: Low to High", "Price: High to Low", "Experience")

    val filteredAstrologers = remember(
        searchQuery, selectedCategoryTab, selectedSortOption, expFilter, ratingFilter, selectedLanguages,
        priceFilter, selectedExpertises, genderFilter, onlineOnly, verifiedOnly, astrologers
    ) {
        var list = astrologers.filter { ast ->
            val matchesQuery = ast.name.contains(searchQuery, ignoreCase = true) ||
                    ast.languages.any { it.contains(searchQuery, ignoreCase = true) } ||
                    ast.skills.any { it.contains(searchQuery, ignoreCase = true) }

            val matchesCategory = if (selectedCategoryTab == "All") true else {
                val cleanTab = selectedCategoryTab.replace(Regex("[^a-zA-Z &]"), "").trim()
                ast.skills.any { skill -> skill.contains(cleanTab, ignoreCase = true) } ||
                        ast.bio.contains(cleanTab, ignoreCase = true)
            }

            val matchesExp = when (expFilter) {
                "1-5 Yrs" -> ast.experienceYears in 1..5
                "5-10 Yrs" -> ast.experienceYears in 5..10
                "10+ Yrs" -> ast.experienceYears >= 10
                else -> true
            }

            val matchesRating = when (ratingFilter) {
                "4.0+" -> ast.rating >= 4.0f
                "4.5+" -> ast.rating >= 4.5f
                else -> true
            }

            val matchesLang = if (selectedLanguages.isEmpty()) true
            else ast.languages.any { lang -> selectedLanguages.contains(lang) }

            val matchesPrice = when (priceFilter) {
                "Under ₹15" -> ast.pricePerMin < 15.0
                "₹15 - ₹30" -> ast.pricePerMin in 15.0..30.0
                "₹30+" -> ast.pricePerMin > 30.0
                else -> true
            }

            val matchesExpertise = if (selectedExpertises.isEmpty()) true
            else ast.skills.any { skill -> selectedExpertises.contains(skill) }

            val matchesGender = when (genderFilter) {
                "Male" -> ast.gender.equals("Male", ignoreCase = true)
                "Female" -> ast.gender.equals("Female", ignoreCase = true)
                else -> true
            }

            val matchesOnline = if (onlineOnly) ast.isOnline else true
            val matchesVerified = if (verifiedOnly) ast.isVerified else true

            matchesQuery && matchesCategory && matchesExp && matchesRating && matchesLang &&
                    matchesPrice && matchesExpertise && matchesGender &&
                    matchesOnline && matchesVerified
        }

        when (selectedSortOption) {
            "Rating" -> list = list.sortedByDescending { it.rating }
            "Price: Low to High" -> list = list.sortedBy { it.pricePerMin }
            "Price: High to Low" -> list = list.sortedByDescending { it.pricePerMin }
            "Experience" -> list = list.sortedByDescending { it.experienceYears }
            else -> list = list.sortedByDescending { it.reviewCount }
        }

        list
    }

    val activeFilterCount = (if (expFilter != "All") 1 else 0) +
            (if (ratingFilter != "All") 1 else 0) +
            selectedLanguages.size +
            (if (priceFilter != "All") 1 else 0) +
            selectedExpertises.size +
            (if (genderFilter != "All") 1 else 0) +
            (if (onlineOnly) 1 else 0) +
            (if (verifiedOnly) 1 else 0)

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(LightCreamBackground)
    ) {
        // Search & Filter Header Box
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(KrishnaYellow.copy(alpha = 0.25f))
                .padding(horizontal = 14.dp, vertical = 10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = { Text("Search astrologer, language, skill...", fontSize = 12.sp, color = TextMuted, maxLines = 1, overflow = TextOverflow.Ellipsis) },
                    leadingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = null, tint = SacredMaroon) },
                    singleLine = true,
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = SacredMaroon,
                        unfocusedBorderColor = PureWhite,
                        focusedContainerColor = PureWhite,
                        unfocusedContainerColor = PureWhite,
                        focusedTextColor = TextDark,
                        unfocusedTextColor = TextDark
                    )
                )

                // Filter Button
                Surface(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .clickable { showFilterSheet = true },
                    color = DarkCharcoal
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(imageVector = Icons.Default.FilterList, contentDescription = "Filters", tint = DivineGold, modifier = Modifier.size(18.dp))
                        if (activeFilterCount > 0) {
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "($activeFilterCount)",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = DivineGold
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Category Tabs Horizontal Scroll
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                items(categoryTabs) { tab ->
                    val isSelected = selectedCategoryTab == tab
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .background(if (isSelected) DarkCharcoal else PureWhite)
                            .border(
                                width = 1.dp,
                                color = if (isSelected) DarkCharcoal else PureWhite,
                                shape = RoundedCornerShape(20.dp)
                            )
                            .clickable { selectedCategoryTab = tab }
                            .padding(horizontal = 14.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = tab,
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

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = androidx.compose.foundation.layout.PaddingValues(14.dp)
        ) {
            item {
                // Sort and Quick Online Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .clickable { onlineOnly = !onlineOnly }
                            .padding(4.dp)
                    ) {
                        OnlineGlowDot(size = 8.dp)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = if (onlineOnly) "Online Only (Active)" else "Online Now Only",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (onlineOnly) SuccessGreen else TextDark
                        )
                    }

                    Text(
                        text = "Sort: $selectedSortOption ▾",
                        fontSize = 12.sp,
                        color = GoldAccent,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.clickable {
                            val nextIdx = (sortOptions.indexOf(selectedSortOption) + 1) % sortOptions.size
                            selectedSortOption = sortOptions[nextIdx]
                        }
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))
            }

            if (filteredAstrologers.isEmpty()) {
                item {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 32.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = PureWhite)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(32.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text("🔮", fontSize = 48.sp)
                            Spacer(modifier = Modifier.height(8.dp))
                            Text("No Astrologers Match Your Filter", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = TextDark)
                            Spacer(modifier = Modifier.height(12.dp))
                            Button(
                                onClick = {
                                    expFilter = "All"
                                    ratingFilter = "All"
                                    selectedLanguages = emptySet()
                                    priceFilter = "All"
                                    selectedExpertises = emptySet()
                                    genderFilter = "All"
                                    onlineOnly = false
                                    verifiedOnly = false
                                    searchQuery = ""
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = KrishnaYellow)
                            ) {
                                Text("Reset All Filters", color = DarkCharcoal, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            } else {
                items(filteredAstrologers) { astrologer ->
                    AstrologerCardItem(
                        astrologer = astrologer,
                        onCardClick = { onSelectAstrologer(astrologer) },
                        onChatClick = { onStartConsultation(astrologer, "Chat") },
                        onCallClick = { onStartConsultation(astrologer, "Call") }
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }
    }

    // Comprehensive Filter Bottom Sheet Panel
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
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "⚙️ Filter Astrologers",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextDark
                    )
                    IconButton(onClick = { showFilterSheet = false }) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "Close", tint = TextMuted)
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f, fill = false)
                ) {
                    // 1. Toggles: Online & Verified
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            OnlineGlowDot(size = 8.dp)
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Online Now Only", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = TextDark)
                        }
                        Switch(
                            checked = onlineOnly,
                            onCheckedChange = { onlineOnly = it },
                            colors = SwitchDefaults.colors(checkedThumbColor = SuccessGreen)
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(imageVector = Icons.Default.CheckCircle, contentDescription = null, tint = SuccessGreen, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Verified Astrologers Only", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = TextDark)
                        }
                        Switch(
                            checked = verifiedOnly,
                            onCheckedChange = { verifiedOnly = it },
                            colors = SwitchDefaults.colors(checkedThumbColor = SuccessGreen)
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))
                    Divider(color = Color.LightGray.copy(alpha = 0.5f))
                    Spacer(modifier = Modifier.height(12.dp))

                    // 2. Experience Filter
                    FilterSectionTitle("Experience Years")
                    FilterChipRow(
                        options = listOf("All", "1-5 Yrs", "5-10 Yrs", "10+ Yrs"),
                        selected = expFilter,
                        onSelect = { expFilter = it }
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // 3. Minimum Rating Filter
                    FilterSectionTitle("Minimum Rating")
                    FilterChipRow(
                        options = listOf("All", "4.0+", "4.5+"),
                        selected = ratingFilter,
                        onSelect = { ratingFilter = it }
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // 4. Price Filter
                    FilterSectionTitle("Price Per Minute")
                    FilterChipRow(
                        options = listOf("All", "Under ₹15", "₹15 - ₹30", "₹30+"),
                        selected = priceFilter,
                        onSelect = { priceFilter = it }
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // 5. Gender Filter
                    FilterSectionTitle("Gender")
                    FilterChipRow(
                        options = listOf("All", "Male", "Female"),
                        selected = genderFilter,
                        onSelect = { genderFilter = it }
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // 6. Languages Filter (Multi-select)
                    FilterSectionTitle("Languages")
                    MultiSelectChipRow(
                        options = listOf("Hindi", "English", "Sanskrit", "Tamil", "Telugu", "Marathi", "Gujarati", "Bengali"),
                        selectedSet = selectedLanguages,
                        onToggle = { lang ->
                            selectedLanguages = if (selectedLanguages.contains(lang)) selectedLanguages - lang else selectedLanguages + lang
                        }
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // 7. Expertise Filter (Multi-select)
                    FilterSectionTitle("Expertise & Skills")
                    MultiSelectChipRow(
                        options = listOf("Vedic", "Kundli", "Vastu", "Prashna", "Tarot", "Palmistry", "Numerology", "KP System", "Lal Kitab"),
                        selectedSet = selectedExpertises,
                        onToggle = { skill ->
                            selectedExpertises = if (selectedExpertises.contains(skill)) selectedExpertises - skill else selectedExpertises + skill
                        }
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Bottom Buttons: Reset All, Apply
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    OutlinedButton(
                        onClick = {
                            expFilter = "All"
                            ratingFilter = "All"
                            selectedLanguages = emptySet()
                            priceFilter = "All"
                            selectedExpertises = emptySet()
                            genderFilter = "All"
                            onlineOnly = false
                            verifiedOnly = false
                        },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Reset All", color = TextDark, fontSize = 13.sp)
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
private fun FilterSectionTitle(title: String) {
    Text(
        text = title,
        fontSize = 13.sp,
        fontWeight = FontWeight.Bold,
        color = TextDark,
        modifier = Modifier.padding(bottom = 6.dp)
    )
}

@Composable
private fun FilterChipRow(
    options: List<String>,
    selected: String,
    onSelect: (String) -> Unit
) {
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
                Text(
                    text = opt,
                    fontSize = 12.sp,
                    fontWeight = if (isSel) FontWeight.Bold else FontWeight.Normal,
                    color = DarkCharcoal
                )
            }
        }
    }
}

@Composable
private fun MultiSelectChipRow(
    options: List<String>,
    selectedSet: Set<String>,
    onToggle: (String) -> Unit
) {
    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        items(options) { opt ->
            val isSel = selectedSet.contains(opt)
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .background(if (isSel) KrishnaYellow else PureWhite)
                    .border(1.dp, if (isSel) KrishnaYellow else Color.LightGray, RoundedCornerShape(16.dp))
                    .clickable { onToggle(opt) }
                    .padding(horizontal = 12.dp, vertical = 6.dp)
            ) {
                Text(
                    text = opt,
                    fontSize = 12.sp,
                    fontWeight = if (isSel) FontWeight.Bold else FontWeight.Normal,
                    color = DarkCharcoal
                )
            }
        }
    }
}

@Composable
fun AstrologerCardItem(
    astrologer: Astrologer,
    onCardClick: () -> Unit,
    onChatClick: () -> Unit,
    onCallClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onCardClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = PureWhite),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top
            ) {
                // Profile Avatar with Online Dot
                Box(
                    contentAlignment = Alignment.BottomEnd,
                    modifier = Modifier.clickable { onCardClick() }
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(68.dp)
                            .clip(CircleShape)
                            .background(KrishnaYellow.copy(alpha = 0.25f))
                            .border(2.dp, KrishnaYellow, CircleShape)
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
                                fontSize = 32.sp
                            )
                        }
                    }

                    if (astrologer.isOnline) {
                        OnlineGlowDot(size = 10.dp)
                    }
                }

                Spacer(modifier = Modifier.width(12.dp))

                // Details Column
                Column(modifier = Modifier.weight(1f)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.clickable { onCardClick() }
                    ) {
                        Text(
                            text = astrologer.name,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextDark,
                            maxLines = 1
                        )
                        if (astrologer.isVerified) {
                            Spacer(modifier = Modifier.width(4.dp))
                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = "Verified",
                                tint = SuccessGreen,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = astrologer.skills.joinToString(", "),
                        fontSize = 12.sp,
                        color = TextMuted,
                        maxLines = 1
                    )

                    Text(
                        text = "🗣️ ${astrologer.languages.joinToString(", ")}",
                        fontSize = 11.sp,
                        color = TextMuted,
                        maxLines = 1
                    )

                    Spacer(modifier = Modifier.height(3.dp))
                    Text(
                        text = "⏳ Exp: ${astrologer.experienceYears} Years",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = DarkCharcoal
                    )
                }

                // Rating & Price Column
                Column(horizontalAlignment = Alignment.End) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(imageVector = Icons.Default.Star, contentDescription = null, tint = GoldAccent, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(2.dp))
                        Text(text = "${astrologer.rating}", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = TextDark)
                    }
                    Text(text = "(${astrologer.reviewCount})", fontSize = 10.sp, color = TextMuted)

                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "₹${astrologer.pricePerMin.toInt()}/min",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = SuccessGreen
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))
            Divider(color = Color.LightGray.copy(alpha = 0.4f))
            Spacer(modifier = Modifier.height(10.dp))

            // Action Buttons: Gradient Chat & Call
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                DharmaGradientButton(
                    text = "Chat",
                    onClick = onChatClick,
                    icon = Icons.Default.Chat,
                    modifier = Modifier.weight(1f)
                )

                DharmaGradientButton(
                    text = "Call",
                    onClick = onCallClick,
                    icon = Icons.Default.Call,
                    gradient = Brush.horizontalGradient(
                        colors = listOf(Color(0xFF2E7D32), Color(0xFF4CAF50))
                    ),
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}
