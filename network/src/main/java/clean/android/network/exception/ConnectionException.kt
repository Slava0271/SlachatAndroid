package clean.android.network.exception

class ConnectionException : Exception() {
    override val message = this.localizedMessage ?: "No internet connection"
}