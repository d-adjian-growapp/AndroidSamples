package biz.growapp.base.extension

import android.util.SparseArray

fun<T> SparseArray<T>.asList(): List<T> {
    return (0 until size()).map { valueAt(it) }
}
