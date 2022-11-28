package com.squaresponge.resource_web.domain.user.exception

import com.squaresponge.resource_web.domain.user.Invitation

class IllegalInvitationCodeException(private val invitation: Invitation) : Exception() {
    override val message: String
        get() = "illegal invitation code ${invitation.code} " +
                "which belongs to a nonexistent user " +
                "or the user is not allowed to invite others."
}