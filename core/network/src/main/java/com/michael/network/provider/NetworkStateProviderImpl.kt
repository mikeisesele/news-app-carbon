package com.michael.network.provider

import android.content.Context
import android.net.ConnectivityManager
import javax.inject.Inject

class NetworkStateProviderImpl @Inject constructor(context: Context) : NetworkStateProvider {
    private val applicationContext: Context = context.applicationContext
    override val isConnected: Boolean
        get() {
            val cm =
                applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork = cm.activeNetworkInfo
            return activeNetwork != null &&
                    activeNetwork.isConnectedOrConnecting
        }
}
