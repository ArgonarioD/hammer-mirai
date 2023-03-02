package io.github.argonariod.hammer.mirai.core.test.util

import kotlinx.coroutines.runBlocking
import org.assertj.core.api.AbstractThrowableAssert
import org.assertj.core.api.WithAssertions

inline fun runTest(crossinline block: suspend () -> Unit) = runBlocking {
    block()
}

inline fun WithAssertions.assertThatThrownSuspendedBy(crossinline block: suspend () -> Unit): AbstractThrowableAssert<*, out Throwable> =
    assertThatThrownBy { runBlocking { block() } }