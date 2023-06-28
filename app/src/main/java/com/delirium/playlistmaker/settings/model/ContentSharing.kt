package com.delirium.playlistmaker.settings.model

import com.delirium.playlistmaker.sharing.model.EmailData

data class ContentSharing(
    val data: List<Pair<String, String>>? = listOf(),
    val address: String? = null,
    val emailData: EmailData? = null,
)
