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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ConfirmationNumber
import androidx.compose.material.icons.filled.LiveTv
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.UserRole
import com.example.ui.theme.DivineGold
import com.example.ui.theme.LightCreamBackground
import com.example.ui.theme.PureWhite
import com.example.ui.theme.SacredMaroon
import com.example.ui.theme.SaffronOrange
import com.example.ui.theme.TextDark
import com.example.ui.theme.TextMuted

@Composable
fun AdminPanelsScreen(
    role: UserRole,
    onBack: () -> Unit,
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
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onBack) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back", tint = SacredMaroon)
            }
            Text(text = "${role.icon} ${role.label} Control Panel", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = SacredMaroon)
        }

        Spacer(modifier = Modifier.height(12.dp))

        when (role) {
            UserRole.TEMPLE_ADMIN -> TempleAdminPanelContent()
            UserRole.PANDIT -> PanditPanelContent()
            UserRole.SUPER_ADMIN -> SuperAdminPanelContent()
            else -> TempleAdminPanelContent()
        }
    }
}

@Composable
private fun TempleAdminPanelContent() {
    Column {
        // Analytics Row
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            MetricCard(title = "Monthly Revenue", value = "₹1,42,000", modifier = Modifier.weight(1f))
            MetricCard(title = "Poojas Booked", value = "128", modifier = Modifier.weight(1f))
        }

        Spacer(modifier = Modifier.height(12.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = PureWhite)
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Text(text = "🔴 Live Darshan Stream Management", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = SacredMaroon)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = "Status: Kashi Vishwanath Live Stream ACTIVE (14.2k Viewers)", fontSize = 12.sp, color = Color(0xFF2E7D32), fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = {}, colors = ButtonDefaults.buttonColors(containerColor = SaffronOrange)) {
                    Text(text = "Start Sandhya Aarti Stream", fontSize = 12.sp)
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = PureWhite)
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Text(text = "📿 Manage Temple Poojas & Pricing", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = SacredMaroon)
                Spacer(modifier = Modifier.height(6.dp))
                Text(text = "• Maha Rudrabhishek - ₹2,100 (Active)\n• Kalyanotsavam Seva - ₹3,500 (Active)", fontSize = 12.sp, color = TextDark)
            }
        }
    }
}

@Composable
private fun PanditPanelContent() {
    Column {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            MetricCard(title = "Monthly Earnings", value = "₹48,500", modifier = Modifier.weight(1f))
            MetricCard(title = "Upcoming Poojas", value = "8 Ceremonies", modifier = Modifier.weight(1f))
        }

        Spacer(modifier = Modifier.height(12.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = PureWhite)
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Text(text = "📅 Next Assigned Ceremony", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = SacredMaroon)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = "Satyanarayan Vrat Katha • Aug 5, 09:00 AM", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = SaffronOrange)
                Text(text = "Devotee: Rajesh Sharma (Kashyap Gotra)\nLocation: Home Pooja / Digital Video Call", fontSize = 12.sp, color = TextDark)
            }
        }
    }
}

@Composable
private fun SuperAdminPanelContent() {
    Column {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            MetricCard(title = "Platform GMV", value = "₹28.4 Lakhs", modifier = Modifier.weight(1f))
            MetricCard(title = "Active Temples", value = "142 Shrines", modifier = Modifier.weight(1f))
        }

        Spacer(modifier = Modifier.height(12.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = PureWhite)
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Text(text = "🛡️ Verification & Onboarding Approvals", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = SacredMaroon)
                Spacer(modifier = Modifier.height(6.dp))
                Text(text = "• Pending Temple Verification: Somnath Trust (Gujarat)\n• Pending Pandit Verification: Pt. Achyutanand Jha", fontSize = 12.sp, color = TextDark)
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = {}, colors = ButtonDefaults.buttonColors(containerColor = SacredMaroon)) {
                    Text(text = "Approve All Verified Requests", fontSize = 12.sp)
                }
            }
        }
    }
}

@Composable
private fun MetricCard(title: String, value: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = SacredMaroon)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(text = title, fontSize = 11.sp, color = DivineGold)
            Text(text = value, fontSize = 16.sp, fontWeight = FontWeight.ExtraBold, color = PureWhite)
        }
    }
}
