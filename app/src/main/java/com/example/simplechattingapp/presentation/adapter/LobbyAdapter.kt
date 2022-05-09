package com.example.simplechattingapp.presentation.adapter

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.example.simplechattingapp.R
import com.example.simplechattingapp.data.model.LobbyMapper
import com.example.simplechattingapp.presentation.ChatActivity


// TODO 다른 사용자에 의하여, 데이터베이스 가 변경될 수 있으므로, 이러한 변경 정보를 감지하여 현재 사용자의
// TODO 리스트를 변경하는 로직 적용 필요 !!!

class LobbyAdapter(context: Context) :
    RecyclerView.Adapter<LobbyAdapter.LobbyViewHolder>() {

    private val lobbyData = emptyList<LobbyMapper>().toMutableList()
    private val lobbyDialog by lazy {
        AlertDialog.Builder(context).apply {
            this.setView(
                LayoutInflater.from(context).inflate(R.layout.view_dialog_lobby_password, null)
            )
        }.create()
    }

    class LobbyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image: AppCompatImageView = view.findViewById(R.id.imageViewRoomImage)
        val title: AppCompatTextView = view.findViewById(R.id.textViewRoomTitle)
        val lastMessage: AppCompatTextView = view.findViewById(R.id.textViewRoomLastMessage)
        var owner = ""
        var password = ""
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LobbyViewHolder {

        val context = parent.context
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        return LobbyViewHolder(inflater.inflate(R.layout.item_lobby_adapter_item, parent, false))
    }

    override fun onBindViewHolder(holder: LobbyViewHolder, position: Int) {
        val item = lobbyData[position]
        holder.title.text = item.title
        holder.lastMessage.text = item.lastMessage
        holder.owner = item.owner
        holder.password = item.password

        holder.itemView.setOnClickListener {

            // 방 비밀번호가 없거나 방장이 나일 경우 통과
            if (item.password == "") {
                it.context.startActivity(Intent(it.context, ChatActivity::class.java))
            }
            // 방 비밀번호가 설정되어 맞춰야 함.
            else {
                lobbyDialog
                    .apply {

                        this.show()
                        val userPassword =
                            this.findViewById<AppCompatEditText>(R.id.editTextConfirmPassword)
                        this.findViewById<AppCompatButton>(R.id.buttonPasswordOk)
                            .setOnClickListener {

                                if (item.password == userPassword.text.toString()) {
                                    userPassword.setText(0)
                                    context.startActivity(Intent(context, ChatActivity::class.java))
                                }
                            }
                        this.findViewById<AppCompatButton>(R.id.buttonPasswordCancel)
                            .setOnClickListener {
                                userPassword.setText(0)
                                this.dismiss()
                            }
                    }
            }
        }

        holder.itemView.setOnLongClickListener {
            false
        }
    }

    override fun getItemCount(): Int {
        return lobbyData.size
    }

    fun getData(data: List<LobbyMapper>) {
        lobbyData.addAll(data)
        notifyDataSetChanged()
    }
}