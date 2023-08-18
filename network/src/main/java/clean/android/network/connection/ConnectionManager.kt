package clean.android.network.connection

import clean.android.network.entity.network.NetworkModel
import kotlinx.coroutines.flow.StateFlow

interface ConnectionManager {
    val network: StateFlow<NetworkModel?>

    fun registerNetworkCallback()

    fun unregisterNetworkCallback()

    fun getCurrentNetwork(): NetworkModel?

    fun isConnected(): Boolean
}