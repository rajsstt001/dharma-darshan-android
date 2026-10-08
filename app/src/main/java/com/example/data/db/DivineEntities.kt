package com.example.data.db

import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.RoomDatabase
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "bookings")
data class BookingEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val bookingType: String, // "TEMPLE_POOJA" or "HOME_PANDIT"
    val targetName: String, // Temple or Pandit Name
    val poojaName: String,
    val dateText: String,
    val timeSlot: String,
    val devoteeName: String,
    val gotra: String,
    val sankalpName: String,
    val totalAmount: Double,
    val status: String = "CONFIRMED", // "CONFIRMED", "COMPLETED", "CANCELLED"
    val bookingTime: Long = System.currentTimeMillis()
)

@Entity(tableName = "cart_items")
data class CartItemEntity(
    @PrimaryKey val prasadId: String,
    val name: String,
    val templeName: String,
    val price: Double,
    val quantity: Int = 1
)

@Entity(tableName = "donations")
data class DonationEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val causeTitle: String,
    val category: String,
    val amount: Double,
    val isAnonymous: Boolean,
    val donorName: String,
    val dateText: String,
    val receiptId: String
)

@Entity(tableName = "favorites")
data class FavoriteEntity(
    @PrimaryKey val itemId: String,
    val itemType: String, // "TEMPLE", "PANDIT", "POOJA"
    val title: String,
    val subtitle: String
)

@Dao
interface DivineDao {
    @Query("SELECT * FROM bookings ORDER BY bookingTime DESC")
    fun getAllBookings(): Flow<List<BookingEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBooking(booking: BookingEntity): Long

    @Query("SELECT * FROM cart_items")
    fun getCartItems(): Flow<List<CartItemEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCartItem(item: CartItemEntity)

    @Query("DELETE FROM cart_items WHERE prasadId = :id")
    suspend fun removeCartItem(id: String)

    @Query("DELETE FROM cart_items")
    suspend fun clearCart()

    @Query("SELECT * FROM donations ORDER BY id DESC")
    fun getAllDonations(): Flow<List<DonationEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDonation(donation: DonationEntity)

    @Query("SELECT * FROM favorites")
    fun getAllFavorites(): Flow<List<FavoriteEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavorite(fav: FavoriteEntity)

    @Query("DELETE FROM favorites WHERE itemId = :id")
    suspend fun removeFavorite(id: String)
}

@Database(
    entities = [BookingEntity::class, CartItemEntity::class, DonationEntity::class, FavoriteEntity::class],
    version = 1,
    exportSchema = false
)
abstract class DivineDatabase : RoomDatabase() {
    abstract fun divineDao(): DivineDao
}
