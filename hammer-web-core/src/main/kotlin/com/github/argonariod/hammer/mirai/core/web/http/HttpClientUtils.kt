package com.github.argonariod.hammer.mirai.core.web.http

import io.ktor.client.request.*
import io.ktor.http.*

inline fun <reified T> HttpRequestBuilder.setJsonBody(body: T) {
    contentType(ContentType.Application.Json)
    setBody(body)
}