package com.example.myapplicationtemp.ui.home

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.Transformation
import android.widget.FrameLayout
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.animation.doOnEnd
import androidx.core.animation.doOnStart
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import biz.growapp.base.extension.*
import com.example.myapplicationtemp.R
import com.example.myapplicationtemp.custom_view.ToastNotification
import kotlinx.android.synthetic.main.fragment_home.*
import kotlin.math.roundToInt


class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel


    private lateinit var root: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProviders.of(this).get(HomeViewModel::class.java)
        root = inflater.inflate(R.layout.fragment_home, container, false)

        return root
    }


    var count = 8
    var toastType = 0

    private val toastNotification: ToastNotification by lazy { initToastNotification() }

    private fun initToastNotification(): ToastNotification {

        val toastNotification = ToastNotification(requireContext())

        toastNotification.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )

        val parentLayout = root as ConstraintLayout
        val set = ConstraintSet()

        toastNotification.id = View.generateViewId()

        parentLayout.addView(toastNotification)

        set.clone(parentLayout);
        set.connect(parentLayout.id, ConstraintSet.TOP, parentLayout.id, ConstraintSet.TOP);
        set.connect(parentLayout.id, ConstraintSet.LEFT, parentLayout.id, ConstraintSet.LEFT);

        set.applyTo(parentLayout);

        return toastNotification
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buttonNext.setOnClickListener {
            nwf_team1.setText(count)
            nwf_team2.setText(count + 3)
            count++

        }

        buttonClear.setOnClickListener {
            count = 1
            nwf_team1.setText(0)
            nwf_team2.setText(3)
        }

        buttonToast.setOnClickListener {

            when (toastType) {
                0 -> {
                    toastNotification.showWithTooImages(
                        R.drawable.team_img,
                        R.drawable.team2_img,
                        "Босния забивает ГОЛ!",
                        "Босния - Герциговина"
                    )
                    toastType = 1
                }
                1 -> {
                    toastNotification.showWithOneImage(
                        R.drawable.ic_mul_balls,
                        "Двойные мячи!",
                        "Получай двойные мячи за ставку"
                    )
                    toastNotification.hideWithDelay(3000)
                    toastType = 2
                }
                2 -> {
                    toastNotification.showWithOneImage(
                        R.drawable.ic_mul_balls,
                        "Матч участвует в X5",
                        "Ювентус - Лацио"
                    )
                    toastNotification.initBtn {
                        Toast.makeText(context, "TODO Action", Toast.LENGTH_SHORT).show()
                    }
                    toastType = 3
                }
                3 -> {
                    toastNotification.showWithoutImages(
                        "Босния забивает ГОЛ!",
                        "Босния - Герциговина"
                    )
                    toastType = 0
                }
                else -> {
                    toastType = 0
                }
            }


        }

        wvMain.loadUrl("https://yandex.ru")

        buttonShowBottomSheet.onClickDebounce {
            showBottomSheet()
        }


        flBkgBottomSheet.onClickDebounce {
            hideBottomSheet()
        }
        flTop.setOnTouchListener { _, event ->

            val y = event.rawY.toInt()
            when (event.action and MotionEvent.ACTION_MASK) {
                MotionEvent.ACTION_DOWN -> {
                    val lParams = flParent.layoutParams as FrameLayout.LayoutParams
                    _yDelta = y - lParams.topMargin
                }
                MotionEvent.ACTION_UP -> {
                    if ((flParent.layoutParams as FrameLayout.LayoutParams).topMargin < maxExpandHeight) {
                        expandBottomSheet()
                    } else {
                        hideBottomSheet()
                    }

                }

                MotionEvent.ACTION_MOVE -> {
                    val layoutParams = flParent.layoutParams as FrameLayout.LayoutParams

                    val newPosition = y - _yDelta

                    if (maxHeight < newPosition)
                        layoutParams.topMargin = newPosition

                    flParent.layoutParams = layoutParams
                }
            }
            flParent.invalidate()
            true
        }

    }


    val maxHeight = 120f.dp

    private val maxExpandHeight = 250f.dp

    private val animationToBottom: Animation = object : Animation() {
        override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
            val params = flParent.layoutParams as FrameLayout.LayoutParams
            params.topMargin =
                (params.topMargin - ((params.topMargin - screenWidth) * interpolatedTime).roundToInt())
            flParent.layoutParams = params
        }
    }

    private val animationToTop: Animation = object : Animation() {
        override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
            val params = flParent.layoutParams as FrameLayout.LayoutParams

            params.topMargin =
                (params.topMargin - ((params.topMargin - maxHeight) * interpolatedTime).roundToInt())

            flParent.layoutParams = params
        }
    }

    init {
        animationToBottom.duration = 1000
        animationToTop.duration = 500
    }

    fun hideBottomSheet() {
        flParent.startAnimation(animationToBottom)
        fadeOutAnimation.start()
    }

    fun showBottomSheet() {
        (flParent.layoutParams as FrameLayout.LayoutParams).topMargin = screenWidth
        fadeInAnimation.start()
    }

    private fun expandBottomSheet() {
        flParent.startAnimation(animationToTop)
    }

    private val fadeOutAnimation: ObjectAnimator by lazy {
        ObjectAnimator.ofFloat(
            flBkgBottomSheet,
            View.ALPHA,
            1f, 0f
        )
            .apply {
                duration = 1000

                doOnEnd {
                    flBkgBottomSheet.hide()
                }
            }
    }

    private val fadeInAnimation: ObjectAnimator by lazy {
        ObjectAnimator.ofFloat(
            flBkgBottomSheet,
            View.ALPHA,
            0f, 1f
        )
            .apply {
                duration = 500
                doOnStart {
                    flBkgBottomSheet.show()
                    expandBottomSheet()
                }
            }
    }

    private var _yDelta = 0
    val screenWidth: Int by lazy { requireContext().getScreenHeight() }


}