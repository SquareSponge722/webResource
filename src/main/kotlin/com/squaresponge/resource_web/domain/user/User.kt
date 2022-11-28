package com.squaresponge.resource_web.domain.user

import com.squaresponge.resource_web.domain.shared.Entity
import com.squaresponge.resource_web.domain.user.exception.IllegalInvitationCodeException
import com.squaresponge.resource_web.domain.user.exception.PointsShortageException

data class User(
    val userId: UserId,
    var identity: Identity,
    var point: Point,
    var account: Account,
    var state: State,
    var invitation: Invitation
) : Entity<User> {
    fun introduceNewUser(newAccount: Account): User {
        if (!this.state.canInviteOthers) throw IllegalInvitationCodeException(invitation)
        return User(
            userId = UserId(),
            identity = identity,
            point = Point(),
            account = newAccount,
            state = State(State.Type.UNREGISTER),
            invitation = Invitation.generate(userId)
        )
    }

    fun collectPoint(point: Point) {
        this.point = this.point + point
    }

    fun login(): Boolean {
        if (!state.canLogin) {
            throw IllegalStateException("user $this is in illegal state to login.")
        }
        val isDailyLogin = state.dailyLogin
        if (isDailyLogin) {
            collectPoint(Point(5))
        }
        state = State(State.Type.ONLINE)
        return isDailyLogin
    }

    fun download() {
        if (!point.canReduce(Point(10))) {
            throw PointsShortageException(account.name, point)
        }
        collectPoint(Point(-10))
    }

    override fun sameIdentityAs(other: User) =
        (this.userId == other.userId)
                && (this.account.name == other.account.name)
                && (this.invitation == other.invitation)
}