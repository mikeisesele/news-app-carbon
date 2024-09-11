package com.michael.common

class LruCache<E>(private val maxSize: Int) {
    private val items = linkedSetOf<E>()

    fun add(item: E) {
        if (items.contains(item)) {
            items.remove(item)
        } else if (items.size == maxSize) {
            items.remove(items.first())
        }
        items.add(item)
    }

    fun getItems(): Set<E> = items
}
