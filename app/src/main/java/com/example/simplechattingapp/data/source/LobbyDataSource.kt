package com.example.simplechattingapp.data.source

import android.util.Log
import com.example.simplechattingapp.constants.Option
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LobbyDataSource @Inject constructor(
    private val database: DatabaseReference
) {
    private val subject = PublishSubject.create<DataSnapshot>()
    val observable: Flowable<DataSnapshot> = subject.toFlowable(BackpressureStrategy.MISSING)
        .doOnSubscribe {
            database.child(Option.DB_PATH_CATEGORY_CHAT).get().addOnCompleteListener { task->
                when(task.isSuccessful) {
                    true -> task.result?.let { result->
                        Log.e("asdfasdfasdf", result.toString())
                    }
                }
            }
        }.doOnCancel {

        }
    fun getDatabases() = database
}