package com.example.simplechattingapp.data.model

import java.sql.Timestamp

data class LobbyMapper(
    val title:String,
    val lastMessage:String,
    val password:String,
    val timestamp:Long,
    val owner:String
)
