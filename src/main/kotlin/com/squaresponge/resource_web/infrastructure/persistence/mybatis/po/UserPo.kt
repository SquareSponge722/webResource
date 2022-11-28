package com.squaresponge.resource_web.infrastructure.persistence.mybatis.po

import com.squaresponge.resource_web.domain.user.*
import java.sql.Date
import java.time.LocalDate

data class UserPo(
    val id: Long,
    val name: String,
    val password: String,
    val identity: Identity,
    val code: String,
    val point: Int = 0,
    val lastLogin: Date? = null,
    val banned: Date? = null,
    val invitorId: Long?
) {
    companion object {
        const val NO_ID = -1L

        fun ofUser(user: User) = UserPo(
            id = user.userId.id?.toLong() ?: NO_ID,
            name = user.account.name,
            password = user.account.password.password!!,
            identity = user.identity,
            code = user.invitation.code,
            point = user.point.point,
            lastLogin = if (user.state.canLogin) Date.valueOf(user.state.date) else null,
            banned = if (user.state.banned) Date.valueOf(user.state.date) else null,
            invitorId = user.invitation.invitor.id?.toLong()
        )
    }

    fun toUser(): User {
        var state = State(State.Type.OFFLINE)
        val currentDate = LocalDate.now()
        if (lastLogin != null && lastLogin.toLocalDate() >= currentDate) {
            state = state.copy(type = State.Type.ONLINE)
        }

        val unbanDate = banned?.toLocalDate()
        if (unbanDate != null && unbanDate >= currentDate) {
            state = State(State.Type.BANNED, unbanDate)
        }

        return User(
            userId = UserId(id.toString()),
            identity = identity,
            point = Point(this.point),
            account = Account(
                name = name,
                password = Password.encoded(password)
            ),
            state = state,
            invitation = Invitation(
                code = code,
                invitor = UserId(invitorId?.toString())
            )
        )
    }
}
