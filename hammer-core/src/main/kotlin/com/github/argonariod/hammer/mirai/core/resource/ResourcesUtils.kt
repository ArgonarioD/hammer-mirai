package com.github.argonariod.hammer.mirai.core.resource

import net.mamoe.mirai.contact.Contact
import net.mamoe.mirai.message.data.Image
import net.mamoe.mirai.utils.ExternalResource.Companion.uploadAsImage
import java.io.InputStream

fun getResourceAsStream(path: String): InputStream? = object {}.javaClass.getResourceAsStream(path)

suspend inline fun InputStream.uploadAsImageAndAutoClose(subject: Contact, format: String): Image {
    return use { uploadAsImage(subject, format) }
}