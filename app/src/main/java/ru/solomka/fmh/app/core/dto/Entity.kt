package ru.solomka.fmh.app.core.dto

import java.time.Instant
import java.util.UUID

interface Entity {
    val id: UUID
    val createdAt: Instant
}