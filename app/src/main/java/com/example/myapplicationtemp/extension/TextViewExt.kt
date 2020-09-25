@file:JvmName("TextViewUtils")

package biz.growapp.base.extension

import android.graphics.drawable.Drawable
import android.os.Build
import android.view.View
import android.widget.TextView
import androidx.annotation.StyleRes


private const val LEFT = 0
private const val TOP = 1
private const val RIGHT = 2
private const val BOTTOM = 3

var TextView.drawableLeft: Drawable?
    get() = compoundDrawables[LEFT]
    set(value) = compoundDrawables.let {
        setCompoundDrawablesWithIntrinsicBounds(value, it[TOP], it[RIGHT], it[BOTTOM])
    }

var TextView.drawableLeftWithBounds: Drawable?
    get() = compoundDrawables[LEFT]
    set(value) = compoundDrawables.let {
        setCompoundDrawables(value, it[TOP], it[RIGHT], it[BOTTOM])
    }

var TextView.drawableRight: Drawable?
    get() = compoundDrawables[RIGHT]
    set(value) = compoundDrawables.let {
        setCompoundDrawablesWithIntrinsicBounds(it[LEFT], it[TOP], value, it[BOTTOM])
    }

var TextView.drawableRightWithBounds: Drawable?
    get() = compoundDrawables[RIGHT]
    set(value) = compoundDrawables.let {
        setCompoundDrawables(it[LEFT], it[TOP], value, it[BOTTOM])
    }

var TextView.drawableTop: Drawable?
    get() = compoundDrawables[TOP]
    set(value) = compoundDrawables.let {
        setCompoundDrawablesWithIntrinsicBounds(it[LEFT], value, it[RIGHT], it[BOTTOM])
    }

var TextView.drawableTopWithBounds: Drawable?
    get() = compoundDrawables[TOP]
    set(value) = compoundDrawables.let {
        setCompoundDrawables(it[LEFT], value, it[RIGHT], it[BOTTOM])
    }

fun TextView.hideIfTextNull(text: CharSequence?) {
    this.text = text
    visibility = if (text == null) View.GONE else View.VISIBLE
}

fun TextView.invisibleIfTextZeroOrNull(text: Int?) {
    this.text = text.toString()
    visibility = if (text == 0 || text == null) View.INVISIBLE else View.VISIBLE
}

fun TextView.hideIfTextZeroOrNull(text: Int?) {
    this.text = text.toString()
    visibility = if (text == 0 || text == null) View.GONE else View.VISIBLE
}

fun TextView.setTextAppearanceFromCode(@StyleRes resId: Int) {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
        setTextAppearance(context, resId)
    } else {
        setTextAppearance(resId)
    }
}