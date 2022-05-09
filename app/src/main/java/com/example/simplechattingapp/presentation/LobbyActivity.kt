package com.example.simplechattingapp.presentation

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.simplechattingapp.R
import com.example.simplechattingapp.data.model.LobbyMapper
import com.example.simplechattingapp.databinding.ActivityLobbyBinding
import com.example.simplechattingapp.presentation.adapter.LobbyAdapter
import com.example.simplechattingapp.presentation.viewmodel.LobbyViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LobbyActivity : AppCompatActivity(), LobbyAdapter.OnRoomClickListener {

    private val lobbyViewModel: LobbyViewModel by viewModels()
    private lateinit var binding: ActivityLobbyBinding
    private lateinit var lobbyAdapter: LobbyAdapter
    private var dragFlag = true
    private var loadingIndicator: AlertDialog? = null



    @SuppressLint("InflateParams")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_lobby)
        binding.lifecycleOwner = this
        binding.lobbyViewModel = lobbyViewModel
        binding.buttonCreateRoom.setOnClickListener { createLobbyDialog() }

        lobbyViewModel.apply {
            this.getLobbyDataInit()
            this.attachLatestObserver()
        }
        initLobbyRecyclerView()
        setLoadingIndicator()
        lobbyViewModel.firebaseCursor.observe(this) {
            Log.e("minCursor", it.minCursor.toString())
            Log.e("maxCursor", it.maxCursor.toString())
        }

        lobbyViewModel.receivedLobby.observe(this) {
            lobbyViewModel.totalLobby.addAll(it).run {
                lobbyAdapter.getData(it)
            }
        }
    }

    private fun initLobbyRecyclerView() {

        binding.recyclerViewRooms.apply {
            lobbyViewModel.getUserEmail()?.let {
                lobbyAdapter = LobbyAdapter(this@LobbyActivity, it, this@LobbyActivity)
            }
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = lobbyAdapter
        }

        binding.recyclerViewRooms.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(v: RecyclerView, newState: Int) {
                super.onScrollStateChanged(v, newState)

                if (!v.canScrollVertically(RecyclerView.SCROLL_INDICATOR_BOTTOM) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    lobbyViewModel.apply {
                        this.firebaseCursor.value?.minCursor?.let {
                            this.getLobbyDataNext(it)
                        }
                    }
                }
            }
        })
    }

    private fun setLoadingIndicator() {
        lobbyViewModel.loadingIndicator.observe(this) {
            when (it.status) {
                "show" -> {
                    loadingIndicator = AlertDialog.Builder(this).apply {
                        setTitle(it.text)
                    }.create().apply {
                        this.show()
                    }
                }
                "dismiss" -> {
                    loadingIndicator?.dismiss()
                }
            }
        }
    }

    private fun createLobbyDialog() {
        val dialog = AlertDialog.Builder(this).apply {
            setTitle("방 생성")
            setView(layoutInflater.inflate(R.layout.view_dialog_lobby, null))
        }.create()

        dialog.show()
        dialog.findViewById<AppCompatButton>(R.id.buttonCreateRoom).setOnClickListener {
            val title =
                dialog.findViewById<AppCompatEditText>(R.id.editTextRoomTitle).text.toString()
            val password =
                dialog.findViewById<AppCompatEditText>(R.id.editTextRoomPassword).text.toString()
            if (title.isNotEmpty()) {
                lobbyViewModel.createRoom(title, password)
            }
            Toast.makeText(applicationContext, "방 생성에 성공하였습니다!", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }
        dialog.findViewById<AppCompatButton>(R.id.buttonCancelCreateRoom).setOnClickListener {
            dialog.dismiss()
        }
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater = menuInflater.inflate(R.menu.lobby_context_menu, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return super.onContextItemSelected(item)

    }

    // LobbyAdapter 로 부터 수행된 이벤트 핸들링 by implement interface
    override fun onItemClick(item: LobbyMapper) {
        Intent(this, ChatActivity::class.java).apply {
            putExtra("item", item)
            startActivity(this)
        }
    }

    // LobbyAdapter 로 부터 수행된 이벤트 핸들링 with contextMenu
    override fun onItemLongClick(item: LobbyMapper) {

    }
}