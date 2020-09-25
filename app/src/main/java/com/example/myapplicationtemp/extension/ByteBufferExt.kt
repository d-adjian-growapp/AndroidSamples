package biz.growapp.base.extension

import java.nio.ByteBuffer

fun ByteBuffer.readType(): Short {
    return this.short
}

fun ByteBuffer.byte(): Byte = this.get()

fun ByteBuffer.string(): String {
    val count = this.short.toInt()
    if (count <= 0) {
        return ""
    }
    val array = ByteArray(count)
    this.get(array, 0, count)
    return String(array)
}

fun ByteBuffer.stringList(size: Int): List<String> {
    val list = mutableListOf<String>()
    for (i in 0 until size) {
        val string = string()
        if (string.isNotEmpty()) {
            list.add(string)
        }
    }
    return list
}



fun ByteBuffer.makeOffset(offset: Int): ByteBuffer {
    val tempArray = ByteArray(offset)
    return this.get(tempArray, 0, offset)
}