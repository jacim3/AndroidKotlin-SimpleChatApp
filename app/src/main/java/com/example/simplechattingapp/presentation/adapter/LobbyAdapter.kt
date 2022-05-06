package com.example.simplechattingapp.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.simplechattingapp.R

class LobbyAdapter(private val code:Int) : RecyclerView.Adapter<LobbyAdapter.LobbyViewHolder>() {

    class LobbyViewHolder(view: View) : RecyclerView.ViewHolder(view){

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LobbyViewHolder {

        val context = parent.context
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        return LobbyViewHolder(inflater.inflate(R.layout.item_lobby_adapter_item, parent, false))
    }

    override fun onBindViewHolder(holder: LobbyViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }
}