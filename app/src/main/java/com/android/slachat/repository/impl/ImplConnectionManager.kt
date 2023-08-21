package com.android.slachat.repository.impl

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import clean.android.network.connection.ConnectionManager
import clean.android.network.entity.network.NetworkModel
import clean.android.network.entity.network.NetworkState
import clean.android.network.entity.network.NetworkType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ImplConnectionManager(context: Context) : ConnectivityManager.NetworkCallback(),
    ConnectionManager {

    private var isConnected = false
    private var currentNetwork: NetworkModel? = null

    private val networkRequest: NetworkRequest by lazy { NetworkRequest.Builder().build() }
    private val connectivityManager by lazy { context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager }

    private val _network: MutableStateFlow<NetworkModel?> = MutableStateFlow(null)
    override val network: StateFlow<NetworkModel?> = _network

    override fun isConnected(): Boolean = isConnected

    init {
        isConnected = hasInternet()
        registerNetworkCallback()
    }

    override fun onAvailable(network: Network) {
        super.onAvailable(network)
        isConnected = true
        currentNetwork = NetworkModel(
            networkID = network.hashCode(),
            networkState = NetworkState.CONNECTED,
            networkType = NetworkType.UNRECOGNIZED
        )
    }

    override fun onLost(network: Network) {
        super.onLost(network)
        isConnected = false
        currentNetwork?.let {
            if (network.hashCode() == it.networkID) {
                onNetwork(it.copy(networkState = NetworkState.LOST))
            }
        }
    }

    override fun onCapabilitiesChanged(network: Network, networkCapabilities: NetworkCapabilities) {
        onNetwork(currentNetwork?.copy(networkType = getNetworkType(networkCapabilities)))
    }

    override fun registerNetworkCallback() {
        connectivityManager.registerNetworkCallback(networkRequest, this)
    }

    override fun unregisterNetworkCallback() {
        connectivityManager.unregisterNetworkCallback(this)
    }

    override fun getCurrentNetwork(): NetworkModel? {
        return currentNetwork
    }

    private fun onNetwork(network: NetworkModel?) {
        _network.value = network
        currentNetwork = network
    }

    private fun getNetworkType(networkCapabilities: NetworkCapabilities) = when {
        networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> NetworkType.WIFI
        networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> NetworkType.ETHERNET
        networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> NetworkType.CELLULAR
        else -> NetworkType.UNRECOGNIZED
    }

    private fun hasInternet(): Boolean {
        connectivityManager.activeNetwork?.let {
            val capabilities = connectivityManager.getNetworkCapabilities(it)
            val isValidated =
                capabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
            return isValidated == true
        }
        return false
    }
}