package com.example.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignUp : AppCompatActivity() {
    private lateinit var edtName: EditText
    private lateinit var edtEmail: EditText
    private lateinit var edtPassword: EditText
    private lateinit var btnSignUp: Button
    private lateinit var mAuth: FirebaseAuth //Declarare variabila pt FireBase autentificare
    private lateinit var mDbRef:DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        supportActionBar?.hide()  // Ascundem bara cu numele proiectului din pagina de SignUp

        mAuth = FirebaseAuth.getInstance() // intsantiere Firebase autentificare
        edtEmail = findViewById(R.id.edt_email)
        edtPassword = findViewById(R.id.edit_pass)
        edtName = findViewById(R.id.edt_name)
        btnSignUp = findViewById(R.id.btnSignUp)

        btnSignUp.setOnClickListener {
            val name=edtName.text.toString()
            val email = edtEmail.text.toString()
            val password=edtPassword.text.toString()


            // Cream (signup) noul user
            signUp(name,email,password)


        }
    }

    private fun signUp(name:String,email:String,password:String)
    {
        //logic of creating user
        //de pe Google
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) { // if user is succesfully created
                    // code for jumping to home activity when task is successful

                    addUserToDataBase(name,email,mAuth.currentUser?.uid!!)// Adaugam userii in DataBase
                    val intent=Intent(this@SignUp, MainActivity::class.java) // Sarim de la pagina SignUp la MainActivity
                    //Inainte sa trecem la urm pagina, vrem sa inchidem pagina curenta
                    finish()
                    startActivity(intent)




                } else {
                    // If sign in fails, display a message to the user.
                        Toast.makeText(this@SignUp, "Some error accurred!",Toast.LENGTH_LONG).show()


                }
            }
    }

    private fun addUserToDataBase(name:String,email:String,uid:String)
    {
        mDbRef=FirebaseDatabase.getInstance().getReference()
        mDbRef.child("user").child(uid).setValue(User(name,email,uid))



    }
}