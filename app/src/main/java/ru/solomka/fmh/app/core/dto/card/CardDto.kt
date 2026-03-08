package ru.solomka.fmh.app.core.dto.card

import ru.solomka.fmh.app.core.dto.Entity
import java.util.Date
import java.util.UUID

data class CardDto(
    val id: UUID,
    val title: String,
    val shortDesc: String,
    val fullDesc: String,
    val mainCategory: CargoMainEventCategory,
    val subEventCategory: CargoMainEventCategory.SubEventCategory,
    val sources: String,
    val createdAt: Date
) : Entity {
    override fun getId(): UUID = id
    override fun getCreatedAt(): Date = createdAt
}