package ru.solomka.fmh.app.core.service.impl

import androidx.lifecycle.ViewModel
import ru.solomka.fmh.app.MainActivity
import ru.solomka.fmh.app.api.CardApi
import ru.solomka.fmh.app.core.dto.card.CardDto
import ru.solomka.fmh.app.core.dto.card.CargoMainEventCategory
import ru.solomka.fmh.app.core.repository.NetworkRepository
import ru.solomka.fmh.app.core.service.CardService
import java.time.Instant
import java.util.UUID

class CardServiceImpl(
    private val api: CardApi = MainActivity.Companion.CARD_AID_API,
    private val networkRepository: NetworkRepository
) : ViewModel(), CardService {

    override fun findAllCards(): List<CardDto> {
        return listOf()
    }

    override fun findCardById(id: UUID): CardDto {
        return CardDto(UUID.randomUUID(), "",
            "", "",
            CargoMainEventCategory.INFRASTRUCTURE,
            CargoMainEventCategory.SubEventCategory.PASSENGER_FALL,
            "", Instant.now())
    }
}