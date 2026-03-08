package ru.solomka.fmh.app.core.service

import ru.solomka.fmh.app.core.dto.user.UserTestInfoDto
import java.util.UUID

interface UserService {

    suspend fun findUserTestsInformation(userId: UUID): List<UserTestInfoDto>?

    suspend fun save(test: UserTestInfoDto): UserTestInfoDto?
}