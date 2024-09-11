package com.michael.common

import java.util.concurrent.ConcurrentHashMap

class LifecycleHandler<T> {

    @Volatile
    private var items: ConcurrentHashMap<Int, T?> = ConcurrentHashMap()

    @Synchronized
    fun getOrInit(
        key: Int,
        factory: () -> T,
    ): T {
        return if (items[key] != null) {
            items[key]!!
        } else {
            items[key] = factory()
            items[key]!!
        }
    }

    @Synchronized
    fun getOrThrow(key: Int): T {
        return if (items[key] != null) {
            items[key]!!
        } else {
            throw IllegalStateException("Trying to get non-initialized item.")
        }
    }

    @Synchronized
    fun destroy(key: Int) {
        items.remove(key)
    }
}
