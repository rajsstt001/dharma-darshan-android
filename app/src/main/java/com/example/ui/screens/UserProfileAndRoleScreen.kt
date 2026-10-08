package com.example.ui.screens

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Help
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.LocalOffer
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PrivacyTip
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.data.model.AuthUser
import com.example.data.model.FamilyMember
import com.example.data.model.UserRole
import com.example.ui.theme.DarkCharcoal
import com.example.ui.theme.GoldAccent
import com.example.ui.theme.KrishnaYellow
import com.example.ui.theme.LightCreamBackground
import com.example.ui.theme.PureWhite
import com.example.ui.theme.SuccessGreen
import com.example.ui.theme.TextDark
import com.example.ui.theme.TextMuted

@Composable
fun UserProfileAndRoleScreen(
    authUser: AuthUser,
    currentRole: UserRole,
    walletBalance: Double,
    rewardPoints: Int,
    familyMembers: List<FamilyMember>,
    selectedLanguage: String,
    onRoleSelect: (UserRole) -> Unit = {},
    onUpdateProfile: (AuthUser) -> Unit = {},
    onNavigateToWallet: () -> Unit,
    onNavigateToOrders: () -> Unit,
    onNavigateToLanguage: () -> Unit,
    onNavigateToNotifications: () -> Unit,
    onNavigateToWishlist: () -> Unit = {},
    onNavigateToRefer: () -> Unit = {},
    onNavigateToOffers: () -> Unit = {},
    onNavigateToSupport: () -> Unit = {},
    onNavigateToAbout: () -> Unit = {},
    onNavigateToPrivacy: () -> Unit = {},
    onNavigateToTerms: () -> Unit = {},
    onAddFamilyMember: (String, String, String, String) -> Unit,
    onLogout: () -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var showEditProfileForm by remember { mutableStateOf(false) }
    var showAddFamilyDialog by remember { mutableStateOf(false) }

    if (showEditProfileForm) {
        var name by remember(authUser) { mutableStateOf(authUser.name) }
        var gender by remember(authUser) { mutableStateOf(authUser.gender) }
        var dob by remember(authUser) { mutableStateOf(authUser.dob) }
        var tob by remember(authUser) { mutableStateOf(authUser.tob) }
        var pob by remember(authUser) { mutableStateOf(authUser.pob) }
        var currentAddress by remember(authUser) { mutableStateOf(authUser.currentAddress) }
        var cityStateCountry by remember(authUser) { mutableStateOf(authUser.cityStateCountry) }
        var pincode by remember(authUser) { mutableStateOf(authUser.pincode) }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(PureWhite)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Text(text = "Name*", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = DarkCharcoal)
            Spacer(modifier = Modifier.height(4.dp))
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = GoldAccent)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = "Gender", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = DarkCharcoal)
            Spacer(modifier = Modifier.height(6.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable { gender = "Male" }
                ) {
                    Text(text = if (gender == "Male") "🔘" else "⚪", fontSize = 18.sp)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(text = "Male", fontSize = 14.sp, color = DarkCharcoal)
                }
                Spacer(modifier = Modifier.width(24.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable { gender = "Female" }
                ) {
                    Text(text = if (gender == "Female") "🔘" else "⚪", fontSize = 18.sp)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(text = "Female", fontSize = 14.sp, color = DarkCharcoal)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = "Date of Birth", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = DarkCharcoal)
            Spacer(modifier = Modifier.height(4.dp))
            OutlinedTextField(
                value = dob,
                onValueChange = { dob = it },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = GoldAccent)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = "Time of Birth", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = DarkCharcoal)
            Spacer(modifier = Modifier.height(4.dp))
            OutlinedTextField(
                value = tob,
                onValueChange = { tob = it },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = GoldAccent)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = "Place of Birth", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = DarkCharcoal)
            Spacer(modifier = Modifier.height(4.dp))
            OutlinedTextField(
                value = pob,
                onValueChange = { pob = it },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = GoldAccent)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = "Current Address", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = DarkCharcoal)
            Spacer(modifier = Modifier.height(4.dp))
            OutlinedTextField(
                value = currentAddress,
                onValueChange = { currentAddress = it },
                placeholder = { Text("Enter Flat, House no, Building, Apartment", fontSize = 13.sp) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = GoldAccent)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = "City, State, Country", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = DarkCharcoal)
            Spacer(modifier = Modifier.height(4.dp))
            OutlinedTextField(
                value = cityStateCountry,
                onValueChange = { cityStateCountry = it },
                placeholder = { Text("Enter Town/City, State, Country", fontSize = 13.sp) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = GoldAccent)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = "Pincode", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = DarkCharcoal)
            Spacer(modifier = Modifier.height(4.dp))
            OutlinedTextField(
                value = pincode,
                onValueChange = { pincode = it },
                placeholder = { Text("Enter Pincode", fontSize = 13.sp) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = GoldAccent)
            )

            Spacer(modifier = Modifier.height(28.dp))

            Button(
                onClick = {
                    val updatedUser = authUser.copy(
                        name = name.ifBlank { authUser.name },
                        gender = gender,
                        dob = dob,
                        tob = tob,
                        pob = pob,
                        currentAddress = currentAddress,
                        cityStateCountry = cityStateCountry,
                        pincode = pincode
                    )
                    onUpdateProfile(updatedUser)
                    showEditProfileForm = false
                    Toast.makeText(context, "Profile details updated successfully!", Toast.LENGTH_SHORT).show()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = DarkCharcoal)
            ) {
                Text(text = "Submit", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = KrishnaYellow)
            }

            Spacer(modifier = Modifier.height(30.dp))
        }
        return
    }

    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(LightCreamBackground)
            .padding(16.dp)
            .verticalScroll(scrollState)
    ) {
            // User Header Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = DarkCharcoal),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .size(64.dp)
                                .clip(CircleShape)
                                .background(KrishnaYellow)
                        ) {
                            Text(text = "🕉️", fontSize = 32.sp)
                        }

                        Spacer(modifier = Modifier.width(14.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = authUser.name,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = PureWhite
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = authUser.phone.ifEmpty { "+91 9876543210" },
                                fontSize = 12.sp,
                                color = KrishnaYellow
                            )
                            Text(
                                text = authUser.email.ifEmpty { "devotee@dharmadarshan.com" },
                                fontSize = 11.sp,
                                color = PureWhite.copy(alpha = 0.7f)
                            )
                        }

                        IconButton(onClick = { showEditProfileForm = true }) {
                            Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit Profile", tint = KrishnaYellow)
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))
                    Divider(color = PureWhite.copy(alpha = 0.2f))
                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "Gotra: ${authUser.gotra}", fontSize = 12.sp, color = PureWhite)
                        Text(text = "Rashi: ${authUser.rashi}", fontSize = 12.sp, color = PureWhite)
                        Text(text = "Points: $rewardPoints 🪙", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = GoldAccent)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Quick Stats Row: Wallet & Bookings
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Card(
                    modifier = Modifier
                        .weight(1f)
                        .clickable { onNavigateToWallet() },
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
                            Icon(imageVector = Icons.Default.AccountBalanceWallet, contentDescription = null, tint = GoldAccent)
                            Icon(imageVector = Icons.Default.ChevronRight, contentDescription = null, tint = TextMuted)
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(text = "Wallet Balance", fontSize = 11.sp, color = TextMuted)
                        Text(text = "₹${walletBalance.toInt()}", fontSize = 20.sp, fontWeight = FontWeight.ExtraBold, color = TextDark)
                    }
                }

                Card(
                    modifier = Modifier
                        .weight(1f)
                        .clickable { onNavigateToOrders() },
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
                            Icon(imageVector = Icons.Default.History, contentDescription = null, tint = GoldAccent)
                            Icon(imageVector = Icons.Default.ChevronRight, contentDescription = null, tint = TextMuted)
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(text = "My Order History", fontSize = 11.sp, color = TextMuted)
                        Text(text = "View Bookings", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = TextDark)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Family Members Card
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
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(imageVector = Icons.Default.People, contentDescription = null, tint = DarkCharcoal)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(text = "Family Members for Sankalp", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = TextDark)
                        }
                        IconButton(onClick = { showAddFamilyDialog = true }) {
                            Icon(imageVector = Icons.Default.Add, contentDescription = "Add", tint = GoldAccent)
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    familyMembers.forEach { member ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = "${member.name} (${member.relation})", fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = TextDark)
                            Text(text = "Gotra: ${member.gotra}", fontSize = 12.sp, color = TextMuted)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Preference & Settings List Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = PureWhite),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(vertical = 8.dp)) {
                    SettingItemRow(
                        icon = Icons.Default.Favorite,
                        title = "Saved Wishlist",
                        subtitle = "Poojas, Astrologers & Store items",
                        onClick = onNavigateToWishlist
                    )
                    Divider(color = Color.LightGray.copy(alpha = 0.3f))

                    SettingItemRow(
                        icon = Icons.Default.People,
                        title = "Refer & Earn ₹108",
                        subtitle = "Invite friends & earn wallet cash",
                        onClick = onNavigateToRefer
                    )
                    Divider(color = Color.LightGray.copy(alpha = 0.3f))

                    SettingItemRow(
                        icon = Icons.Default.LocalOffer,
                        title = "Coupons & Offers",
                        subtitle = "Active discounts & promo codes",
                        onClick = onNavigateToOffers
                    )
                    Divider(color = Color.LightGray.copy(alpha = 0.3f))

                    SettingItemRow(
                        icon = Icons.Default.Language,
                        title = "App Language",
                        subtitle = selectedLanguage,
                        onClick = onNavigateToLanguage
                    )
                    Divider(color = Color.LightGray.copy(alpha = 0.3f))

                    SettingItemRow(
                        icon = Icons.Default.Notifications,
                        title = "Notifications & Reminders",
                        subtitle = "Aarti, Puja & Live Stream alerts",
                        onClick = onNavigateToNotifications
                    )
                    Divider(color = Color.LightGray.copy(alpha = 0.3f))

                    SettingItemRow(
                        icon = Icons.Default.Help,
                        title = "Help & Customer Support",
                        subtitle = "24/7 Spiritual Helpdesk & WhatsApp",
                        onClick = onNavigateToSupport
                    )
                    Divider(color = Color.LightGray.copy(alpha = 0.3f))

                    SettingItemRow(
                        icon = Icons.Default.Info,
                        title = "About DharmaDarshan",
                        subtitle = "App story & core mission",
                        onClick = onNavigateToAbout
                    )
                    Divider(color = Color.LightGray.copy(alpha = 0.3f))

                    SettingItemRow(
                        icon = Icons.Default.PrivacyTip,
                        title = "Privacy Policy",
                        subtitle = "Data protection & security",
                        onClick = onNavigateToPrivacy
                    )
                    Divider(color = Color.LightGray.copy(alpha = 0.3f))

                    SettingItemRow(
                        icon = Icons.Default.Receipt,
                        title = "Terms & Conditions",
                        subtitle = "Legal terms of service",
                        onClick = onNavigateToTerms
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Logout Button
            Button(
                onClick = onLogout,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red.copy(alpha = 0.1f))
            ) {
                Icon(imageVector = Icons.Default.Logout, contentDescription = null, tint = Color.Red)
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "LOGOUT ACCOUNT", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.Red)
            }

            Spacer(modifier = Modifier.height(30.dp))
        }

    // Add Family Member Modal Dialog
    if (showAddFamilyDialog) {
        var famName by remember { mutableStateOf("") }
        var famRelation by remember { mutableStateOf("") }
        var famGotra by remember { mutableStateOf("") }
        var famRashi by remember { mutableStateOf("") }

        Dialog(onDismissRequest = { showAddFamilyDialog = false }) {
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = PureWhite),
                modifier = Modifier.fillMaxWidth().padding(16.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(text = "Add Family Member for Sankalp", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = TextDark)
                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(value = famName, onValueChange = { famName = it }, label = { Text("Name") }, modifier = Modifier.fillMaxWidth())
                    OutlinedTextField(value = famRelation, onValueChange = { famRelation = it }, label = { Text("Relation (e.g. Wife, Son)") }, modifier = Modifier.fillMaxWidth())
                    OutlinedTextField(value = famGotra, onValueChange = { famGotra = it }, label = { Text("Gotra") }, modifier = Modifier.fillMaxWidth())
                    OutlinedTextField(value = famRashi, onValueChange = { famRashi = it }, label = { Text("Rashi") }, modifier = Modifier.fillMaxWidth())

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = {
                            if (famName.isNotBlank()) {
                                onAddFamilyMember(famName, famRelation, famGotra, famRashi)
                                showAddFamilyDialog = false
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = KrishnaYellow),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("SAVE FAMILY MEMBER", fontWeight = FontWeight.Bold, color = DarkCharcoal)
                    }
                }
            }
        }
    }
}

@Composable
fun SettingItemRow(
    icon: ImageVector,
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 14.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(imageVector = icon, contentDescription = null, tint = DarkCharcoal, modifier = Modifier.size(22.dp))
            Spacer(modifier = Modifier.width(14.dp))
            Column {
                Text(text = title, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = TextDark)
                Text(text = subtitle, fontSize = 11.sp, color = TextMuted)
            }
        }
        Icon(imageVector = Icons.Default.ChevronRight, contentDescription = null, tint = TextMuted)
    }
}
