package ru.solomka.fmh.app.utils.impl

import ru.solomka.fmh.app.core.dto.user.UserTestInfoDto
import ru.solomka.fmh.app.utils.ConcurrentCache

class UserTestConcurrentCache : ConcurrentCache<UserTestInfoDto>()