@file:Suppress("NOTHING_TO_INLINE")

package io.github.argonariod.hammer.mirai.core.message

import net.mamoe.mirai.console.command.CommandContext
import net.mamoe.mirai.console.command.ConsoleCommandSender
import net.mamoe.mirai.event.MessageDsl
import net.mamoe.mirai.event.events.MessageEvent
import net.mamoe.mirai.message.data.*

@MessageDsl
inline fun Any?.quote(event: MessageEvent): Any {
    return when (this) {
        null, is Unit -> Unit
        else -> this.quote(event.message)
    }
}

inline fun Any?.quote(sourceMessage: MessageChain): MessageChain {
    return when (this) {
        is Message -> QuoteReply(sourceMessage) + this
        else -> QuoteReply(sourceMessage) + PlainText(this.toString())
    }
}

suspend fun CommandContext.quoteReply(message: String) = quoteReply(PlainText(message))

suspend fun CommandContext.quoteReply(message: Message) {
    val sender = sender
    if (sender is ConsoleCommandSender) {
        sender.sendMessage(message)
    } else {
        sender.sendMessage(message.quote(this.originalMessage))
    }
}