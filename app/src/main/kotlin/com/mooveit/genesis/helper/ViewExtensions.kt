package com.mapright.android.helper

import android.view.View
import android.view.animation.DecelerateInterpolator
import android.widget.TextView
import com.mooveit.genesis.helper.perform
import org.threeten.bp.Duration

fun View.visible() = perform { visibility = View.VISIBLE }

fun View.gone() = perform { visibility = View.GONE }

fun View.invisible() = perform { visibility = View.INVISIBLE }

fun View.isVisible() = visibility == View.VISIBLE

fun View.isGone() = visibility == View.GONE

fun View.isInvisible() = visibility == View.INVISIBLE

fun View.visibleIf(predicate: () -> Boolean) = if (predicate()) visible() else gone()

fun View.fadeOut(animationDuration: Duration = Constants.DEFAULT_DURATION_TIME) = animate()
    .alpha(Constants.ALPHA_INVISIBLE)
    .setInterpolator(DecelerateInterpolator())
    .setDuration(animationDuration.toMillis())
    .withEndAction { gone() }
    .start()

fun View.fadeIn(animationDuration: Duration = Constants.DEFAULT_DURATION_TIME) {
  alpha = Constants.ALPHA_INVISIBLE
  visible()

  animate()
      .alpha(Constants.ALPHA_VISIBLE)
      .setInterpolator(DecelerateInterpolator())
      .setDuration(animationDuration.toMillis())
      .start()
}

fun View.slideDown(animationDuration: Duration = Constants.DEFAULT_DURATION_TIME, onEnd: (() -> Unit)? = null) {
  animate()
      .translationYBy(height.toFloat())
      .setInterpolator(DecelerateInterpolator())
      .setDuration(animationDuration.toMillis())
      .withEndAction(onEnd)
      .start()
}

fun View.slideUp(animationDuration: Duration = Constants.DEFAULT_DURATION_TIME, onEnd: (() -> Unit)? = null) {
  animate()
      .translationYBy(-height.toFloat())
      .setInterpolator(DecelerateInterpolator())
      .setDuration(animationDuration.toMillis())
      .withEndAction(onEnd)
      .start()
}

val TextView.content
  get() = text?.toString()

private object Constants {
  @Suppress("MagicNumber")
  val DEFAULT_DURATION_TIME: Duration = Duration.ofMillis(300L)

  const val ALPHA_VISIBLE = 1f
  const val ALPHA_INVISIBLE = 0f
}
