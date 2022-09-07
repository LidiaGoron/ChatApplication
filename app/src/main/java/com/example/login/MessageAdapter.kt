package com.example.login

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.connection.ConnectionContext
import com.google.firebase.ktx.Firebase
import org.w3c.dom.Text

class MessageAdapter(val context: Context, val messageList: ArrayList<Message>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val ITEM_RECEIVE=1
    val ITEM_SEND=2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if(viewType==2)
        {
            //inflate the sentMessage
            val view:View= LayoutInflater.from(context).inflate(R.layout.sent,parent,false)
            return sentViewHolder(view)

        }
        else
        {
            //inflate the receiveMessage
            val view:View= LayoutInflater.from(context).inflate(R.layout.receive,parent,false)
            return receiveViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentMessage=messageList[position]

        if(holder.javaClass==sentViewHolder::class.java)
        {
            //Avem sentViewHolder
            val viewHolder = holder as sentViewHolder
            holder.sentMessage.text=currentMessage.message // Setam mesajul sent la mesajul curent daca se acceseaza sentHolder
        }else
        {
            //Avem receiveViewHolder
            val viewHolder = holder as receiveViewHolder
            holder.receiveMessage.text=currentMessage.message // Setam mesajul receive la mesajul curent daca se acceseaza receiveHolder
        }

    }

    override fun getItemCount(): Int {
        return messageList.size

    }

    override fun getItemViewType(position: Int): Int {
        val currentMessage=messageList[position]
        if(FirebaseAuth.getInstance().currentUser?.uid.equals(currentMessage.senderId)) // user id= sender id
        {
            return ITEM_SEND
        }
        else {
            return ITEM_RECEIVE
        }
    }


    class sentViewHolder(itemView: View):RecyclerView.ViewHolder(itemView)

    {
        val sentMessage=itemView.findViewById<TextView>(R.id.txt_message_sent)

    }
    class receiveViewHolder(itemView:View):RecyclerView.ViewHolder(itemView)
    {
        val receiveMessage=itemView.findViewById<TextView>(R.id.txt_message_receive)

    }
}