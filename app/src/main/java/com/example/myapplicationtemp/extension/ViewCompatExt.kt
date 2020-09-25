@file:JvmName("ViewCompatUtils")
@file:Suppress("NOTHING_TO_INLINE")

package biz.growapp.base.extension


import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.core.view.ViewCompat

inline fun View.show() {
    visibility = View.VISIBLE
}

inline  fun View.invisible() {
    visibility = View.INVISIBLE
}

inline fun View.hide() {
    visibility = View.GONE
}

inline fun View.changeVisibility(isVisible: Boolean) {
    if (isVisible) {
        show()
    } else {
        hide()
    }
}

inline fun View.isVisible() = visibility == View.VISIBLE

fun ViewGroup.enable(value: Boolean, withChildren: Boolean = false) {
    isEnabled = value
    if (withChildren) {
        (0 until childCount)
                .map { getChildAt(it) }
                .forEach {
                    if (it is ViewGroup)
                        it.enable(value, true)
                    else
                        it.isEnabled = value
                }
    }
}

fun View.updateMarginTop(size: Int) {
    (this.layoutParams as? ViewGroup.MarginLayoutParams)?.let {
        it.topMargin = size
        this.layoutParams = it
    }
}

fun ViewGroup.invalidateBottomMargin(size: Int) {
    val params = this.layoutParams as ViewGroup.MarginLayoutParams
    params.bottomMargin = size
    layoutParams = params
}

fun ViewGroup.invalidateMargins(left: Int, top: Int, right: Int, bottom: Int) {
    val params = this.layoutParams as ViewGroup.MarginLayoutParams
    params.leftMargin = left
    params.topMargin = top
    params.rightMargin = right
    params.bottomMargin = bottom
    layoutParams = params
}

var View.elevationCompat: Float
    get() = ViewCompat.getElevation(this)
    set(value) = ViewCompat.setElevation(this, value)

inline fun View.onGlobalLayout(crossinline action: () -> Unit): ViewTreeObserver.OnGlobalLayoutListener {
    val listener = object : ViewTreeObserver.OnGlobalLayoutListener {
        override fun onGlobalLayout() {
            viewTreeObserver.removeOnGlobalLayoutListener(this)
            action()
        }
    }
    viewTreeObserver.addOnGlobalLayoutListener(listener)
    return listener
}

val DEFAULT_DELAY_MS = 750L

inline fun View.onClickDebounce(delayMs: Long = DEFAULT_DELAY_MS, crossinline l: (View?) -> Unit) {
    setOnClickListener(object : View.OnClickListener {
        private var notClicked = true
        override fun onClick(view: View) {
            if (notClicked) {
                notClicked = false
                l(view)
                view.postDelayed({ notClicked = true }, delayMs)
            }
        }
    })
}

inline fun View.onClick(crossinline l: (View?) -> Unit) = onClickDebounce(DEFAULT_DELAY_MS, l)

fun View.updateSize(width: Int? = null, height: Int? = null) {
    val params = this.layoutParams
    if (width != null) {
        params.width = width
    }
    if (height != null) {
        params.height = height
    }
    this.layoutParams = params
}

