package io.github.argonariod.hammer.mirai.core.data

import com.github.benmanes.caffeine.cache.Cache
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend inline fun <K, V> Cache<K, V>.getOrPut(key: K, crossinline defaultValue: suspend (key: K) -> V): V {
    return getIfPresent(key) ?: withContext(Dispatchers.Default) {
        defaultValue(key)
    }.also { put(key, it) }
}