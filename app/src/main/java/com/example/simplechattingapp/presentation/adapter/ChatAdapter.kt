package com.example.simplechattingapp.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.example.simplechattingapp.R

class ChatAdapter(private val code:Int) : RecyclerView.Adapter<ChatAdapter.ChatViewHolder>() {

    class ChatViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val textViewPostUser:AppCompatTextView = view.findViewById(R.id.textViewPostUser)
        val imageViewPostUser:AppCompatImageView = view.findViewById(R.id.imageViewPostUser)
        val postUserRecyclerView:RecyclerView = view.findViewById(R.id.recyclerViewPostUser)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {

        val context = parent.context
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        return if (code == 0) {
            ChatViewHolder(inflater.inflate(R.layout.item_chat_adapter_me, parent, false))
        } else {
            ChatViewHolder(inflater.inflate(R.layout.item_chat_adapter_other, parent, false))
        }
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }
}