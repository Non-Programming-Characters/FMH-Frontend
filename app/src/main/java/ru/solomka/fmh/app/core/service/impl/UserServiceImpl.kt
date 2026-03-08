package ru.solomka.fmh.app.core.service.impl

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ru.solomka.fmh.app.MainActivity
import ru.solomka.fmh.app.core.api.UserApi
import ru.solomka.fmh.app.core.dto.user.UserTestInfoDto
import ru.solomka.fmh.app.core.repository.NetworkRepository
import ru.solomka.fmh.app.core.service.UserService
import kotlinx.coroutines.async
import java.util.UUID

class UserServiceImpl(
    private val api: UserApi = MainActivity.Companion.USER_AID_API,
    private val networkRepository: NetworkRepository
) : ViewModel(), UserService {

    private val _error = MutableLiveData<String>()

    override suspend fun findUserTestsInformation(userId: UUID): List<UserTestInfoDto>? {
        return viewModelScope.async {
            try {
                if (networkRepository.hasInternetConnection()) {
                    val response = api.getTestsInformation(userId)
                    if (response.isSuccessful) {
                        response.body() ?: listOf()
                    } else {
                        _error.value = "Ошибка сервера: ${response.code()}"
                        null
                    }
                } else {
                    _error.value = "Нет подключения к интернету"
                    null
                }
            } catch (e: Exception) {
                _error.value = "Ошибка: ${e.message}"
                null
            }
        }.await()
    }

    override suspend fun save(test: UserTestInfoDto): UserTestInfoDto? {
        return viewModelScope.async {
            try {
                if (networkRepository.hasInternetConnection()) {
                    val response = api.uploadTestInformation(test)
                    if (response.isSuccessful) {
                        response.body()
                    } else {
                        _error.value = "Ошибка сервера: ${response.code()}"
                        null
                    }
                } else {
                    _error.value = "Нет подключения к интернету"
                    null
                }
            } catch (e: Exception) {
                _error.value = "Ошибка: ${e.message}"
                null
            }
        }.await()
    }
}