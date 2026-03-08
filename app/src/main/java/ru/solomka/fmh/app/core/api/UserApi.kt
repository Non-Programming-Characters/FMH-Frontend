package ru.solomka.fmh.app.core.api

import ru.solomka.fmh.app.core.dto.user.UserTestInfoDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import java.util.UUID

interface UserApi : Api {
    @GET("/api/v1/users/tests")
    suspend fun getTestsInformation(@Path("userId") userId: UUID): Response<List<UserTestInfoDto>>

    @POST("/api/v1/users/tests")
    suspend fun uploadTestInformation(@Body userTestInfo: UserTestInfoDto): Response<UserTestInfoDto>
}
