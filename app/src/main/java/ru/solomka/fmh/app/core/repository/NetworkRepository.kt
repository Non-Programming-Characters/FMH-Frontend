package ru.solomka.fmh.app.core.repository

import android.Manifest
import androidx.annotation.RequiresPermission

interface NetworkRepository {
    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    fun hasInternetConnection(): Boolean
}