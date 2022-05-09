package com.example.simplechattingapp.presentation.viewmodel

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.example.simplechattingapp.constants.PAGE
import com.example.simplechattingapp.data.LobbyRepository
import com.example.simplechattingapp.data.model.FirebaseCursor
import com.example.simplechattingapp.data.model.LoadingIndicator
import com.example.simplechattingapp.data.model.LobbyMapper
import com.google.firebase.database.*
import dagger.hilt.android.lifecycle.HiltViewModel
import java.sql.Timestamp
import java.util.*
import javax.inject.Inject

@HiltViewModel
class LobbyViewModel @Inject constructor(
    application: Application,
    private val repository: LobbyRepository
) : AndroidViewModel(application) {

    // TODO 사용자가 참조하는 범위를 기록.
    // TODO 가장 최신 데이터를 항상 참조.


    val receivedLobby = MutableLiveData<List<LobbyMapper>>()
    val totalLobby = emptyList<LobbyMapper>().toMutableList()
    val loadingIndicator = MutableLiveData<LoadingIndicator>().apply {
        this.postValue(LoadingIndicator("show", "채팅 초기화"))
    }

    private val minCursor = MutableLiveData<Long>().apply { this.postValue(0L) }
    private val maxCursor = MutableLiveData<Long>().apply { this.postValue(0L) }

    val firebaseCursor = MediatorLiveData<FirebaseCursor>().apply {
        this.addSource(minCursor) {
            this.value = combineLatestData(minCursor, maxCursor)
        }
        this.addSource(maxCursor) {
            this.value = combineLatestData(minCursor, maxCursor)
        }
    }

    fun createRoom(title: String, password: String) {
        createTimestamp().apply {
            repository.createRoom(
                mapOf(
                    Pair("password", password),
                    Pair("title", title),
                    Pair("timestamp", this)
                )
            )?.let {
                it.addOnCompleteListener {
                    makeToast("방 생성 성공!!!")
                }
            }
        }
    }

    // 최초 초기화에 필요한 데이터 받아옴.
    fun getLobbyDataInit() {
        repository.getLobbyDataInit(PAGE.READ_COUNT_INIT)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    snapshotToMap(snapshot)
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
    }

    // 이후 데이터 받아옴.
    fun getLobbyDataNext(afterTo: Long) {
        repository.getLobbyDataNext(PAGE.READ_COUNT_NEXT, afterTo)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    Log.e("receiveSnapshot", snapshot.toString())
                    Log.e("receiveSnapshot", snapshot.value.toString())
                    snapshotToMap(snapshot)

                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
    }

    // db에 최신 데이터가 들어갈 때마다 해당 데이터 의 key(날찌:타임스탬프(Long))를 감지하여 알릴 수 있도록
    fun attachLatestObserver() {
        repository.observeLatestData().addChildEventListener(object :
            ChildEventListener {

            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                Log.e("latestObserver", snapshot.key.toString())
                maxCursor.postValue(snapshot.key?.toLong())
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}

            override fun onChildRemoved(snapshot: DataSnapshot) {}

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}

            override fun onCancelled(error: DatabaseError) {}
        })
    }

    // 서버에 데이터 업로드를 위한 타임스탬프 생성
    private fun createTimestamp() = Timestamp(Calendar.getInstance().timeInMillis).time

    private fun makeToast(text: String) {
        Toast.makeText(getApplication(), text, Toast.LENGTH_SHORT).show()
    }

    private fun snapshotToMap(snapshot: DataSnapshot) {
        snapshot.value?.let {
            mapToSortedMap(it).apply {
                receivedLobby.postValue(this)
                minCursor.postValue(this[this.size - 1].timestamp)
            }
        }
    }

    // realtimeDatabase 에서 받아온 데이터에 대한 snapshot 가 정렬되지 않아, treemap 형태로 정렬 및 리턴
    private fun mapToSortedMap(value: Any): List<LobbyMapper> {
        return sortedMapToLobbyMapper(TreeMap<String, Any> { s1, s2 -> (s2.toLong() - s1.toLong()).toInt() }.apply {
            this.putAll(value as Map<String, Any>)
        })
    }

    // 받은 Map 형태의 데이터를 Object 로 변환
    private fun sortedMapToLobbyMapper(map: TreeMap<String, Any>): List<LobbyMapper> {
        return mutableListOf<LobbyMapper>().apply {
            map.forEach {

                (it.value as Map<String, Any>).also { item ->
                    this.add(
                        LobbyMapper(
                            title = item["title"].toString(),
                            lastMessage = item["lastMessage"].toString(),
                            password = item["password"].toString(),
                            timestamp = it.key.toLong(),
                            owner = item["owner"].toString()
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