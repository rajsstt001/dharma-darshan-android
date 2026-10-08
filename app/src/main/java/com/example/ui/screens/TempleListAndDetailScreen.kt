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
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.text.style.TextOverflow
import com.example.ui.theme.DarkCharcoal
import com.example.ui.theme.KrishnaYellow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.FlightTakeoff
import androidx.compose.material.icons.filled.LiveTv
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Train
import androidx.compose.material.icons.filled.VolunteerActivism
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.PoojaService
import com.example.data.model.Temple
import com.example.ui.components.DivineSearchBar
import com.example.ui.components.RatingBadge
import com.example.ui.theme.CardLightBorder
import com.example.ui.theme.DivineGold
import com.example.ui.theme.LightCreamBackground
import com.example.ui.theme.PureWhite
import com.example.ui.theme.SacredMaroon
import com.example.ui.theme.SaffronOrange
import com.example.ui.theme.TextDark
import com.example.ui.theme.TextMuted

@Composable
fun TempleListAndDetailScreen(
    temples: List<Temple>,
    poojas: List<PoojaService>,
    onBookPooja: (PoojaService) -> Unit,
    onLiveDarshan: (Temple) -> Unit,
    onOrderPrasad: () -> Unit,
    onDonate: () -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedTemple by remember { mutableStateOf<Temple?>(null) }
    var searchQuery by remember { mutableStateOf("") }
    var filterGod by remember { mutableStateOf("All") }

    if (selectedTemple != null) {
        TempleDetailView(
            temple = selectedTemple!!,
            poojas = poojas.filter { it.templeName.contains(selectedTemple!!.name, ignoreCase = true) || it.templeName.contains("Home") },
            onBack = { selectedTemple = null },
            onBookPooja = onBookPooja,
            onLiveDarshan = { onLiveDarshan(selectedTemple!!) },
            onOrderPrasad = onOrderPrasad,
            onDonate = onDonate
        )
    } else {
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(LightCreamBackground)
        ) {
            // Header Section with KrishnaYellow Background
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(KrishnaYellow.copy(alpha = 0.25f))
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                Column {
                    Text(
                        text = "🛕 Sacred Temples & Shrines",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = DarkCharcoal,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = "Discover divine temples across India & watch Live Darshan",
                        fontSize = 12.sp,
                        color = DarkCharcoal.copy(alpha = 0.8f),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    DivineSearchBar(
                        query = searchQuery,
                        onQueryChange = { searchQuery = it },
                        placeholder = "Search temples, deities, city..."
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    // Single Line Filter Tags in LazyRow
                    LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(listOf("All", "Live Darshan 🔴", "Lord Shiva", "Lord Vishnu", "Lord Ganesha")) { god ->
                            val isSelected = filterGod == god || (god == "Live Darshan 🔴" && filterGod == "Live")
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(16.dp))
                                    .background(if (isSelected) DarkCharcoal else PureWhite)
                                    .border(1.dp, if (isSelected) DarkCharcoal else PureWhite, RoundedCornerShape(16.dp))
                                    .clickable {
                                        filterGod = if (god == "Live Darshan 🔴") "Live" else god
                                    }
                                    .padding(horizontal = 14.dp, vertical = 6.dp)
                            ) {
                                Text(
                                    text = god,
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
            }

            Spacer(modifier = Modifier.height(10.dp))

            val filteredTemples = remember(temples, filterGod, searchQuery) {
                temples.filter {
                    val matchesGod = when (filterGod) {
                        "All" -> true
                        "Live" -> it.isLiveDarshanAvailable
                        else -> it.deity.equals(filterGod, ignoreCase = true)
                    }
                    val matchesQuery = searchQuery.isEmpty() ||
                            it.name.contains(searchQuery, ignoreCase = true) ||
                            it.city.contains(searchQuery, ignoreCase = true) ||
                            it.deity.contains(searchQuery, ignoreCase = true)

                    matchesGod && matchesQuery
                }.sortedByDescending { it.isLiveDarshanAvailable }
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(bottom = 24.dp)
            ) {
                items(filteredTemples) { temple ->
                    TempleListItemCard(
                        temple = temple,
                        onClick = { selectedTemple = temple }
                    )
                }
            }
        }
    }
}

@Composable
fun TempleListItemCard(temple: Temple, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = PureWhite),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = temple.coverImageRes),
                contentDescription = temple.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(90.dp)
                    .clip(RoundedCornerShape(12.dp))
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = temple.name,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = SacredMaroon
                )
                Text(
                    text = "Deity: ${temple.deity}",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = SaffronOrange
                )
                Text(
                    text = "📍 ${temple.city}, ${temple.state}",
                    fontSize = 11.sp,
                    color = TextMuted
                )
                Spacer(modifier = Modifier.height(6.dp))
                RatingBadge(rating = temple.rating, reviewCount = temple.reviewCount)
            }
        }
    }
}

@Composable
fun TempleDetailView(
    temple: Temple,
    poojas: List<PoojaService>,
    onBack: () -> Unit,
    onBookPooja: (PoojaService) -> Unit,
    onLiveDarshan: () -> Unit,
    onOrderPrasad: () -> Unit,
    onDonate: () -> Unit
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(LightCreamBackground)
            .verticalScroll(scrollState)
    ) {
        // Hero Image Header with Back button
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
        ) {
            Image(
                painter = painterResource(id = temple.coverImageRes),
                contentDescription = temple.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color.Black.copy(alpha = 0.5f), Color.Black.copy(alpha = 0.8f))
                        )
                    )
            )

            IconButton(
                onClick = onBack,
                modifier = Modifier
                    .padding(12.dp)
                    .align(Alignment.TopStart)
                    .clip(CircleShape)
                    .background(PureWhite.copy(alpha = 0.3f))
            ) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back", tint = PureWhite)
            }

            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.BottomStart)
            ) {
                Text(
                    text = temple.name,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = PureWhite
                )
                Text(
                    text = "📍 ${temple.city}, ${temple.state} • ${temple.deity}",
                    fontSize = 13.sp,
                    color = DivineGold
                )
            }
        }

        Column(modifier = Modifier.padding(16.dp)) {
            // Quick CTAs Bar
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = onLiveDarshan,
                    colors = ButtonDefaults.buttonColors(containerColor = SaffronOrange),
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(imageVector = Icons.Default.LiveTv, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "Live Darshan", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }

                Button(
                    onClick = onOrderPrasad,
                    colors = ButtonDefaults.buttonColors(containerColor = SacredMaroon),
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(imageVector = Icons.Default.ShoppingBag, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "Order Prasad", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Timings & History Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = PureWhite)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(
                        text = "🏛️ Timings & Overview",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = SacredMaroon
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(text = "Darshan Hours: ${temple.timings}", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = TextDark)
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(text = temple.history, fontSize = 12.sp, color = TextMuted)
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Facilities & Connectivity
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = PureWhite)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(
                        text = "🚆 Location & Amenities",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = SacredMaroon
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(imageVector = Icons.Default.Train, contentDescription = null, tint = SaffronOrange, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(text = "Nearest Railway: ${temple.nearestRailway}", fontSize = 12.sp, color = TextDark)
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(imageVector = Icons.Default.FlightTakeoff, contentDescription = null, tint = SaffronOrange, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(text = "Nearest Airport: ${temple.nearestAirport}", fontSize = 12.sp, color = TextDark)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        temple.facilities.forEach { fac ->
                            Box(
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .background(DivineGold.copy(alpha = 0.15f))
                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                            ) {
                                Text(text = "✔ $fac", fontSize = 10.sp, fontWeight = FontWeight.SemiBold, color = SacredMaroon)
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Available Poojas at this temple
            Text(
                text = "📿 Book Sevas & Poojas",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = SacredMaroon
            )
            Spacer(modifier = Modifier.height(8.dp))

            poojas.forEach { pooja ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = PureWhite)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(text = pooja.name, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = SacredMaroon)
                            Text(text = "${pooja.durationMinutes} mins • Includes Prasad", fontSize = 11.sp, color = TextMuted)
                            Text(text = "₹${pooja.price.toInt()}", fontSize = 14.sp, fontWeight = FontWeight.ExtraBold, color = SaffronOrange)
                        }
                        Button(
                            onClick = { onBookPooja(pooja) },
                            colors = ButtonDefaults.buttonColors(containerColor = SacredMaroon)
                        ) {
                            Text(text = "Book", fontSize = 12.sp)
                        }
                    }
                }
            }
        }
    }
}
