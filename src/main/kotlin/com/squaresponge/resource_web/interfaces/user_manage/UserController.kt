package com.squaresponge.resource_web.interfaces.user_manage

import com.squaresponge.resource_web.domain.service.UserService
import com.squaresponge.resource_web.domain.user.*
import com.squaresponge.resource_web.domain.user.repository.UserRepository
import com.squaresponge.resource_web.infrastructure.security.JwtUtil
import com.squaresponge.resource_web.interfaces.user_manage.dto.RegisterInfo
import com.squaresponge.resource_web.interfaces.user_manage.dto.TokenResponse
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate

@RestController
@RequestMapping("/user")
class UserController(
    private val userRepository: UserRepository,
    private val userService: UserService,
    private val jwtUtil: JwtUtil
) {
    @PostMapping("/register")
    fun register(registerInfo: RegisterInfo) {
        val invitation = registerInfo.toInvitation()
        val invitor = userRepository.findUser(invitation)
        userRepository.save(
            invitor.introduceNewUser(
                registerInfo.toAccount()
            )
        )
    }

    @PostMapping("/login")
    @Transactional
    fun login(
        @RequestPart("username") username: String,
        @RequestPart("password") password: String
    ): TokenResponse {
        val account = Account(username, Password.raw(password))
        val user = userRepository.findUser(account)
        userService.loginWithAccount(user, account)
        userRepository.save(user)
        return TokenResponse(
            accessToken = jwtUtil.issueToken(username, user.identity.name),
            refreshToken = jwtUtil.issueToken(username, user.identity.name, 360000L)
        )
    }

    @GetMapping("/refresh")
    fun refresh(authenticationToken: JwtAuthenticationToken): String {
        val username = authenticationToken.token.subject
        val identity = authenticationToken.token.getClaimAsString("scope")
        return jwtUtil.issueToken(username, identity)
    }

    @GetMapping("/ban")
    @Transactional
    fun ban(
        @RequestParam("id") userId: String,
        @RequestParam("days") days: Long = 7,
        @RequestParam("points") points: Int = -50
    ) {
        if (days <= 0 || points > 0) throw IllegalArgumentException(
            "days can not be negative.\n" + "points can not be positive."
        )

        val user = userRepository.findUser(UserId(userId))
        user.state = State(State.Type.BANNED, LocalDate.now().plusDays(days))

        val invitor = userRepository.findUser(user.invitation.invitor)
        invitor.collectPoint(Point(points))

        userRepository.save(user)
        userRepository.save(invitor)
    }
}