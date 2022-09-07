package com.example.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.ActionBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class ChatActivity : AppCompatActivity() {

    private lateinit var chatRecyclerView: RecyclerView
    private lateinit var messageBox: EditText
    private lateinit var buttonSend:ImageView
    private lateinit var messageAdapter: MessageAdapter
    private lateinit var messageList:ArrayList<Message>
    private lateinit var mDb:DatabaseReference //referinta la RealTime Data Base

    var senderRoom:String?=null
    var receiveRoom:String?=null




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        chatRecyclerView=findViewById(R.id.chatrecyclerView)
        messageBox=findViewById(R.id.messageBox)
        buttonSend=findViewById(R.id.buttonSend)
        messageList=ArrayList()
        messageAdapter=MessageAdapter(this,messageList)

        chatRecyclerView.layoutManager=LinearLayoutManager(this)
        chatRecyclerView.adapter=messageAdapter

        val name=intent.getStringExtra("Name") // Numele user ului pe care il accesez
        val receiverUid=intent.getStringExtra("uid") //Uid ul user ului pe care il accesez
        val senderUid=FirebaseAuth.getInstance().currentUser?.uid // Uid-ul pentru user ul ce se logeaza in aplicatie
        senderRoom= receiverUid + senderUid
        receiveRoom=senderUid+receiverUid
        mDb=FirebaseDatabase.getInstance().getReference()

        //Adaugam mesajele din DB in lista noastra

        mDb.child("chats").child(senderRoom!!).child("messages").addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                messageList.clear()
                for(postSnapshot in snapshot.children)
                {
                    val message=postSnapshot.getValue(Message::class.java)
                    messageList.add(message!!)

                }
                messageAdapter.notifyDataSetChanged() // Mereu trebuie sa notificam adapter ul dupa schimbari
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })


        buttonSend.setOnClickListener()
        {
            val message=messageBox.text.toString()
            val messageObject=Message(message,senderUid!!)
            mDb.child("chats").child(senderRoom!!).child("messages").push().setValue(messageObject).addOnSuccessListener {
                mDb.child("chats").child(receiveRoom!!).child("messages").push().setValue(messageObject)
            }
            messageBox.setText("") //stergem ce e in message box pentru a putea scrie un nou mesaj
        }


       //Adaugam mesajele in baza de date



        supportActionBar?.title=name

    }
}