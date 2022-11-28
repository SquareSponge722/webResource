package com.squaresponge.resource_web.domain.user

import com.squaresponge.resource_web.domain.shared.ValueObject
import java.time.LocalDate

data class State(
    val type: Type,
    val date: LocalDate = LocalDate.now()
) : ValueObject<State> {

    val canInviteOthers: Boolean
        get() = when (this.type) {
            Type.UNREGISTER, Type.BANNED -> false
            else -> true
        }

    val dailyLogin: Boolean
        get() = when (this.type) {
            Type.OFFLINE -> true
            else -> false
        }

    val canLogin: Boolean
        get() = when (this.type) {
            Type.OFFLINE, Type.ONLINE -> true
            else -> false
        }

    val banned: Boolean
        get() = when (this.type) {
            Type.BANNED -> true
            else -> false
        }

    enum class Type {
        UNREGISTER, OFFLINE, ONLINE, BANNED;
    }
}
