package com.example.moviemood

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.LinearInterpolator
import androidx.appcompat.app.AppCompatActivity
import com.example.moviemood.databinding.ActivityLoadingBinding
import java.util.Locale

class LoadingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoadingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoadingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mood = intent.getStringExtra(MovieDetailActivity.EXTRA_MOOD) ?: "your mood"
        binding.tvMoodSubtitle.text = "For your mood: $mood"

        // Set emojis based on mood BEFORE starting animations
        setupMoodBasedSparkles(mood)
        
        startBrainAnimations()
        startSparkleAnimations()
        startLoadingProgress(mood)
    }

    private fun setupMoodBasedSparkles(mood: String) {
        val sparkles = listOf(
            binding.tvSparkle1, binding.tvSparkle2, binding.tvSparkle3,
            binding.tvSparkle4, binding.tvSparkle5, binding.tvSparkle6,
            binding.tvSparkle7, binding.tvSparkle8, binding.tvSparkle9,
            binding.tvSparkle10
        )

        val normalizedMood = mood.lowercase(Locale.getDefault())
        
        val emojiPool = when {
            normalizedMood.contains("horror") -> listOf("👻", "💀", "🎃", "🕷️", "🔪")
            normalizedMood.contains("thriller") -> listOf("🕵️", "🔐", "🤫", "🔍", "👣")
            normalizedMood.contains("action") -> listOf("🔥", "💥", "💣", "⚡", "🗡️")
            normalizedMood.contains("comedy") -> listOf("😂", "🤣", "🎈", "🎭", "🍿")
            normalizedMood.contains("romance") -> listOf("❤️", "💖", "🌹", "💌", "💍")
            normalizedMood.contains("sci-fi") -> listOf("👽", "🚀", "🪐", "🛸", "🤖")
            normalizedMood.contains("drama") -> listOf("🎭", "🥀", "🕯️", "🫂", "🎻")
            normalizedMood.contains("classic") -> listOf("🎬", "🎞️", "📽️", "🎥", "🎩")
            else -> listOf("✨", "🌟", "⭐", "💫")
        }

        sparkles.forEachIndexed { index, textView ->
            textView.text = emojiPool[index % emojiPool.size]
            textView.alpha = 0f 
        }
    }

    private fun startBrainAnimations() {
        ObjectAnimator.ofPropertyValuesHolder(
            binding.flBrainContainer,
            PropertyValuesHolder.ofFloat(View.SCALE_X, 1.0f, 1.1f),
            PropertyValuesHolder.ofFloat(View.SCALE_Y, 1.0f, 1.1f)
        ).apply {
            duration = 1800
            repeatCount = ValueAnimator.INFINITE
            repeatMode = ValueAnimator.REVERSE
            interpolator = AccelerateDecelerateInterpolator()
        }.start()

        ObjectAnimator.ofFloat(binding.tvBrainEmoji, View.TRANSLATION_Y, 0f, -25f).apply {
            duration = 1400
            repeatCount = ValueAnimator.INFINITE
            repeatMode = ValueAnimator.REVERSE
            interpolator = AccelerateDecelerateInterpolator()
        }.start()
    }

    private fun startSparkleAnimations() {
        val sparkles = listOf(
            binding.tvSparkle1, binding.tvSparkle2, binding.tvSparkle3,
            binding.tvSparkle4, binding.tvSparkle5, binding.tvSparkle6,
            binding.tvSparkle7, binding.tvSparkle8, binding.tvSparkle9,
            binding.tvSparkle10
        )
        
        sparkles.forEachIndexed { index, sparkle ->
            val delay = (index * 200L)
            val duration = 1200L + (index * 100L)
            animateSparkle(sparkle, delay, duration)
        }
    }

    private fun animateSparkle(sparkle: View, startDelay: Long, animDuration: Long) {
        val fadeAnim = ObjectAnimator.ofFloat(sparkle, View.ALPHA, 0f, 1f, 0f).apply {
            duration = animDuration
            repeatCount = ValueAnimator.INFINITE
            this.startDelay = startDelay
        }
        
        val scaleAnimX = ObjectAnimator.ofFloat(sparkle, View.SCALE_X, 0.5f, 1.4f, 0.5f).apply {
            duration = animDuration
            repeatCount = ValueAnimator.INFINITE
            this.startDelay = startDelay
        }

        val scaleAnimY = ObjectAnimator.ofFloat(sparkle, View.SCALE_Y, 0.5f, 1.4f, 0.5f).apply {
            duration = animDuration
            repeatCount = ValueAnimator.INFINITE
            this.startDelay = startDelay
        }

        AnimatorSet().apply {
            playTogether(fadeAnim, scaleAnimX, scaleAnimY)
            start()
        }
    }

    private fun startLoadingProgress(mood: String) {
        val animator = ValueAnimator.ofInt(0, 100)
        animator.duration = 5000
        animator.interpolator = LinearInterpolator()
        
        animator.addUpdateListener { animation ->
            val progress = animation.animatedValue as Int
            binding.pbDiscovery.progress = progress
            binding.tvProgressPercent.text = "$progress%"
        }

        animator.setOnEndListener {
            // Navigate directly to MovieListActivity to show more movies
            val intent = Intent(this, MovieListActivity::class.java)
            intent.putExtra(MovieListActivity.EXTRA_MOOD, mood)
            startActivity(intent)
            finish()
        }
        
        animator.start()
    }
    
    private fun ValueAnimator.setOnEndListener(onEnd: () -> Unit) {
        this.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {}
            override fun onAnimationEnd(animation: Animator) { onEnd() }
            override fun onAnimationCancel(animation: Animator) {}
            override fun onAnimationRepeat(animation: Animator) {}
        })
    }
}
