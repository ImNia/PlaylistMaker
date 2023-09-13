package com.delirium.playlistmaker.sharing.model

data class ContentSharing(
    val data: List<Pair<String, String>>? = listOf(),
    val address: String? = null,
    val emailData: EmailData? = null,
)
