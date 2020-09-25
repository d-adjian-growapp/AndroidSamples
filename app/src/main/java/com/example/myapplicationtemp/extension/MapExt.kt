package biz.growapp.base.extension

fun<K, V> Map<K, V>.toValuesList(): List<V> = this.map { it.value }