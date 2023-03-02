@file:Suppress("NOTHING_TO_INLINE")

package com.github.argonariod.hammer.mirai.core.permission

import net.mamoe.mirai.console.permission.AbstractPermitteeId
import net.mamoe.mirai.console.permission.PermissionId
import net.mamoe.mirai.event.events.GroupMessageEvent
import net.mamoe.mirai.event.events.MessageEvent

inline val GroupMessageEvent.exactMember: AbstractPermitteeId.ExactMember
    get() = AbstractPermitteeId.ExactMember(this.group.id, this.sender.id)
inline val MessageEvent.exactUser: AbstractPermitteeId.ExactUser
    get() = AbstractPermitteeId.ExactUser(this.sender.id)

inline fun permissionId(id: String): PermissionId {
    val (namespace, name) = id.split(":", limit = 2)
    return PermissionId(namespace, name)
}