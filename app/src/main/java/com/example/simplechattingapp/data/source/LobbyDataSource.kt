package com.example.simplechattingapp.data.source

import android.util.Log
import com.example.simplechattingapp.constants.DB
import com.example.simplechattingapp.data.model.ItemLobby
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.subjects.PublishSubject
import java.sql.Timestamp
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LobbyDataSource @Inject constructor(
    private val database: DatabaseReference
) {
    private val subject = PublishSubject.create<DataSnapshot>()
    private val reference = database.child(DB.PATH_CHAT)
        .child(DB.PATH_CHAT_LOBBY)
    private val lobbyPath = "${DB.PATH_CHAT}/${DB.PATH_CHAT_LOBBY}"


/*    val observable: Flowable<DataSnapshot> = subject.toFlowable(BackpressureStrategy.MISSING)
        .doOnSubscribe {
            database.child(DB.PATH_CHAT).get().addOnCompleteListener { task ->
                when (task.isSuccessful) {
                    true -> task.result?.let { result ->
                        Log.e("asdfasdfasdf", result.toString())
                    }
                }
            }
        }.doOnCancel {

        }*/

    fun createRoom(data: Map<String, Any>, userEmail: String): Task<Void> {
        return database.updateChildren(
            hashMapOf<String, Any>(
                 "${lobbyPath}/${data["timestamp"]}" to ItemLobby(
                    owner = userEmail,
                    password = data["password"] as String?,
                    title = data["title"] as String?,
                    lastMessage = ""
                ).toMap()
            )
        )
    }


    fun observeLatestData() = reference.orderByKey().limitToLast(1)

    fun getLobbyDataInit(readCount:Int) = reference.orderByKey().limitToLast(readCount)

    fun getLobbyDataNext(readCount:Int, timestamp:Long) =
        reference.orderByKey().endBefore(timestamp.toString()).limitToLast(readCount)

    fun generateLobbyTitleHash(timestamp: Timestamp) = timestamp.hashCode()

/*    private fun dataExistCheckReactive(timestamp: Long): Single<Boolean> {
        return Single.create<Boolean> { task ->
            database.child(Option.DB_CATEGORY_CHAT).child(Option.DB_PATH_CHAT_LOBBY)
                .child(timestamp.toString()).get()
                .addOnCompleteListener {
                    Log.e("itComplete",it.isSuccessful.toString())
                }
                .addOnSuccessListener {
                    Log.e("itExists", it.exists().toString())
                    when (it.exists()) {
                        true -> task.onError(Exception())
                        false -> task.onSuccess(it.exists())
                    }
                }.addOnFailureListener {
                    Log.e("OnException", "onException")
                    task.onError(Exception())
                }
        }.observeOn(io()).subscribeOn(AndroidSchedulers.mainThread()).retry(5).onErrorReturn {
            true
        }
    }*/
}