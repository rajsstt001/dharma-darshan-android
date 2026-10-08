package com.example.data.ai

import com.example.BuildConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.util.concurrent.TimeUnit

object GeminiClient {
    private const val API_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-3.5-flash:generateContent"

    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(20, TimeUnit.SECONDS)
        .readTimeout(20, TimeUnit.SECONDS)
        .build()

    suspend fun askGuru(
        prompt: String,
        systemInstruction: String = "You are Divine Guru, an empathetic, deeply knowledgeable, and serene spiritual AI guide on the Divine Connect Online Pooja Platform. Provide concise, peaceful, and uplifting Vedic wisdom and practical guidance for devotions, poojas, horoscopes, and inner peace."
    ): String = withContext(Dispatchers.IO) {
        val apiKey = BuildConfig.GEMINI_API_KEY
        if (apiKey.isEmpty() || apiKey == "MY_GEMINI_API_KEY") {
            return@withContext getLocalFallbackGuruResponse(prompt)
        }

        try {
            val jsonPayload = JSONObject().apply {
                put("contents", JSONArray().apply {
                    put(JSONObject().apply {
                        put("parts", JSONArray().apply {
                            put(JSONObject().apply { put("text", prompt) })
                        })
                    })
                })
                put("systemInstruction", JSONObject().apply {
                    put("parts", JSONArray().apply {
                        put(JSONObject().apply { put("text", systemInstruction) })
                    })
                })
            }

            val body = jsonPayload.toString().toRequestBody("application/json".toMediaType())
            val request = Request.Builder()
                .url("$API_URL?key=$apiKey")
                .post(body)
                .build()

            val response = okHttpClient.newCall(request).execute()
            val responseBody = response.body?.string()

            if (response.isSuccessful && !responseBody.isNullOrBlank()) {
                val json = JSONObject(responseBody)
                val candidates = json.optJSONArray("candidates")
                if (candidates != null && candidates.length() > 0) {
                    val firstCandidate = candidates.getJSONObject(0)
                    val content = firstCandidate.optJSONObject("content")
                    val parts = content?.optJSONArray("parts")
                    if (parts != null && parts.length() > 0) {
                        val replyText = parts.getJSONObject(0).optString("text")
                        if (!replyText.isNullOrBlank()) {
                            return@withContext replyText
                        }
                    }
                }
            }
            getLocalFallbackGuruResponse(prompt)
        } catch (e: Exception) {
            getLocalFallbackGuruResponse(prompt)
        }
    }

    private fun getLocalFallbackGuruResponse(prompt: String): String {
        val lower = prompt.lowercase()
        return when {
            lower.contains("panchang") || lower.contains("today") || lower.contains("muhurat") ->
                "Today is Shukla Paksha Tritiya. Abhijit Muhurat starts at 11:54 AM. Chanting 'Om Namah Shivaya' during this hour brings clarity and auspicious energy to your home."
            lower.contains("kundli") || lower.contains("horoscope") || lower.contains("astrology") ->
                "Your planetary alignment favors spiritual learning and peace. Performing a simple Ganesha Pooja or chanting Gayatri Mantra today will harmonize your planetary energies."
            lower.contains("pooja") || lower.contains("pandit") || lower.contains("book") ->
                "For career and family harmony, Satyanarayan Pooja or Rudrabhishek at a sacred shrine like Kashi Vishwanath brings divine grace and obstacle removal."
            else ->
                "Namaste Devotee 🙏 Remember that devotion (Bhakti) and inner stillness open the pathway to peace. Offer a humble diya and chant Om Namah Shivaya with pure intention."
        }
    }
}
