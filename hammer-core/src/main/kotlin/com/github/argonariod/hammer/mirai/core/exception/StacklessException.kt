package com.github.argonariod.hammer.mirai.core.exception

open class StacklessException(message: String) : Exception(message) {
    override fun fillInStackTrace() = this
}