@file:JvmName("DrawableUtils")

package biz.growapp.base.extension


import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.drawable.Drawable
import android.graphics.drawable.StateListDrawable
import android.os.Build
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat


fun Int.loadVector(context: Context): Drawable = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
    ContextCompat.getDrawable(context, this)!!
else
    VectorDrawableCompat.create(context.resources, this, context.theme) as Drawable

fun Int.loadVector(context: Context, @ColorRes colorRes: Int): Drawable =
        loadVector(context).mutate().apply {
            colorFilter = PorterDuffColorFilter(
                    ContextCompat.getColor(context, colorRes),
                    PorterDuff.Mode.SRC_IN
            )
        }

fun @receiver:DrawableRes Int.getDrawableCompat(context: Context) = ContextCompat.getDrawable(context, this)!!

fun @receiver:DrawableRes Int.getDrawableCompat(context: Context, @ColorRes tintRes: Int): Drawable =
        ContextCompat.getDrawable(context, this)!!.mutate().apply {
            colorFilter = PorterDuffColorFilter(
                    ContextCompat.getColor(context, tintRes),
                    PorterDuff.Mode.SRC_IN
            )
        }

fun Int.bitmapFromVectorRes(context: Context): Bitmap {
    val drawable = this.loadVector(context)
    val bitmap = Bitmap.createBitmap(drawable.intrinsicWidth, drawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    drawable.setBounds(0, 0, canvas.width, canvas.height)
    drawable.draw(canvas)
    return bitmap
}

fun Context.makeCheckedSelector(@DrawableRes checkedId: Int, @DrawableRes defaultId: Int) =
        StateListDrawable().apply {
            addState(intArrayOf(android.R.attr.state_checked), checkedId.loadVector(this@makeCheckedSelector))
            addState(intArrayOf(), defaultId.loadVector(this@makeCheckedSelector))
        }
