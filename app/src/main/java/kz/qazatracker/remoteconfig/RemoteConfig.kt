package kz.qazatracker.remoteconfig

import android.app.Activity

interface RemoteConfig {

    fun fetchAndActivate(activity: Activity)

    fun getString(key: String): String

    fun getBoolean(key: String): Boolean
}