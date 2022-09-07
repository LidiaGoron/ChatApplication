package com.example.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.view.Menu
import android.view.MenuItem
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {
    private lateinit var userRecycleView: RecyclerView
    private lateinit var userList:ArrayList<User>
    private lateinit var adapter: UserAdapter
    private lateinit var mAuth:FirebaseAuth // Avem nevoie de FireBase daca vrem log out
    private lateinit var mDbRef:DatabaseReference // Real Time data base?


    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mAuth=FirebaseAuth.getInstance() // initializare FireBase
        mDbRef=FirebaseDatabase.getInstance().getReference()

       // AnimationUtils.loadAnimation(applicationContext, androidx.appcompat.R.anim.abc_fade_in)

        userList= ArrayList()
        adapter=UserAdapter(context = this,userList)

        // Dupa initializere, vrem sa adaugam users in userList ca sa se afiseze in RecycleView
        userRecycleView=findViewById(R.id.userRecycleView)

        userRecycleView.layoutManager=LinearLayoutManager(this)
        userRecycleView.adapter=adapter
        //Citim datele din BD si le punem in RecycleView

        mDbRef.child("user").addValueEventListener(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {  // Aceasta functie este apelata de fiecare data cand avem schimbari in BD
                //snapshot este folosit pentru a accesa datele din DB
                //inainte de for trebuie sa stergem lista trecuta de useri
                userList.clear()
                for (postSnapshot in snapshot.children) {
                    val currentUser = postSnapshot.getValue(User::class.java)
                    //Vrem sa ne apara lista cu toti in afara de user ul curent,
                    //deoarece nu vrem sa avem chat cu noi
                    if(mAuth.currentUser?.uid!=currentUser?.uid) {
                        userList.add(currentUser!!) // !! = super safe
                    }

                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })





    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu) // primul parametru este menu.xml iar al doilea este param din functia
                                                // onCreateOptionsMenu
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId==R.id.logout)
        {
            //Scriem codul pentru logout
            mAuth.signOut()
            val intent= Intent(this@MainActivity,LogIn::class.java)
            startActivity(intent)
            finish()
            return true
        }
        return true
    }

}