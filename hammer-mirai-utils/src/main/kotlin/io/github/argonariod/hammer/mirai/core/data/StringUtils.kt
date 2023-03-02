package io.github.argonariod.hammer.mirai.core.data

import cn.hutool.core.convert.Convert

suspend fun <T> Iterable<T>.joinToStringSuspendable(
    separator: CharSequence = ", ",
    prefix: CharSequence = "",
    postfix: CharSequence = "",
    limit: Int = -1,
    truncated: CharSequence = "...",
    transform: suspend (T) -> CharSequence
): String {
    val buffer = StringBuilder()
    buffer.append(prefix)
    var count = 0
    for (element in this) {
        if (++count > 1) buffer.append(separator)
        if (limit < 0 || count <= limit) {
            buffer.append(transform(element))
        } else break
    }
    if (limit in 0 until count) buffer.append(truncated)
    buffer.append(postfix)
    return buffer.toString()
}

@Suppress("NOTHING_TO_INLINE")
inline fun String.fromChineseToInt(): Int = Convert.chineseToNumber(this)

@Suppress("NOTHING_TO_INLINE")
inline fun String.fromChineseToIntOrNull(): Int? {
    return try {
        this.fromChineseToInt()
    } catch (e: Exception) {
        null
    }
}