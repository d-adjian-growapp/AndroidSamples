@file:JvmName("ParcelableUtils")
@file:Suppress("NOTHING_TO_INLINE")

package biz.growapp.base.extension

import android.os.Parcel
import android.os.Parcelable

inline fun <reified T> parcelableCreator(crossinline body: (source: Parcel) -> T) = object : Parcelable.Creator<T> {
    override fun createFromParcel(source: Parcel): T = body(source)
    override fun newArray(size: Int): Array<T?> = arrayOfNulls(size)
}

inline fun Parcel.readBooleanExt() = readInt() != 0

inline fun Parcel.writeBooleanExt(value: Boolean?) = writeInt(if (value ?: false) 1 else 0)


inline fun Parcel.writeInt(value: Int?) = if (value == null) {
    writeBooleanExt(false)
} else {
    writeBooleanExt(true)
    writeInt(value)
}

inline fun Parcel.readIntUtil() = if (readBooleanExt()) readInt() else null


inline fun Parcel.writeDoubleExt(value: Double?) = if (value == null) {
    writeBooleanExt(false)
} else {
    writeBooleanExt(true)
    writeDouble(value)
}

inline fun Parcel.readDoubleUtil() = if (readBooleanExt()) readDouble() else null


inline fun Parcel.writeLongExt(value: Long?) = if (value == null) {
    writeBooleanExt(false)
} else {
    writeBooleanExt(true)
    writeLong(value)
}

inline fun Parcel.readLongUtil() = if (readBooleanExt()) readLong() else null