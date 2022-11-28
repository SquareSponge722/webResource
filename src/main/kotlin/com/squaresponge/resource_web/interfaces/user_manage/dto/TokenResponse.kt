package com.squaresponge.resource_web.interfaces.user_manage.dto

data class TokenResponse(
    val accessToken: String,
    val refreshToken: String
)
