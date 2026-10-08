package com.example.ui.screens

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Place
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.model.PoojaService
import com.example.data.model.UpcomingPooja
import com.example.ui.theme.CardLightBorder
import com.example.ui.theme.DarkCharcoal
import com.example.ui.theme.DivineGold
import com.example.ui.theme.KrishnaYellow
import com.example.ui.theme.LightCreamBackground
import com.example.ui.theme.PureWhite
import com.example.ui.theme.SacredMaroon
import com.example.ui.theme.SaffronOrange
import com.example.ui.theme.TextDark
import com.example.ui.theme.TextMuted

enum class PoojaListFilter(val label: String, val emoji: String) {
    ALL("All Poojas", "🕉️"),
    UPCOMING("Trending Scheduled", "🔥"),
    RECOMMENDED("Recommended", "✨")
}

@Composable
fun PoojaListScreen(
    upcomingPoojas: List<UpcomingPooja>,
    recommendedPoojas: List<PoojaService>,
    initialFilter: PoojaListFilter = PoojaListFilter.ALL,
    onBack: () -> Unit,
    onSelectUpcomingPooja: (UpcomingPooja) -> Unit,
    onSelectRecommendedPooja: (PoojaService) -> Unit,
    onOpenBookingForm: (PoojaDetailData) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedFilter by remember { mutableStateOf(initialFilter) }
    var searchQuery by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(LightCreamBackground)
    ) {
        // Search Bar & Filter Chips Header Section
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(KrishnaYellow.copy(alpha = 0.25f))
                .padding(12.dp)
        ) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text("Search poojas, temples, deities...", fontSize = 12.sp, color = TextMuted, maxLines = 1, overflow = TextOverflow.Ellipsis) },
                leadingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = null, tint = SacredMaroon) },
                singleLine = true,
                shape = RoundedCornerShape(14.dp),
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

            // Filter Tabs Row in Single Line
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(PoojaListFilter.entries.toTypedArray()) { filter ->
                    val isSelected = selectedFilter == filter
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(14.dp))
                            .background(if (isSelected) DarkCharcoal else PureWhite)
                            .border(1.dp, if (isSelected) DarkCharcoal else PureWhite, RoundedCornerShape(14.dp))
                            .clickable { selectedFilter = filter }
                            .padding(horizontal = 14.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = "${filter.emoji} ${filter.label}",
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

        // Content List
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            // Upcoming Poojas Section (If ALL or UPCOMING selected)
            if (selectedFilter == PoojaListFilter.ALL || selectedFilter == PoojaListFilter.UPCOMING) {
                item {
                    Text(
                        text = "🔥 Trending Scheduled Poojas",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = SacredMaroon,
                        modifier = Modifier.padding(bottom = 6.dp)
                    )
                }

                val filteredUpcoming = upcomingPoojas.filter {
                    searchQuery.isEmpty() || it.name.contains(searchQuery, ignoreCase = true) || it.templeName.contains(searchQuery, ignoreCase = true)
                }

                items(filteredUpcoming) { pooja ->
                    UpcomingPoojaListItem(
                        pooja = pooja,
                        onCardClick = { onSelectUpcomingPooja(pooja) },
                        onBookClick = { onOpenBookingForm(pooja.toDetailData()) }
                    )
                }
            }

            // Recommended Poojas Section (If ALL or RECOMMENDED selected)
            if (selectedFilter == PoojaListFilter.ALL || selectedFilter == PoojaListFilter.RECOMMENDED) {
                item {
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "✨ Recommended Custom Date Poojas",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = SacredMaroon,
                        modifier = Modifier.padding(bottom = 6.dp)
                    )
                }

                val filteredRecommended = recommendedPoojas.filter {
                    searchQuery.isEmpty() || it.name.contains(searchQuery, ignoreCase = true) || it.templeName.contains(searchQuery, ignoreCase = true)
                }

                items(filteredRecommended) { pooja ->
                    RecommendedPoojaListItem(
                        pooja = pooja,
                        onCardClick = { onSelectRecommendedPooja(pooja) },
                        onBookClick = { onOpenBookingForm(pooja.toDetailData()) }
                    )
                }
            }
        }
    }
}

@Composable
fun UpcomingPoojaListItem(
    pooja: UpcomingPooja,
    onCardClick: () -> Unit,
    onBookClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onCardClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = PureWhite),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
            ) {
                val img = if (pooja.bannerRes != 0) pooja.bannerRes else R.drawable.img_hero_divine_1785577730862
                Image(
                    painter = painterResource(id = img),
                    contentDescription = pooja.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.8f))
                            )
                        )
                )

                // Countdown Badge
                Box(
                    modifier = Modifier
                        .padding(10.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(SaffronOrange)
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                        .align(Alignment.TopStart)
                ) {
                    Text("⏳ Upcoming Festival Pooja", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = PureWhite)
                }

                Row(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(imageVector = Icons.Outlined.Place, contentDescription = null, tint = DivineGold, modifier = Modifier.size(14.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = pooja.location, fontSize = 11.sp, color = PureWhite)
                }
            }

            Column(modifier = Modifier.padding(14.dp)) {
                Text(
                    text = pooja.name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = SacredMaroon
                )

                Text(
                    text = "🛕 ${pooja.templeName}",
                    fontSize = 12.sp,
                    color = TextMuted
                )

                Spacer(modifier = Modifier.height(6.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "📅 ${pooja.dateText} • ${pooja.timeSlot}",
                        fontSize = 11.sp,
                        color = SaffronOrange,
                        fontWeight = FontWeight.SemiBold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f)
                    )

                    Spacer(modifier = Modifier.width(6.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(imageVector = Icons.Default.Star, contentDescription = null, tint = DivineGold, modifier = Modifier.size(14.dp))
                        Text(text = " ${pooja.rating} (${pooja.reviewsCount})", fontSize = 11.sp, color = TextDark, maxLines = 1)
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(text = "Starting from", fontSize = 9.sp, color = TextMuted)
                        Text(text = "₹${pooja.startingPrice.toInt()}", fontSize = 17.sp, fontWeight = FontWeight.ExtraBold, color = SacredMaroon)
                    }

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .height(36.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .border(1.dp, SacredMaroon, RoundedCornerShape(10.dp))
                                .clickable { onCardClick() }
                                .padding(horizontal = 12.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("View Details", fontSize = 11.sp, color = SacredMaroon, fontWeight = FontWeight.Bold)
                        }

                        Box(
                            modifier = Modifier
                                .height(36.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(Brush.horizontalGradient(listOf(Color(0xFF2E7D32), Color(0xFF4CAF50))))
                                .clickable { onBookClick() }
                                .padding(horizontal = 14.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("Participate", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = PureWhite)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun RecommendedPoojaListItem(
    pooja: PoojaService,
    onCardClick: () -> Unit,
    onBookClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onCardClick() }
            .border(1.dp, CardLightBorder, RoundedCornerShape(14.dp)),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = PureWhite)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = pooja.name,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = SacredMaroon
                    )
                    Text(
                        text = "🛕 ${pooja.templeName}",
                        fontSize = 11.sp,
                        color = TextMuted
                    )
                }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(SaffronOrange.copy(alpha = 0.15f))
                        .padding(horizontal = 8.dp, vertical = 3.dp)
                ) {
                    Text("⏱️ ${pooja.durationMinutes} mins", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = SacredMaroon)
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = pooja.description,
                fontSize = 12.sp,
                color = TextDark,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "₹${pooja.price.toInt()}",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = SacredMaroon
                )

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .height(36.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .border(1.dp, SacredMaroon, RoundedCornerShape(10.dp))
                            .clickable { onCardClick() }
                            .padding(horizontal = 12.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("View Details", fontSize = 11.sp, color = SacredMaroon, fontWeight = FontWeight.Bold)
                    }

                    Box(
                        modifier = Modifier
                            .height(36.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(Brush.horizontalGradient(listOf(Color(0xFF2E7D32), Color(0xFF4CAF50))))
                            .clickable { onBookClick() }
                            .padding(horizontal = 14.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Book Slot", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = PureWhite)
                    }
                }
            }
        }
    }
}
