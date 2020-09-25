package com.example.myapplicationtemp.custom_view

import android.animation.ObjectAnimator
import android.content.Context
import android.view.MotionEvent
import android.view.View
import androidx.annotation.DrawableRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.animation.doOnEnd
import androidx.core.animation.doOnStart
import biz.growapp.base.extension.*
import com.example.myapplicationtemp.R
import com.example.myapplicationtemp.extension.OnSwipeTouchListener
import kotlinx.android.synthetic.main.toast_notification.view.*

class ToastNotification(context: Context) : ConstraintLayout(context) {

    companion object {
        const val ANIMATION_DURATION = 500L
    }

    private val screenWidth by lazy { rootToast.context.getScreenWidth() }

    private var toastVisible = false
    private var needDelayToHide = false

    init {
        inflate(context, R.layout.toast_notification, this)
        rootToast.hide()
        initSwipes()
    }

    fun showWithoutImages(textTitle: String, textDescription: String) {
        imgGroup2.hide()
        ivImg1.hide()
        btnInfo.hide()
        tvTitle.text = textTitle
        tvDescription.text = textDescription

        show()
    }

    fun showWithOneImage(@DrawableRes image: Int, textTitle: String, textDescription: String) {
        imgGroup2.hide()
        ivImg1.show()
        btnInfo.hide()
        ivImg1.setImageDrawable(image.getDrawableCompat(context))
        tvTitle.text = textTitle
        tvDescription.text = textDescription

        show()
    }

    fun showWithTooImages(
        @DrawableRes imageTeam1: Int,
        @DrawableRes imageTeam2: Int,
        textTitle: String,
        textDescription: String
    ) {
        imgGroup2.show()
        ivImg1.hide()
        btnInfo.hide()
        ivImgTeam1.setImageDrawable(imageTeam1.getDrawableCompat(context))
        ivImgTeam2.setImageDrawable(imageTeam2.getDrawableCompat(context))

        tvTitle.text = textTitle
        tvDescription.text = textDescription

        show()
    }


    fun show() {
        slideTop()
    }

    fun hide() {
        rootToast?.let {
            slideDown()
        }
    }

    fun hideWithDelay(delay: Long) {
        needDelayToHide = true
        rootToast.postDelayed({
            if (animStart.not() && needDelayToHide) {
                hide()
            }
        }, delay)
    }

    fun initBtn(action: () -> Unit) {
        btnInfo.show()
        btnInfo.onClickDebounce {
            action.invoke()
        }
    }


    private fun initSwipes() {
        rootToast.setOnTouchListener(object : OnSwipeTouchListener(context) {

            override fun onSwipeLeft() {
                if (animStart.not()) {
                    slideLeft()
                }
            }

            override fun onSwipeRight() {
                if (animStart.not()) {
                    slideRight()
                }
            }

            override fun onSwipeBottom() {
                if (animStart.not()) {
                    slideDown()
                }
            }

            override fun onTouch(v: View, event: MotionEvent): Boolean {
                v.postDelayed(
                    {
                        if (animStart.not()) {
                            slideDown()
                        }
                    }, 200
                )

                return super.onTouch(v, event)
            }
        })
    }

    private val fadeOutAnimation =
        ObjectAnimator.ofFloat(
            rootToast,
            View.ALPHA,
            0f
        )
            .apply {
                duration = ANIMATION_DURATION
                doOnStart {
                    toastVisible = false
                    needDelayToHide = false
                }
            }

    private val fadeInAnimation =
        ObjectAnimator.ofFloat(
            rootToast,
            View.ALPHA,
            1f
        )
            .apply {
                duration = ANIMATION_DURATION
                doOnStart {
                    toastVisible = true
                    needDelayToHide = false
                }
            }

    private var animStart = false

    private fun fadeOut() {
        rootToast.alpha = 1f
        fadeOutAnimation.start()
    }

    private fun fadeIn() {
        rootToast.alpha = 0f
        fadeInAnimation.start()
    }

    private fun slideTop() {
        rootToast.translationX = 0f
        rootToast.translationY = 80f

        ObjectAnimator.ofFloat(
            rootToast,
            View.TRANSLATION_Y,
            0f
        )
            .apply {
                duration = ANIMATION_DURATION
                doOnStart {
                    rootToast.show()
                    fadeIn()
                }
            }.start()
    }

    private fun slideDown() {
        ObjectAnimator.ofFloat(
            rootToast,
            View.TRANSLATION_Y,
            80f
        )
            .apply {
                duration = ANIMATION_DURATION
                doOnStart {
                    fadeOut()
                    animStart = true
                }
                doOnEnd {
                    animStart = false
                    rootToast.hide()
                }
            }.start()
    }

    private fun slideRight() {
        ObjectAnimator.ofFloat(
            rootToast,
            View.TRANSLATION_X,
            screenWidth.toFloat()
        )
            .apply {
                duration = ANIMATION_DURATION
                doOnStart {
                    fadeOut()
                    animStart = true
                }
                doOnEnd {
                    animStart = false
                    rootToast.hide()
                }
            }.start()
    }

    private fun slideLeft() {
        rootToast.translationX = 0f
        rootToast.translationY = 0f

        ObjectAnimator.ofFloat(
            rootToast,
            View.TRANSLATION_X,
            -screenWidth.toFloat()
        )
            .apply {
                duration = ANIMATION_DURATION
                doOnStart {
                    fadeOut()
                    animStart = true
                }
                doOnEnd {
                    animStart = false
                    rootToast.hide()
                }
            }.start()
    }

}