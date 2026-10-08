package com.example.data.model

import java.io.Serializable

enum class UserRole(val label: String, val icon: String) {
    DEVOTEE("Devotee", "🙏"),
    TEMPLE_ADMIN("Temple Admin", "🛕"),
    PANDIT("Pandit / Pujari", "📿"),
    SUPER_ADMIN("Platform Super Admin", "👑")
}

data class AuthUser(
    val isGuest: Boolean = true,
    val name: String = "Raj Chauhan",
    val phone: String = "+91 8700241177",
    val email: String = "rajesh.sharma@faithtech.com",
    val gotra: String = "Kashyap",
    val rashi: String = "Tula (Libra)",
    val isSso: Boolean = false,
    val gender: String = "Male",
    val dob: String = "08-March-1991",
    val tob: String = "12:05 PM",
    val pob: String = "New Delhi, National Capital Territory of Delhi",
    val currentAddress: String = "",
    val cityStateCountry: String = "",
    val pincode: String = ""
)

data class PanchangInfo(
    val dateText: String = "Sunday, 2 Aug 2026",
    val tithi: String = "Shukla Paksha Chaturthi",
    val nakshatra: String = "Purva Phalguni",
    val yoga: String = "Siddhi Yoga",
    val karana: String = "Bava Karana",
    val muhurat: String = "Abhijit: 11:54 AM - 12:46 PM",
    val sunrise: String = "05:42 AM",
    val sunset: String = "07:12 PM",
    val moonrise: String = "08:30 AM",
    val moonset: String = "09:45 PM",
    val auspiciousTime: String = "11:30 AM - 01:15 PM",
    val rahuKaal: String = "09:00 AM - 10:30 AM",
    val abhijitMuhurat: String = "11:54 AM - 12:46 PM",
    val dailyQuote: String = "Atha Yoga Anushasanam — Quiet your mind, let devotion fill your spirit today.",
    val dailyMantra: String = "Om Namah Shivaya",
    val todaysFestivals: List<String> = listOf("Ganesh Chaturthi Vrat", "Sravana Somvar Eve")
)

data class Temple(
    val id: String,
    val name: String,
    val deity: String,
    val city: String,
    val state: String,
    val rating: Float,
    val reviewCount: Int,
    val history: String,
    val timings: String,
    val coverImageRes: Int,
    val isLiveDarshanAvailable: Boolean = true,
    val facilities: List<String> = listOf("Parking", "Annadanam", "Wheelchair Accessible", "Accommodation"),
    val nearestRailway: String = "Varanasi Junction (3 km)",
    val nearestAirport: String = "Lal Bahadur Shastri Airport (24 km)"
)

data class UpcomingPooja(
    val id: String,
    val name: String,
    val templeName: String,
    val location: String,
    val dateText: String,
    val timeSlot: String,
    val countdownMinutes: Int,
    val devoteesParticipating: Int,
    val startingPrice: Double,
    val bannerRes: Int,
    val description: String,
    val spiritualBenefits: List<String>,
    val priestName: String,
    val priestTitle: String,
    val reviewsCount: Int = 340,
    val rating: Float = 4.9f
)

data class PoojaService(
    val id: String,
    val name: String,
    val templeName: String,
    val deity: String,
    val price: Double,
    val durationMinutes: Int,
    val description: String,
    val benefits: List<String>,
    val includesPrasad: Boolean = true,
    val rating: Float = 4.85f,
    val imageRes: Int = 0
)

data class Pandit(
    val id: String,
    val name: String,
    val title: String,
    val experienceYears: Int,
    val rating: Float,
    val reviewCount: Int,
    val languages: List<String>,
    val specializations: List<String>,
    val city: String,
    val priceBasic: Double,
    val pricePremium: Double,
    val priceVip: Double,
    val imageRes: Int,
    val completedPoojas: Int
)

data class LiveDarshanStream(
    val id: String,
    val templeName: String,
    val location: String,
    val currentAarti: String,
    val viewersCount: Int,
    val thumbnailRes: Int,
    val isLiveNow: Boolean = true,
    val scheduleText: String = "Daily 05:00 AM & 07:00 PM"
)

data class ChadhavaItem(
    val id: String,
    val name: String,
    val description: String,
    val category: String,
    val price: Double,
    val iconEmoji: String,
    val defaultQuantity: Int = 1
)

data class PaidAddon(
    val id: String,
    val name: String,
    val description: String,
    val price: Double,
    val iconEmoji: String,
    val selectedQuantity: Int = 0
)

data class PrasadItem(
    val id: String,
    val name: String,
    val templeName: String,
    val category: String,
    val price: Double,
    val rating: Float,
    val description: String,
    val imageRes: Int,
    val isInStock: Boolean = true
)

data class BhajanTrack(
    val id: String,
    val title: String,
    val artist: String,
    val deity: String,
    val durationText: String,
    val lyrics: String
)

data class DonationCause(
    val id: String,
    val title: String,
    val category: String,
    val targetAmount: Double,
    val raisedAmount: Double,
    val description: String
)

data class FamilyMember(
    val id: String,
    val name: String,
    val relation: String,
    val gotra: String,
    val rashi: String
)

data class WalletTransaction(
    val id: String,
    val title: String,
    val dateText: String,
    val timeText: String = "10:30 AM",
    val amount: Double,
    val isCredit: Boolean,
    val referenceId: String,
    val closingBalance: Double = 2450.0,
    val paymentMethod: String = "UPI / GPay",
    val status: String = "Success", // Success, Pending, Failed
    val description: String = "DharmaDarshan Wallet Transaction"
)

data class Astrologer(
    val id: String,
    val name: String,
    val photoRes: Int = 0,
    val experienceYears: Int,
    val languages: List<String>,
    val skills: List<String>,
    val rating: Float,
    val reviewCount: Int,
    val pricePerMin: Double,
    val isOnline: Boolean = true,
    val isVerified: Boolean = true,
    val gender: String = "Male", // Male, Female
    val consultationTypes: List<String> = listOf("Chat", "Call", "Video"),
    val bio: String = "Gold Medalist Vedic Astrologer with 10+ years of experience in Prashna Kundli and Vastu guidance."
)

enum class ToolCategory { VEDIC, AI }

data class VedicToolItem(
    val id: String,
    val title: String,
    val subtitle: String,
    val category: ToolCategory,
    val iconEmoji: String,
    val description: String,
    val isPopular: Boolean = false
)

data class BlogPost(
    val id: String,
    val title: String,
    val category: String,
    val author: String,
    val publishedDate: String,
    val readingTime: String,
    val imageRes: Int = 0,
    val summary: String,
    val content: String,
    val likesCount: Int = 142,
    val commentsCount: Int = 18,
    val isBookmarked: Boolean = false,
    val isLiked: Boolean = false
)

data class BlogComment(
    val id: String,
    val authorName: String,
    val dateText: String,
    val commentText: String,
    val userAvatar: String = "🙏"
)

data class OrderHistoryItem(
    val bookingId: String,
    val titleName: String, // e.g., Astrologer Name or Pooja Name
    val dateText: String,
    val timeText: String,
    val serviceType: String, // "Astrologer Chat", "Online Pooja", "Remedy", "Horoscope"
    val durationText: String,
    val amount: Double,
    val status: String, // "Confirmed", "Completed", "In Progress", "Cancelled"
    val paymentStatus: String = "Paid", // "Paid", "Pending", "Refunded"
    val rating: Float = 5.0f
)

data class AppLanguage(
    val code: String,
    val nativeName: String,
    val englishName: String,
    val isAvailable: Boolean = true
)

data class NotificationItem(
    val id: String,
    val title: String,
    val message: String,
    val timestamp: String,
    val category: String, // BOOKING, PAYMENT, REMINDER, FESTIVAL, WALLET, PROMO
    val isRead: Boolean = false
)

data class AiChatMessage(
    val id: String,
    val sender: String, // "USER" or "GURU"
    val text: String,
    val timestamp: String = "Just now"
)
