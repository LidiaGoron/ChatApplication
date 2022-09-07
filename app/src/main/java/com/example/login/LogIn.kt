package com.example.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase

class LogIn : AppCompatActivity() {
    private lateinit var edtEmail:EditText
    private lateinit var edtPassword:EditText
    private lateinit var btnLogIn: Button
    private lateinit var btnSignUp: Button

    //Declarare variabila pt FireBase autentificare
    private lateinit var mAuth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)

        supportActionBar?.hide()  // Ascundem bara cu numele proiectului din pagina de LogIn

        mAuth=FirebaseAuth.getInstance() // intsantiere Firebase autentificare
        edtEmail=findViewById(R.id.edt_email)
        edtPassword=findViewById(R.id.edit_pass)
        btnLogIn=findViewById(R.id.btnLogIn)
        btnSignUp=findViewById(R.id.btnSignUp)

        btnSignUp.setOnClickListener {
            val intent= Intent(this,SignUp::class.java) //?????????
            finish()
            startActivity(intent)
        }

        //Implementare functionalitate FareBase autentificare
        // Trebuie ca butonul de login sa fie apasat
        btnLogIn.setOnClickListener {
            // Primul lucru care il facem este sa luat email ul si parola de la user
            val email=edtEmail.text.toString()
            val password=edtPassword.text.toString()

            login(email,password); // cream o functie care logeaza cu ajutorul emai-ului si parolei date in TextView
            // functia trebuie apelata la momentul apasarii butonului
        }

    }
    // Crearea metodei login, se va face in afara functiei onCreate
    private fun login(email: String, password: String)
    {
            //logic for logig in user
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // code for loggin in user
                    val intent=Intent(this@LogIn, MainActivity::class.java) // Sarim de la pagina SignUp la MainActivity
                    startActivity(intent)

                } else {
                   Toast.makeText(this@LogIn,"User does not exist!",Toast.LENGTH_LONG).show()
                }
            }

    }
}