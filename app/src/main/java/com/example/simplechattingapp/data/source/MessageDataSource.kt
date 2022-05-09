package com.example.simplechattingapp.data.source

import com.example.simplechattingapp.constants.DB
import com.example.simplechattingapp.data.model.ItemMessages
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DatabaseReference
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MessageDataSource @Inject constructor(
    private val database: DatabaseReference
) {

    private val reference = database.child(DB.PATH_CHAT).child(DB.PATH_CHAT_MESSAGES)
    private val messagePath = "${DB.PATH_CHAT}/${DB.PATH_CHAT_MESSAGES}"

    fun createMessage(timestamp: Long, data: Map<String, Any>): Task<Void> {
        return database.updateChildren(
            hashMapOf<String, Any>(
                "${messagePath}/${timestamp}" to ItemMessages(
                    owner = data["email"] as String,
                    messages = data["message"] as String,
                    timestamp = data["timestamp"] as Long
                )
            )
        )
    }

    fun observeLatestData() = reference.orderByKey().limitToLast(1)

    fun readMessageInit(readCount : Int)  = reference.orderByKey().limitToLast(readCount)

    fun readMessageNext(readCount: Int, timestamp: Long) = reference.orderByKey().endBefore(timestamp.toString()).limitToLast(readCount)

    fun updateMessage(timestampRoom:Long, timestampMessage:Long){

    }

    fun deleteMessage() {

    }
}