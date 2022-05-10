package com.example.simplechattingapp.presentation.viewmodel

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.simplechattingapp.data.MessageRepository
import com.example.simplechattingapp.data.model.FirebaseCursor
import com.example.simplechattingapp.data.model.ItemMessages
import com.example.simplechattingapp.data.model.LobbyMapper
import com.example.simplechattingapp.data.model.MessageMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import java.sql.Timestamp
import java.util.*
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val messageRepository: MessageRepository
) : ViewModel() {


    private val minCursor = MutableLiveData<Long>().apply { this.postValue(0L) }
    private val maxCursor = MutableLiveData<Long>().apply { this.postValue(0L) }

    val messageCursor = MediatorLiveData<FirebaseCursor>().apply {
        this.addSource(minCursor) {
            this.value = combineLatestData(minCursor, maxCursor)
        }
        this.addSource(maxCursor) {
            this.value = combineLatestData(minCursor, maxCursor)
        }
    }

    fun createMessage() {

    }

    fun readMessageInit() {

    }

    fun readMessageNext() {

    }


    fun attachLatestObserver(){

    }

    private fun createTimestamp() = Timestamp(Calendar.getInstance().timeInMillis).time

    // nullCheck 가 완료된 snapshot.value(Map<String,Any) 를 TreeMap 을 통하여 키 값을 기준으로 정렬하여
    // 아래 메서드에 리턴.
    private fun mapToSortedMap(value: Any): MutableList<ItemMessages> {
        return sortedMapToMessageMapper(TreeMap<String, Any> { s1, s2 -> (s2.toLong() - s1.toLong()).toInt() }.apply {
            this.putAll(value as Map<String, Any>)
        })
    }

    //  mapToSortedMap() 에서 리턴받은 TreeMap 을 통하여 Object 배열 생성.
    private fun sortedMapToMessageMapper(map: TreeMap<String, Any>): MutableList<ItemMessages> {
        return mutableListOf<ItemMessages>().apply {
            map.forEach {

                (it.value as Map<String, Any>).also { item ->
                    this.add(
                        ItemMessages(
                            owner = item["owner"].toString(),
                            message = item["message"].toString(),
                            timestamp = it.key.toLong()
                        )
                    )
                }
            }
        }
    }


    // 여러개의 LiveData 바인딩 관련
    private fun combineLatestData(
        min: MutableLiveData<Long>,
        max: MutableLiveData<Long>
    ): FirebaseCursor {

        if (min.value != null && max.value != null) {
            return FirebaseCursor(min.value!!, max.value!!)
        }
        return FirebaseCursor(0L, 0L)
    }
}