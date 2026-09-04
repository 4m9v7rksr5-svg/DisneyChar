package com.edcode.disneychar

import android.content.Context
import android.net.NetworkCapabilities

class ConnectivityManager(private val context: Context) {
    fun isOnline(): Boolean {
        val connManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as android.net.ConnectivityManager
        val capabilities = connManager.getNetworkCapabilities(connManager.activeNetwork)
        return capabilities != null && (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                || capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
        )
    }
}