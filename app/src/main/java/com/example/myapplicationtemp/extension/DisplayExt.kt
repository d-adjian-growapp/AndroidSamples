@file:JvmName("DisplayUtils")

package biz.growapp.base.extension

import android.app.Activity
import android.content.Context
import android.util.DisplayMetrics
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

fun Activity.hideKeyboard(clearFocus: Boolean = true) {
    (currentFocus ?: window.decorView).hideKeyboard(clearFocus)
}

fun View?.hideKeyboard(clearFocus: Boolean = true) {
    this ?: return
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
    if (clearFocus) clearFocus()
}

fun View?.requestKeyboardFocus() {
    this ?: return
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.toggleSoftInputFromWindow(applicationWindowToken, InputMethodManager.SHOW_IMPLICIT, 0)
    requestFocus()
}

fun Context.requestKeyboardFocus(editText: EditText): Boolean {
    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    editText.requestFocus()
    val result = imm.showSoftInput(editText, 0)
    return result
}

fun Context.getScreenWidth(): Int {
    return getDisplayMetrics(this).widthPixels
}

fun Context.getScreenHeight(): Int {
    return getDisplayMetrics(this).heightPixels
}

private fun getDisplayMetrics(context: Context): DisplayMetrics {
    val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    val display = wm.defaultDisplay
    val metrics = DisplayMetrics()
    display.getMetrics(metrics)
    return metrics
}
