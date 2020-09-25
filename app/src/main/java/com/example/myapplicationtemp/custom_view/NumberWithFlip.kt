package com.example.myapplicationtemp.custom_view

import android.animation.ObjectAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.animation.doOnEnd
import androidx.core.animation.doOnStart
import com.example.myapplicationtemp.R
import kotlinx.android.synthetic.main.number_with_flip.view.*

class NumberWithFlip(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs) {

    companion object {
        const val ANIMATION_DURATION = 300L
    }

    init {
        inflate(context, R.layout.number_with_flip, this)

        oldVal(0)
    }

    private val anim2 = ObjectAnimator.ofFloat(
        tvNewDynamic,
        View.ROTATION_X,
        0f
    )
        .apply {
            duration = ANIMATION_DURATION
            doOnStart {
                tvNewDynamic.visibility = View.VISIBLE
            }
        }

    private val anim1 = ObjectAnimator.ofFloat(
        tvOldDynamic,
        View.ROTATION_X,
        -90f
    )
        .apply {
            duration = ANIMATION_DURATION
            doOnEnd {
                tvOldDynamic.visibility = View.INVISIBLE
                anim2.start()
            }
        }


    fun setText(text: Int) {
        setNew(text)

        tvNewDynamic.rotationX = 90f
        tvOldDynamic.rotationX = 0f
        tvNewDynamic.visibility = View.INVISIBLE
        tvOldDynamic.visibility = View.VISIBLE

        animStart()
        oldValue = text
    }

    private fun setNew(number: Int) {
        tvNewDynamic.text = number.toString()
        tvNewStatic.text = number.toString()
        oldVal(oldValue)
    }

    private var oldValue = 0

    private fun oldVal(number: Int) {
        oldValue = number
        tvOldDynamic.text = oldValue.toString()
        tvOldStatic.text = oldValue.toString()
    }

    private fun animStart() {
        anim1.start()
    }


}