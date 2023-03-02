@file:Suppress("NOTHING_TO_INLINE")

package com.github.argonariod.hammer.mirai.core.listener

import net.mamoe.mirai.event.Event
import net.mamoe.mirai.event.EventChannel
import net.mamoe.mirai.event.ListenerHost

/**
 * 事件监听器处理器，在[registerSubscribeHandler]中注册监听器后，通过[registerHandlers]注册到[EventChannel]中
 */
interface EventListenerHandler<out E : Event> : ListenerHost {
    fun registerSubscribeHandler(eventChannel: EventChannel<@UnsafeVariance E>)
}

inline fun <E : Event> EventChannel<E>.registerHandlers(vararg handlers: EventListenerHandler<E>) {
    handlers.forEach { it.registerSubscribeHandler(this) }
}