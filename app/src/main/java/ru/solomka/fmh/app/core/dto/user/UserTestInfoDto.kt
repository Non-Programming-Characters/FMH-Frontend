package ru.solomka.fmh.app.core.dto.user

import ru.solomka.fmh.app.core.dto.Entity
import java.time.Instant
import java.util.UUID

data class UserTestInfoDto(
    override val id: UUID,
    val userId: UUID,
    val totalQuestions: Int,
    val result: Int,
    override val createdAt: Instant
) : Entity