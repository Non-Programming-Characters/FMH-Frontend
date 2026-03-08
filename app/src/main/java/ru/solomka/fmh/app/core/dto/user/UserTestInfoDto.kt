package ru.solomka.fmh.app.core.dto.user

import ru.solomka.fmh.app.core.dto.Entity
import java.util.Date
import java.util.UUID

data class UserTestInfoDto(
    val id: UUID = UUID.randomUUID(),
    val userId: UUID,
    val totalQuestions: Int,
    val result: Int,
    val createdAt: Date
) : Entity {
    override fun getId(): UUID = id
    override fun getCreatedAt(): Date = createdAt
}