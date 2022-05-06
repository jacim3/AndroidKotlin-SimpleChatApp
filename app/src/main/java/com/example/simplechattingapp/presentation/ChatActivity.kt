package com.example.simplechattingapp.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.example.simplechattingapp.R
import com.example.simplechattingapp.databinding.ActivityChatBinding
import com.example.simplechattingapp.presentation.viewmodel.ChatViewModel
import dagger.hilt.android.AndroidEntryPoint

// 명령어 -> ./gradlew signingReport
@AndroidEntryPoint
class ChatActivity : AppCompatActivity() {

    private var binding:ActivityChatBinding? = null
    private val viewModel: ChatViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =
            DataBindingUtil.setContentView(this, R.layout.activity_chat)
        binding!!.lifecycleOwner = this
        binding!!.chatViewModel = viewModel
    }
}