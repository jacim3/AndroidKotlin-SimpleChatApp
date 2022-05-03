package com.example.simplechattingapp.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.example.simplechattingapp.R

class ChatInnerAdapter(private val code:Int) : RecyclerView.Adapter<ChatInnerAdapter.InnerViewHolder>() {

    class InnerViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val textViewPostUser:AppCompatTextView = view.findViewById(R.id.textViewPostUser)
        val imageViewPostUser:AppCompatImageView = view.findViewById(R.id.imageViewPostUser)
        val postUserRecyclerView:RecyclerView = view.findViewById(R.id.recyclerViewPostUser)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InnerViewHolder {

        val context = parent.context
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        return if (code == 0) {
            InnerViewHolder(inflater.inflate(R.layout.item_chat_inner_adapter_image, parent, false))
        } else {
            InnerViewHolder(inflater.inflate(R.layout.item_chat_inner_adapter_text, parent, false))
        }
    }

    override fun onBindViewHolder(holder: InnerViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }
}