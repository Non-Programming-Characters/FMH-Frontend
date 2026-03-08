package ru.solomka.fmh.app.core.api

import ru.solomka.fmh.app.core.dto.card.CardDto
import retrofit2.Response
import retrofit2.http.GET

interface CardApi {
    @GET("/api/v1/cards")
    suspend fun getAllCards(): Response<List<CardDto>>
}