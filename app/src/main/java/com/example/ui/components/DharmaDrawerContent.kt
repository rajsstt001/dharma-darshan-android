package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.ConfirmationNumber
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Help
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.LiveTv
import androidx.compose.material.icons.filled.LocalOffer
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PrivacyTip
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.AuthUser
import com.example.ui.theme.DarkCharcoal
import com.example.ui.theme.GoldAccent
import com.example.ui.theme.KrishnaYellow
import com.example.ui.theme.LightCreamBackground
import com.example.ui.theme.PureWhite
import com.example.ui.theme.TextDark
import com.example.ui.theme.TextMuted

import androidx.compose.material.icons.filled.SelfImprovement

@Composable
fun DharmaDrawerContent(
    authUser: AuthUser,
    walletBalance: Double,
    onNavigate: (String) -> Unit,
    onCloseDrawer: () -> Unit,
    onLogout: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxHeight()
            .width(300.dp)
            .background(LightCreamBackground)
    ) {
        // Sticky Krishna Yellow Brand Header Banner with statusBarsPadding
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(KrishnaYellow)
                .statusBarsPadding()
                .padding(20.dp)
        ) {
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(52.dp)
                            .clip(CircleShape)
                            .background(DarkCharcoal)
                    ) {
                        Text(text = "🕉️", fontSize = 26.sp)
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = "DharmaDarshan",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = DarkCharcoal
                        )
                        Text(
                            text = "Divine Sanatana Platform",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Medium,
                            color = DarkCharcoal.copy(alpha = 0.8f)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(DarkCharcoal)
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(text = authUser.name, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = PureWhite)
                        Text(text = "Wallet: ₹${walletBalance.toInt()}", fontSize = 11.sp, color = KrishnaYellow)
                    }
                    Text(text = "🙏", fontSize = 18.sp)
                }
            }
        }

        // Scrollable Menu Content Column
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .verticalScroll(scrollState)
                .navigationBarsPadding()
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            // Menu Section 1: Core Navigation
            DrawerCategoryHeader("CORE SERVICES")
            DrawerMenuItem("Home", Icons.Default.Home) { onNavigate("HOME"); onCloseDrawer() }
            DrawerMenuItem("Live Darshan", Icons.Default.LiveTv) { onNavigate("LIVE_DARSHAN"); onCloseDrawer() }
            DrawerMenuItem("Online Pooja", Icons.Default.WaterDrop) { onNavigate("POOJA_LIST"); onCloseDrawer() }
            DrawerMenuItem("Book a Pandit", Icons.Default.SelfImprovement) { onNavigate("PANDIT_BOOKING"); onCloseDrawer() }
            DrawerMenuItem("Talk / Chat Astrologers", Icons.Default.Call) { onNavigate("ASTROLOGERS"); onCloseDrawer() }
            DrawerMenuItem("Live Astrologers 🔴", Icons.Default.LiveTv) { onNavigate("LIVE_ASTROLOGERS"); onCloseDrawer() }
            DrawerMenuItem("AI Guru & Vedic Tools", Icons.Default.AutoAwesome) { onNavigate("VEDIC_TOOLS"); onCloseDrawer() }
            DrawerMenuItem("Dharma Blogs", Icons.Default.Book) { onNavigate("BLOGS"); onCloseDrawer() }

            Divider(modifier = Modifier.padding(vertical = 8.dp), color = Color.LightGray.copy(alpha = 0.4f))

            // Menu Section 2: Account & Bookings
            DrawerCategoryHeader("MY ACCOUNT")
            DrawerMenuItem("Profile & Settings", Icons.Default.Person) { onNavigate("PROFILE"); onCloseDrawer() }
            DrawerMenuItem("My Following Astrologers", Icons.Default.People) { onNavigate("FOLLOWING_ASTROLOGERS"); onCloseDrawer() }
            DrawerMenuItem("My Bookings", Icons.Default.ConfirmationNumber) { onNavigate("ORDERS"); onCloseDrawer() }
            DrawerMenuItem("Wallet Transactions", Icons.Default.AccountBalanceWallet) { onNavigate("WALLET"); onCloseDrawer() }
            DrawerMenuItem("Notifications", Icons.Default.Notifications) { onNavigate("NOTIFICATIONS"); onCloseDrawer() }
            DrawerMenuItem("Saved Wishlist", Icons.Default.Favorite) { onNavigate("WISHLIST"); onCloseDrawer() }

            Divider(modifier = Modifier.padding(vertical = 8.dp), color = Color.LightGray.copy(alpha = 0.4f))

            // Menu Section 3: System & Preferences
            DrawerCategoryHeader("PREFERENCES & OFFERS")
            DrawerMenuItem("Refer & Earn ₹100", Icons.Default.People) { onNavigate("REFER"); onCloseDrawer() }
            DrawerMenuItem("Coupons & Offers", Icons.Default.LocalOffer) { onNavigate("OFFERS"); onCloseDrawer() }
            DrawerMenuItem("App Language", Icons.Default.Language) { onNavigate("LANGUAGE"); onCloseDrawer() }

            Divider(modifier = Modifier.padding(vertical = 8.dp), color = Color.LightGray.copy(alpha = 0.4f))

            // Menu Section 4: Support & Legal
            DrawerCategoryHeader("HELP & INFORMATION")
            DrawerMenuItem("Help & Customer Support", Icons.Default.Help) { onNavigate("SUPPORT"); onCloseDrawer() }
            DrawerMenuItem("About DharmaDarshan", Icons.Default.Info) { onNavigate("ABOUT"); onCloseDrawer() }
            DrawerMenuItem("Privacy Policy", Icons.Default.PrivacyTip) { onNavigate("PRIVACY"); onCloseDrawer() }
            DrawerMenuItem("Terms & Conditions", Icons.Default.Receipt) { onNavigate("TERMS"); onCloseDrawer() }

            Spacer(modifier = Modifier.height(12.dp))

            // Logout Item
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onLogout(); onCloseDrawer() }
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(imageVector = Icons.Default.Logout, contentDescription = null, tint = Color.Red, modifier = Modifier.size(20.dp))
                Spacer(modifier = Modifier.width(12.dp))
                Text(text = "Logout", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.Red)
            }

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
fun DrawerCategoryHeader(title: String) {
    Text(
        text = title,
        fontSize = 10.sp,
        fontWeight = FontWeight.Bold,
        color = GoldAccent,
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
    )
}

@Composable
fun DrawerMenuItem(
    label: String,
    icon: ImageVector,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = icon, contentDescription = null, tint = DarkCharcoal, modifier = Modifier.size(20.dp))
        Spacer(modifier = Modifier.width(14.dp))
        Text(text = label, fontSize = 14.sp, fontWeight = FontWeight.Medium, color = TextDark)
    }
}
