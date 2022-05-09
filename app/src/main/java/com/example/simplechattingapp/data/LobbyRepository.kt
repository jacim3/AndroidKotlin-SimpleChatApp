package com.example.simplechattingapp.data

import com.example.simplechattingapp.data.source.AuthDataSource
import com.example.simplechattingapp.data.source.LobbyDataSource
import com.google.android.gms.tasks.Task
import java.sql.Timestamp
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class LobbyRepository @Inject constructor(
    private val lobbyDataSource: LobbyDataSource,
    private val authDataSource: AuthDataSource
) {
    fun createRoom(data: Map<String, Any>): Task<Void>? {
        return getUserEmail()?.let {
            lobbyDataSource.createRoom(data, it)
        }
    }

    fun getUserEmail() = authDataSource.getUserEmail()


    fun getLobbyDataInit(readCount:Int) = lobbyDataSource.getLobbyDataInit(readCount)


    fun observeLatestData() = lobbyDataSource.observeLatestData()


    fun getLobbyDataNext(readCount: Int, timestamp: Long) = lobbyDataSource.getLobbyDataNext(readCount, timestamp)

}