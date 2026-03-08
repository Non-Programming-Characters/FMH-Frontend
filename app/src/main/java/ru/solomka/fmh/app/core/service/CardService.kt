package ru.solomka.fmh.app.core.service

import ru.solomka.fmh.app.core.dto.card.CardDto
import java.util.UUID

interface CardService {
    fun findAllCards(): List<CardDto>
    fun findCardById(id: UUID): CardDto
}