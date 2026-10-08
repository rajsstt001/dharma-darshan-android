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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.filled.VolunteerActivism
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.db.CartItemEntity
import com.example.data.db.DonationEntity
import com.example.data.model.DonationCause
import com.example.data.model.PrasadItem
import com.example.ui.theme.CardLightBorder
import com.example.ui.theme.DivineGold
import com.example.ui.theme.LightCreamBackground
import com.example.ui.theme.PureWhite
import com.example.ui.theme.SacredMaroon
import com.example.ui.theme.SaffronOrange
import com.example.ui.theme.TextDark
import com.example.ui.theme.TextMuted

@Composable
fun DonationAndStoreScreen(
    causes: List<DonationCause>,
    prasadProducts: List<PrasadItem>,
    cartItems: List<CartItemEntity>,
    donations: List<DonationEntity>,
    onMakeDonation: (title: String, category: String, amount: Double, isAnon: Boolean, name: String) -> Unit,
    onAddToCart: (PrasadItem) -> Unit,
    onRemoveFromCart: (String) -> Unit,
    onCheckoutCart: () -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedTab by remember { mutableStateOf(0) } // 0: Donations, 1: Prasad Store, 2: Cart

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(LightCreamBackground)
    ) {
        TabRow(
            selectedTabIndex = selectedTab,
            containerColor = PureWhite,
            contentColor = SacredMaroon,
            indicator = { tabPositions ->
                TabRowDefaults.SecondaryIndicator(
                    Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                    color = SaffronOrange
                )
            }
        ) {
            Tab(selected = selectedTab == 0, onClick = { selectedTab = 0 }) {
                Text(text = "Donations", fontSize = 13.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(12.dp))
            }
            Tab(selected = selectedTab == 1, onClick = { selectedTab = 1 }) {
                Text(text = "Prasad Store", fontSize = 13.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(12.dp))
            }
            Tab(selected = selectedTab == 2, onClick = { selectedTab = 2 }) {
                Text(text = "Cart (${cartItems.size})", fontSize = 13.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(12.dp))
            }
        }

        if (selectedTab == 0) {
            DonationTabContent(causes = causes, donations = donations, onMakeDonation = onMakeDonation)
        } else if (selectedTab == 1) {
            PrasadStoreTabContent(products = prasadProducts, onAddToCart = onAddToCart)
        } else {
            CartTabContent(cartItems = cartItems, onRemoveFromCart = onRemoveFromCart, onCheckout = onCheckoutCart)
        }
    }
}

@Composable
private fun DonationTabContent(
    causes: List<DonationCause>,
    donations: List<DonationEntity>,
    onMakeDonation: (title: String, category: String, amount: Double, isAnon: Boolean, name: String) -> Unit
) {
    var customAmount by remember { mutableStateOf("1100") }
    var donorName by remember { mutableStateOf("Devotee") }
    var isAnon by remember { mutableStateOf(false) }
    var successMsg by remember { mutableStateOf("") }

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState)
    ) {
        if (successMsg.isNotEmpty()) {
            Card(
                modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F5E9))
            ) {
                Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                    Icon(imageVector = Icons.Default.CheckCircle, contentDescription = null, tint = Color(0xFF2E7D32))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = successMsg, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1B5E20))
                }
            }
        }

        Text(text = "🐄 Featured Spiritual Seva Causes", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = SacredMaroon)
        Spacer(modifier = Modifier.height(8.dp))

        causes.forEach { cause ->
            Card(
                modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = PureWhite)
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(text = cause.title, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = SacredMaroon)
                    Text(text = cause.description, fontSize = 11.sp, color = TextMuted)
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(text = "Raised ₹${cause.raisedAmount.toInt()} / Target ₹${cause.targetAmount.toInt()}", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = SaffronOrange)

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        listOf(501.0, 1100.0, 2100.0, 5100.0).forEach { amt ->
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(SaffronOrange.copy(alpha = 0.15f))
                                    .clickable {
                                        onMakeDonation(cause.title, cause.category, amt, isAnon, donorName)
                                        successMsg = "Thank you! Donated ₹${amt.toInt()} with Tax Receipt Generated!"
                                    }
                                    .padding(horizontal = 10.dp, vertical = 6.dp)
                            ) {
                                Text(text = "₹${amt.toInt()}", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = SacredMaroon)
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Tax Receipts History
        if (donations.isNotEmpty()) {
            Text(text = "📄 Tax Receipts (Section 80G)", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = SacredMaroon)
            Spacer(modifier = Modifier.height(6.dp))
            donations.forEach { don ->
                Card(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = CardDefaults.cardColors(containerColor = PureWhite)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(text = don.causeTitle, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = TextDark)
                            Text(text = "Receipt: ${don.receiptId} • ₹${don.amount.toInt()}", fontSize = 11.sp, color = TextMuted)
                        }
                        Icon(imageVector = Icons.Default.Download, contentDescription = "Download Receipt", tint = SaffronOrange)
                    }
                }
            }
        }
    }
}

@Composable
private fun PrasadStoreTabContent(
    products: List<PrasadItem>,
    onAddToCart: (PrasadItem) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(products) { item ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = PureWhite)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = item.imageRes),
                        contentDescription = item.name,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.size(72.dp).clip(RoundedCornerShape(10.dp))
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(text = item.name, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = SacredMaroon)
                        Text(text = "Temple: ${item.templeName}", fontSize = 11.sp, color = TextMuted)
                        Text(text = "₹${item.price.toInt()}", fontSize = 14.sp, fontWeight = FontWeight.ExtraBold, color = SaffronOrange)
                    }
                    Button(
                        onClick = { onAddToCart(item) },
                        colors = ButtonDefaults.buttonColors(containerColor = SacredMaroon)
                    ) {
                        Text(text = "+ Add", fontSize = 11.sp)
                    }
                }
            }
        }
    }
}

@Composable
private fun CartTabContent(
    cartItems: List<CartItemEntity>,
    onRemoveFromCart: (String) -> Unit,
    onCheckout: () -> Unit
) {
    if (cartItems.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = "🛒 Your Divine Cart is empty", fontSize = 14.sp, color = TextMuted)
        }
    } else {
        val total = cartItems.sumOf { it.price * it.quantity }
        Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            LazyColumn(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(cartItems) { item ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = PureWhite)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(text = item.name, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = TextDark)
                                Text(text = "₹${item.price.toInt()} x ${item.quantity}", fontSize = 12.sp, color = TextMuted)
                            }
                            Button(
                                onClick = { onRemoveFromCart(item.prasadId) },
                                colors = ButtonDefaults.buttonColors(containerColor = Color.Red.copy(alpha = 0.8f))
                            ) {
                                Text(text = "Remove", fontSize = 10.sp)
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = onCheckout,
                modifier = Modifier.fillMaxWidth().height(48.dp),
                colors = ButtonDefaults.buttonColors(containerColor = SacredMaroon),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(text = "Place Order (Total: ₹${total.toInt()})", fontSize = 15.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}
