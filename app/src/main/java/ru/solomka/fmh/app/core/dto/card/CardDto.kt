package ru.solomka.fmh.app.core.dto.card

import ru.solomka.fmh.app.core.dto.Entity
import java.time.Instant
import java.util.UUID

data class CardDto(
    override val id: UUID,
    val title: String,
    val shortDesc: String,
    val fullDesc: String,
    val mainCategory: CargoMainEventCategory,
    val subEventCategory: CargoMainEventCategory.SubEventCategory,
    val sources: String,
    override val createdAt: Instant,
) : Entity