package com.example.simplechattingapp.data.model

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties
import java.sql.Timestamp


@IgnoreExtraProperties
data class ItemLobby(
    var owner: String? = "",
    var password: String? = "",
    var title: String? = "",
    var lastMessage: String? = "",
    var timestamp: Long = 0
) {
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "owner" to owner,
            "password" to password,
            "title" to title,
            "lastMessage" to lastMessage,
            "timestamp" to timestamp
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
    var name: String? = "",
    var messages: String? = "",
    var timestamp: Long
) {
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "name" to name,
            "messages" to messages,
            "timestamp" to timestamp,
        )
    }
}
