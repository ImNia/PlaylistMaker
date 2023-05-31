package com.delirium.playlistmaker.data.repository

import android.content.Context
import android.util.Log
import com.delirium.playlistmaker.R
import com.delirium.playlistmaker.domain.models.AdapterModel
import com.delirium.playlistmaker.domain.models.DataITunes
import com.delirium.playlistmaker.domain.models.ErrorItem
import com.delirium.playlistmaker.domain.models.NotFoundItem
import com.delirium.playlistmaker.domain.repository.RetrofitCallback
import com.delirium.playlistmaker.domain.repository.RetrofitRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RetrofitRepositoryImpl(
    private val context: Context,
    private val callback: RetrofitCallback
): RetrofitRepository {
    override fun getSongs(nameSong: String): MutableList<AdapterModel> {
        val request = ITunesSetting.itunesInstant
        val receiveData = mutableListOf<AdapterModel>()

        request.getSongs(nameSong).enqueue(object : Callback<DataITunes> {
            override fun onFailure(call: Call<DataITunes>, t: Throwable) {
                t.printStackTrace()
                receiveData.add(
                    ErrorItem(
                        text = context.getString(R.string.not_connect_item_text),
                        textSub = context.getString(R.string.not_connect_item_text_sub)
                    )
                )
                callback.error(receiveData)
            }

            override fun onResponse(
                call: Call<DataITunes>,
                response: Response<DataITunes>
            ) {
                if (response.isSuccessful) {
                    val rawData = response.body()
                    rawData?.let {
                        if (it.resultCount == 0) {
                            receiveData.add(NotFoundItem(textProblem = context.getString(R.string.not_found)))
                        } else {
                            for (item in it.results) {
                                receiveData.add(item)
                            }
                        }
                    }
                    callback.successful(receiveData)
                } else {
                    Log.i("RETROFIT_ERROR", "${response.errorBody()?.string()}")
                    receiveData.add(
                        ErrorItem(
                            text = context.getString(R.string.not_connect_item_text),
                            textSub = context.getString(R.string.not_connect_item_text_sub)
                        )
                    )
                    callback.error(receiveData)
                }
            }
        })
        return receiveData
    }
}