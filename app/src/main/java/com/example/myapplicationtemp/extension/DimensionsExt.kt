@file:JvmName("DimensionUtils")

package biz.growapp.base.extension


import android.content.res.Resources
import android.util.TypedValue

val Float.dp: Int
    get() = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this, Resources.getSystem().displayMetrics).toInt()