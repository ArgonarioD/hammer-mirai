@file:Suppress("NOTHING_TO_INLINE")

package io.github.argonariod.hammer.mirai.core.data

import cn.hutool.core.convert.Convert
import java.security.MessageDigest

@PublishedApi
internal val MD5_ALGORITHM = MessageDigest.getInstance("MD5")

@PublishedApi
internal val SHA1_ALGORITHM = MessageDigest.getInstance("SHA-1")

@PublishedApi
internal val SHA256_ALGORITHM = MessageDigest.getInstance("SHA-256")

inline fun ByteArray.md5(): ByteArray = MD5_ALGORITHM.digest(this)
inline fun ByteArray.sha256(): ByteArray = SHA1_ALGORITHM.digest(this)
inline fun ByteArray.sha1(): ByteArray = SHA256_ALGORITHM.digest(this)
inline fun ByteArray.toHexString(): String = Convert.toHex(this)