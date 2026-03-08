package ru.solomka.fmh.app.core.dto

import java.util.Date
import java.util.UUID

interface Entity {

    fun getId(): UUID

    fun getCreatedAt(): Date
}