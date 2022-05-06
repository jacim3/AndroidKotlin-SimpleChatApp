package com.example.simplechattingapp.data

import android.util.Log
import com.example.simplechattingapp.constants.Option
import com.example.simplechattingapp.data.model.ItemLobby
import com.example.simplechattingapp.data.source.AuthDataSource
import com.example.simplechattingapp.data.source.LobbyDataSource
import com.google.android.gms.tasks.Task
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers.io
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class LobbyRepository @Inject constructor(
    private val dataSource: LobbyDataSource,
    private val authDataSource: AuthDataSource
) {

    fun createRoom(data:Map<String, Any>): Task<Void> {
        return dataSource.getDatabases().updateChildren(
            hashMapOf<String, Any>(
                "${Option.DB_PATH_CATEGORY_CHAT}/${Option.DB_PATH_LOBBY}/${createRoomId()}" to ItemLobby(
                    authDataSource.getUserEmail(),
                    data["password"] as String?,
                    data["title"] as String?,
                    "",
                    data["timestamp"] as Long
                ).toMap()
            )
        )
    }

    fun readRoom(path:String) {

        dataSource.observable
            .observeOn(io()).subscribeOn(AndroidSchedulers.mainThread())

        dataSource.getDatabases().child(Option.DB_PATH_CATEGORY_CHAT).get().addOnSuccessListener {
            Log.e("gettttt", "${it.value}")
        }.addOnFailureListener {
            Log.e("gettttt", "$it")
        }
    }

    private fun createRoomId() = System.currentTimeMillis().hashCode()
}