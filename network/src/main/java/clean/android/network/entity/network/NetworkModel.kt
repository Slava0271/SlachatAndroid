package clean.android.network.entity.network

import clean.android.network.entity.BaseEntity

data class NetworkModel(
    val networkID: Int,
    val networkState: NetworkState,
    val networkType: NetworkType
) : BaseEntity() {
    fun isConnected(): Boolean = networkState == NetworkState.CONNECTED
}