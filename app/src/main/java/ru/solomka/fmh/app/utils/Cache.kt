package ru.solomka.fmh.app.utils

import ru.solomka.fmh.app.core.dto.Entity

interface Cache<K, out V : Entity> {

    fun put(value: @UnsafeVariance V) : V

    fun putAll(value: Array<@UnsafeVariance V>): Boolean

    fun removeByKey(key: K) : V

    fun removeByKeyAndObject(value: @UnsafeVariance V) : Boolean

    fun getAll() : List<V>
}