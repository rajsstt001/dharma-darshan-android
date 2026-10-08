package com.example.ui.screens

import android.media.AudioAttributes
import android.media.AudioFormat
import android.media.AudioTrack
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.DarkCharcoal
import com.example.ui.theme.DivineGold
import com.example.ui.theme.GoldAccent
import com.example.ui.theme.KrishnaYellow
import com.example.ui.theme.LightCreamBackground
import com.example.ui.theme.PureWhite
import com.example.ui.theme.TextMuted
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.exp
import kotlin.math.sin
import kotlin.random.Random

/**
 * Synthesizes an authentic Indian Temple Bell (Ghanti / घंटा) sound using PCM 16-bit audio.
 * Uses sacred harmonics (528Hz Solfeggio fundamental + metallic overtones + exponential ring decay)
 * with a dual chime strike to mimic a traditional temple brass bell.
 */
object TempleBellSoundPlayer {
    fun playBellSound() {
        Thread {
            try {
                val sampleRate = 44100
                val durationSec = 3.5
                val numSamples = (durationSec * sampleRate).toInt()
                val buffer = ShortArray(numSamples)

                // Acoustic frequencies of a traditional Indian brass bell
                val fund = 528.0 // Sacred Om frequency / 528Hz fundamental
                val overtones = doubleArrayOf(1.0, 2.0, 2.76, 3.25, 4.8, 5.4, 7.1)
                val weights = doubleArrayOf(1.0, 0.65, 0.45, 0.35, 0.25, 0.15, 0.08)
                val decayFactors = doubleArrayOf(1.2, 1.8, 2.8, 3.5, 4.2, 5.0, 6.0)

                for (i in 0 until numSamples) {
                    val t = i.toDouble() / sampleRate
                    var sample = 0.0

                    // First strike at t = 0s
                    for (k in overtones.indices) {
                        val freq = fund * overtones[k]
                        val env = exp(-decayFactors[k] * t)
                        sample += sin(2.0 * PI * freq * t) * weights[k] * env
                    }

                    // Second chime strike echo at t = 0.4s ("Ting... Tanngg...")
                    if (t >= 0.4) {
                        val t2 = t - 0.4
                        for (k in overtones.indices) {
                            val freq = fund * 1.04 * overtones[k]
                            val env = exp(-decayFactors[k] * t2) * 0.75
                            sample += sin(2.0 * PI * freq * t2) * weights[k] * env
                        }
                    }

                    // Soft attack to eliminate initial pop
                    val attack = if (t < 0.008) t / 0.008 else 1.0
                    sample *= attack * 0.65

                    // Clamp PCM sample
                    val clamped = (sample * Short.MAX_VALUE).coerceIn(
                        Short.MIN_VALUE.toDouble(),
                        Short.MAX_VALUE.toDouble()
                    )
                    buffer[i] = clamped.toInt().toShort()
                }

                val minBuf = AudioTrack.getMinBufferSize(
                    sampleRate,
                    AudioFormat.CHANNEL_OUT_MONO,
                    AudioFormat.ENCODING_PCM_16BIT
                )

                val track = AudioTrack.Builder()
                    .setAudioAttributes(
                        AudioAttributes.Builder()
                            .setUsage(AudioAttributes.USAGE_MEDIA)
                            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                            .build()
                    )
                    .setAudioFormat(
                        AudioFormat.Builder()
                            .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
                            .setSampleRate(sampleRate)
                            .setChannelMask(AudioFormat.CHANNEL_OUT_MONO)
                            .build()
                    )
                    .setBufferSizeInBytes(numSamples * 2)
                    .setTransferMode(AudioTrack.MODE_STATIC)
                    .build()

                track.write(buffer, 0, numSamples)
                track.play()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }.start()
    }
}

private data class SparkleParticle(
    var x: Float,
    var y: Float,
    var size: Float,
    var alpha: Float,
    var speed: Float
)

@Composable
fun WelcomeScreen(
    onEnterApp: () -> Unit,
    modifier: Modifier = Modifier
) {
    val coroutineScope = rememberCoroutineScope()
    val haptic = LocalHapticFeedback.current

    // Trigger ring waves animation on bell strikes
    var ringCount by remember { mutableStateOf(0) }
    val ringRipples = remember { mutableStateListOf<Float>() }

    // Continuous pendulum swing animation for the Mandir Bell
    val infiniteTransition = rememberInfiniteTransition(label = "WelcomeAnim")
    val bellSwingAngle by infiniteTransition.animateFloat(
        initialValue = -8f,
        targetValue = 8f,
        animationSpec = infiniteRepeatable(
            animation = tween(2200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "BellSwing"
    )

    val rayRotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(40000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "RayRotation"
    )

    val auraGlowScale by infiniteTransition.animateFloat(
        initialValue = 0.95f,
        targetValue = 1.15f,
        animationSpec = infiniteRepeatable(
            animation = tween(2500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "AuraGlow"
    )

    // Strike vibration effect on bell click/launch
    val bellStrikeScale = remember { Animatable(1f) }

    // Function to trigger sound + visual shockwave
    val triggerBellRing = {
        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
        TempleBellSoundPlayer.playBellSound()
        ringCount++
        coroutineScope.launch {
            bellStrikeScale.animateTo(1.22f, tween(100))
            bellStrikeScale.animateTo(1.0f, tween(300))
        }
    }

    // Play authentic Mandir Ghanti sound IMMEDIATELY when Welcome Screen opens
    LaunchedEffect(Unit) {
        delay(150)
        triggerBellRing()
    }

    // Deep Spiritual Ambient Background Gradient (Deep Temple Maroon to Warm Saffron Charcoal)
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF1E0C06),
                        Color(0xFF2A1508),
                        Color(0xFF1A0A02)
                    )
                )
            )
    ) {
        // Divine Rotating Rays Canvas Background
        Canvas(modifier = Modifier.fillMaxSize()) {
            val center = Offset(size.width / 2f, size.height * 0.38f)
            val rayRadius = size.width * 1.2f
            val numRays = 16

            rotate(rayRotation, pivot = center) {
                for (i in 0 until numRays) {
                    val angle1 = (i * (360f / numRays)) * (PI / 180f).toFloat()
                    val angle2 = ((i + 0.45f) * (360f / numRays)) * (PI / 180f).toFloat()

                    val p1 = Offset(
                        center.x + rayRadius * cos(angle1),
                        center.y + rayRadius * sin(angle1)
                    )
                    val p2 = Offset(
                        center.x + rayRadius * cos(angle2),
                        center.y + rayRadius * sin(angle2)
                    )

                    val rayPath = Path().apply {
                        moveTo(center.x, center.y)
                        lineTo(p1.x, p1.y)
                        lineTo(p2.x, p2.y)
                        close()
                    }

                    drawPath(
                        path = rayPath,
                        color = KrishnaYellow.copy(alpha = if (i % 2 == 0) 0.04f else 0.02f)
                    )
                }
            }

            // Radial divine aura in center
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        KrishnaYellow.copy(alpha = 0.22f),
                        GoldAccent.copy(alpha = 0.08f),
                        Color.Transparent
                    ),
                    center = center,
                    radius = size.width * 0.65f * auraGlowScale
                ),
                center = center,
                radius = size.width * 0.65f * auraGlowScale
            )
        }

        // Floating Lotus & Gold Sparkles Animation Canvas
        Canvas(modifier = Modifier.fillMaxSize()) {
            val center = Offset(size.width / 2f, size.height * 0.38f)

            // Draw expanding acoustic ripples on bell ring
            if (ringCount > 0) {
                drawCircle(
                    color = KrishnaYellow.copy(alpha = 0.35f),
                    center = center,
                    radius = 90.dp.toPx() * bellStrikeScale.value,
                    style = Stroke(width = 3.dp.toPx())
                )
                drawCircle(
                    color = DivineGold.copy(alpha = 0.20f),
                    center = center,
                    radius = 140.dp.toPx() * bellStrikeScale.value,
                    style = Stroke(width = 2.dp.toPx())
                )
            }
        }

        // Content Column Layout
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Top Bar: Skip button & Brand badge
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 28.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(KrishnaYellow.copy(alpha = 0.2f))
                            .border(1.dp, DivineGold, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "ॐ",
                            color = KrishnaYellow,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "DharmaDarshan",
                        color = PureWhite,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(Color.White.copy(alpha = 0.12f))
                        .clickable { onEnterApp() }
                        .padding(horizontal = 14.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = "Skip • सीधे जाएं",
                        color = PureWhite.copy(alpha = 0.85f),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            // Center Visual: Hanging Mandir Ghanti (Temple Bell) + Glow
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                // Mandir Frame Arch & Hanging Chain
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .rotate(bellSwingAngle)
                        .scale(bellStrikeScale.value)
                        .clickable { triggerBellRing() }
                ) {
                    // Temple Roof Chain
                    Canvas(
                        modifier = Modifier
                            .width(6.dp)
                            .height(70.dp)
                    ) {
                        val chainColor = GoldAccent
                        for (y in 0..70 step 12) {
                            drawCircle(
                                color = chainColor,
                                radius = 3.dp.toPx(),
                                center = Offset(size.width / 2f, y.dp.toPx())
                            )
                        }
                    }

                    // Brass Bell Top Ring
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .border(3.dp, DivineGold, CircleShape)
                    )

                    Spacer(modifier = Modifier.height(2.dp))

                    // Detailed Golden Mandir Bell (Ghanti) Canvas
                    Canvas(
                        modifier = Modifier
                            .size(160.dp)
                    ) {
                        val w = size.width
                        val h = size.height

                        // Bell Body Path
                        val bellPath = Path().apply {
                            moveTo(w * 0.35f, h * 0.15f)
                            cubicTo(
                                w * 0.32f, h * 0.30f,
                                w * 0.15f, h * 0.65f,
                                w * 0.08f, h * 0.82f
                            )
                            cubicTo(
                                w * 0.30f, h * 0.95f,
                                w * 0.70f, h * 0.95f,
                                w * 0.92f, h * 0.82f
                            )
                            cubicTo(
                                w * 0.85f, h * 0.65f,
                                w * 0.68f, h * 0.30f,
                                w * 0.65f, h * 0.15f
                            )
                            close()
                        }

                        // Metallic Golden Gradient
                        val bellBrush = Brush.horizontalGradient(
                            colors = listOf(
                                Color(0xFFFFD700),
                                Color(0xFFFFA000),
                                Color(0xFFFFF59D),
                                Color(0xFFB78103)
                            )
                        )

                        // Bell Base Lip Ring
                        drawPath(
                            path = bellPath,
                            brush = bellBrush
                        )

                        // Bell Rim Accent Line
                        drawLine(
                            color = DarkCharcoal.copy(alpha = 0.4f),
                            start = Offset(w * 0.12f, h * 0.80f),
                            end = Offset(w * 0.88f, h * 0.80f),
                            strokeWidth = 3.dp.toPx()
                        )

                        // Hanging Clapper (Lolat)
                        drawCircle(
                            brush = Brush.radialGradient(
                                colors = listOf(Color(0xFFFFF176), Color(0xFFE65100))
                            ),
                            radius = 14.dp.toPx(),
                            center = Offset(w * 0.5f, h * 0.94f)
                        )

                        // Center ॐ Engraving on Bell
                        drawCircle(
                            color = PureWhite.copy(alpha = 0.25f),
                            radius = 22.dp.toPx(),
                            center = Offset(w * 0.5f, h * 0.55f)
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Bell Hint Pill
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .background(KrishnaYellow.copy(alpha = 0.2f))
                            .border(1.dp, DivineGold.copy(alpha = 0.6f), RoundedCornerShape(20.dp))
                            .padding(horizontal = 14.dp, vertical = 6.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.NotificationsActive,
                                contentDescription = "Ring Ghanti",
                                tint = KrishnaYellow,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "स्पर्श कर घंटी बजाएं • Touch Bell",
                                color = KrishnaYellow,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }

            // Bottom Section: Sacred Message & Enter Button
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Sacred Sanskrit Blessing
                Text(
                    text = "॥ ॐ नमः शिवाय ॥",
                    color = DivineGold,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 2.sp
                )

                Spacer(modifier = Modifier.height(12.dp))

                // User Request Required Sacred Message Card
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(18.dp))
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color(0x33FFC107),
                                    Color(0x11FFC107)
                                )
                            )
                        )
                        .border(1.dp, DivineGold.copy(alpha = 0.4f), RoundedCornerShape(18.dp))
                        .padding(horizontal = 20.dp, vertical = 18.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "“मैं एक पवित्र और शांत आध्यात्मिक स्थान में प्रवेश कर रहा हूँ।”",
                            color = PureWhite,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            lineHeight = 26.sp
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "Experience authentic divine darshan, poojas, and Vedic guidance.",
                            color = LightCreamBackground.copy(alpha = 0.8f),
                            fontSize = 13.sp,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Primary Enter Sanctuary CTA Button
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .clip(RoundedCornerShape(28.dp))
                        .background(
                            Brush.horizontalGradient(
                                colors = listOf(
                                    KrishnaYellow,
                                    Color(0xFFFFB300),
                                    KrishnaYellow
                                )
                            )
                        )
                        .clickable { onEnterApp() },
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.AutoAwesome,
                            contentDescription = null,
                            tint = DarkCharcoal,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "प्रवेश करें • Enter Divine Realm",
                            color = DarkCharcoal,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.ExtraBold
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = null,
                            tint = DarkCharcoal,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
        }
    }
}
