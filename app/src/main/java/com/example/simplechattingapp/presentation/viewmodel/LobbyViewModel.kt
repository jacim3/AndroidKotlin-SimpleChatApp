package com.example.simplechattingapp.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.simplechattingapp.constants.Option
import com.example.simplechattingapp.data.LobbyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import java.sql.Timestamp
import java.util.*
import javax.inject.Inject

@HiltViewModel
class LobbyViewModel @Inject constructor(
    private val repository: LobbyRepository
) : ViewModel() {

    val roomList = MutableLiveData<Any>()

    fun createRoom(title: String, password: String) {
        repository.createRoom(
            mapOf(
                Pair("password", password),
                Pair("title", title),
                Pair("timestamp", Timestamp(Calendar.getInstance().timeInMillis).time)
            )
        )

        readRoom()
    }

    private fun readRoom(){
        repository.readRoom(Option.DB_PATH_LOBBY)
    }
}