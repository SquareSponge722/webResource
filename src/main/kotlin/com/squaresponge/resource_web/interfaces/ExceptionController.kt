package com.squaresponge.resource_web.interfaces

import com.squaresponge.resource_web.domain.user.exception.IllegalInvitationCodeException
import com.squaresponge.resource_web.domain.user.exception.PasswordIncorrectException
import com.squaresponge.resource_web.domain.user.exception.PointsShortageException
import com.squaresponge.resource_web.interfaces.message_manage.MessageController
import com.squaresponge.resource_web.interfaces.resource_manage.ResourceController
import com.squaresponge.resource_web.interfaces.user_manage.UserController
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice(basePackageClasses = [
    MessageController::class,
    ResourceController::class,
    UserController::class
])
class ExceptionController {
    @ExceptionHandler(value = [
        IllegalStateException::class,
        IllegalInvitationCodeException::class,
        PasswordIncorrectException::class,
        PointsShortageException::class
    ])
    fun onIllegalState(exception: Exception) = ResponseEntity.status(HttpStatus.FORBIDDEN).body(exception.message)

    @ExceptionHandler(value = [NotFoundException::class])
    fun onNotFound(exception: Exception) = ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.message)

    @ExceptionHandler(value = [IllegalArgumentException::class])
    fun onIllegalArgument(exception: Exception) = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.message)
}