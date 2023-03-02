package io.github.argonariod.hammer.mirai.core.message

import io.github.argonariod.hammer.mirai.core.data.getOrPut
import io.github.argonariod.hammer.mirai.core.data.toHexString
import io.github.argonariod.hammer.mirai.core.resource.getResourceAsStream
import com.github.benmanes.caffeine.cache.Caffeine
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import net.mamoe.mirai.contact.Contact
import net.mamoe.mirai.message.data.Image
import net.mamoe.mirai.message.data.Image.Key.isUploaded
import net.mamoe.mirai.utils.ExternalResource.Companion.toExternalResource
import java.io.FileNotFoundException
import java.io.InputStream
import java.nio.file.Path
import kotlin.io.path.extension
import kotlin.io.path.inputStream
import kotlin.time.Duration.Companion.days
import kotlin.time.toJavaDuration

private val imageHashToInfoCache = Caffeine.newBuilder()
    .maximumSize(500)
    .expireAfterAccess(1.days.toJavaDuration())
    .build<String, ImageInfo>()

suspend fun imageFromExternalFiles(path: Path, contact: Contact): Image {
    return withContext(Dispatchers.IO) {
        path.inputStream().buffered()
            .let { imageFromInputStream(it, contact, path.extension) }
    }
}

suspend fun imageFromResources(path: String, contact: Contact): Image {
    return withContext(Dispatchers.IO) {
        @Suppress("ComplexRedundantLet")
        (getResourceAsStream(path) ?: throw FileNotFoundException("resource not found: $path"))
            .let { imageFromInputStream(it, contact, path.substringAfterLast('.')) }
    }
}

suspend fun imageFromInputStream(inputStream: InputStream, contact: Contact, format: String): Image {
    return inputStream.toExternalResource(format).use { resource ->
        var imageFromSource: Image? = null
        val imageInfo = imageHashToInfoCache.getOrPut(resource.sha1.toHexString()) {
            contact.uploadImage(resource)
                .let {
                    imageFromSource = it
                    ImageInfo(it)
                }
        }
        imageFromSource?.let { return@use it }

        val imageFromId = Image(imageInfo.imageId) {
            size = imageInfo.size
        }
        return@use if (!imageFromId.isUploaded(contact.bot)) {
            contact.uploadImage(resource)
                .also { imageHashToInfoCache.put(resource.sha1.toHexString(), ImageInfo(it)) }
        } else {
            imageFromId
        }
    }
}

internal data class ImageInfo(
    val imageId: String,
    val size: Long,
) {
    constructor(image: Image) : this(image.imageId, image.size)
}