package mkug.mpp.common

fun <T : Any?> MutableList<T>?.initIfNeedAndAdd(item: T): MutableList<T> =
    (this ?: mutableListOf()).also { it.add(item) }

fun <T : Any?> MutableList<T>?.initIfNeedAndAddAll(items: Collection<T>): MutableList<T> =
    (this ?: mutableListOf()).also { it.addAll(items) }