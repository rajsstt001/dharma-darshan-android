package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.data.model.ChadhavaItem
import com.example.data.model.LiveDarshanStream
import com.example.data.model.PoojaService
import com.example.data.model.UpcomingPooja
import com.example.ui.DivineViewModel
import com.example.ui.components.DivineBottomNavBar
import com.example.ui.components.FaithTechAuthDialog
import com.example.ui.components.FaithTechNotificationsSheet
import com.example.ui.components.FaithTechPersistentHeader
import com.example.ui.components.FaithTechWalletSheet
import com.example.ui.components.NavigationTab
import com.example.ui.screens.AdminPanelsScreen
import com.example.ui.screens.AiGuruScreen
import com.example.ui.screens.AstrologyScreen
import com.example.ui.screens.CartAndAddonsScreen
import com.example.ui.screens.DonationAndStoreScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.LiveDarshanScreen
import com.example.ui.screens.MeditationAndBhajanScreen
import com.example.data.model.BlogPost
import com.example.data.model.VedicToolItem
import com.example.ui.components.DharmaDrawerContent
import com.example.ui.screens.AstrologerListingScreen
import com.example.ui.screens.BlogDetailScreen
import com.example.ui.screens.BlogListingScreen
import com.example.ui.screens.LanguageSelectionScreen
import com.example.ui.screens.OrderHistoryScreen
import com.example.ui.screens.PanchangDetailScreen
import com.example.ui.screens.PanditBookingScreen
import com.example.ui.screens.PoojaBookingScreen
import com.example.ui.screens.PoojaDetailData
import com.example.ui.screens.PoojaDetailScreen
import com.example.ui.screens.PoojaListFilter
import com.example.ui.screens.PoojaListScreen
import com.example.ui.screens.TempleListAndDetailScreen
import com.example.ui.screens.UserProfileAndRoleScreen
import com.example.ui.screens.VedicToolsAndAiScreen
import com.example.ui.screens.WalletTransactionsScreen
import com.example.ui.screens.ShopComingSoonScreen
import com.example.ui.screens.toDetailData
import com.example.ui.theme.DharmaDarshanTheme
import com.example.ui.theme.DivineConnectTheme
import kotlinx.coroutines.launch

import com.example.ui.screens.AstrologerDetailScreen
import com.example.ui.screens.FollowingAstrologersScreen
import com.example.ui.screens.LiveAstrologersScreen
import com.example.ui.screens.WelcomeScreen
import com.example.ui.screens.DharmaSplashScreen
import com.example.ui.screens.DharmaLoginScreen
import com.example.ui.screens.LiveChatConsultationScreen
import com.example.ui.screens.SavedWishlistScreen
import com.example.ui.screens.ReferAndEarnScreen
import com.example.ui.screens.CouponsAndOffersScreen
import com.example.ui.screens.HelpAndSupportScreen
import com.example.ui.screens.AboutDharmaDarshanScreen
import com.example.ui.screens.PrivacyPolicyScreen
import com.example.ui.screens.TermsAndConditionsScreen
import com.example.data.model.Astrologer

enum class ScreenState {
    SPLASH,
    LOGIN,
    WELCOME,
    MAIN_TAB,
    TEMPLE_DETAIL,
    POOJA_DETAIL,
    POOJA_LIST,
    POOJA_BOOKING,
    CART_CHECKOUT,
    PANDIT_BOOKING,
    LIVE_DARSHAN,
    DONATION_STORE,
    ASTROLOGY,
    MEDITATION_BHAJAN,
    AI_GURU,
    ADMIN_PANELS,
    ASTROLOGERS,
    LIVE_ASTROLOGERS,
    ASTROLOGER_DETAIL,
    FOLLOWING_ASTROLOGERS,
    VEDIC_TOOLS,
    BLOGS,
    BLOG_DETAIL,
    WALLET_TRANSACTIONS,
    ORDER_HISTORY,
    LANGUAGE,
    PANCHANG,
    LIVE_CHAT_CONSULTATION,
    SAVED_WISHLIST,
    REFER_AND_EARN,
    COUPONS_OFFERS,
    HELP_SUPPORT,
    ABOUT_DHARMA,
    PRIVACY_POLICY,
    TERMS_CONDITIONS
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DharmaDarshanTheme {
                FaithTechAppMain()
            }
        }
    }
}

@Composable
fun FaithTechAppMain(
    viewModel: DivineViewModel = viewModel()
) {
    var currentTab by remember { mutableStateOf(NavigationTab.HOME) }
    var screenState by remember { mutableStateOf(ScreenState.SPLASH) }

    var activePoojaName by remember { mutableStateOf("Maha Rudrabhishek") }
    var activeTempleName by remember { mutableStateOf("Kashi Vishwanath Temple") }
    var activeBasePrice by remember { mutableStateOf(2100.0) }
    var activePoojaService by remember { mutableStateOf<PoojaService?>(null) }
    var activeLiveStream by remember { mutableStateOf<LiveDarshanStream?>(null) }
    var activePoojaDetailData by remember { mutableStateOf<PoojaDetailData?>(null) }
    var initialPoojaFilter by remember { mutableStateOf(PoojaListFilter.ALL) }
    var activeBlog by remember { mutableStateOf<BlogPost?>(null) }
    var activeAstrologer by remember { mutableStateOf<Astrologer?>(null) }
    var activeLiveAstrologerId by remember { mutableStateOf<String?>(null) }

    // Dialog & Sheet states
    var showAuthDialog by remember { mutableStateOf(false) }
    var authReasonText by remember { mutableStateOf("") }
    var pendingGuestAction by remember { mutableStateOf<(() -> Unit)?>(null) }

    var showWalletSheet by remember { mutableStateOf(false) }
    var showNotificationsSheet by remember { mutableStateOf(false) }

    val coroutineScope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    val authUser by viewModel.authUser.collectAsState()
    val currentRole by viewModel.currentRole.collectAsState()
    val walletBalance by viewModel.walletBalance.collectAsState()
    val rewardPoints by viewModel.rewardPoints.collectAsState()
    val familyMembers by viewModel.familyMembers.collectAsState()
    val notifications by viewModel.notifications.collectAsState()
    val walletTransactions by viewModel.walletTransactions.collectAsState()
    val selectedLanguage by viewModel.selectedLanguage.collectAsState()
    val followedAstrologerIds by viewModel.followedAstrologerIds.collectAsState()

    val astrologers = remember { viewModel.astrologers }
    val vedicTools = remember { viewModel.vedicTools }
    val aiServices = remember { viewModel.aiServices }
    val blogPosts = remember { viewModel.blogPosts }
    val orderHistoryItems = remember { viewModel.orderHistoryItems }

    val unreadNotificationsCount = remember(notifications) { notifications.count { !it.isRead } }

    val bookings by viewModel.bookings.collectAsState()
    val cartItems by viewModel.cartItems.collectAsState()
    val donations by viewModel.donations.collectAsState()

    val temples = remember { viewModel.repository.getTemples() }
    val poojas = remember { viewModel.repository.getPoojaServices() }
    val upcomingPoojas = remember { viewModel.repository.getUpcomingPoojas() }
    val chadhavaItems = remember { viewModel.repository.getChadhavaItems() }
    val paidAddons = remember { viewModel.repository.getPaidAddons() }
    val pandits = remember { viewModel.repository.getPandits() }
    val liveStreams = remember { viewModel.repository.getLiveStreams() }
    val prasadProducts = remember { viewModel.repository.getPrasadProducts() }
    val bhajans = remember { viewModel.repository.getBhajanTracks() }
    val causes = remember { viewModel.repository.getDonationCauses() }
    val panchang = remember { viewModel.repository.getPanchang() }

    // Interception helper for Guest Mode
    val requireAuth = { reason: String, onAuthenticated: () -> Unit ->
        if (authUser.isGuest) {
            authReasonText = reason
            pendingGuestAction = { onAuthenticated() }
            showAuthDialog = true
        } else {
            onAuthenticated()
        }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DharmaDrawerContent(
                authUser = authUser,
                walletBalance = walletBalance,
                onCloseDrawer = { coroutineScope.launch { drawerState.close() } },
                onLogout = { viewModel.logout(); screenState = ScreenState.WELCOME },
                onNavigate = { route ->
                    coroutineScope.launch { drawerState.close() }
                    when (route) {
                        "HOME" -> { screenState = ScreenState.MAIN_TAB; currentTab = NavigationTab.HOME }
                        "WELCOME" -> { screenState = ScreenState.WELCOME }
                        "LIVE_DARSHAN" -> {
                            activeLiveStream = liveStreams.firstOrNull()
                            screenState = ScreenState.LIVE_DARSHAN
                        }
                        "POOJA_LIST" -> { screenState = ScreenState.MAIN_TAB; currentTab = NavigationTab.POOJA }
                        "PANDIT_BOOKING" -> { screenState = ScreenState.PANDIT_BOOKING }
                        "ASTROLOGERS" -> { screenState = ScreenState.MAIN_TAB; currentTab = NavigationTab.CHAT }
                        "LIVE_ASTROLOGERS" -> { screenState = ScreenState.LIVE_ASTROLOGERS }
                        "FOLLOWING_ASTROLOGERS" -> { screenState = ScreenState.FOLLOWING_ASTROLOGERS }
                        "VEDIC_TOOLS" -> { screenState = ScreenState.VEDIC_TOOLS }
                        "BLOGS" -> { screenState = ScreenState.BLOGS }
                        "PROFILE" -> { screenState = ScreenState.MAIN_TAB; currentTab = NavigationTab.PROFILE }
                        "ORDERS" -> { screenState = ScreenState.ORDER_HISTORY }
                        "WALLET" -> { screenState = ScreenState.WALLET_TRANSACTIONS }
                        "NOTIFICATIONS" -> { showNotificationsSheet = true }
                        "LANGUAGE" -> { screenState = ScreenState.LANGUAGE }
                        "WISHLIST" -> { screenState = ScreenState.SAVED_WISHLIST }
                        "REFER" -> { screenState = ScreenState.REFER_AND_EARN }
                        "OFFERS" -> { screenState = ScreenState.COUPONS_OFFERS }
                        "SUPPORT" -> { screenState = ScreenState.HELP_SUPPORT }
                        "ABOUT" -> { screenState = ScreenState.ABOUT_DHARMA }
                        "PRIVACY" -> { screenState = ScreenState.PRIVACY_POLICY }
                        "TERMS" -> { screenState = ScreenState.TERMS_CONDITIONS }
                        "LOGIN" -> { screenState = ScreenState.LOGIN }
                        else -> { screenState = ScreenState.MAIN_TAB }
                    }
                }
            )
        }
    ) {
        val (headerTitle, showHeaderBack, onHeaderBack) = remember(screenState, currentTab) {
            if (screenState == ScreenState.MAIN_TAB) {
                when (currentTab) {
                    NavigationTab.HOME -> Triple("DharmaDarshan", false, null)
                    NavigationTab.POOJA -> Triple("Poojas", true) { currentTab = NavigationTab.HOME }
                    NavigationTab.CHAT -> Triple("Astrologers", true) { currentTab = NavigationTab.HOME }
                    NavigationTab.SHOP -> Triple("Shop", true) { currentTab = NavigationTab.HOME }
                    NavigationTab.PROFILE -> Triple("Profile", true) { currentTab = NavigationTab.HOME }
                }
            } else {
                val title = when (screenState) {
                    ScreenState.ASTROLOGERS -> "Astrologers"
                    ScreenState.LIVE_ASTROLOGERS -> "Live Astrologers"
                    ScreenState.ASTROLOGER_DETAIL -> "Astrologer"
                    ScreenState.FOLLOWING_ASTROLOGERS -> "My Following"
                    ScreenState.SAVED_WISHLIST -> "Saved Wishlist"
                    ScreenState.REFER_AND_EARN -> "Refer & Earn"
                    ScreenState.COUPONS_OFFERS -> "Coupons & Offers"
                    ScreenState.HELP_SUPPORT -> "Help & Support"
                    ScreenState.ABOUT_DHARMA -> "About DharmaDarshan"
                    ScreenState.PRIVACY_POLICY -> "Privacy Policy"
                    ScreenState.TERMS_CONDITIONS -> "Terms & Conditions"
                    ScreenState.PANDIT_BOOKING -> "Book Pandit"
                    ScreenState.POOJA_LIST -> "Poojas"
                    ScreenState.POOJA_DETAIL -> "Pooja Details"
                    ScreenState.LIVE_DARSHAN -> "Live Darshan"
                    ScreenState.DONATION_STORE -> "Shop"
                    ScreenState.ASTROLOGY -> "Kundli & Astro"
                    ScreenState.MEDITATION_BHAJAN -> "Bhajans"
                    ScreenState.AI_GURU -> "AI Guru"
                    ScreenState.VEDIC_TOOLS -> "Vedic Tools"
                    ScreenState.BLOGS -> "Articles"
                    ScreenState.BLOG_DETAIL -> "Article"
                    ScreenState.WALLET_TRANSACTIONS -> "Wallet"
                    ScreenState.ORDER_HISTORY -> "My Bookings"
                    ScreenState.LANGUAGE -> "Language"
                    ScreenState.CART_CHECKOUT -> "Checkout"
                    else -> "DharmaDarshan"
                }
                Triple(title, true) { screenState = ScreenState.MAIN_TAB }
            }
        }

        Scaffold(
            topBar = {
                if (screenState != ScreenState.WELCOME &&
                    screenState != ScreenState.SPLASH &&
                    screenState != ScreenState.LOGIN &&
                    screenState != ScreenState.LIVE_CHAT_CONSULTATION) {
                    FaithTechPersistentHeader(
                        walletBalance = walletBalance,
                        unreadNotificationsCount = unreadNotificationsCount,
                        authUser = authUser,
                        onWalletClick = { showWalletSheet = true },
                        onNotificationsClick = { showNotificationsSheet = true },
                        onProfileClick = {
                            screenState = ScreenState.MAIN_TAB
                            currentTab = NavigationTab.PROFILE
                        },
                        onMenuClick = { coroutineScope.launch { drawerState.open() } },
                        title = headerTitle,
                        showBackButton = showHeaderBack,
                        onBackClick = onHeaderBack
                    )
                }
            },
            bottomBar = {
                val showBottomBar = (screenState == ScreenState.MAIN_TAB ||
                        screenState == ScreenState.POOJA_LIST ||
                        screenState == ScreenState.ASTROLOGERS ||
                        screenState == ScreenState.PANDIT_BOOKING ||
                        screenState == ScreenState.DONATION_STORE ||
                        screenState == ScreenState.BLOGS ||
                        screenState == ScreenState.LIVE_DARSHAN ||
                        screenState == ScreenState.VEDIC_TOOLS ||
                        screenState == ScreenState.ASTROLOGY ||
                        screenState == ScreenState.MEDITATION_BHAJAN ||
                        screenState == ScreenState.PANCHANG) && screenState != ScreenState.WELCOME

                if (showBottomBar) {
                    DivineBottomNavBar(
                        selectedTab = currentTab,
                        onTabSelected = { tab ->
                            currentTab = tab
                            screenState = ScreenState.MAIN_TAB
                        }
                    )
                }
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .let { modifier ->
                        if (screenState == ScreenState.LIVE_CHAT_CONSULTATION ||
                            screenState == ScreenState.LIVE_ASTROLOGERS ||
                            screenState == ScreenState.SPLASH ||
                            screenState == ScreenState.LOGIN ||
                            screenState == ScreenState.WELCOME) {
                            modifier
                        } else {
                            modifier.padding(innerPadding)
                        }
                    }
            ) {
                when (screenState) {
                    ScreenState.WELCOME -> {
                        WelcomeScreen(
                            onEnterApp = { screenState = ScreenState.MAIN_TAB }
                        )
                    }

                    ScreenState.MAIN_TAB -> {
                        when (currentTab) {
                            NavigationTab.HOME -> {
                                HomeScreen(
                                    panchang = panchang,
                                    upcomingPoojas = upcomingPoojas,
                                    recommendedPoojas = poojas,
                                    liveStreams = liveStreams,
                                    chadhavaItems = chadhavaItems,
                                    temples = temples,
                                    astrologers = astrologers,
                                    vedicTools = vedicTools,
                                    aiServices = aiServices,
                                    blogPosts = blogPosts,
                                    onSelectUpcomingPooja = { upPooja ->
                                        activePoojaDetailData = upPooja.toDetailData()
                                        screenState = ScreenState.POOJA_DETAIL
                                    },
                                    onSelectRecommendedPooja = { pooja ->
                                        activePoojaDetailData = pooja.toDetailData()
                                        screenState = ScreenState.POOJA_DETAIL
                                    },
                                    onViewAllPoojas = { filter ->
                                        initialPoojaFilter = filter
                                        currentTab = NavigationTab.POOJA
                                    },
                                    onJoinLiveStream = { stream ->
                                        requireAuth("Join Live Stream from ${stream.templeName}") {
                                            activeLiveStream = stream
                                            screenState = ScreenState.LIVE_DARSHAN
                                        }
                                    },
                                    onAddChadhava = { chadhava ->
                                        requireAuth("Offer ${chadhava.name}") {
                                            activePoojaName = chadhava.name
                                            activeTempleName = "Temple Offering"
                                            activeBasePrice = chadhava.price
                                            screenState = ScreenState.CART_CHECKOUT
                                        }
                                    },
                                    onNavigateToAiGuru = { screenState = ScreenState.AI_GURU },
                                    onNavigateToLiveAstrologers = { liveId ->
                                        activeLiveAstrologerId = liveId
                                        screenState = ScreenState.LIVE_ASTROLOGERS
                                    },
                                    onNavigateToTemples = { screenState = ScreenState.TEMPLE_DETAIL },
                                    onNavigateToPandits = { screenState = ScreenState.PANDIT_BOOKING },
                                    onNavigateToAstrologers = { currentTab = NavigationTab.CHAT },
                                    onSelectAstrologer = { ast ->
                                        activeAstrologer = ast
                                        screenState = ScreenState.ASTROLOGER_DETAIL
                                    },
                                    onNavigateToVedicTools = { screenState = ScreenState.VEDIC_TOOLS },
                                    onNavigateToBlogs = { screenState = ScreenState.BLOGS },
                                    onNavigateToStore = {
                                        currentTab = NavigationTab.SHOP
                                        screenState = ScreenState.MAIN_TAB
                                    },
                                    onSelectBlog = { blog ->
                                        activeBlog = blog
                                        screenState = ScreenState.BLOG_DETAIL
                                    },
                                    onStartChatConsultation = { ast ->
                                        requireAuth("Start Chat Consultation with ${ast.name}") {
                                            activeAstrologer = ast
                                            screenState = ScreenState.LIVE_CHAT_CONSULTATION
                                        }
                                    },
                                    onNavigateToPanchang = { screenState = ScreenState.PANCHANG },
                                    onOpenDrawer = { coroutineScope.launch { drawerState.open() } }
                                )
                            }

                            NavigationTab.POOJA -> {
                                PoojaListScreen(
                                    upcomingPoojas = upcomingPoojas,
                                    recommendedPoojas = poojas,
                                    initialFilter = initialPoojaFilter,
                                    onBack = { currentTab = NavigationTab.HOME },
                                    onSelectUpcomingPooja = { upPooja ->
                                        activePoojaDetailData = upPooja.toDetailData()
                                        screenState = ScreenState.POOJA_DETAIL
                                    },
                                    onSelectRecommendedPooja = { recPooja ->
                                        activePoojaDetailData = recPooja.toDetailData()
                                        screenState = ScreenState.POOJA_DETAIL
                                    },
                                    onOpenBookingForm = { dData ->
                                        requireAuth("Participate in ${dData.name}") {
                                            activePoojaName = dData.name
                                            activeTempleName = dData.templeName
                                            activeBasePrice = dData.price
                                            screenState = ScreenState.CART_CHECKOUT
                                        }
                                    }
                                )
                            }

                            NavigationTab.CHAT -> {
                                AstrologerListingScreen(
                                    astrologers = astrologers,
                                    onBack = { currentTab = NavigationTab.HOME },
                                    onSelectAstrologer = { ast ->
                                        activeAstrologer = ast
                                        screenState = ScreenState.ASTROLOGER_DETAIL
                                    },
                                    onStartConsultation = { ast, mode ->
                                        requireAuth("Connect with ${ast.name}") {
                                            activeAstrologer = ast
                                            screenState = ScreenState.ASTROLOGER_DETAIL
                                        }
                                    }
                                )
                            }

                            NavigationTab.SHOP -> {
                                ShopComingSoonScreen(
                                    onBack = { currentTab = NavigationTab.HOME }
                                )
                            }

                            NavigationTab.PROFILE -> {
                                UserProfileAndRoleScreen(
                                    authUser = authUser,
                                    currentRole = currentRole,
                                    walletBalance = walletBalance,
                                    rewardPoints = rewardPoints,
                                    familyMembers = familyMembers,
                                    selectedLanguage = selectedLanguage,
                                    onRoleSelect = { viewModel.setRole(it) },
                                    onUpdateProfile = { updated -> viewModel.updateUserProfile(updated) },
                                    onNavigateToWallet = { screenState = ScreenState.WALLET_TRANSACTIONS },
                                    onNavigateToOrders = { screenState = ScreenState.ORDER_HISTORY },
                                    onNavigateToLanguage = { screenState = ScreenState.LANGUAGE },
                                    onNavigateToNotifications = { showNotificationsSheet = true },
                                    onNavigateToWishlist = { screenState = ScreenState.SAVED_WISHLIST },
                                    onNavigateToRefer = { screenState = ScreenState.REFER_AND_EARN },
                                    onNavigateToOffers = { screenState = ScreenState.COUPONS_OFFERS },
                                    onNavigateToSupport = { screenState = ScreenState.HELP_SUPPORT },
                                    onNavigateToAbout = { screenState = ScreenState.ABOUT_DHARMA },
                                    onNavigateToPrivacy = { screenState = ScreenState.PRIVACY_POLICY },
                                    onNavigateToTerms = { screenState = ScreenState.TERMS_CONDITIONS },
                                    onAddFamilyMember = { name, rel, gotra, rashi ->
                                        viewModel.addFamilyMember(name, rel, gotra, rashi)
                                    },
                                    onLogout = { viewModel.logout(); screenState = ScreenState.WELCOME },
                                    onBack = { currentTab = NavigationTab.HOME }
                                )
                            }
                        }
                    }

                    ScreenState.TEMPLE_DETAIL -> {
                        TempleListAndDetailScreen(
                            temples = temples,
                            poojas = poojas,
                            onBookPooja = { p ->
                                activePoojaService = p
                                screenState = ScreenState.POOJA_BOOKING
                            },
                            onLiveDarshan = {
                                activeLiveStream = liveStreams.first()
                                screenState = ScreenState.LIVE_DARSHAN
                            },
                            onOrderPrasad = { screenState = ScreenState.DONATION_STORE },
                            onDonate = { screenState = ScreenState.DONATION_STORE }
                        )
                    }

                    ScreenState.POOJA_DETAIL -> {
                        PoojaDetailScreen(
                            detailData = activePoojaDetailData ?: upcomingPoojas.first().toDetailData(),
                            onBack = { screenState = ScreenState.MAIN_TAB },
                            onParticipateNow = { dData ->
                                requireAuth("Participate in ${dData.name}") {
                                    activePoojaName = dData.name
                                    activeTempleName = dData.templeName
                                    activeBasePrice = dData.price
                                    screenState = ScreenState.CART_CHECKOUT
                                }
                            },
                            onViewAcharyaProfile = { priestName ->
                                screenState = ScreenState.PANDIT_BOOKING
                            }
                        )
                    }

                    ScreenState.POOJA_LIST -> {
                        PoojaListScreen(
                            upcomingPoojas = upcomingPoojas,
                            recommendedPoojas = poojas,
                            initialFilter = initialPoojaFilter,
                            onBack = { screenState = ScreenState.MAIN_TAB },
                            onSelectUpcomingPooja = { upPooja ->
                                activePoojaDetailData = upPooja.toDetailData()
                                screenState = ScreenState.POOJA_DETAIL
                            },
                            onSelectRecommendedPooja = { recPooja ->
                                activePoojaDetailData = recPooja.toDetailData()
                                screenState = ScreenState.POOJA_DETAIL
                            },
                            onOpenBookingForm = { dData ->
                                requireAuth("Participate in ${dData.name}") {
                                    activePoojaName = dData.name
                                    activeTempleName = dData.templeName
                                    activeBasePrice = dData.price
                                    screenState = ScreenState.CART_CHECKOUT
                                }
                            }
                        )
                    }

                    ScreenState.POOJA_BOOKING -> {
                        val pService = activePoojaService ?: poojas.first()
                        PoojaBookingScreen(
                            pooja = pService,
                            onBack = { screenState = ScreenState.MAIN_TAB },
                            onConfirmBooking = { type, target, pName, date, slot, dName, g, sankalp, price ->
                                requireAuth("Book $pName") {
                                    viewModel.bookPooja(type, target, pName, date, slot, dName, g, sankalp, price)
                                }
                            },
                            onJoinLiveDarshan = {
                                activeLiveStream = liveStreams.first()
                                screenState = ScreenState.LIVE_DARSHAN
                            }
                        )
                    }

                    ScreenState.CART_CHECKOUT -> {
                        CartAndAddonsScreen(
                            itemName = activePoojaName,
                            templeName = activeTempleName,
                            basePrice = activeBasePrice,
                            walletBalance = walletBalance,
                            availableAddons = paidAddons,
                            onBack = { screenState = ScreenState.MAIN_TAB },
                            onCheckoutWithWallet = { totalAmount, addons ->
                                viewModel.bookPooja(
                                    bookingType = "POOJA_CHADHAVA",
                                    targetName = activeTempleName,
                                    poojaName = activePoojaName,
                                    dateText = "Aug 5, 2026",
                                    timeSlot = "09:00 AM",
                                    devoteeName = authUser.name,
                                    gotra = authUser.gotra,
                                    sankalpName = "Sankalp with ${addons.joinToString(", ")}",
                                    amount = totalAmount
                                )
                                screenState = ScreenState.MAIN_TAB
                                currentTab = NavigationTab.PROFILE
                            },
                            onOpenAddWalletMoney = { showWalletSheet = true }
                        )
                    }

                    ScreenState.PANDIT_BOOKING -> {
                        PanditBookingScreen(
                            pandits = pandits,
                            onBack = { screenState = ScreenState.MAIN_TAB },
                            onConfirmPanditBooking = { pandit, pkg, price ->
                                requireAuth("Book Pandit ${pandit.name}") {
                                    viewModel.bookPooja(
                                        bookingType = "HOME_PANDIT",
                                        targetName = pandit.name,
                                        poojaName = "Pandit Seva ($pkg Package)",
                                        dateText = "Aug 6, 2026",
                                        timeSlot = "09:00 AM",
                                        devoteeName = authUser.name,
                                        gotra = authUser.gotra,
                                        sankalpName = "Family Welfare",
                                        amount = price
                                    )
                                    screenState = ScreenState.MAIN_TAB
                                    currentTab = NavigationTab.PROFILE
                                }
                            }
                        )
                    }

                    ScreenState.LIVE_DARSHAN -> {
                        val stream = activeLiveStream ?: liveStreams.first()
                        LiveDarshanScreen(
                            stream = stream,
                            onBack = { screenState = ScreenState.MAIN_TAB },
                            onDonateClick = { screenState = ScreenState.DONATION_STORE }
                        )
                    }

                    ScreenState.DONATION_STORE -> {
                        DonationAndStoreScreen(
                            causes = causes,
                            prasadProducts = prasadProducts,
                            cartItems = cartItems,
                            donations = donations,
                            onMakeDonation = { title, cat, amt, isAnon, name ->
                                requireAuth("Make Donation for $title") {
                                    viewModel.makeDonation(title, cat, amt, isAnon, name)
                                }
                            },
                            onAddToCart = { item ->
                                requireAuth("Add ${item.name} to Cart") {
                                    viewModel.addToCart(item)
                                }
                            },
                            onRemoveFromCart = { viewModel.removeFromCart(it) },
                            onCheckoutCart = { viewModel.clearCart() },
                            onBack = { screenState = ScreenState.MAIN_TAB }
                        )
                    }

                    ScreenState.ASTROLOGY -> {
                        AstrologyScreen(
                            onAskAiGuru = { prompt ->
                                coroutineScope.launch {
                                    viewModel.askAiGuru(prompt)
                                }
                            },
                            onBack = { screenState = ScreenState.MAIN_TAB }
                        )
                    }

                    ScreenState.MEDITATION_BHAJAN -> {
                        MeditationAndBhajanScreen(
                            bhajans = bhajans,
                            onBack = { screenState = ScreenState.MAIN_TAB }
                        )
                    }

                    ScreenState.AI_GURU -> {
                        AiGuruScreen(
                            onAskAiGuru = { prompt -> viewModel.askAiGuru(prompt) },
                            onBack = { screenState = ScreenState.MAIN_TAB }
                        )
                    }

                    ScreenState.ADMIN_PANELS -> {
                        AdminPanelsScreen(
                            role = currentRole,
                            onBack = { screenState = ScreenState.MAIN_TAB }
                        )
                    }

                    ScreenState.ASTROLOGERS -> {
                        AstrologerListingScreen(
                            astrologers = astrologers,
                            onBack = { screenState = ScreenState.MAIN_TAB },
                            onSelectAstrologer = { ast ->
                                activeAstrologer = ast
                                screenState = ScreenState.ASTROLOGER_DETAIL
                            },
                            onStartConsultation = { ast, mode ->
                                requireAuth("Connect with ${ast.name}") {
                                    activeAstrologer = ast
                                    if (mode.contains("Chat", ignoreCase = true)) {
                                        screenState = ScreenState.LIVE_CHAT_CONSULTATION
                                    } else {
                                        viewModel.addNotification(
                                            title = "Connecting with ${ast.name}",
                                            message = "Your $mode session request has been sent to ${ast.name}.",
                                            category = "ASTROLOGY"
                                        )
                                        showNotificationsSheet = true
                                    }
                                }
                            }
                        )
                    }

                    ScreenState.LIVE_ASTROLOGERS -> {
                        LiveAstrologersScreen(
                            initialAstrologerId = activeLiveAstrologerId,
                            onBack = { screenState = ScreenState.MAIN_TAB },
                            onStartConsultation = { astroId ->
                                val ast = astrologers.find { it.id == astroId } ?: astrologers.first()
                                activeAstrologer = ast
                                screenState = ScreenState.ASTROLOGER_DETAIL
                            }
                        )
                    }

                    ScreenState.ASTROLOGER_DETAIL -> {
                        val ast = activeAstrologer ?: astrologers.first()
                        AstrologerDetailScreen(
                            astrologer = ast,
                            isFollowed = followedAstrologerIds.contains(ast.id),
                            onToggleFollow = { viewModel.toggleFollowAstrologer(ast.id) },
                            onBack = { screenState = ScreenState.ASTROLOGERS },
                            onStartConsultation = { astItem, mode ->
                                requireAuth("Connect with ${astItem.name}") {
                                    activeAstrologer = astItem
                                    if (mode.contains("Chat", ignoreCase = true)) {
                                        screenState = ScreenState.LIVE_CHAT_CONSULTATION
                                    } else {
                                        viewModel.addNotification(
                                            title = "Connecting with ${astItem.name}",
                                            message = "Your $mode session request has been sent to ${astItem.name}.",
                                            category = "ASTROLOGY"
                                        )
                                        showNotificationsSheet = true
                                    }
                                }
                            }
                        )
                    }

                    ScreenState.FOLLOWING_ASTROLOGERS -> {
                        val followedList = astrologers.filter { followedAstrologerIds.contains(it.id) }
                        FollowingAstrologersScreen(
                            followedAstrologers = followedList,
                            onBack = { screenState = ScreenState.MAIN_TAB },
                            onSelectAstrologer = { ast ->
                                activeAstrologer = ast
                                screenState = ScreenState.ASTROLOGER_DETAIL
                            },
                            onStartChatConsultation = { ast ->
                                activeAstrologer = ast
                                screenState = ScreenState.LIVE_CHAT_CONSULTATION
                            },
                            onUnfollow = { astId -> viewModel.toggleFollowAstrologer(astId) },
                            onExploreAstrologers = {
                                screenState = ScreenState.ASTROLOGERS
                            }
                        )
                    }

                    ScreenState.VEDIC_TOOLS -> {
                        VedicToolsAndAiScreen(
                            onBack = { screenState = ScreenState.MAIN_TAB },
                            onAskAiGuru = { prompt -> viewModel.askAiGuru(prompt) }
                        )
                    }

                    ScreenState.BLOGS -> {
                        BlogListingScreen(
                            blogs = blogPosts,
                            onBack = { screenState = ScreenState.MAIN_TAB },
                            onSelectBlog = { blog ->
                                activeBlog = blog
                                screenState = ScreenState.BLOG_DETAIL
                            }
                        )
                    }

                    ScreenState.BLOG_DETAIL -> {
                        BlogDetailScreen(
                            blog = activeBlog ?: blogPosts.first(),
                            onBack = { screenState = ScreenState.BLOGS }
                        )
                    }

                    ScreenState.WALLET_TRANSACTIONS -> {
                        WalletTransactionsScreen(
                            walletBalance = walletBalance,
                            transactions = walletTransactions,
                            onRechargeClick = { showWalletSheet = true },
                            onBack = { screenState = ScreenState.MAIN_TAB }
                        )
                    }

                    ScreenState.ORDER_HISTORY -> {
                        OrderHistoryScreen(
                            orders = orderHistoryItems,
                            onBack = { screenState = ScreenState.MAIN_TAB },
                            onRebook = { item ->
                                viewModel.addNotification(
                                    title = "Rebooking ${item.titleName}",
                                    message = "Rebooking initiated for ${item.titleName}",
                                    category = "BOOKING"
                                )
                                showNotificationsSheet = true
                            }
                        )
                    }

                    ScreenState.LANGUAGE -> {
                        LanguageSelectionScreen(
                            currentLanguage = selectedLanguage,
                            languages = viewModel.availableLanguages,
                            onSelectLanguage = { langCode ->
                                viewModel.setLanguage(langCode)
                                screenState = ScreenState.MAIN_TAB
                            },
                            onBack = { screenState = ScreenState.MAIN_TAB }
                        )
                    }

                    ScreenState.PANCHANG -> {
                        PanchangDetailScreen(
                            panchang = panchang
                        )
                    }

                    ScreenState.SPLASH -> {
                        DharmaSplashScreen(
                            onSplashFinished = {
                                screenState = if (authUser.isGuest) ScreenState.LOGIN else ScreenState.MAIN_TAB
                            }
                        )
                    }

                    ScreenState.LOGIN -> {
                        DharmaLoginScreen(
                            onLoginSuccess = { phone, name ->
                                viewModel.loginWithPhone(phone, name)
                                screenState = ScreenState.MAIN_TAB
                            },
                            onSkipLogin = {
                                screenState = ScreenState.MAIN_TAB
                            }
                        )
                    }

                    ScreenState.LIVE_CHAT_CONSULTATION -> {
                        LiveChatConsultationScreen(
                            astrologer = activeAstrologer ?: astrologers.first(),
                            walletBalance = walletBalance.toInt(),
                            onEndChat = { screenState = ScreenState.MAIN_TAB },
                            onNavigateToWallet = { showWalletSheet = true }
                        )
                    }

                    ScreenState.SAVED_WISHLIST -> {
                        SavedWishlistScreen(
                            onBack = { screenState = ScreenState.MAIN_TAB },
                            onNavigateToItem = { id, cat -> }
                        )
                    }

                    ScreenState.REFER_AND_EARN -> {
                        ReferAndEarnScreen(
                            walletBalance = walletBalance.toInt(),
                            onBack = { screenState = ScreenState.MAIN_TAB }
                        )
                    }

                    ScreenState.COUPONS_OFFERS -> {
                        CouponsAndOffersScreen(
                            onBack = { screenState = ScreenState.MAIN_TAB }
                        )
                    }

                    ScreenState.HELP_SUPPORT -> {
                        HelpAndSupportScreen(
                            onBack = { screenState = ScreenState.MAIN_TAB }
                        )
                    }

                    ScreenState.ABOUT_DHARMA -> {
                        AboutDharmaDarshanScreen(
                            onBack = { screenState = ScreenState.MAIN_TAB }
                        )
                    }

                    ScreenState.PRIVACY_POLICY -> {
                        PrivacyPolicyScreen(
                            onBack = { screenState = ScreenState.MAIN_TAB }
                        )
                    }

                    ScreenState.TERMS_CONDITIONS -> {
                        TermsAndConditionsScreen(
                            onBack = { screenState = ScreenState.MAIN_TAB }
                        )
                    }
                }
            }
        }
    }

    // Auth Interception Dialog
    if (showAuthDialog) {
        FaithTechAuthDialog(
            reasonText = authReasonText,
            onDismiss = {
                showAuthDialog = false
                pendingGuestAction = null
            },
            onLoginSuccess = { phoneOrName, isSso ->
                if (isSso) {
                    viewModel.loginWithSso("Google")
                } else {
                    viewModel.loginWithPhone(phoneOrName)
                }
                showAuthDialog = false
                pendingGuestAction?.invoke()
                pendingGuestAction = null
            }
        )
    }

    // Wallet Recharge Sheet
    if (showWalletSheet) {
        FaithTechWalletSheet(
            currentBalance = walletBalance,
            onAddMoney = { amount -> viewModel.addWalletMoney(amount) },
            onDismiss = { showWalletSheet = false }
        )
    }

    // Notifications Center Sheet
    if (showNotificationsSheet) {
        FaithTechNotificationsSheet(
            notifications = notifications,
            onMarkAllRead = { viewModel.markNotificationsRead() },
            onDismiss = { showNotificationsSheet = false }
        )
    }
}
