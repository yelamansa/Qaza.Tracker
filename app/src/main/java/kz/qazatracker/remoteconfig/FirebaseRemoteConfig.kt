package kz.qazatracker.remoteconfig

import android.app.Activity
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import kz.qazatracker.R
import java.util.concurrent.TimeUnit

const val CONTACT_LINK_REMOTE_CONFIG = "contact_link"
const val SOCIAL_NETWORK_REMOTE_CONFIG = "social_network"

class FirebaseRemoteConfig : RemoteConfig {

    private val remoteConfig: FirebaseRemoteConfig = Firebase.remoteConfig

    init {
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = TimeUnit.MINUTES.toSeconds(30)
        }
        remoteConfig.setConfigSettingsAsync(configSettings)
        remoteConfig.setDefaultsAsync(R.xml.remote_config_defaults)
    }

    override fun fetchAndActivate(activity: Activity) {
        remoteConfig.fetchAndActivate()
    }

    override fun getString(key: String): String = remoteConfig.getString(key)

    override fun getBoolean(key: String): Boolean = remoteConfig.getBoolean(key)
}