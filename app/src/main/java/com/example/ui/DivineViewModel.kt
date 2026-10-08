package com.example.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.db.BookingEntity
import com.example.data.db.CartItemEntity
import com.example.data.db.DonationEntity
import com.example.data.db.FavoriteEntity
import com.example.data.model.AuthUser
import com.example.data.model.ChadhavaItem
import com.example.data.model.FamilyMember
import com.example.data.model.NotificationItem
import com.example.data.model.PaidAddon
import com.example.data.model.PrasadItem
import com.example.data.model.UpcomingPooja
import com.example.data.model.UserRole
import com.example.data.model.WalletTransaction
import com.example.data.repository.DivineRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class DivineViewModel(application: Application) : AndroidViewModel(application) {
    val repository = DivineRepository(application)

    val currentRole: StateFlow<UserRole> = repository.currentRole
    val authUser: StateFlow<AuthUser> = repository.authUser
    val walletBalance: StateFlow<Double> = repository.walletBalance
    val rewardPoints: StateFlow<Int> = repository.rewardPoints
    val walletTransactions: StateFlow<List<WalletTransaction>> = repository.walletTransactions
    val selectedLanguage: StateFlow<String> = repository.selectedLanguage
    val followedAstrologerIds: StateFlow<Set<String>> = repository.followedAstrologerIds
    val familyMembers: StateFlow<List<FamilyMember>> = repository.familyMembers

    fun toggleFollowAstrologer(id: String) {
        repository.toggleFollowAstrologer(id)
    }
    val notifications: StateFlow<List<NotificationItem>> = repository.notifications

    val astrologers = repository.astrologers
    val vedicTools = repository.vedicTools
    val aiServices = repository.aiServices
    val blogPosts = repository.blogPosts
    val orderHistoryItems = repository.orderHistoryItems
    val availableLanguages = repository.availableLanguages

    fun addNotification(title: String, message: String, category: String = "GENERAL") {
        repository.addNotification(title, message, category)
    }

    fun setLanguage(lang: String) {
        repository.setLanguage(lang)
    }

    val bookings: StateFlow<List<BookingEntity>> = repository.allBookings
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val cartItems: StateFlow<List<CartItemEntity>> = repository.cartItems
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val donations: StateFlow<List<DonationEntity>> = repository.allDonations
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val favorites: StateFlow<List<FavoriteEntity>> = repository.favorites
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun loginWithPhone(phone: String, name: String = "Rajesh Sharma") {
        repository.loginWithPhone(phone, name)
    }

    fun updateUserProfile(user: AuthUser) {
        repository.updateUserProfile(user)
    }

    fun loginWithSso(provider: String) {
        repository.loginWithSso(provider)
    }

    fun logout() {
        repository.logout()
    }

    fun setRole(role: UserRole) {
        repository.setRole(role)
    }

    fun addWalletMoney(amount: Double) {
        repository.addWalletMoney(amount)
    }

    fun addFamilyMember(name: String, relation: String, gotra: String, rashi: String) {
        repository.addFamilyMember(name, relation, gotra, rashi)
    }

    fun markNotificationsRead() {
        repository.markNotificationsRead()
    }

    fun bookPooja(
        bookingType: String,
        targetName: String,
        poojaName: String,
        dateText: String,
        timeSlot: String,
        devoteeName: String,
        gotra: String,
        sankalpName: String,
        amount: Double
    ) {
        viewModelScope.launch {
            repository.bookPooja(
                bookingType, targetName, poojaName, dateText, timeSlot, devoteeName, gotra, sankalpName, amount
            )
        }
    }

    fun addToCart(prasadItem: PrasadItem) {
        viewModelScope.launch {
            repository.addToCart(prasadItem)
        }
    }

    fun removeFromCart(id: String) {
        viewModelScope.launch {
            repository.removeFromCart(id)
        }
    }

    fun clearCart() {
        viewModelScope.launch {
            repository.clearCart()
        }
    }

    fun makeDonation(causeTitle: String, category: String, amount: Double, isAnon: Boolean, donorName: String) {
        viewModelScope.launch {
            repository.makeDonation(causeTitle, category, amount, isAnon, donorName)
        }
    }

    suspend fun askAiGuru(prompt: String): String {
        return repository.askAiGuru(prompt)
    }
}
