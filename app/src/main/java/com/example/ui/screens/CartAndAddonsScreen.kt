package com.example.ui.screens

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
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ConfirmationNumber
import androidx.compose.material.icons.filled.ShoppingCart
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.PaidAddon
import com.example.ui.theme.CardLightBorder
import com.example.ui.theme.DivineGold
import com.example.ui.theme.LightCreamBackground
import com.example.ui.theme.PureWhite
import com.example.ui.theme.SacredMaroon
import com.example.ui.theme.SaffronOrange
import com.example.ui.theme.TextDark
import com.example.ui.theme.TextMuted

@Composable
fun CartAndAddonsScreen(
    itemName: String,
    templeName: String,
    basePrice: Double,
    walletBalance: Double,
    availableAddons: List<PaidAddon>,
    onBack: () -> Unit,
    onCheckoutWithWallet: (totalAmount: Double, addonsSelected: List<String>) -> Unit,
    onOpenAddWalletMoney: () -> Unit,
    modifier: Modifier = Modifier
) {
    var couponCode by remember { mutableStateOf("") }
    var discountAmount by remember { mutableStateOf(0.0) }
    var couponAppliedMessage by remember { mutableStateOf("") }

    // Devotee & Family Sankalp Details Form
    var devoteeName by remember { mutableStateOf("") }
    var gotra by remember { mutableStateOf("") }
    var rashi by remember { mutableStateOf("Select Rashi") }
    var familyMembersInput by remember { mutableStateOf("") }
    var sankalpWish by remember { mutableStateOf("Health, Wealth & Peace") }
    var prasadAddress by remember { mutableStateOf("") }

    val rashis = listOf("Mesha (Aries)", "Vrishabha (Taurus)", "Mithuna (Gemini)", "Karka (Cancer)", "Simha (Leo)", "Kanya (Virgo)", "Tula (Libra)", "Vrishchika (Scorpio)", "Dhanu (Sagittarius)", "Makara (Capricorn)", "Kumbha (Aquarius)", "Meena (Pisces)")
    val sankalpOptions = listOf("Health, Wealth & Peace", "Career & Business Growth", "Remove Marriage Obstacles", "Protection from Evil Eyes & Doshas", "Child Welfare & Education")

    // Addon quantities map
    val addonQuantities = remember { mutableStateMapOf<String, Int>() }

    // Calculate addons total
    val addonsTotal = availableAddons.sumOf { addon ->
        val qty = (addonQuantities[addon.id] ?: 0).toDouble()
        addon.price * qty
    }

    val taxes = (basePrice + addonsTotal) * 0.18 // 18% GST
    val convenienceFee = 50.0
    val grandTotal = (basePrice + addonsTotal + taxes + convenienceFee - discountAmount).coerceAtLeast(0.0)

    val isWalletSufficient = walletBalance >= grandTotal
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(LightCreamBackground)
            .padding(16.dp)
            .verticalScroll(scrollState)
    ) {
        // Selected Item Summary Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = PureWhite)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "Selected Pooja / Ritual", fontSize = 11.sp, color = TextMuted)
                Text(text = itemName, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = SacredMaroon)
                Text(text = "🛕 $templeName", fontSize = 12.sp, color = TextDark)
                Spacer(modifier = Modifier.height(6.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Base Pooja Price", fontSize = 12.sp, color = TextMuted)
                    Text(text = "₹${basePrice.toInt()}", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = SacredMaroon)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Devotee & Family Sankalp Details Form Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = PureWhite),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "📜 Devotee & Family Sankalp Form", fontSize = 15.sp, fontWeight = FontWeight.ExtraBold, color = SacredMaroon)
                }
                Text(
                    text = "Pandit ji will chant these details during sacred mantra recitation",
                    fontSize = 11.sp,
                    color = TextMuted
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Primary Devotee Name & Gotra
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(
                        value = devoteeName,
                        onValueChange = { devoteeName = it },
                        label = { Text("Devotee Name *", fontSize = 12.sp, maxLines = 1, overflow = TextOverflow.Ellipsis) },
                        placeholder = { Text("e.g. Rahul Sharma", fontSize = 12.sp, maxLines = 1, overflow = TextOverflow.Ellipsis) },
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = SaffronOrange),
                        modifier = Modifier.weight(1f)
                    )

                    OutlinedTextField(
                        value = gotra,
                        onValueChange = { gotra = it },
                        label = { Text("Gotra *", fontSize = 12.sp, maxLines = 1, overflow = TextOverflow.Ellipsis) },
                        placeholder = { Text("e.g. Kashyap", fontSize = 12.sp, maxLines = 1, overflow = TextOverflow.Ellipsis) },
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = SaffronOrange),
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Family Members Input
                OutlinedTextField(
                    value = familyMembersInput,
                    onValueChange = { familyMembersInput = it },
                    label = { Text("Family Members' Names (Optional)", fontSize = 12.sp, maxLines = 1, overflow = TextOverflow.Ellipsis) },
                    placeholder = { Text("e.g. Sunita (Wife), Aarav (Son)", fontSize = 12.sp, maxLines = 1, overflow = TextOverflow.Ellipsis) },
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = SaffronOrange),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Rashi Selection Chips
                Text(text = "Rashi / Moon Sign (Optional)", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = TextDark)
                Spacer(modifier = Modifier.height(4.dp))
                androidx.compose.foundation.lazy.LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    items(rashis.size) { idx ->
                        val r = rashis[idx]
                        val isSelected = rashi == r
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .background(if (isSelected) SaffronOrange else LightCreamBackground)
                                .clickable { rashi = r }
                                .padding(horizontal = 10.dp, vertical = 6.dp)
                        ) {
                            Text(
                                text = r,
                                fontSize = 11.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                color = if (isSelected) PureWhite else TextDark
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Sankalp Purpose Selection
                Text(text = "Sankalp Purpose / Wish", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = TextDark)
                Spacer(modifier = Modifier.height(4.dp))
                androidx.compose.foundation.lazy.LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    items(sankalpOptions.size) { idx ->
                        val wish = sankalpOptions[idx]
                        val isSelected = sankalpWish == wish
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .background(if (isSelected) SacredMaroon else LightCreamBackground)
                                .clickable { sankalpWish = wish }
                                .padding(horizontal = 10.dp, vertical = 6.dp)
                        ) {
                            Text(
                                text = wish,
                                fontSize = 11.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                color = if (isSelected) DivineGold else TextDark
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Prasad Delivery Address
                OutlinedTextField(
                    value = prasadAddress,
                    onValueChange = { prasadAddress = it },
                    label = { Text("Prasad Delivery Address *", fontSize = 12.sp, maxLines = 1, overflow = TextOverflow.Ellipsis) },
                    placeholder = { Text("Enter House No, Street, City & Pincode", fontSize = 12.sp, maxLines = 1, overflow = TextOverflow.Ellipsis) },
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = SaffronOrange),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Paid Addons Section
        Text(
            text = "🪷 Customize Your Offering (Paid Addons)",
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            color = SacredMaroon
        )
        Text(
            text = "Enhance your ritual with fresh flowers, prasad & VIP Sankalp",
            fontSize = 11.sp,
            color = TextMuted
        )
        Spacer(modifier = Modifier.height(8.dp))

        availableAddons.forEach { addon ->
            val qty = addonQuantities[addon.id] ?: 0
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
                    .border(
                        1.dp,
                        if (qty > 0) SaffronOrange else CardLightBorder,
                        RoundedCornerShape(12.dp)
                    ),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = PureWhite)
            ) {
                Row(
                    modifier = Modifier
                        .padding(12.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                        Text(text = addon.iconEmoji, fontSize = 24.sp)
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(text = addon.name, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = SacredMaroon)
                            Text(text = addon.description, fontSize = 10.sp, color = TextMuted, maxLines = 1)
                            Text(text = "₹${addon.price.toInt()} each", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = SaffronOrange)
                        }
                    }

                    // Quantity Controls
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .size(30.dp)
                                .clip(CircleShape)
                                .background(LightCreamBackground)
                                .clickable {
                                    if (qty > 0) addonQuantities[addon.id] = qty - 1
                                }
                        ) {
                            Text("-", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = SacredMaroon)
                        }

                        Text(
                            text = "$qty",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 12.dp)
                        )

                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .size(30.dp)
                                .clip(CircleShape)
                                .background(SaffronOrange)
                                .clickable {
                                    addonQuantities[addon.id] = qty + 1
                                }
                        ) {
                            Text("+", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = PureWhite)
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Coupon Section
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = PureWhite)
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                Text(text = "🏷️ Apply Devotion Coupon", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = SacredMaroon)
                Spacer(modifier = Modifier.height(6.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    OutlinedTextField(
                        value = couponCode,
                        onValueChange = { couponCode = it },
                        placeholder = { Text("e.g. DEVOTION100") },
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = SaffronOrange),
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = {
                            if (couponCode.equals("DEVOTION100", ignoreCase = true)) {
                                discountAmount = 100.0
                                couponAppliedMessage = "✓ ₹100 Devotion Coupon Applied!"
                            } else if (couponCode.isNotEmpty()) {
                                discountAmount = 50.0
                                couponAppliedMessage = "✓ ₹50 Special Blessing Coupon Applied!"
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = SaffronOrange),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text("Apply", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                }
                if (couponAppliedMessage.isNotEmpty()) {
                    Text(text = couponAppliedMessage, color = Color(0xFF2E7D32), fontSize = 11.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 4.dp))
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Bill Breakdown Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = PureWhite)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "📄 Price Breakdown", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = SacredMaroon)
                Spacer(modifier = Modifier.height(8.dp))

                BillRow("Pooja Base Price", "₹${basePrice.toInt()}")
                if (addonsTotal > 0) {
                    BillRow("Paid Addons Total", "₹${addonsTotal.toInt()}")
                }
                BillRow("GST (18%)", "₹${taxes.toInt()}")
                BillRow("Convenience & Dakshina Fee", "₹${convenienceFee.toInt()}")
                if (discountAmount > 0) {
                    BillRow("Coupon Discount", "-₹${discountAmount.toInt()}", isDiscount = true)
                }

                Divider(modifier = Modifier.padding(vertical = 8.dp), color = Color.LightGray)

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Grand Total", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = SacredMaroon)
                    Text(text = "₹${grandTotal.toInt()}", fontSize = 20.sp, fontWeight = FontWeight.ExtraBold, color = SacredMaroon)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Wallet Balance Check Banner
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(
                containerColor = if (isWalletSufficient) Color(0xFFE8F5E9) else Color(0xFFFFEBEE)
            )
        ) {
            Row(
                modifier = Modifier
                    .padding(12.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.AccountBalanceWallet,
                        contentDescription = "Wallet",
                        tint = if (isWalletSufficient) Color(0xFF2E7D32) else Color(0xFFC62828)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            text = "Faith Tech Wallet Balance: ₹${walletBalance.toInt()}",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (isWalletSufficient) Color(0xFF2E7D32) else Color(0xFFC62828)
                        )
                        Text(
                            text = if (isWalletSufficient) "Ready to pay with 1-click Wallet" else "Insufficient Balance! Recharge required.",
                            fontSize = 10.sp,
                            color = TextDark
                        )
                    }
                }

                if (!isWalletSufficient) {
                    Button(
                        onClick = onOpenAddWalletMoney,
                        colors = ButtonDefaults.buttonColors(containerColor = SaffronOrange),
                        shape = RoundedCornerShape(8.dp),
                        contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        Text("+ Recharge", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Pay Button
        Button(
            onClick = {
                val selectedList = availableAddons.filter { (addonQuantities[it.id] ?: 0) > 0 }
                    .map { "${it.name} (x${addonQuantities[it.id]})" }
                onCheckoutWithWallet(grandTotal, selectedList)
            },
            enabled = isWalletSufficient,
            colors = ButtonDefaults.buttonColors(containerColor = SaffronOrange),
            shape = RoundedCornerShape(14.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text(
                text = if (isWalletSufficient) "Pay ₹${grandTotal.toInt()} via Wallet" else "Recharge Wallet to Proceed",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = PureWhite
            )
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
private fun BillRow(label: String, amountText: String, isDiscount: Boolean = false) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 3.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, fontSize = 12.sp, color = TextMuted)
        Text(
            text = amountText,
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            color = if (isDiscount) Color(0xFF2E7D32) else TextDark
        )
    }
}
