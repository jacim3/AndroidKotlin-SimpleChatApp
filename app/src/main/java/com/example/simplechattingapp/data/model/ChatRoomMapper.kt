package com.example.simplechattingapp.data.model

import java.io.Serializable
import java.sql.Timestamp

/*
    서버에서 가져온 데이터를 비즈니스 로직에 맞춰 수정하기 위한 DAO 모델
*/

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


