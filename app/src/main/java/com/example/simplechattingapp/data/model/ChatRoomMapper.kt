package com.example.simplechattingapp.data.model

import java.sql.Timestamp

data class ChatRoomMapper(
    val id:Int,
    val name:String,
    val password:String,
    val createAt:Timestamp
)
