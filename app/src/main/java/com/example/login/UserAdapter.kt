package com.example.login

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth

class UserAdapter(val context:Context, val userList:ArrayList<User>): RecyclerView.Adapter<UserAdapter.UserViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
            val view:View=LayoutInflater.from(context).inflate(R.layout.user_layout,parent,false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val currentUser=userList[position]
        // We need holder to inflate the views
        holder.textName.text=currentUser.name   // Asa vom arata numele user ului curent

        //Cand dam click pe un user vrem sa se deschida chat activity

        holder.itemView.setOnClickListener()
        {
            val intent= Intent(context,ChatActivity::class.java)
            // Vrem sa ne apara in bara numele user ului cu care vorbim
            intent.putExtra("Name",currentUser.name)
            intent.putExtra("uid",currentUser.uid)
            context.startActivity(intent)
        }


    }

    override fun getItemCount(): Int {
        return userList.size
    }
    class UserViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        //In aceasta clasa trebuie sa initializam toate view-urile din layout care ne intereseaza
        //in cazul acesta este doar TextView de la user_layout
    val textName=itemView.findViewById<TextView>(R.id.txt_name)
    }
}