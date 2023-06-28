package com.delirium.playlistmaker.search.data.repository

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.delirium.playlistmaker.search.domain.repository.NetworkClient
import com.delirium.playlistmaker.search.data.models.Response
import com.delirium.playlistmaker.search.data.network.ITunesServiceApi
import com.delirium.playlistmaker.search.domain.model.SongsSearchRequest

class RetrofitClientImpl(
    private val ITunesService: ITunesServiceApi,
    private val context: Context,
): NetworkClient {
    override fun getSongs(dto: Any): Response {
        if (!isConnected()) {
            return Response().apply { resultCode = -1 }
        }
        if (dto !is SongsSearchRequest) {
            return Response().apply { resultCode = 400 }
        }

        val response = ITunesService.getSongs(dto.expression).execute()
        val body = response.body()

        return if (body != null) {
            body.apply { resultCode = response.code() }
        } else {
            Response().apply { resultCode = response.code() }
        }
    }

    private fun isConnected(): Boolean {
        val connectivityManager = context.getSystemService(
            Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> return true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> return true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> return true
            }
        }
        return false
    }
}