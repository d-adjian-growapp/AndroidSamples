package biz.growapp.base.extension

public inline fun <T> List<T>.lastIndexOfElem(predicate: (T) -> Boolean): Int {
    val iterator = this.listIterator(size)
    var index = size - 1
    while (iterator.hasPrevious()) {
        val element = iterator.previous()
        if (predicate(element)) {
            return index
        } else {
            index--
        }
    }
    throw NoSuchElementException("List contains no element matching the predicate.")
}