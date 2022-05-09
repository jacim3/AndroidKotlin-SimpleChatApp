package com.example.simplechattingapp.data.model

import java.io.Serializable
import java.sql.Timestamp

data class LobbyMapper(
    val title:String,
    val lastMessage:String,
    val password:String,
    val timestamp:Long,
    val owner:String
) : Serializable


data class MessageMapper(
    val text:String,
    val timestamp:Long,
    val owner:String
) : Serializable


