package com.example.ui.screens

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
import androidx.compose.foundation.lazy.items as lazyRowItems
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.ToolCategory
import com.example.data.model.VedicToolItem
import com.example.ui.theme.DarkCharcoal
import com.example.ui.theme.DivineGold
import com.example.ui.theme.GoldAccent
import com.example.ui.theme.KrishnaYellow
import com.example.ui.theme.LightCreamBackground
import com.example.ui.theme.PureWhite
import com.example.ui.theme.SacredMaroon
import com.example.ui.theme.SaffronOrange
import com.example.ui.theme.TextDark
import com.example.ui.theme.TextMuted

@Composable
fun VedicServicesCatalogueScreen(
    vedicTools: List<VedicToolItem>,
    aiServices: List<VedicToolItem>,
    onSelectTool: (VedicToolItem) -> Unit,
    modifier: Modifier = Modifier
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("All") }

    val categories = listOf("All", "AI Guru Chat", "Kundli & Astrology", "Horoscope & Transit", "Tarot & Palmistry", "Vastu & Numerology")

    val allTools = remember(vedicTools, aiServices) { vedicTools + aiServices }

    val filteredTools = remember(searchQuery, selectedCategory, allTools) {
        allTools.filter { tool ->
            val matchesCategory = when (selectedCategory) {
                "All" -> true
                "AI Guru Chat" -> tool.category == ToolCategory.AI || tool.title.contains("AI", ignoreCase = true)
                "Kundli & Astrology" -> tool.title.contains("Kundli", ignoreCase = true) || tool.title.contains("Dosha", ignoreCase = true) || tool.title.contains("Patri", ignoreCase = true)
                "Horoscope & Transit" -> tool.title.contains("Horoscope", ignoreCase = true) || tool.title.contains("Panchang", ignoreCase = true) || tool.title.contains("Rashifal", ignoreCase = true)
                "Tarot & Palmistry" -> tool.title.contains("Tarot", ignoreCase = true) || tool.title.contains("Palmistry", ignoreCase = true)
                "Vastu & Numerology" -> tool.title.contains("Vastu", ignoreCase = true) || tool.title.contains("Numerology", ignoreCase = true) || tool.title.contains("Gemstone", ignoreCase = true)
                else -> true
            }

            val matchesSearch = searchQuery.isEmpty() ||
                    tool.title.contains(searchQuery, ignoreCase = true) ||
                    tool.description.contains(searchQuery, ignoreCase = true)

            matchesCategory && matchesSearch
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(LightCreamBackground)
    ) {
        // KrishnaYellow Header Section for Search and Categories (Matching Home Page aesthetic)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(KrishnaYellow.copy(alpha = 0.25f))
                .padding(bottom = 12.dp)
        ) {
            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                Text(
                    text = "✨ AI Guru & Vedic Tools Catalogue",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = DarkCharcoal,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = "Explore 18+ divine AI insights, Janam Kundli, and Vastu tools",
                    fontSize = 12.sp,
                    color = DarkCharcoal.copy(alpha = 0.8f),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Search Bar in Single Line
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = {
                        Text(
                            text = "Search Kundli, Tarot, Vastu, AI Guru...",
                            fontSize = 12.sp,
                            color = TextMuted,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    },
                    leadingIcon = {
                        Icon(imageVector = Icons.Default.Search, contentDescription = "Search", tint = SacredMaroon)
                    },
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
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Filter Category Tags in Single Line Horizontal Scroll
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(end = 16.dp)
                ) {
                    lazyRowItems(categories) { cat ->
                        val isSelected = selectedCategory == cat
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(16.dp))
                                .background(if (isSelected) DarkCharcoal else PureWhite)
                                .border(1.dp, if (isSelected) DarkCharcoal else PureWhite, RoundedCornerShape(16.dp))
                                .clickable { selectedCategory = cat }
                                .padding(horizontal = 14.dp, vertical = 6.dp)
                        ) {
                            Text(
                                text = cat,
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

        // Tools Vertical Grid
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            contentPadding = PaddingValues(bottom = 24.dp)
        ) {
            items(filteredTools) { tool ->
                ServiceToolCard(
                    tool = tool,
                    onClick = { onSelectTool(tool) }
                )
            }
        }
    }
}

@Composable
private fun ServiceToolCard(
    tool: VedicToolItem,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = PureWhite),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(42.dp)
                        .clip(CircleShape)
                        .background(KrishnaYellow.copy(alpha = 0.25f))
                ) {
                    Text(text = tool.iconEmoji, fontSize = 22.sp)
                }

                if (tool.category == ToolCategory.AI || tool.isPopular) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .background(if (tool.category == ToolCategory.AI) SacredMaroon else SaffronOrange)
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = if (tool.category == ToolCategory.AI) "🤖 AI POWERED" else "🔥 POPULAR",
                            fontSize = 8.sp,
                            fontWeight = FontWeight.Bold,
                            color = PureWhite,
                            maxLines = 1
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = tool.title,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = DarkCharcoal,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = tool.subtitle,
                fontSize = 10.sp,
                color = TextMuted,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = tool.description,
                fontSize = 10.sp,
                color = TextDark,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                lineHeight = 13.sp
            )

            Spacer(modifier = Modifier.height(10.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .background(KrishnaYellow)
                    .padding(vertical = 6.dp),
                contentAlignment = Alignment.Center
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Open Tool",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = DarkCharcoal,
                        maxLines = 1
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    Icon(
                        imageVector = Icons.Default.ChevronRight,
                        contentDescription = null,
                        tint = DarkCharcoal,
                        modifier = Modifier.size(14.dp)
                    )
                }
            }
        }
    }
}
