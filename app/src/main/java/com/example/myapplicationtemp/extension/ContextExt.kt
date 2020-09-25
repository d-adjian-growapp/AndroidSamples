package biz.growapp.base.extension

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Uri
import java.io.InputStream
import java.nio.charset.Charset

fun InputStream.readText(charset: Charset = Charsets.UTF_8) = bufferedReader(charset).use { it.readText() }

fun Context.browse(url: String, newTask: Boolean = false): Boolean {
    try {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        if (newTask) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        startActivity(intent)
        return true
    } catch (e: ActivityNotFoundException) {
        e.printStackTrace()
        return false
    }
}

fun Context.email(email: String, subject: String = "", text: String = ""): Boolean {
    val intent = Intent(Intent.ACTION_SENDTO)
    intent.data = Uri.parse("mailto:")
    intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
    if (subject.length > 0)
        intent.putExtra(Intent.EXTRA_SUBJECT, subject)
    if (text.length > 0)
        intent.putExtra(Intent.EXTRA_TEXT, text)
    if (intent.resolveActivity(packageManager) != null) {
        startActivity(intent)
        return true
    }
    return false

}

fun Context.hasNetworkConnection(): Boolean {
    val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val networkInfo = cm.activeNetworkInfo
    return networkInfo != null && networkInfo.isConnected
}

fun Context.makeDial(phone: String): Boolean {
    try {
        val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phone"))
        startActivity(intent)
        return true
    } catch (e: ActivityNotFoundException) {
        e.printStackTrace()
        return false
    }
}