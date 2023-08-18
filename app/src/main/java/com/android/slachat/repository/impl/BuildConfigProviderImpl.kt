package com.android.slachat.repository.impl

import clean.android.network.configprovider.BuildConfigProvider

class BuildConfigProviderImpl : BuildConfigProvider {
    override fun getBaseUrl() = "http://192.168.0.102:8080"
}