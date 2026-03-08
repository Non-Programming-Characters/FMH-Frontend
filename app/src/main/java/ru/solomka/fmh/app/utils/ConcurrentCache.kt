package ru.solomka.fmh.app.utils

import ru.solomka.fmh.app.core.dto.Entity
import java.util.UUID
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap

abstract class ConcurrentCache<T : Entity> : Cache<UUID, T> {
    val concurrentCache: ConcurrentMap<UUID, T> = ConcurrentHashMap()

    override fun put(value: T): T = concurrentCache.put(value.getId(), value)!!

    override fun putAll(value: Array<T>): Boolean {
        value.forEach {
            concurrentCache[it.getId()] = it
        }
        return true
    }

    override fun removeByKey(key: UUID): T = concurrentCache.remove(key)!!

    override fun removeByKeyAndObject(value: T): Boolean = concurrentCache.remove(value.getId(), value)

    override fun getAll(): List<T> = concurrentCache.values.toList()
}