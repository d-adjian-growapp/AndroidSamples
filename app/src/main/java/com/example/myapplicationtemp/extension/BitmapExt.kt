package biz.growapp.base.extension

import android.graphics.Bitmap

fun Bitmap.scaleDown(maxHeith: Double): Bitmap {
    val ratio = this.height / maxHeith
    val newHeight = maxHeith.toInt()
    val newWidth = (this.width / ratio).toInt()
    return Bitmap.createScaledBitmap(this, newWidth, newHeight, true)
}