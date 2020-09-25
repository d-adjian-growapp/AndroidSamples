package biz.growapp.base.extension

import java.io.ByteArrayInputStream
import java.util.zip.GZIPInputStream

fun ByteArray.asString(): String {
    return String(this)
}

fun ByteArray.decompressGZIP(): ByteArray {
    val gzipInputStream = GZIPInputStream(ByteArrayInputStream(this))
    return gzipInputStream.readBytes()
}

/*fun Any.toByteArray(): ByteArray {
    when(this) {
        is String -> {
            String.toByteArray()
        }
        else -> {
            ByteArrayOutputStream().use({ b ->
                ObjectOutputStream(b).use({ o -> o.writeObject(this) })
                return b.toByteArray()
            })
        }
    }
}*/
