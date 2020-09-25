package biz.growapp.base.extension

import android.os.Bundle

fun Bundle.getIntOrNull(key: String): Int? {
    return if (containsKey(key)) {
        getInt(key)
    } else {
        null
    }
}
