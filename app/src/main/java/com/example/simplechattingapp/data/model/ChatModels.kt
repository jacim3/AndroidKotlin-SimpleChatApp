package com.example.simplechattingapp.data.model

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties


@IgnoreExtraProperties
data class ItemLobby(
    var owner: String? = "",
    var password: String? = "",
    var title: String? = "",
    var lastMessage: String=""
) {
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "owner" to owner,
            "password" to password,
            "title" to title,
            "lastMessage" to lastMessage,
        )
    }
}

@IgnoreExtraProperties
data class ItemMembers(
    var members: MutableMap<String, Boolean> = HashMap()
) {
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "members" to members,
        )
    }
}

@IgnoreExtraProperties
data class ItemMessages(
    var owner: String? = "",
    var messages: String? = "",
    var timestamp: Long
) {
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "name" to owner,
            "messages" to messages,
            "timestamp" to timestamp,
        )
    }
}
