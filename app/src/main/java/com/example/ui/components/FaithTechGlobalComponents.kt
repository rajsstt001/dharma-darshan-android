package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.outlined.AccountBalance
import androidx.compose.material.icons.outlined.CreditCard
import androidx.compose.material.icons.outlined.QrCodeScanner
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.TextButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.data.model.AuthUser
import com.example.data.model.NotificationItem
import com.example.ui.theme.DarkCharcoal
import com.example.ui.theme.DivineGold
import com.example.ui.theme.KrishnaYellow
import com.example.ui.theme.LightCreamBackground
import com.example.ui.theme.PureWhite
import com.example.ui.theme.SacredMaroon
import com.example.ui.theme.SaffronOrange
import com.example.ui.theme.SuccessGreen
import com.example.ui.theme.TextDark
import com.example.ui.theme.TextMuted
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun DharmaGradientButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    gradient: Brush = Brush.horizontalGradient(
        colors = listOf(Color(0xFFFFC107), Color(0xFFFF9800))
    ),
    icon: ImageVector? = null,
    iconEmoji: String? = null,
    shape: androidx.compose.ui.graphics.Shape = RoundedCornerShape(24.dp),
    textColor: Color = DarkCharcoal,
    contentPadding: androidx.compose.foundation.layout.PaddingValues = androidx.compose.foundation.layout.PaddingValues(horizontal = 12.dp, vertical = 6.dp)
) {
    Surface(
        modifier = modifier
            .shadow(2.dp, shape = shape)
            .clip(shape)
            .background(if (enabled) gradient else Brush.horizontalGradient(listOf(Color.LightGray, Color.Gray)))
            .clickable(enabled = enabled, onClick = onClick),
        color = Color.Transparent
    ) {
        Row(
            modifier = Modifier.padding(contentPadding),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (icon != null) {
                Icon(imageVector = icon, contentDescription = null, tint = textColor, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(4.dp))
            } else if (!iconEmoji.isNullOrEmpty()) {
                Text(text = iconEmoji, fontSize = 14.sp)
                Spacer(modifier = Modifier.width(4.dp))
            }
            Text(
                text = text,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = textColor,
                maxLines = 1
            )
        }
    }
}

@Composable
fun OnlineGlowDot(
    modifier: Modifier = Modifier,
    size: androidx.compose.ui.unit.Dp = 10.dp
) {
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val alpha by infiniteTransition.animateFloat(
        initialValue = 0.4f,
        targetValue = 1.0f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "alpha"
    )
    val scale by infiniteTransition.animateFloat(
        initialValue = 1.0f,
        targetValue = 1.35f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "scale"
    )

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.size(size * 1.8f)
    ) {
        Box(
            modifier = Modifier
                .size(size * scale)
                .clip(CircleShape)
                .background(SuccessGreen.copy(alpha = alpha * 0.4f))
        )
        Box(
            modifier = Modifier
                .size(size)
                .clip(CircleShape)
                .background(SuccessGreen)
                .border(1.5.dp, PureWhite, CircleShape)
        )
    }
}

@Composable
fun FaithTechPersistentHeader(
    walletBalance: Double,
    unreadNotificationsCount: Int,
    authUser: AuthUser,
    onWalletClick: () -> Unit,
    onNotificationsClick: () -> Unit,
    onProfileClick: () -> Unit,
    onMenuClick: () -> Unit = {},
    title: String? = null,
    showBackButton: Boolean = false,
    onBackClick: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        color = com.example.ui.theme.KrishnaYellow,
        tonalElevation = 4.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(horizontal = 8.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Left: Back button + Screen title OR Drawer Menu Icon & DharmaDarshan Logo
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f, fill = false)
            ) {
                if (showBackButton) {
                    IconButton(onClick = { onBackClick?.invoke() }) {
                        Icon(
                            imageVector = androidx.compose.material.icons.Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = com.example.ui.theme.DarkCharcoal,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(2.dp))
                    Text(
                        text = title ?: "DharmaDarshan",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color = com.example.ui.theme.DarkCharcoal,
                        maxLines = 1,
                        overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
                    )
                } else {
                    IconButton(onClick = onMenuClick) {
                        Icon(
                            imageVector = androidx.compose.material.icons.Icons.Default.Menu,
                            contentDescription = "Menu",
                            tint = com.example.ui.theme.DarkCharcoal,
                            modifier = Modifier.size(24.dp)
                        )
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.clickable { onProfileClick() }
                    ) {
                        Column {
                            Text(
                                text = title ?: "DharmaDarshan",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = com.example.ui.theme.DarkCharcoal,
                                letterSpacing = 0.5.sp
                            )
                            if (title == null) {
                                Text(
                                    text = if (authUser.isGuest) "Guest Devotee" else authUser.name,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = com.example.ui.theme.DarkCharcoal.copy(alpha = 0.8f)
                                )
                            }
                        }
                    }
                }
            }

            // Right: Wallet & Profile
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                // Wallet Pill
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .background(com.example.ui.theme.DarkCharcoal)
                        .clickable { onWalletClick() }
                        .padding(horizontal = 10.dp, vertical = 5.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.AccountBalanceWallet,
                            contentDescription = "Wallet",
                            tint = com.example.ui.theme.KrishnaYellow,
                            modifier = Modifier.size(15.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "₹${walletBalance.toInt()}",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = PureWhite
                        )
                    }
                }

                // Profile Avatar Icon
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(com.example.ui.theme.DarkCharcoal)
                        .clickable { onProfileClick() }
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Profile",
                        tint = com.example.ui.theme.KrishnaYellow,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun FaithTechAuthDialog(
    reasonText: String,
    onDismiss: () -> Unit,
    onLoginSuccess: (phone: String, isSso: Boolean) -> Unit
) {
    var mobileNumber by remember { mutableStateOf("") }
    var otpCode by remember { mutableStateOf("") }
    var step by remember { mutableStateOf(1) } // 1: Enter Phone / SSO, 2: OTP Verification
    var errorMessage by remember { mutableStateOf("") }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = LightCreamBackground),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "DharmaDarshan Login",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = SacredMaroon
                    )
                    IconButton(onClick = onDismiss, modifier = Modifier.size(28.dp)) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "Close", tint = TextMuted)
                    }
                }

                if (reasonText.isNotEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(SaffronOrange.copy(alpha = 0.1f))
                            .padding(10.dp)
                    ) {
                        Text(
                            text = "🔒 $reasonText. Please sign in to continue.",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            color = SacredMaroon
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                if (step == 1) {
                    // Mobile Number Section
                    Text(
                        text = "Mobile Number Login",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextDark,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    OutlinedTextField(
                        value = mobileNumber,
                        onValueChange = {
                            if (it.length <= 10) mobileNumber = it
                            errorMessage = ""
                        },
                        placeholder = { Text("Enter 10-digit Mobile Number") },
                        leadingIcon = { Text("🇮🇳 +91", fontWeight = FontWeight.Bold, fontSize = 13.sp) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = SaffronOrange,
                            unfocusedBorderColor = Color.LightGray
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )

                    if (errorMessage.isNotEmpty()) {
                        Text(text = errorMessage, color = Color.Red, fontSize = 11.sp, modifier = Modifier.padding(top = 4.dp))
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Button(
                        onClick = {
                            if (mobileNumber.length == 10) {
                                step = 2
                            } else {
                                errorMessage = "Please enter a valid 10-digit mobile number"
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = SaffronOrange),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Get OTP", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = PureWhite)
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                        Divider(modifier = Modifier.weight(1f), color = Color.LightGray)
                        Text(text = "  OR  ", fontSize = 11.sp, color = TextMuted)
                        Divider(modifier = Modifier.weight(1f), color = Color.LightGray)
                    }
                    Spacer(modifier = Modifier.height(16.dp))

                    // SSO Buttons
                    Text(text = "Single Sign-On (SSO)", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = TextMuted)
                    Spacer(modifier = Modifier.height(8.dp))

                    Button(
                        onClick = {
                            onLoginSuccess("Google SSO User", true)
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = PureWhite),
                        border = androidx.compose.foundation.BorderStroke(1.dp, Color.LightGray),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("🌐 Continue with Google", fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = TextDark)
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Button(
                        onClick = {
                            onLoginSuccess("Apple SSO User", true)
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("🍎 Continue with Apple ID", fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = PureWhite)
                    }
                } else {
                    // Step 2: OTP Verification
                    Text(
                        text = "Enter 4-Digit OTP",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextDark
                    )
                    Text(
                        text = "Sent to +91 $mobileNumber",
                        fontSize = 12.sp,
                        color = TextMuted
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = otpCode,
                        onValueChange = {
                            if (it.length <= 4) otpCode = it
                            errorMessage = ""
                        },
                        placeholder = { Text("Try: 1234") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = SaffronOrange,
                            unfocusedBorderColor = Color.LightGray
                        ),
                        modifier = Modifier.width(180.dp)
                    )

                    if (errorMessage.isNotEmpty()) {
                        Text(text = errorMessage, color = Color.Red, fontSize = 11.sp, modifier = Modifier.padding(top = 4.dp))
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = {
                            if (otpCode.length == 4 || otpCode.isEmpty()) {
                                onLoginSuccess("+91 $mobileNumber", false)
                            } else {
                                errorMessage = "Enter 4-digit code (or 1234)"
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = SacredMaroon),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Verify & Login", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = PureWhite)
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    TextButton(
                        onClick = { step = 1 },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back", tint = SaffronOrange, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Change Mobile Number", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = SaffronOrange)
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FaithTechWalletSheet(
    currentBalance: Double,
    onAddMoney: (Double) -> Unit,
    onDismiss: () -> Unit
) {
    var amountInput by remember { mutableStateOf("") }
    var selectedPaymentMethod by remember { mutableStateOf("UPI") }
    var isSuccessShown by remember { mutableStateOf(false) }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = rememberModalBottomSheetState(),
        containerColor = LightCreamBackground
    ) {
        Column(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "💳 DharmaDarshan Wallet",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = SacredMaroon
                )
                IconButton(onClick = onDismiss) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = "Close", tint = TextMuted)
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Wallet Balance Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = SacredMaroon)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Available Wallet Balance", fontSize = 12.sp, color = PureWhite.copy(alpha = 0.8f))
                    Text(
                        text = "₹${currentBalance.toInt()}",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = DivineGold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "🔒 All poojas & chadhava bookings are paid seamlessly via Wallet",
                        fontSize = 10.sp,
                        color = PureWhite.copy(alpha = 0.7f)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (isSuccessShown) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color(0xFFE8F5E9))
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = "Success",
                            tint = Color(0xFF2E7D32),
                            modifier = Modifier.size(36.dp)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("Money Added Successfully!", fontWeight = FontWeight.Bold, color = Color(0xFF2E7D32))
                        Text("Updated Balance: ₹${(currentBalance + (amountInput.toDoubleOrNull() ?: 500.0)).toInt()}", fontSize = 12.sp, color = TextDark)
                    }
                }
            } else {
                Text("Recharge Wallet", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = TextDark)
                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = amountInput,
                    onValueChange = { amountInput = it },
                    placeholder = { Text("Enter Amount (e.g. 500)") },
                    leadingIcon = { Text("₹", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = SacredMaroon) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Quick presets
                Text("Quick Add Options:", fontSize = 11.sp, color = TextMuted)
                Spacer(modifier = Modifier.height(6.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    listOf(500, 1000, 2100, 5100).forEach { preset ->
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(10.dp))
                                .background(SaffronOrange.copy(alpha = 0.12f))
                                .border(1.dp, SaffronOrange, RoundedCornerShape(10.dp))
                                .clickable { amountInput = preset.toString() }
                                .padding(vertical = 8.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("+₹$preset", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = SaffronOrange)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text("Select Payment Gateway:", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = TextDark)
                Spacer(modifier = Modifier.height(6.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    PaymentOptionChip("UPI / GPay", Icons.Outlined.QrCodeScanner, selectedPaymentMethod == "UPI") { selectedPaymentMethod = "UPI" }
                    PaymentOptionChip("Cards", Icons.Outlined.CreditCard, selectedPaymentMethod == "CARD") { selectedPaymentMethod = "CARD" }
                    PaymentOptionChip("Net Banking", Icons.Outlined.AccountBalance, selectedPaymentMethod == "BANK") { selectedPaymentMethod = "BANK" }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        val amt = amountInput.toDoubleOrNull() ?: 500.0
                        onAddMoney(amt)
                        isSuccessShown = true
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = SaffronOrange),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Proceed to Add ₹${amountInput.ifEmpty { "500" }}", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = PureWhite)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun PaymentOptionChip(
    title: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(if (isSelected) SacredMaroon else PureWhite)
            .border(1.dp, if (isSelected) DivineGold else Color.LightGray, RoundedCornerShape(12.dp))
            .clickable { onClick() }
            .padding(horizontal = 12.dp, vertical = 8.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = if (isSelected) DivineGold else TextDark,
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = title,
                fontSize = 11.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                color = if (isSelected) PureWhite else TextDark
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FaithTechNotificationsSheet(
    notifications: List<NotificationItem>,
    onMarkAllRead: () -> Unit,
    onDismiss: () -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = LightCreamBackground
    ) {
        Column(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "🔔 Notifications Center",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = SacredMaroon
                )
                Text(
                    text = "Mark all read",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = SaffronOrange,
                    modifier = Modifier.clickable { onMarkAllRead() }
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(notifications) { item ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = if (!item.isRead) PureWhite else PureWhite.copy(alpha = 0.6f)
                        ),
                        elevation = CardDefaults.cardElevation(defaultElevation = if (!item.isRead) 2.dp else 0.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(12.dp)
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.Top
                        ) {
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(CircleShape)
                                    .background(SaffronOrange.copy(alpha = 0.15f))
                            ) {
                                Text(
                                    text = when (item.category) {
                                        "BOOKING" -> "🙏"
                                        "WALLET" -> "💳"
                                        "FESTIVAL" -> "🪔"
                                        else -> "🔔"
                                    },
                                    fontSize = 16.sp
                                )
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = item.title,
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = SacredMaroon,
                                        modifier = Modifier.weight(1f, fill = false),
                                        maxLines = 1
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(text = item.timestamp, fontSize = 10.sp, color = TextMuted, maxLines = 1)
                                }
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(text = item.message, fontSize = 11.sp, color = TextDark)
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}
