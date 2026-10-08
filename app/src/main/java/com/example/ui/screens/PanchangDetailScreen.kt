package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.NightsStay
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.PanchangInfo
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

@Composable
fun PanchangDetailScreen(
    panchang: PanchangInfo,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(LightCreamBackground)
            .padding(16.dp)
            .verticalScroll(scrollState)
    ) {
        // Hero Header Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = PureWhite),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Brush.horizontalGradient(
                            colors = listOf(SacredMaroon, SaffronOrange)
                        )
                    )
                    .padding(20.dp)
            ) {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "☀️ Today's Divine Panchang",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = PureWhite,
                            modifier = Modifier.weight(1f, fill = false),
                            maxLines = 1
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Box(
                            modifier = Modifier
                                .clip(CircleShape)
                                .background(PureWhite.copy(alpha = 0.25f))
                                .padding(horizontal = 10.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = "Varanasi, UP",
                                fontSize = 11.sp,
                                color = PureWhite,
                                fontWeight = FontWeight.Bold,
                                maxLines = 1
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = panchang.dateText,
                        fontSize = 14.sp,
                        color = KrishnaYellow,
                        fontWeight = FontWeight.SemiBold
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Solar & Lunar Times Grid
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        TimePill(icon = Icons.Default.WbSunny, label = "Sunrise", time = "05:48 AM")
                        TimePill(icon = Icons.Default.WbSunny, label = "Sunset", time = "07:12 PM")
                        TimePill(icon = Icons.Default.NightsStay, label = "Moonrise", time = "09:30 AM")
                        TimePill(icon = Icons.Default.NightsStay, label = "Moonset", time = "10:15 PM")
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Panchang Core Five Elements (Tithi, Vara, Nakshatra, Yoga, Karana)
        Text(
            text = "🪷 Five Elements of Panchang (Panch-Anga)",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = SacredMaroon
        )

        Spacer(modifier = Modifier.height(8.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = PureWhite),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                PanchangDetailRow(title = "Tithi (Lunar Day)", value = panchang.tithi, timing = "Ends at 02:45 PM tomorrow")
                Divider(modifier = Modifier.padding(vertical = 10.dp), color = Color.LightGray.copy(alpha = 0.3f))

                PanchangDetailRow(title = "Paksha (Lunar Phase)", value = "Shukla Paksha (Waxing Phase)", timing = "Sravana Month")
                Divider(modifier = Modifier.padding(vertical = 10.dp), color = Color.LightGray.copy(alpha = 0.3f))

                PanchangDetailRow(title = "Nakshatra (Constellation)", value = panchang.nakshatra, timing = "Rule Deity: Lord Shukra / Venus")
                Divider(modifier = Modifier.padding(vertical = 10.dp), color = Color.LightGray.copy(alpha = 0.3f))

                PanchangDetailRow(title = "Yoga", value = "Siddhi Yoga", timing = "Highly auspicious for all spiritual work")
                Divider(modifier = Modifier.padding(vertical = 10.dp), color = Color.LightGray.copy(alpha = 0.3f))

                PanchangDetailRow(title = "Karana", value = "Bava Karana", timing = "Favorable for investments & Pujas")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Auspicious Timings (Shubh Muhurat)
        Text(
            text = "✨ Shubh Muhurat (Auspicious Windows)",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = SuccessGreen
        )

        Spacer(modifier = Modifier.height(8.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = PureWhite),
            border = androidx.compose.foundation.BorderStroke(1.dp, SuccessGreen.copy(alpha = 0.5f))
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                MuhuratRow(label = "Abhijit Muhurat", time = panchang.muhurat, isShubh = true, desc = "Best window for launching new work, trade, or Pujas")
                Spacer(modifier = Modifier.height(10.dp))
                MuhuratRow(label = "Brahma Muhurat", time = "04:22 AM - 05:08 AM", isShubh = true, desc = "Ideal time for Meditation, Yoga, and Mantra chanting")
                Spacer(modifier = Modifier.height(10.dp))
                MuhuratRow(label = "Amrit Kaal", time = "03:15 PM - 04:50 PM", isShubh = true, desc = "Very auspicious for home entry (Grah Pravesh)")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Inauspicious Timings (Rahu Kaal & Caution Windows)
        Text(
            text = "⚠️ Ashubh Muhurat (Inauspicious Windows)",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFC62828)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = PureWhite),
            border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFEF5350).copy(alpha = 0.5f))
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                MuhuratRow(label = "Rahu Kalam", time = panchang.rahuKaal, isShubh = false, desc = "Avoid initiating new financial deals or traveling")
                Spacer(modifier = Modifier.height(10.dp))
                MuhuratRow(label = "Yamaganda", time = "06:00 AM - 07:30 AM", isShubh = false, desc = "Avoid vital decision making or vehicle purchase")
                Spacer(modifier = Modifier.height(10.dp))
                MuhuratRow(label = "Gulika Kalam", time = "09:00 AM - 10:30 AM", isShubh = false, desc = "Moderate caution advised for vital signatures")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Daily Wisdom & Mantra
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = PureWhite),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(imageVector = Icons.Default.AutoAwesome, contentDescription = null, tint = SaffronOrange)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Today's Daily Vedic Quote & Mantra",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = DarkCharcoal
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(KrishnaYellow.copy(alpha = 0.2f))
                        .padding(14.dp)
                ) {
                    Text(
                        text = "🪷 ${panchang.dailyQuote}",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = SacredMaroon
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
private fun TimePill(icon: androidx.compose.ui.graphics.vector.ImageVector, label: String, time: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(imageVector = icon, contentDescription = label, tint = KrishnaYellow, modifier = Modifier.size(18.dp))
        Spacer(modifier = Modifier.height(2.dp))
        Text(text = label, fontSize = 10.sp, color = PureWhite.copy(alpha = 0.8f))
        Text(text = time, fontSize = 11.sp, color = PureWhite, fontWeight = FontWeight.Bold)
    }
}

@Composable
private fun PanchangDetailRow(title: String, value: String, timing: String) {
    Column {
        Text(text = title, fontSize = 12.sp, color = TextMuted, fontWeight = FontWeight.SemiBold)
        Spacer(modifier = Modifier.height(2.dp))
        Text(text = value, fontSize = 14.sp, color = DarkCharcoal, fontWeight = FontWeight.Bold)
        Text(text = timing, fontSize = 11.sp, color = SaffronOrange, fontWeight = FontWeight.Medium)
    }
}

@Composable
private fun MuhuratRow(label: String, time: String, isShubh: Boolean, desc: String) {
    Row(verticalAlignment = Alignment.Top) {
        Box(
            modifier = Modifier
                .padding(top = 2.dp)
                .size(8.dp)
                .clip(CircleShape)
                .background(if (isShubh) SuccessGreen else Color(0xFFE53935))
        )
        Spacer(modifier = Modifier.width(10.dp))
        Column(modifier = Modifier.weight(1f)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = label, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = DarkCharcoal)
                Text(
                    text = time,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = if (isShubh) SuccessGreen else Color(0xFFC62828)
                )
            }
            Text(text = desc, fontSize = 11.sp, color = TextMuted)
        }
    }
}
