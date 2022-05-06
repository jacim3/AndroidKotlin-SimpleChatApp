package com.example.simplechattingapp.presentation

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.databinding.DataBindingUtil
import com.example.simplechattingapp.R
import com.example.simplechattingapp.databinding.ActivityLobbyBinding
import com.example.simplechattingapp.presentation.viewmodel.LobbyViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LobbyActivity : AppCompatActivity() {

    private val lobbyViewModel: LobbyViewModel by viewModels()
    private lateinit var binding: ActivityLobbyBinding

    @SuppressLint("InflateParams")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_lobby)
        binding.lifecycleOwner = this
        binding.lobbyViewModel = lobbyViewModel

        binding.buttonCreateRoom.setOnClickListener {
            val dialog = AlertDialog.Builder(this).apply {
                setTitle("방 생성")
                setView(layoutInflater.inflate(R.layout.view_dialog_lobby, null))
            }.create()

            dialog.show()
            dialog.findViewById<AppCompatButton>(R.id.buttonCreateRoom).setOnClickListener {
                val title = dialog.findViewById<AppCompatEditText>(R.id.editTextRoomTitle).text.toString()
                val password = dialog.findViewById<AppCompatEditText>(R.id.editTextRoomPassword).text.toString()
                if (title.isNotEmpty() && password.isNotEmpty()) {
                    lobbyViewModel.createRoom(title, password)
                }
                Toast.makeText(applicationContext, "방 생성에 성공하였습니다!", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }
            dialog.findViewById<AppCompatButton>(R.id.buttonCancelCreateRoom).setOnClickListener {
                dialog.dismiss()
            }
        }
    }
}