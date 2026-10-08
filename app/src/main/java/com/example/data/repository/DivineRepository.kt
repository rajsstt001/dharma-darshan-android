package com.example.data.repository

import android.content.Context
import androidx.room.Room
import com.example.R
import com.example.data.ai.GeminiClient
import com.example.data.db.BookingEntity
import com.example.data.db.CartItemEntity
import com.example.data.db.DivineDatabase
import com.example.data.db.DonationEntity
import com.example.data.db.FavoriteEntity
import com.example.data.model.AuthUser
import com.example.data.model.BhajanTrack
import com.example.data.model.ChadhavaItem
import com.example.data.model.DonationCause
import com.example.data.model.FamilyMember
import com.example.data.model.LiveDarshanStream
import com.example.data.model.NotificationItem
import com.example.data.model.PaidAddon
import com.example.data.model.PanchangInfo
import com.example.data.model.Pandit
import com.example.data.model.PoojaService
import com.example.data.model.PrasadItem
import com.example.data.model.Temple
import com.example.data.model.UpcomingPooja
import com.example.data.model.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class DivineRepository(context: Context) {
    private val db = Room.databaseBuilder(
        context.applicationContext,
        DivineDatabase::class.java,
        "faith_tech.db"
    ).fallbackToDestructiveMigration().build()

    private val dao = db.divineDao()

    val allBookings: Flow<List<BookingEntity>> = dao.getAllBookings()
    val cartItems: Flow<List<CartItemEntity>> = dao.getCartItems()
    val allDonations: Flow<List<DonationEntity>> = dao.getAllDonations()
    val favorites: Flow<List<FavoriteEntity>> = dao.getAllFavorites()

    private val _currentRole = MutableStateFlow(UserRole.DEVOTEE)
    val currentRole: StateFlow<UserRole> = _currentRole.asStateFlow()

    private val _authUser = MutableStateFlow(AuthUser(isGuest = true, name = "Guest Devotee"))
    val authUser: StateFlow<AuthUser> = _authUser.asStateFlow()

    private val _walletBalance = MutableStateFlow(2450.0)
    val walletBalance: StateFlow<Double> = _walletBalance.asStateFlow()

    private val _rewardPoints = MutableStateFlow(1280)
    val rewardPoints: StateFlow<Int> = _rewardPoints.asStateFlow()

    private val _selectedLanguage = MutableStateFlow("English")
    val selectedLanguage: StateFlow<String> = _selectedLanguage.asStateFlow()

    private val _followedAstrologerIds = MutableStateFlow<Set<String>>(setOf("ast1", "ast2"))
    val followedAstrologerIds: StateFlow<Set<String>> = _followedAstrologerIds.asStateFlow()

    fun toggleFollowAstrologer(id: String) {
        val current = _followedAstrologerIds.value.toMutableSet()
        if (current.contains(id)) {
            current.remove(id)
        } else {
            current.add(id)
        }
        _followedAstrologerIds.value = current
    }

    fun setLanguage(lang: String) {
        _selectedLanguage.value = lang
    }

    private val _walletTransactions = MutableStateFlow<List<WalletTransaction>>(
        listOf(
            WalletTransaction("TX10092", "Recharge via UPI (GPay)", "05 Aug 2026", "11:15 AM", 2000.0, true, "DD-UPI-99201", 2450.0, "UPI / GPay", "Success", "Added money to DharmaDarshan wallet"),
            WalletTransaction("TX10091", "Chat with Acharya Kamalendra", "04 Aug 2026", "04:30 PM", 150.0, false, "DD-AST-88392", 450.0, "Wallet Balance", "Success", "10 mins Vedic Astrology consultation"),
            WalletTransaction("TX10090", "Booked Kashi Vishwanath Pooja", "02 Aug 2026", "09:20 AM", 2100.0, false, "DD-POOJA-3392", 600.0, "Wallet Balance", "Success", "Maha Rudrabhishek with Prasad Delivery"),
            WalletTransaction("TX10089", "DharmaDarshan Welcome Bonus", "01 Aug 2026", "08:00 AM", 100.0, true, "DD-BONUS-001", 2700.0, "Cashback", "Success", "New Devotee Signup Promo Cashback"),
            WalletTransaction("TX10088", "Recharge via HDFC Credit Card", "28 Jul 2026", "06:45 PM", 1000.0, true, "DD-CC-44821", 2600.0, "Credit Card", "Success", "Wallet top-up")
        )
    )
    val walletTransactions: StateFlow<List<WalletTransaction>> = _walletTransactions.asStateFlow()

    val availableLanguages = listOf(
        AppLanguage("en", "English", "English"),
        AppLanguage("hi", "हिंदी", "Hindi"),
        AppLanguage("pa", "ਪੰਜਾਬੀ", "Punjabi"),
        AppLanguage("mr", "मराठी", "Marathi"),
        AppLanguage("te", "తెలుగు", "Telugu"),
        AppLanguage("kn", "ಕನ್ನಡ", "Kannada"),
        AppLanguage("bn", "বাংলা", "Bengali"),
        AppLanguage("ta", "தமிழ்", "Tamil"),
        AppLanguage("ml", "മലയാളം", "Malayalam"),
        AppLanguage("gu", "ગુજરાતી", "Gujarati"),
        AppLanguage("or", "ଓଡ଼ିଆ", "Odia")
    )

    val astrologers = listOf(
        Astrologer("ast1", "Acharya Kamalendra", 0, 14, listOf("Hindi", "English", "Sanskrit"), listOf("Vedic", "Prashna", "Nadi", "Lal Kitab"), 4.9f, 1840, 15.0, true, true, "Male", bio = "Gold Medalist from BHU. Specialist in Career, Marriage compatibility, and Lal Kitab remedies."),
        Astrologer("ast2", "Dr. Radhika Shastri", 0, 18, listOf("English", "Hindi", "Marathi", "Gujarati"), listOf("Numerology", "Vastu", "Tarot", "KP System"), 4.95f, 3120, 22.0, true, true, "Female", bio = "International Vastu & Numerology Consultant. Guided over 25,000+ devotees across the globe."),
        Astrologer("ast3", "Pandit Anand Sharma", 0, 11, listOf("Hindi", "Telugu", "Kannada"), listOf("Jaimini", "Dosha Shanti", "Gemstone Therapy"), 4.8f, 940, 12.0, false, true, "Male", bio = "Hereditary astrologer from Tirupati. Expert in Sade Sati remedies and Manglik dosha remedies."),
        Astrologer("ast4", "Guruma Sunita Devi", 0, 22, listOf("Hindi", "Bengali", "English"), listOf("Palmistry", "Prashna Kundli", "Medical Astrology"), 4.98f, 4290, 30.0, true, true, "Female", bio = "Renowned Spiritual Healer & Vedic Astrologer. Expert in birth chart rectification and health insights."),
        Astrologer("ast5", "Swami Vishwanand", 0, 16, listOf("Hindi", "Tamil", "English"), listOf("Vedic Astrology", "Nakshatra Nadi", "Vastu Shastra"), 4.87f, 1560, 18.0, true, true, "Male", bio = "Senior Astrologer and Vastu master from Haridwar. Guiding business growth and planetary alignment."),
        Astrologer("ast6", "Acharya Devraj Vashishta", 0, 20, listOf("Hindi", "Sanskrit", "English"), listOf("Vedic", "Kundli", "Face Reading", "Graha Shanti"), 4.93f, 2100, 25.0, true, true, "Male", bio = "Specialist in Mahadasha predictions and high-level corporate career guidance."),
        Astrologer("ast7", "Dr. Meenakshi Sundaram", 0, 15, listOf("Tamil", "English", "Hindi"), listOf("Nadi Jyotish", "KP System", "Prashna"), 4.89f, 1420, 20.0, true, true, "Female", bio = "Descendant of Agastya Nadi lineage. Known for accurate timing of life events."),
        Astrologer("ast8", "Pandit Suresh Joshi", 0, 19, listOf("Hindi", "Gujarati", "Marathi"), listOf("Lal Kitab", "Gemology", "Vastu"), 4.85f, 1780, 18.0, false, true, "Male", bio = "Lal Kitab remedy specialist. Simplified remedies for financial growth."),
        Astrologer("ast9", "Acharyaa Gayatri Devi", 0, 12, listOf("English", "Hindi", "Punjabi"), listOf("Tarot", "Numerology", "Angel Reading"), 4.91f, 1950, 16.0, true, true, "Female", bio = "Intuitive Tarot card reader and cosmic life coach for relationships and career."),
        Astrologer("ast10", "Dr. Rajeshwar Prasad", 0, 25, listOf("Hindi", "English"), listOf("Financial Astro", "Corporate Vastu", "Vedic"), 4.97f, 5100, 35.0, true, true, "Male", bio = "Astro-consultant to top industrialists and political leaders."),
        Astrologer("ast11", "Pandit Hemant Trivedi", 0, 13, listOf("Hindi", "Gujarati"), listOf("Kundli Matching", "Guna Milan", "Muhurat"), 4.82f, 1120, 14.0, true, true, "Male", bio = "Matchmaking expert with 36 Guna analysis and marriage timing forecast."),
        Astrologer("ast12", "Guruma Annapurna Devi", 0, 21, listOf("Hindi", "Bengali"), listOf("Vedic", "Shakti Sadhana", "Prashna"), 4.94f, 2890, 28.0, true, true, "Female", bio = "Spiritual guidance for mental peace, family harmony and Shakti remedies."),
        Astrologer("ast13", "Swami Chidambaram", 0, 28, listOf("Sanskrit", "Tamil", "English"), listOf("Vedic", "Nadi", "Palmistry"), 4.99f, 6200, 40.0, false, true, "Male", bio = "Veteran Vedic scholar from Kanchi Puram. Deep scriptural insights."),
        Astrologer("ast14", "Acharya Vivek Pandey", 0, 10, listOf("Hindi", "English"), listOf("KP System", "Ashtakvarga", "Prashna"), 4.79f, 880, 12.0, true, true, "Male", bio = "Scientific KP System astrologer specializing in job promotion & exam success."),
        Astrologer("ast15", "Dr. Ananya Mukherjee", 0, 17, listOf("Bengali", "English", "Hindi"), listOf("Medical Astrology", "Gemstones", "Palmistry"), 4.88f, 1640, 22.0, true, true, "Female", bio = "Certified Gemologist and astro-health consultant."),
        Astrologer("ast16", "Pandit Rameshwar Jha", 0, 22, listOf("Hindi", "Maithili", "Sanskrit"), listOf("Karmakand", "Dosha Shanti", "Vedic"), 4.90f, 2300, 20.0, true, true, "Male", bio = "Expert in Rahu-Ketu Shanti, Pitra Dosha remedies, and Vedic mantras."),
        Astrologer("ast17", "Acharya Bhavanishankar", 0, 16, listOf("Hindi", "Marathi"), listOf("Trikal Jyotish", "Prashna", "Face Reading"), 4.86f, 1310, 17.0, true, true, "Male", bio = "Face reading & Instant Prashna Kundli specialist from Nashik."),
        Astrologer("ast18", "Dr. Kavita Deshmukh", 0, 14, listOf("Marathi", "Hindi", "English"), listOf("KP System", "Vastu Shastra", "Numerology"), 4.87f, 1500, 18.0, false, true, "Female", bio = "Commercial Vastu & Residential Vastu layout planning expert."),
        Astrologer("ast19", "Pandit Venkatesh Iyer", 0, 20, listOf("Tamil", "Telugu", "English"), listOf("South Indian Nadi", "Prasna", "Jaimini"), 4.92f, 2400, 24.0, true, true, "Male", bio = "Nadi palm leaf reader from Chidambaram. Accurate timing of marriage."),
        Astrologer("ast20", "Acharyaa Malini Sen", 0, 11, listOf("Bengali", "Hindi", "English"), listOf("Vedic", "Tarot", "Gemology"), 4.81f, 980, 15.0, true, true, "Female", bio = "Love relationship & soulmate compatibility guidance."),
        Astrologer("ast21", "Swami Ramkrishna Shastri", 0, 26, listOf("Hindi", "Sanskrit"), listOf("Vedic", "Gita Philosophy", "Graha Remedies"), 4.96f, 3800, 30.0, true, true, "Male", bio = "Haridwar Ashram scholar. Spiritual upliftment and life purpose reading."),
        Astrologer("ast22", "Dr. Arvind Bhatt", 0, 18, listOf("Gujarati", "Hindi", "English"), listOf("Vastu", "Gemology", "Financial Astro"), 4.89f, 1750, 22.0, true, true, "Male", bio = "Astro-consultant for business expansion and stock market luck."),
        Astrologer("ast23", "Pandit Somnath Dikshit", 0, 24, listOf("Hindi", "Sanskrit"), listOf("Karmakand", "Kundli", "Navgrah Shanti"), 4.93f, 3100, 25.0, false, true, "Male", bio = "Varanasi Ghat head pujari and veteran astrological remedies guide."),
        Astrologer("ast24", "Acharya Parameshwar Sharma", 0, 15, listOf("Hindi", "Punjabi"), listOf("Palmistry", "Medical Astro", "Vedic"), 4.84f, 1290, 16.0, true, true, "Male", bio = "Hand analysis and line reading specialist for health & wealth."),
        Astrologer("ast25", "Guruma Shakuntala Devi", 0, 23, listOf("Hindi", "English"), listOf("Intuitive Tarot", "Chakra Healer", "Numerology"), 4.95f, 4100, 32.0, true, true, "Female", bio = "Chakra balancing & Tarot guide for inner peace and mental clarity."),
        Astrologer("ast26", "Dr. Hariprasad Rao", 0, 19, listOf("Telugu", "English", "Hindi"), listOf("KP System", "Nadi", "Vastu"), 4.90f, 2050, 21.0, true, true, "Male", bio = "Hyderabad-based KP system expert for abroad settlement & visa timing."),
        Astrologer("ast27", "Pandit Brijmohan Mishra", 0, 13, listOf("Hindi", "Bhojpuri"), listOf("Lal Kitab", "Rahu Remedies", "Vedic"), 4.83f, 1150, 14.0, true, true, "Male", bio = "Lal Kitab totke & simple house remedies for evil eye removal."),
        Astrologer("ast28", "Acharyaa Sunanda Nair", 0, 17, listOf("Malayalam", "English", "Tamil"), listOf("Kerala Prashna", "Ashtamangala", "Vedic"), 4.91f, 1880, 23.0, false, true, "Female", bio = "Authentic Kerala Ashtamangala Prashnam for lost items & health."),
        Astrologer("ast29", "Swami Krishnanand Saraswati", 0, 30, listOf("Hindi", "Sanskrit", "English"), listOf("Vedic", "Upanishad", "Graha Shanti"), 4.98f, 7400, 45.0, true, true, "Male", bio = "Vedic Seer & Master of Ashtakvarga planetary strength analysis."),
        Astrologer("ast30", "Dr. Alok Kumar Jha", 0, 16, listOf("Hindi", "English"), listOf("Financial Astro", "Share Market", "Vedic"), 4.88f, 1670, 20.0, true, true, "Male", bio = "Astro-financial forecaster for crypto, stocks, and business investments."),
        Astrologer("ast31", "Pandit Dayanand Shastri", 0, 21, listOf("Hindi", "Sanskrit"), listOf("Sade Sati", "Manglik", "Kaal Sarp"), 4.92f, 2600, 22.0, true, true, "Male", bio = "Shani Dev worshipper and Sade Sati pacification specialist."),
        Astrologer("ast32", "Acharya Vikrant Tyagi", 0, 12, listOf("Hindi", "English"), listOf("Vastu Shastra", "Industrial Vastu", "Vedic"), 4.80f, 920, 15.0, true, true, "Male", bio = "Factory & Office Vastu layout specialist without structural changes."),
        Astrologer("ast33", "Guruma Priyadarshini", 0, 14, listOf("Hindi", "English", "Odia"), listOf("Numerology", "Name Correction", "Tarot"), 4.86f, 1430, 17.0, false, true, "Female", bio = "Newborn baby name correction and brand business name numerology."),
        Astrologer("ast34", "Dr. Subhash Chandra Roy", 0, 22, listOf("Bengali", "English", "Hindi"), listOf("Vedic", "Career Guide", "Jaimini"), 4.94f, 3200, 26.0, true, true, "Male", bio = "Government job prediction & civil service examination astro timing."),
        Astrologer("ast35", "Pandit Nitin Joshi", 0, 10, listOf("Hindi", "Marathi"), listOf("Graha Shanti", "Hawan Rituals", "Vedic"), 4.78f, 760, 11.0, true, true, "Male", bio = "Quick horoscope remedies and home pooja rituals guide."),
        Astrologer("ast36", "Acharyaa Vandana Gupta", 0, 15, listOf("Hindi", "English"), listOf("Prashna Kundli", "Business Dates", "Gemology"), 4.87f, 1540, 18.0, true, true, "Female", bio = "Instant query resolution for property buying & land deals."),
        Astrologer("ast37", "Swami Brahmadev", 0, 27, listOf("Hindi", "Sanskrit"), listOf("Sannyasi Astro", "Meditation", "Vedic"), 4.97f, 4900, 35.0, true, true, "Male", bio = "Spiritual initiation, kundalini awakening guidance & horoscope."),
        Astrologer("ast38", "Dr. Koteshwara Rao", 0, 18, listOf("Telugu", "English"), listOf("KP System", "Nadi Expert", "Astakvarga"), 4.89f, 1810, 22.0, false, true, "Male", bio = "Precise sub-lord theory analysis in KP system for exact event dates."),
        Astrologer("ast39", "Pandit Janardan Bhat", 0, 20, listOf("Hindi", "Marathi"), listOf("Siddhivinayak Priest", "Vedic", "Ganesha Pooja"), 4.91f, 2150, 20.0, true, true, "Male", bio = "Removing hurdles and legal issues through Ganapati Astro remedies."),
        Astrologer("ast40", "Acharya Yogesh Pathak", 0, 13, listOf("Hindi", "English"), listOf("Western & Vedic Fusion", "Tarot", "Kundli"), 4.82f, 1050, 15.0, true, true, "Male", bio = "Modern astrological perspective combining Tropical & Sidereal zodiacs."),
        Astrologer("ast41", "Guruma Padmavati", 0, 25, listOf("Hindi", "Telugu", "Kannada"), listOf("Sri Vidya", "Astro Healer", "Vedic"), 4.96f, 3950, 30.0, true, true, "Female", bio = "Divine Mother Sri Vidya devotee providing benevolent horoscope readings."),
        Astrologer("ast42", "Dr. Girish Agrawal", 0, 17, listOf("Hindi", "English"), listOf("Gemstone Specialist", "Mineralogy", "Vedic"), 4.88f, 1690, 24.0, true, true, "Male", bio = "Scientific gemstone testing and planetary ratna recommendation."),
        Astrologer("ast43", "Pandit Umashankar Dubey", 0, 23, listOf("Hindi", "Sanskrit"), listOf("Pitra Dosha", "Kaal Sarp", "Gaya Shraddh"), 4.93f, 2800, 25.0, false, true, "Male", bio = "Gaya Tirth priest specializing in ancestral peace & Pitra Rin removal."),
        Astrologer("ast44", "Acharyaa Deepa Pillai", 0, 16, listOf("Malayalam", "English"), listOf("Kerala Astro", "Ashtamangala", "Gemology"), 4.90f, 1720, 22.0, true, true, "Female", bio = "Kerala style horoscope matching and family dispute resolution."),
        Astrologer("ast45", "Swami Satyanand", 0, 29, listOf("Hindi", "Sanskrit", "English"), listOf("Himalayan Seer", "Kundli", "Yogi"), 4.99f, 6800, 42.0, true, true, "Male", bio = "Rishikesh Himalayan monk offering profound soul purpose readings."),
        Astrologer("ast46", "Dr. Bhaskaracharya II", 0, 21, listOf("Hindi", "Sanskrit", "English"), listOf("Astronomical Jyotish", "Mathematical Vedic"), 4.94f, 3100, 28.0, true, true, "Male", bio = "Panchang author and mathematical astronomy astrologer."),
        Astrologer("ast47", "Pandit Keshav Murthy", 0, 19, listOf("Kannada", "English", "Telugu"), listOf("Kannada Astro", "Muhurat Master", "Vedic"), 4.87f, 1580, 19.0, true, true, "Male", bio = "Bengaluru senior astrologer for house construction & vehicle buying muhurat."),
        Astrologer("ast48", "Acharya Tarun Chatterji", 0, 14, listOf("Bengali", "Hindi", "English"), listOf("Graha Remedies", "Vedic", "Palmistry"), 4.83f, 1180, 16.0, false, true, "Male", bio = "Kolkata Kalighat lineage remedies for business obstacles."),
        Astrologer("ast49", "Guruma Yashodhara Devi", 0, 18, listOf("Hindi", "English"), listOf("Child Birth Astro", "Baby Names", "Vedic"), 4.89f, 1920, 22.0, true, true, "Female", bio = "Santan Gopal pooja guidance and progeny astrological analysis."),
        Astrologer("ast50", "Dr. Manish Dwivedi", 0, 15, listOf("Hindi", "English"), listOf("Vedic Astro", "Corporate Career", "Vastu"), 4.86f, 1370, 18.0, true, true, "Male", bio = "IT & Corporate employee career promotion timing and job switch astro."),
        Astrologer("ast51", "Pandit Satish Kulkarni", 0, 17, listOf("Marathi", "Hindi"), listOf("Mahalakshmi Astro", "Wealth Remedies", "Vedic"), 4.88f, 1610, 20.0, true, true, "Male", bio = "Kolhapur Mahalakshmi grace remedies for debt clearance."),
        Astrologer("ast52", "Acharyaa Shalini Verma", 0, 10, listOf("Hindi", "English"), listOf("Tarot", "Love Relationship", "Angel Healer"), 4.80f, 890, 13.0, true, true, "Female", bio = "Love horoscope, breakup healing and twin flame connection tarot.")
    )

    val vedicTools = listOf(
        VedicToolItem("vt1", "Kundli / Janam Patri", "Detailed Birth Chart & Planetary Positions", ToolCategory.VEDIC, "📜", "Generate instant birth chart with D1, D9, D10 charts & Dasha predictions.", isPopular = true),
        VedicToolItem("vt2", "Daily Horoscope", "Rashifal for 12 Zodiac Signs", ToolCategory.VEDIC, "♈", "Accurate daily predictions for Career, Love, Finance, and Health.", isPopular = true),
        VedicToolItem("vt3", "Panchang & Choghadiya", "Daily Tithi, Muhurat & Rahu Kaal", ToolCategory.VEDIC, "📅", "Complete Hindu Calendar with auspicious and inauspicious time slots."),
        VedicToolItem("vt4", "Shubh Muhurat", "Best Timing for Marriage, Vehicle & House", ToolCategory.VEDIC, "⏳", "Find divine auspicious timings for new beginnings."),
        VedicToolItem("vt5", "Numerology Calculator", "Life Path, Name & Driver Number", ToolCategory.VEDIC, "🔢", "Discover lucky numbers and planetary destiny."),
        VedicToolItem("vt6", "Nakshatra Finder", "Birth Star & Deity Alignment", ToolCategory.VEDIC, "⭐", "Know your birth Nakshatra characteristics and ruling deity."),
        VedicToolItem("vt7", "Gemstone Recommendation", "Ratna Suggestion as per Kundli", ToolCategory.VEDIC, "💎", "Certified gemstone remedies based on weak Lagna lords."),
        VedicToolItem("vt8", "Rudraksha Guide", "Sacred Bead Recommendation", ToolCategory.VEDIC, "📿", "Find which Mukhi Rudraksha brings peace and prosperity."),
        VedicToolItem("vt9", "Dosha Analysis", "Manglik, Kaal Sarp & Pitra Dosha Check", ToolCategory.VEDIC, "🔥", "Identify planetary doshas and exact Vedic remedy pujas.")
    )

    val aiServices = listOf(
        VedicToolItem("ai1", "AI Horoscope 2026", "Personalized Gemini Astrological Forecast", ToolCategory.AI, "🤖", "Instant deep-dive AI forecast based on exact birth details.", isPopular = true),
        VedicToolItem("ai2", "AI Kundli Analysis", "Comprehensive AI Birth Chart Breakdown", ToolCategory.AI, "✨", "Gemini AI analyzes yogas, rajyogas, and planetary strengths.", isPopular = true),
        VedicToolItem("ai3", "AI Chat with Spiritual Guru", "24/7 Spiritual Guidance & Advice", ToolCategory.AI, "🧘", "Ask any question regarding Bhagavad Gita, Dharma & Life decisions."),
        VedicToolItem("ai4", "AI Relationship Compatibility", "Matchmaking & Guna Milan Report", ToolCategory.AI, "❤️", "36 Guna Milan analysis and emotional connection insight."),
        VedicToolItem("ai5", "AI Career & Business Prediction", "Job Change, Promotion & Business Luck", ToolCategory.AI, "💼", "Predict favorable business timing and job movements."),
        VedicToolItem("ai6", "AI Health Insights", "Ayurvedic & Astrological Wellness", ToolCategory.AI, "🌿", "Planetary impact on health and customized preventive steps."),
        VedicToolItem("ai7", "AI Business Guidance", "Stock Market & Commercial Luck", ToolCategory.AI, "📈", "Astro-financial insights and auspicious launch dates."),
        VedicToolItem("ai8", "AI Baby Name Generator", "Nakshatra-based Auspicious Names", ToolCategory.AI, "👶", "Generate meaningful Sanskrit and modern divine baby names."),
        VedicToolItem("ai9", "AI Daily Horoscope Note", "Custom Morning Spiritual Note", ToolCategory.AI, "☀️", "Get daily personalized morning guidance and mantra.")
    )

    val blogPosts = listOf(
        BlogPost(
            id = "blog1",
            title = "Significance of Sravana Somvar and Maha Rudrabhishek Puja",
            category = "Festivals & Poojas",
            author = "Acharya Kamalendra",
            publishedDate = "04 Aug 2026",
            readingTime = "4 min read",
            imageRes = R.drawable.img_live_darshan_kashi_1785577744688,
            summary = "Discover why worshipping Lord Shiva during the holy Sravana month brings immense peace, prosperity, and removal of planetary obstacles.",
            content = "The month of Sravana holds immense spiritual significance in Sanatana Dharma. Worshipping Lord Shiva with Bilva leaves and performing Maha Rudrabhishek neutralizes malefic planetary transits, particularly Rahu and Saturn. Participating in live temple pujas allows devotees to connect with sacred energy vibrations regardless of physical location."
        ),
        BlogPost(
            id = "blog2",
            title = "How Saturn Retrograde (Shani Vakri) Affects Your Zodiac Sign",
            category = "Astrology & Planetary Transits",
            author = "Dr. Radhika Shastri",
            publishedDate = "02 Aug 2026",
            readingTime = "6 min read",
            imageRes = R.drawable.img_hero_divine_1785577730862,
            summary = "Detailed analysis of Saturn's current retrograde motion, affected zodiac signs, and simple everyday remedies to stay balanced.",
            content = "Saturn represents discipline, karma, and patience. When Shani goes Vakri (retrograde), past karma resurfaces for introspection. Remedies include chanting Shani Chalisa, lighting a mustard oil lamp under a Peepal tree every Saturday evening, and donating black sesame seeds to the needy."
        ),
        BlogPost(
            id = "blog3",
            title = "Vastu Tips for Home: Attract Wealth and Positivity in 2026",
            category = "Vastu Shastra",
            author = "Swami Vishwanand",
            publishedDate = "29 Jul 2026",
            readingTime = "5 min read",
            imageRes = R.drawable.img_pandit_banner_1785577758893,
            summary = "Simple Vastu alignment tips for your main entrance, temple area, and bedroom to enhance harmony and financial flow.",
            content = "The North-East direction (Ishan Kon) is the seat of divine energy and Lord Kuber. Keep this corner clean, clutter-free, and illuminated. Placing a sacred Tulsi plant or brass Swastika symbol at the main entrance prevents negative energy from entering."
        )
    )

    val orderHistoryItems = listOf(
        OrderHistoryItem(
            bookingId = "DD-BK-99201",
            titleName = "Maha Rudrabhishek at Kashi Vishwanath",
            dateText = "06 Aug 2026",
            timeText = "09:00 AM - 10:30 AM",
            serviceType = "Online Pooja",
            durationText = "90 Mins",
            amount = 2100.0,
            status = "Confirmed",
            paymentStatus = "Paid",
            rating = 5.0f
        ),
        OrderHistoryItem(
            bookingId = "DD-AST-88392",
            titleName = "Acharya Kamalendra (Vedic Consultation)",
            dateText = "04 Aug 2026",
            timeText = "04:30 PM",
            serviceType = "Astrologer Chat",
            durationText = "15 Mins",
            amount = 225.0,
            status = "Completed",
            paymentStatus = "Paid",
            rating = 4.9f
        ),
        OrderHistoryItem(
            bookingId = "DD-PRASAD-4410",
            titleName = "Special Kashi Prasad Box Delivery",
            dateText = "28 Jul 2026",
            timeText = "Delivered on 30 Jul",
            serviceType = "Prasad Order",
            durationText = "Standard Courier",
            amount = 501.0,
            status = "Completed",
            paymentStatus = "Paid",
            rating = 4.8f
        )
    )

    private val _familyMembers = MutableStateFlow<List<FamilyMember>>(
        listOf(
            FamilyMember("fam1", "Sunita Sharma", "Wife", "Kashyap", "Kanya (Virgo)"),
            FamilyMember("fam2", "Aarav Sharma", "Son", "Kashyap", "Simha (Leo)")
        )
    )
    val familyMembers: StateFlow<List<FamilyMember>> = _familyMembers.asStateFlow()

    private val _notifications = MutableStateFlow<List<NotificationItem>>(
        listOf(
            NotificationItem("n1", "Pooja Confirmed! 🙏", "Your Maha Rudrabhishek at Kashi Vishwanath is scheduled for tomorrow 09:00 AM.", "10 mins ago", "BOOKING", isRead = false),
            NotificationItem("n2", "Wallet Recharged ₹2,000", "Added ₹2,000 via UPI successfully to Faith Tech Wallet.", "1 hour ago", "WALLET", isRead = false),
            NotificationItem("n3", "Ganesh Chaturthi Special", "Book special Chadhava & Modak Prasad before 05 Aug.", "1 day ago", "FESTIVAL", isRead = false),
            NotificationItem("n4", "Live Aarti Alert 🔴", "Evening Ganga Aarti live streaming is about to begin.", "2 days ago", "REMINDER", isRead = true)
        )
    )
    val notifications: StateFlow<List<NotificationItem>> = _notifications.asStateFlow()

    fun loginWithPhone(phone: String, name: String = "Rajesh Sharma"): AuthUser {
        val user = AuthUser(
            isGuest = false,
            name = name,
            phone = phone,
            email = "rajesh.sharma@faithtech.com",
            gotra = "Kashyap",
            rashi = "Tula (Libra)",
            isSso = false
        )
        _authUser.value = user
        return user
    }

    fun loginWithSso(provider: String): AuthUser {
        val user = AuthUser(
            isGuest = false,
            name = "Rajesh Sharma ($provider)",
            phone = "+91 9876543210",
            email = "rajesh.sso@faithtech.com",
            gotra = "Kashyap",
            rashi = "Mesh (Aries)",
            isSso = true
        )
        _authUser.value = user
        return user
    }

    fun logout() {
        _authUser.value = AuthUser(isGuest = true, name = "Guest Devotee")
    }

    fun updateUserProfile(user: AuthUser) {
        _authUser.value = user
    }

    fun setRole(role: UserRole) {
        _currentRole.value = role
    }

    fun addWalletMoney(amount: Double) {
        _walletBalance.value += amount
        val newTx = WalletTransaction(
            id = "tx_" + System.currentTimeMillis(),
            title = "Top-up via Wallet UPI",
            dateText = "Today",
            amount = amount,
            isCredit = true,
            referenceId = "FT-TOP-" + System.currentTimeMillis().toString().takeLast(6)
        )
        _walletTransactions.value = listOf(newTx) + _walletTransactions.value
    }

    fun deductWalletMoney(amount: Double): Boolean {
        if (_walletBalance.value >= amount) {
            _walletBalance.value -= amount
            val newTx = WalletTransaction(
                id = "tx_" + System.currentTimeMillis(),
                title = "Payment for Pooja/Chadhava",
                dateText = "Today",
                amount = amount,
                isCredit = false,
                referenceId = "FT-PAY-" + System.currentTimeMillis().toString().takeLast(6)
            )
            _walletTransactions.value = listOf(newTx) + _walletTransactions.value
            return true
        }
        return false
    }

    fun addFamilyMember(name: String, relation: String, gotra: String, rashi: String) {
        val member = FamilyMember("fam_" + System.currentTimeMillis(), name, relation, gotra, rashi)
        _familyMembers.value = _familyMembers.value + member
    }

    fun markNotificationsRead() {
        _notifications.value = _notifications.value.map { it.copy(isRead = true) }
    }

    fun addNotification(title: String, message: String, category: String = "GENERAL") {
        val newNotif = NotificationItem(
            id = "n_" + System.currentTimeMillis(),
            title = title,
            message = message,
            timestamp = "Just now",
            category = category,
            isRead = false
        )
        _notifications.value = listOf(newNotif) + _notifications.value
    }

    suspend fun bookPooja(
        bookingType: String,
        targetName: String,
        poojaName: String,
        dateText: String,
        timeSlot: String,
        devoteeName: String,
        gotra: String,
        sankalpName: String,
        amount: Double
    ): Long {
        val entity = BookingEntity(
            bookingType = bookingType,
            targetName = targetName,
            poojaName = poojaName,
            dateText = dateText,
            timeSlot = timeSlot,
            devoteeName = devoteeName,
            gotra = gotra,
            sankalpName = sankalpName,
            totalAmount = amount
        )
        _rewardPoints.value += (amount * 0.1).toInt()
        deductWalletMoney(amount)
        return dao.insertBooking(entity)
    }

    suspend fun addToCart(item: PrasadItem) {
        dao.insertCartItem(CartItemEntity(item.id, item.name, item.templeName, item.price, 1))
    }

    suspend fun removeFromCart(id: String) {
        dao.removeCartItem(id)
    }

    suspend fun clearCart() {
        dao.clearCart()
    }

    suspend fun makeDonation(causeTitle: String, category: String, amount: Double, isAnon: Boolean, donorName: String) {
        val receipt = "FT-DON-" + System.currentTimeMillis().toString().takeLast(6)
        deductWalletMoney(amount)
        dao.insertDonation(
            DonationEntity(
                causeTitle = causeTitle,
                category = category,
                amount = amount,
                isAnonymous = isAnon,
                donorName = donorName,
                dateText = "Aug 2, 2026",
                receiptId = receipt
            )
        )
    }

    suspend fun toggleFavorite(itemId: String, itemType: String, title: String, subtitle: String, isCurrentlyFav: Boolean) {
        if (isCurrentlyFav) {
            dao.removeFavorite(itemId)
        } else {
            dao.addFavorite(FavoriteEntity(itemId, itemType, title, subtitle))
        }
    }

    suspend fun askAiGuru(prompt: String): String {
        return GeminiClient.askGuru(prompt)
    }

    fun getPanchang(): PanchangInfo = PanchangInfo()

    fun getUpcomingPoojas(): List<UpcomingPooja> = listOf(
        UpcomingPooja(
            id = "up1",
            name = "Maha Rudrabhishek & Bhasma Aarti",
            templeName = "Kashi Vishwanath Temple",
            location = "Varanasi, UP",
            dateText = "Today (02 Aug 2026)",
            timeSlot = "06:00 PM - 07:30 PM",
            countdownMinutes = 165,
            devoteesParticipating = 2450,
            startingPrice = 2100.0,
            bannerRes = R.drawable.img_live_darshan_kashi_1785577744688,
            description = "Grand Shivling Abhishekam with Panchamrit, Belpatra, and sacred Bhasma recitation for peace, health, and liberation.",
            spiritualBenefits = listOf("Removes severe Rahu-Ketu and Shani Doshas", "Fulfills career & family desires", "Sanctifies home with divine aura"),
            priestName = "Acharya Devrat Sharma",
            priestTitle = "Chief Priest, Kashi Ghat"
        ),
        UpcomingPooja(
            id = "up2",
            name = "Sahasra Archana & Kalyanotsavam",
            templeName = "Tirumala Venkateswara",
            location = "Tirupati, AP",
            dateText = "Tomorrow (03 Aug 2026)",
            timeSlot = "08:30 AM - 10:30 AM",
            countdownMinutes = 980,
            devoteesParticipating = 4120,
            startingPrice = 3500.0,
            bannerRes = R.drawable.img_hero_divine_1785577730862,
            description = "1000 sacred Namavalis recited during the celestial marriage ritual of Lord Venkateswara.",
            spiritualBenefits = listOf("Ensures financial growth & debt relief", "Blesses newlyweds with harmony", "Brings Lord Vishnu's protection"),
            priestName = "Pt. Ranganathan Dixit",
            priestTitle = "Agama Veda Scholar"
        ),
        UpcomingPooja(
            id = "up3",
            name = "Siddhivinayak Atharvashirsha Hawan",
            templeName = "Siddhivinayak Temple",
            location = "Mumbai, MH",
            dateText = "05 Aug 2026 (Ganesh Chaturthi)",
            timeSlot = "09:00 AM - 10:30 AM",
            countdownMinutes = 3400,
            devoteesParticipating = 1890,
            startingPrice = 1500.0,
            bannerRes = R.drawable.img_hero_divine_1785577730862,
            description = "Sacred fire offering with 108 recitations of Ganapati Atharvashirsha Stotram.",
            spiritualBenefits = listOf("Removes all hurdles in new projects", "Bestows sharp intellect & luck", "Brings harmony in household"),
            priestName = "Pt. Vinayak Bhat",
            priestTitle = "Siddhivinayak Pujari"
        )
    )

    fun getChadhavaItems(): List<ChadhavaItem> = listOf(
        ChadhavaItem("ch1", "Fresh Lotus & Belpatra Garland", "Sacred offerings for Shivling and Deities", "Flowers", 150.0, "🪷"),
        ChadhavaItem("ch2", "Desi Ghee Diya (Lamp Offering)", "Illuminates temple sanctum in devotee's name", "Diya", 100.0, "🪔"),
        ChadhavaItem("ch3", "Panchamrit & Pure Cow Milk", "5 sacred items for Holy Abhishekam", "Abhishekam", 120.0, "🥛"),
        ChadhavaItem("ch4", "Pure Honey & Saffron Water", "Offered for sweet fortune & wellbeing", "Offering", 180.0, "🍯"),
        ChadhavaItem("ch5", "Dry Fruits & Modak Prasadam", "Dedicated to Lord Ganesha & Pilgrims", "Prasad", 250.0, "🍡"),
        ChadhavaItem("ch6", "Temple Renovation Fund", "Contribute towards sacred sanctum upkeep", "Donation", 500.0, "🛕")
    )

    fun getPaidAddons(): List<PaidAddon> = listOf(
        PaidAddon("add1", "VIP Sankalp Name Reading", "Priest speaks your family names & Gotra during live ritual", 500.0, "📜"),
        PaidAddon("add2", "Fresh Flower Basket & Mala", "Offered directly on the deity idol during Aarti", 200.0, "🌸"),
        PaidAddon("add3", "Home Delivered Sacred Prasad Box", "Speed post delivery of blessed laddoo & kumkum", 350.0, "📦"),
        PaidAddon("add4", "108 Ghee Diyas Lighting", "Light 108 lamps around the temple courtyard", 1100.0, "🪔"),
        PaidAddon("add5", "Extra Annadanam Food Box", "Feed 5 hungry pilgrims in your name", 250.0, "🍲")
    )

    fun getTemples(): List<Temple> = listOf(
        Temple(
            id = "t1",
            name = "Kashi Vishwanath Temple",
            deity = "Lord Shiva",
            city = "Varanasi",
            state = "Uttar Pradesh",
            rating = 4.9f,
            reviewCount = 12450,
            history = "One of the twelve Jyotirlingas, located on the sacred western bank of the Ganges river in Varanasi.",
            timings = "03:00 AM - 11:00 PM",
            coverImageRes = R.drawable.img_live_darshan_kashi_1785577744688
        ),
        Temple(
            id = "t2",
            name = "Tirumala Venkateswara",
            deity = "Lord Vishnu",
            city = "Tirupati",
            state = "Andhra Pradesh",
            rating = 4.95f,
            reviewCount = 28900,
            history = "A grand landmark temple situated in the Venkatadri hill range, receiving millions of devotees globally.",
            timings = "02:30 AM - 01:30 AM",
            coverImageRes = R.drawable.img_hero_divine_1785577730862
        ),
        Temple(
            id = "t3",
            name = "Siddhivinayak Temple",
            deity = "Lord Ganesha",
            city = "Mumbai",
            state = "Maharashtra",
            rating = 4.85f,
            reviewCount = 18200,
            history = "A highly revered Ganesha shrine known for fulfilling earnest devotee vows (Navasacha Ganapati).",
            timings = "05:30 AM - 10:00 PM",
            coverImageRes = R.drawable.img_hero_divine_1785577730862
        ),
        Temple(
            id = "t4",
            name = "Kedarnath Dham",
            deity = "Lord Shiva",
            city = "Rudraprayag",
            state = "Uttarakhand",
            rating = 4.98f,
            reviewCount = 9500,
            history = "Nestled amidst the Himalayas, this ancient shrine is one of the premier Char Dhams.",
            timings = "04:00 AM - 09:00 PM",
            coverImageRes = R.drawable.img_hero_divine_1785577730862
        )
    )

    fun getPoojaServices(): List<PoojaService> = listOf(
        PoojaService("p1", "Maha Rudrabhishek Pooja", "Kashi Vishwanath Temple", "Lord Shiva", 2100.0, 90, "Sacred bathing of Shiva Lingam with panchamrit, holy Ganga water, bilva leaves and Vedic chanting.", listOf("Removes negative energies", "Brings health & mental peace", "Removes horoscope doshas"), true, 4.95f, R.drawable.img_live_darshan_kashi_1785577744688),
        PoojaService("p2", "Kalyanotsavam Seva", "Tirumala Venkateswara", "Lord Vishnu", 3500.0, 120, "Celestial wedding ceremony of Lord Venkateswara performed with traditional Vedic rites.", listOf("Marital bliss and harmony", "Family prosperity", "Removes financial obstacles"), true, 4.98f, R.drawable.img_hero_divine_1785577730862),
        PoojaService("p3", "Ganapati Atharvashirsha Hawan", "Siddhivinayak Temple", "Lord Ganesha", 1500.0, 60, "Special fire ceremony invoked with 108 recitation of Atharvashirsha for success.", listOf("Wisdom & career success", "Business growth", "Good beginnings"), true, 4.90f, R.drawable.img_pandit_banner_1785577758893),
        PoojaService("p4", "Satyanarayan Vrat Katha", "Home / Digital Pooja", "Lord Vishnu", 1100.0, 75, "Traditional Vrat katha ritual with panchamrit prasad and floral decoration.", listOf("General well-being", "New house blessing", "Gratitude offering"), true, 4.85f, R.drawable.img_prasad_pack_1785577773896),
        PoojaService("p5", "Kaal Sarp Dosha Shanti Pooja", "Trimbakeshwar Temple", "Lord Shiva", 3100.0, 150, "Authentic Vedic rituals performed at Trimbakeshwar to neutralize Rahu-Ketu Kaal Sarp Dosha.", listOf("Removes career delay & fears", "Brings peace in marriage", "Shields against unexpected accidents"), true, 4.92f, R.drawable.img_hero_divine_1785577730862),
        PoojaService("p6", "Navagraha Shanti Homa", "Navagraha Temple", "All 9 Planets", 2500.0, 110, "Pacification of all nine planetary lords using specific ghee offerings, herbs, and mantras.", listOf("Balances malefic Dasha periods", "Brings holistic prosperity", "Protects against ill-health"), true, 4.88f, R.drawable.img_pandit_banner_1785577758893),
        PoojaService("p7", "Kuber Lakshmi Dhan Prapti Pooja", "Kollur Mookambika", "Goddess Lakshmi", 2800.0, 90, "Special Lakshmi Kuber Yantra invocation for financial overflow and business stability.", listOf("Attracts wealth & luck", "Clears pending debts", "Boosts business profitability"), true, 4.94f, R.drawable.img_hero_divine_1785577730862),
        PoojaService("p8", "Maha Mrityunjaya Jaap & Hawan", "Mahakaleshwar Ujjain", "Lord Shiva", 4500.0, 180, "125,000 recitations of Maha Mrityunjaya Mantra for longevity and critical illness recovery.", listOf("Grants health & longevity", "Protects against severe diseases", "Removes fear of premature death"), true, 4.99f, R.drawable.img_live_darshan_kashi_1785577744688),
        PoojaService("p9", "Baglamukhi Shatru Vinash Pooja", "Pitambara Peeth Datia", "Goddess Baglamukhi", 5100.0, 120, "Powerful Tantrik & Vedic ritual for overcoming court cases, rivalries, and false accusations.", listOf("Ensures victory in legal disputes", "Silences malicious enemies", "Builds unshakeable authority"), true, 4.91f, R.drawable.img_hero_divine_1785577730862),
        PoojaService("p10", "Mangal Dosh Nivaran Pooja", "Mangalnath Temple Ujjain", "Lord Mars", 2100.0, 90, "Red coral and kumkum archana performed at birthplace of Mars to calm Manglik afflictions.", listOf("Facilitates timely marriage", "Resolves marital conflicts", "Controls temperament & anger"), true, 4.87f, R.drawable.img_pandit_banner_1785577758893),
        PoojaService("p11", "Chandi Homa & Durga Saptashati", "Kamakhya Temple", "Goddess Durga", 4200.0, 160, "Sacred 700 verses of Devi Mahatmyam recited with elaborate fire sacrifice.", listOf("Destroys black magic & evil eye", "Grants supreme protection", "Fulfills desires"), true, 4.96f, R.drawable.img_hero_divine_1785577730862),
        PoojaService("p12", "Rahu Ketu Shanti Pooja", "Kalahasti Temple", "Rahu Ketu", 2700.0, 100, "Serpent deity offerings and specialized Rahu-Ketu stotram chanting.", listOf("Protects from sudden losses", "Stabilizes mental peace", "Eliminates relationship strain"), true, 4.89f, R.drawable.img_live_darshan_kashi_1785577744688),
        PoojaService("p13", "Pitra Dosha Tarpan & Shanti", "Gaya Ghat", "Ancestors", 1800.0, 75, "Ancestral homage and pinda daan ritual at Gaya for peace of departed ancestors.", listOf("Clears family curses", "Brings harmony to offspring", "Unlocks delayed blessings"), true, 4.93f, R.drawable.img_pandit_banner_1785577758893),
        PoojaService("p14", "Saraswati Vidya Abhishekam", "Basar Temple", "Goddess Saraswati", 1250.0, 45, "Aksharabhyasam and honey abhishekam for academic brilliance and memory retention.", listOf("Boosts exam scores", "Enhances memory & focus", "Blesses with artistic skill"), true, 4.86f, R.drawable.img_prasad_pack_1785577773896),
        PoojaService("p15", "Sundarkand Path & Hanuman Chalisa", "Salasar Balaji", "Lord Hanuman", 1100.0, 60, "Rhythmic recitations of Sundarkand for courage, strength, and overcoming obstacles.", listOf("Instills courage & strength", "Removes ghost & negative fears", "Grants speedy task execution"), true, 4.92f, R.drawable.img_hero_divine_1785577730862),
        PoojaService("p16", "Annapurna Mahaprasad Seva", "Varanasi Annapurna", "Goddess Annapurna", 900.0, 30, "Offering food grains and feast sponsorship for pilgrims in Varanasi.", listOf("Ensures never-ending food abundance", "Clears past karmic debts", "Invokes divine grace"), true, 4.84f, R.drawable.img_prasad_pack_1785577773896),
        PoojaService("p17", "Subramanya Sashti Abhishekam", "Kukke Subramanya", "Lord Murugan", 2200.0, 90, "Milk and panchamrit abhishekam for Sarpa Dosha and skin ailments.", listOf("Heals skin problems", "Removes Sarpa Dosha", "Grants bravery and vitality"), true, 4.90f, R.drawable.img_hero_divine_1785577730862),
        PoojaService("p18", "Vishnu Sahasranama Parayana", "Guruvayur Temple", "Lord Vishnu", 1600.0, 60, "Recitation of 1,000 holy names of Lord Vishnu with tulsi leaves.", listOf("Guarantees peace of mind", "Brings family togetherness", "Attracts positive aura"), true, 4.87f, R.drawable.img_pandit_banner_1785577758893),
        PoojaService("p19", "Narasimha Kavacham Hawan", "Ahobilam Temple", "Lord Narasimha", 3300.0, 110, "Fiery protection ritual against extreme negativity and hidden enemies.", listOf("Invincible psychic shield", "Eliminates fear and paranoia", "Overcomes business rivalry"), true, 4.95f, R.drawable.img_hero_divine_1785577730862),
        PoojaService("p20", "Shani Graha Shanti & Oil Abhishekam", "Shani Shingnapur", "Lord Shani", 1500.0, 60, "Mustard oil abhishekam on rock idol for Sade Sati and Dhayya relief.", listOf("Mitigates Sade Sati severity", "Prevents career downfall", "Brings judicial fairness"), true, 4.89f, R.drawable.img_live_darshan_kashi_1785577744688),
        PoojaService("p21", "Dattatreya Jayanti Pooja", "Girnar Dham", "Lord Dattatreya", 2400.0, 90, "Trinity worship invoking Brahma, Vishnu, and Shiva for spiritual awakening.", listOf("Accelerates spiritual growth", "Provides ideal guru guidance", "Restores inner tranquility"), true, 4.91f, R.drawable.img_hero_divine_1785577730862),
        PoojaService("p22", "Lord Krishna Janmashtami Pooja", "Bankey Bihari Vrindavan", "Lord Krishna", 1900.0, 75, "Makhan, panchamrit, and lotus flower offerings to Lord Bankey Bihari.", listOf("Fills home with bliss & love", "Blesses with joyful children", "Removes sorrow and grief"), true, 4.97f, R.drawable.img_hero_divine_1785577730862),
        PoojaService("p23", "Mahakali Kavach Pooja", "Kalighat Kolkata", "Goddess Kali", 3800.0, 120, "Tantrik protection and red hibiscus flower archana.", listOf("Removes severe dark forces", "Grants fearlessness", "Brings rapid success"), true, 4.94f, R.drawable.img_hero_divine_1785577730862),
        PoojaService("p24", "Santana Gopal Progeny Blessing", "Dwarkadhish Temple", "Lord Krishna", 2900.0, 100, "Vedic chantings specifically for couples desiring healthy offspring.", listOf("Blesses couples with children", "Protects pregnancy wellness", "Promotes child intelligence"), true, 4.92f, R.drawable.img_pandit_banner_1785577758893),
        PoojaService("p25", "Vastu Shanti & Griha Pravesh", "Puri Jagannath Temple", "Lord Jagannath", 3500.0, 140, "Directional lord invocation for harmonious residential and office spaces.", listOf("Removes architectural Vastu doshas", "Attracts peace and happiness", "Energizes building premises"), true, 4.96f, R.drawable.img_hero_divine_1785577730862)
    )

    fun getPandits(): List<Pandit> = listOf(
        Pandit("pan1", "Acharya Devrat Sharma", "Veda Murti & Vedic Scholar", 22, 4.95f, 420, listOf("Sanskrit", "Hindi", "English"), listOf("🔱 Rudrabhishek", "🏠 Griha Pravesh", "🔥 Havan & Homam", "🌙 Navagraha Pooja", "🧭 Vastu Shanti"), "Varanasi, UP", 1500.0, 3100.0, 5100.0, R.drawable.img_pandit_banner_1785577758893, 1250),
        Pandit("pan2", "Pt. Ranganathan Dixit", "Agama Shastra & Karmakandi", 18, 4.88f, 310, listOf("Tamil", "Telugu", "Sanskrit", "Hindi"), listOf("🪔 Lakshmi Pooja", "💍 Marriage Ceremony", "🔥 Havan & Homam", "🛕 Temple Services"), "Bengaluru, KA", 2000.0, 3800.0, 7500.0, R.drawable.img_pandit_banner_1785577758893, 890),
        Pandit("pan3", "Pt. Brijesh Narayan Shastri", "Bhagwat Katha & Sanatana Acharya", 25, 4.92f, 580, listOf("Hindi", "Sanskrit", "Gujarati"), listOf("📖 Bhagwat Katha", "📿 Satyanarayan Katha", "👴 Pitru Karma", "🐘 Ganesh Pooja"), "Ujjain, MP", 2500.0, 4500.0, 8500.0, R.drawable.img_pandit_banner_1785577758893, 1420),
        Pandit("pan4", "Acharya Kameshwara Rao", "Mantra Chanting & Tantra Shanti", 16, 4.85f, 275, listOf("Telugu", "Kannada", "Hindi", "English"), listOf("📜 Sanskrit & Chanting", "🌿 Shiva Pooja", "🌺 Durga Pooja", "🐒 Hanuman Pooja"), "Tirupati, AP", 1800.0, 3500.0, 6800.0, R.drawable.img_pandit_banner_1785577758893, 730),
        Pandit("pan5", "Pt. Vivekanand Joshi", "Rigveda Karmakand Specialist", 20, 4.90f, 490, listOf("Marathi", "Hindi", "Sanskrit"), listOf("🏠 Griha Pravesh", "🧭 Vastu Shanti", "🌙 Navagraha Pooja", "🕉️ General Pooja"), "Nashik, MH", 1600.0, 3200.0, 6000.0, R.drawable.img_pandit_banner_1785577758893, 1100),
        Pandit("pan6", "Pt. Harishchandra Vidyalankar", "Shukla Yajurveda Acharya", 28, 4.98f, 620, listOf("Hindi", "Sanskrit"), listOf("🔥 Maha Yajna", "🔱 Rudrabhishek", "🏠 Vastu Shanti", "👴 Pind Daan"), "Haridwar, UK", 2200.0, 4200.0, 8000.0, R.drawable.img_pandit_banner_1785577758893, 1680),
        Pandit("pan7", "Pt. Shankaranarayana Bhat", "South Indian Smartha Pandit", 19, 4.89f, 340, listOf("Kannada", "Sanskrit", "English"), listOf("🪔 Ganapati Homam", "💍 Vivaha Samskara", "🐍 Nagadosha Shanti"), "Sringeri, KA", 1900.0, 3600.0, 6900.0, R.drawable.img_pandit_banner_1785577758893, 910),
        Pandit("pan8", "Acharya Tribhuvan Nath", "Trikal Sandhya & Tantra Seva", 21, 4.91f, 410, listOf("Hindi", "Maithili", "Sanskrit"), listOf("🔥 Chandi Homa", "🌺 Baglamukhi Pooja", "🌙 Rahu Ketu Shanti"), "Guwahati, AS", 2400.0, 4800.0, 8800.0, R.drawable.img_pandit_banner_1785577758893, 1150),
        Pandit("pan9", "Pt. Giridharilal Mishra", "Vrindavan Rasik Pujari", 23, 4.93f, 520, listOf("Hindi", "Sanskrit"), listOf("🪷 Radha Krishna Archana", "📿 Satyanarayan Vrat", "👶 Santana Gopal"), "Vrindavan, UP", 1700.0, 3300.0, 6200.0, R.drawable.img_pandit_banner_1785577758893, 1340),
        Pandit("pan10", "Acharya Mukund Shastri", "Jyotish & Karma Kanda Expert", 17, 4.86f, 290, listOf("Gujarati", "Hindi", "English"), listOf("🏠 Griha Pravesh", "🧭 Business Vastu", "🌙 Navagraha Shanti"), "Ahmedabad, GJ", 1600.0, 3100.0, 5800.0, R.drawable.img_pandit_banner_1785577758893, 820),
        Pandit("pan11", "Pt. Prabhakar Shastri", "Ayodhya Ram Mandir Lineage", 26, 4.96f, 710, listOf("Hindi", "Sanskrit"), listOf("🏹 Sundarkand Path", "🏠 Ramcharitmanas", "🔥 Hanuman Havan"), "Ayodhya, UP", 2100.0, 4000.0, 7800.0, R.drawable.img_pandit_banner_1785577758893, 1590),
        Pandit("pan12", "Pt. Somnath Acharya", "Puri Jagannath Temple Pujari", 24, 4.94f, 480, listOf("Odia", "Hindi", "Sanskrit"), listOf("🛕 Jagannath Pooja", "🌾 Annadhan Seva", "🚩 Dhwaja Aarohan"), "Puri, OD", 2000.0, 3900.0, 7200.0, R.drawable.img_pandit_banner_1785577758893, 1280),
        Pandit("pan13", "Pt. Kailash Nath Dwivedi", "Kashi Mahapandit", 30, 4.99f, 950, listOf("Hindi", "Sanskrit", "English"), listOf("🔱 Bhasma Aarti", "🔥 Maha Rudrabhishek", "👴 Pitra Tarpan"), "Varanasi, UP", 2500.0, 5000.0, 9500.0, R.drawable.img_pandit_banner_1785577758893, 2100),
        Pandit("pan14", "Acharya Vasudevan Namboodiri", "Kerala Tantric Ritualist", 22, 4.92f, 380, listOf("Malayalam", "Sanskrit", "English"), listOf("🐍 Sarpa Kavu Pooja", "🔥 Bhagavathy Seva", "🪔 Ganapathy Homam"), "Thrissur, KL", 2300.0, 4500.0, 8200.0, R.drawable.img_pandit_banner_1785577758893, 1040),
        Pandit("pan15", "Pt. Shrikant Pande", "Trimbakeshwar Kaal Sarp Guru", 20, 4.88f, 430, listOf("Marathi", "Hindi"), listOf("🐍 Kaal Sarp Shanti", "🔥 Narayan Nagbali", "🔱 Tripindi Shradh"), "Trimbak, MH", 1800.0, 3500.0, 6500.0, R.drawable.img_pandit_banner_1785577758893, 1180),
        Pandit("pan16", "Pt. Loknath Jha", "Maithil Karmakand Acharya", 15, 4.84f, 260, listOf("Maithili", "Hindi", "Sanskrit"), listOf("📖 Janakpur Katha", "📿 Satyanarayan Pooja", "🐘 Ganesh Havan"), "Darbhanga, BR", 1400.0, 2800.0, 5200.0, R.drawable.img_pandit_banner_1785577758893, 690),
        Pandit("pan17", "Pt. Badri Prasad Upadhyay", "Kalyan Kalpataru Scholar", 27, 4.95f, 640, listOf("Hindi", "Sanskrit"), listOf("📖 Gita Parayan", "🏠 Vastu Shanti", "🔥 Maha Mrityunjaya"), "Gorakhpur, UP", 1900.0, 3700.0, 7100.0, R.drawable.img_pandit_banner_1785577758893, 1490),
        Pandit("pan18", "Pt. Madhusudan Roy", "Kalighat Senior Priest", 21, 4.89f, 390, listOf("Bengali", "Hindi", "Sanskrit"), listOf("🌺 Kali Pooja", "🔥 Chandi Path", "🪔 Lakshmi Narayan Havan"), "Kolkata, WB", 1750.0, 3400.0, 6300.0, R.drawable.img_pandit_banner_1785577758893, 1020),
        Pandit("pan19", "Acharya Venkateshan", "Madurai Meenakshi Priest", 18, 4.87f, 320, listOf("Tamil", "Sanskrit", "English"), listOf("🌺 Meenakshi Kalyanam", "🔥 Sudarshana Homam", "🪔 Navagraha Homa"), "Madurai, TN", 1850.0, 3600.0, 6800.0, R.drawable.img_pandit_banner_1785577758893, 870),
        Pandit("pan20", "Pt. Vishwambhar Sharma", "Mathura Braj Mandal Priest", 25, 4.93f, 510, listOf("Hindi", "Sanskrit"), listOf("🪷 Bankey Bihari Seva", "👶 Santana Gopal", "📿 Yamuna Aarti"), "Mathura, UP", 1650.0, 3200.0, 6100.0, R.drawable.img_pandit_banner_1785577758893, 1310),
        Pandit("pan21", "Pt. Anandatheertha", "Udupi Sri Krishna Priest", 23, 4.91f, 440, listOf("Kannada", "Tulu", "Sanskrit"), listOf("🥛 Kanaka Abhishekam", "🔥 Pavamana Homa", "🪔 Madhwa Rituals"), "Udupi, KA", 1950.0, 3800.0, 7000.0, R.drawable.img_pandit_banner_1785577758893, 1120),
        Pandit("pan22", "Pt. Ramakant Dikshit", "Mahakaleshwar Bhasma Specialist", 29, 4.97f, 810, listOf("Hindi", "Sanskrit"), listOf("🔱 Mahakal Abhishekam", "🔥 Maha Mrityunjaya", "🌙 Shani Shanti"), "Ujjain, MP", 2200.0, 4400.0, 8500.0, R.drawable.img_pandit_banner_1785577758893, 1850),
        Pandit("pan23", "Acharya Suryakant Shastri", "Delhi NCR Vastu Guru", 16, 4.85f, 280, listOf("Hindi", "English"), listOf("🏠 Griha Pravesh", "🧭 Commercial Vastu", "🔥 Navagraha Havan"), "New Delhi, DL", 2000.0, 4000.0, 7500.0, R.drawable.img_pandit_banner_1785577758893, 760),
        Pandit("pan24", "Pt. Yagyeshwar Agnihotri", "Sama Veda Chanter", 31, 4.99f, 990, listOf("Sanskrit", "Hindi"), listOf("🎵 Sama Veda Chanting", "🔥 Soma Yajna", "🔱 Rudrabhishek"), "Rishikesh, UK", 2800.0, 5500.0, 10000.0, R.drawable.img_pandit_banner_1785577758893, 2250),
        Pandit("pan25", "Pt. Purushottam Joshi", "Jaipur Royal Lineage Pujari", 24, 4.90f, 460, listOf("Hindi", "Rajasthani"), listOf("🏠 Griha Pravesh", "💍 Vivaha Rituals", "🪔 Lakshmi Pooja"), "Jaipur, RJ", 1800.0, 3500.0, 6600.0, R.drawable.img_pandit_banner_1785577758893, 1190),
        Pandit("pan26", "Pt. Janardan Tripathy", "Chhinnamasta Peeth Acharya", 20, 4.88f, 350, listOf("Hindi", "Sanskrit"), listOf("🌺 Devi Durga Pooja", "🔥 Chandi Homa", "🌙 Rahu Ketu Shanti"), "Ranchi, JH", 1700.0, 3300.0, 6200.0, R.drawable.img_pandit_banner_1785577758893, 930),
        Pandit("pan27", "Pt. Shivshankar Tiwari", "Chitrakoot Ramayana Reader", 22, 4.91f, 430, listOf("Hindi", "Sanskrit"), listOf("🏹 Akhand Ramayan", "🐒 Hanuman Chalisa", "📿 Satyanarayan Katha"), "Chitrakoot, MP", 1500.0, 3000.0, 5600.0, R.drawable.img_pandit_banner_1785577758893, 1110),
        Pandit("pan28", "Acharya Vidyadhar", "Kanchi Kamakoti Peeth Scholar", 28, 4.96f, 680, listOf("Tamil", "Sanskrit", "Telugu"), listOf("🪔 Sri Vidya Homa", "🌺 Lalita Sahasranama", "🔱 Abhishekam"), "Kanchipuram, TN", 2400.0, 4700.0, 8900.0, R.drawable.img_pandit_banner_1785577758893, 1540),
        Pandit("pan29", "Pt. Dinanath Shastri", "Gaya Shraddha Guru", 26, 4.93f, 570, listOf("Hindi", "Sanskrit"), listOf("👴 Gaya Pind Daan", "🔱 Tripindi Shradh", "🔥 Pitra Shanti"), "Gaya, BR", 1900.0, 3800.0, 7100.0, R.drawable.img_pandit_banner_1785577758893, 1420),
        Pandit("pan30", "Pt. Tarun Kumar Bhattacharya", "Tarapith Siddha Priest", 19, 4.87f, 310, listOf("Bengali", "Hindi"), listOf("🌺 Tara Maa Pooja", "🔥 Maha Havan", "🌙 Shani Shanti"), "Tarapith, WB", 1650.0, 3200.0, 6000.0, R.drawable.img_pandit_banner_1785577758893, 850),
        Pandit("pan31", "Acharya Raghavendra", "Mantralayam Seva Acharya", 21, 4.92f, 400, listOf("Kannada", "Telugu", "Sanskrit"), listOf("🪔 Rayaru Seva", "🔥 Sudarshana Homa", "📿 Satyanarayan Katha"), "Mantralayam, AP", 1850.0, 3600.0, 6700.0, R.drawable.img_pandit_banner_1785577758893, 1050),
        Pandit("pan32", "Pt. Mahadev Shastri", "Kedarnath Dham Pujari", 25, 4.95f, 530, listOf("Hindi", "Sanskrit"), listOf("🔱 Kedarnath Abhishekam", "🔥 Rudra Yajna", "🌙 Shani Shanti"), "Rudraprayag, UK", 2100.0, 4100.0, 7900.0, R.drawable.img_pandit_banner_1785577758893, 1370),
        Pandit("pan33", "Pt. Hemant Kumar", "Somnath Temple Priest", 22, 4.89f, 390, listOf("Gujarati", "Hindi"), listOf("🔱 Somnath Abhishekam", "🌙 Navagraha Pooja", "🏠 Vastu Shanti"), "Veraval, GJ", 1750.0, 3400.0, 6400.0, R.drawable.img_pandit_banner_1785577758893, 980),
        Pandit("pan34", "Pt. Nityanand Das", "ISCKON Vrindavan Priest", 17, 4.88f, 330, listOf("English", "Hindi", "Bengali"), listOf("🪷 Gaura Nitai Abhishekam", "📿 Harinam Sankirtan", "👶 Baby Naming"), "Vrindavan, UP", 1500.0, 2900.0, 5400.0, R.drawable.img_pandit_banner_1785577758893, 880),
        Pandit("pan35", "Acharya Brajeshwar", "Siddhivinayak Mumbai Priest", 20, 4.90f, 420, listOf("Marathi", "Hindi", "English"), listOf("🐘 Ganesh Atharvashirsha", "🏠 Griha Pravesh", "🧭 Vastu Shanti"), "Mumbai, MH", 2000.0, 3800.0, 7200.0, R.drawable.img_pandit_banner_1785577758893, 1140),
        Pandit("pan36", "Pt. Umakant Shastri", "Badrinath Rawal Lineage", 27, 4.96f, 650, listOf("Hindi", "Sanskrit"), listOf("🪷 Vishnu Sahasranama", "🔥 Maha Havan", "👴 Ancestral Pooja"), "Badrinath, UK", 2300.0, 4500.0, 8400.0, R.drawable.img_pandit_banner_1785577758893, 1610),
        Pandit("pan37", "Pt. Shambhoolal Sharma", "Pushkar Brahma Temple Priest", 23, 4.89f, 370, listOf("Hindi", "Rajasthani"), listOf("🌊 Pushkar Holy Bath", "🔥 Brahma Havan", "🌙 Rahu Ketu Shanti"), "Pushkar, RJ", 1600.0, 3100.0, 5800.0, R.drawable.img_pandit_banner_1785577758893, 920),
        Pandit("pan38", "Pt. Chandrashekhar Dixit", "Kalahasti Rahu Ketu Priest", 24, 4.93f, 490, listOf("Telugu", "Tamil", "Sanskrit"), listOf("🐍 Rahu Ketu Shanti", "🔱 Shiva Abhishekam", "🌙 Navagraha Homa"), "Srikalahasti, AP", 1900.0, 3700.0, 7000.0, R.drawable.img_pandit_banner_1785577758893, 1260),
        Pandit("pan39", "Acharya Manoranjan Jha", "Baidyanath Dham Pujari", 21, 4.91f, 410, listOf("Hindi", "Sanskrit"), listOf("🔱 Baidyanath Abhishekam", "🔥 Maha Mrityunjaya", "🏠 Griha Pravesh"), "Deoghar, JH", 1700.0, 3300.0, 6200.0, R.drawable.img_pandit_banner_1785577758893, 1080),
        Pandit("pan40", "Pt. Krishna Kant Shastri", "Kashi Vishwanath Senior Archaka", 28, 4.98f, 880, listOf("Hindi", "Sanskrit", "English"), listOf("🔱 Rudrabhishek", "🔥 Maha Yajna", "🌙 All Graha Shanti"), "Varanasi, UP", 2400.0, 4800.0, 9000.0, R.drawable.img_pandit_banner_1785577758893, 1920)
    )

    fun getLiveStreams(): List<LiveDarshanStream> = listOf(
        LiveDarshanStream(
            id = "ls1",
            templeName = "Kashi Vishwanath Temple",
            location = "Varanasi, UP",
            currentAarti = "Mangala Aarti Live",
            viewersCount = 14200,
            thumbnailRes = R.drawable.img_live_darshan_kashi_1785577744688,
            scheduleText = "Morning 05:00 AM & Evening 07:00 PM"
        ),
        LiveDarshanStream(
            id = "ls2",
            templeName = "Shree Siddhivinayak",
            location = "Mumbai, MH",
            currentAarti = "Kakad Aarti & Live Darshan",
            viewersCount = 9800,
            thumbnailRes = R.drawable.img_hero_divine_1785577730862,
            scheduleText = "Morning 06:00 AM & Evening 08:00 PM"
        )
    )

    fun getPrasadProducts(): List<PrasadItem> = listOf(
        PrasadItem(
            id = "pr1",
            name = "Kashi Vishwanath Mahaprasad Box",
            templeName = "Kashi Vishwanath",
            category = "Temple Prasad",
            price = 350.0,
            rating = 4.9f,
            description = "Sacred dry fruits laddoo, holy Ganga jal bottle, kumkum, chandan and Rudraksha bead.",
            imageRes = R.drawable.img_prasad_pack_1785577773896
        ),
        PrasadItem(
            id = "pr2",
            name = "Tirupati Laddu Prasadam (2 Pcs)",
            templeName = "Tirumala Venkateswara",
            category = "Temple Prasad",
            price = 450.0,
            rating = 4.95f,
            description = "Authentic GI tagged Tirupati Srivari Laddu prasad with divine packaging.",
            imageRes = R.drawable.img_prasad_pack_1785577773896
        ),
        PrasadItem(
            id = "pr3",
            name = "Original 5 Mukhi Nepal Rudraksha",
            templeName = "Faith Tech Store",
            category = "Rudraksha",
            price = 1200.0,
            rating = 4.8f,
            description = "Lab-certified authentic 5 Mukhi Nepalese Rudraksha for focus and spiritual balance.",
            imageRes = R.drawable.img_prasad_pack_1785577773896
        )
    )

    fun getBhajanTracks(): List<BhajanTrack> = listOf(
        BhajanTrack(
            id = "b1",
            title = "Shiv Tandav Stotram",
            artist = "Ravana Krut",
            deity = "Lord Shiva",
            durationText = "09:15",
            lyrics = "Jatatavee galajjala pravaha pavitasthale\nGaleavalambya lambitam bhujangatungamalikam..."
        ),
        BhajanTrack(
            id = "b2",
            title = "Hanuman Chalisa",
            artist = "Hariharan",
            deity = "Lord Hanuman",
            durationText = "09:42",
            lyrics = "Shri Guru Charan Saroj Raj\nNij Mane Mukure Sudhari..."
        ),
        BhajanTrack(
            id = "b3",
            title = "Achyutam Keshavam Krishna Damodaram",
            artist = "Anup Jalota",
            deity = "Lord Krishna",
            durationText = "06:30",
            lyrics = "Achyutam Keshavam Krishna Damodaram\nRama Narayanam Janakivallabham..."
        )
    )

    fun getDonationCauses(): List<DonationCause> = listOf(
        DonationCause(
            id = "dc1",
            title = "Kamdhenu Gaushala Seva",
            category = "Gaushala",
            targetAmount = 500000.0,
            raisedAmount = 345000.0,
            description = "Feed fodder, medical care, and shelter for 200 rescue cows."
        ),
        DonationCause(
            id = "dc2",
            title = "Daily Nitya Annadanam Scheme",
            category = "Annadanam",
            targetAmount = 1000000.0,
            raisedAmount = 820000.0,
            description = "Serve free hygienic hot meals to 1000 pilgrim devotees daily at holy ghats."
        )
    )
}
