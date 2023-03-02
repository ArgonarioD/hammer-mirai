package com.github.argonariod.hammer.mirai.core.test.util

import net.mamoe.mirai.event.Event
import net.mamoe.mirai.event.GlobalEventChannel
import net.mamoe.mirai.mock.utils.MockActionsScope
import net.mamoe.mirai.mock.utils.broadcastMockEvents

suspend inline fun broadcastMockEventsAndCatching(crossinline block: suspend MockActionsScope.() -> Unit): List<Event> {
    val events = mutableListOf<Event>()
    val listener = GlobalEventChannel.subscribeAlways<Event> { events.add(this) }
    broadcastMockEvents { block() }
    listener.cancel()
    return events
}