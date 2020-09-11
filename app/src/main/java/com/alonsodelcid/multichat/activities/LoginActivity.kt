package com.alonsodelcid.multichat.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.alonsodelcid.multichat.R
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private val auth = Firebase.auth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginButton.setOnClickListener { loginUser() }
        createButton.setOnClickListener { createUser() }

        checkUser()
    }

    private fun checkUser(){
        val currentUser = auth.currentUser

        if(currentUser != null){
            val intent = Intent(this, ListOfChatsActivity::class.java)
            intent.putExtra("user", currentUser.email)
            startActivity(intent)

            finish()
        }
    }

    private fun createUser(){
        val email = emailText.text.toString()
        val password = passwordText.text.toString()

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if(task.isSuccessful){
                checkUser()
            } else {
                task.exception?.let {
                    Toast.makeText(baseContext, it.message, Toast.LENGTH_LONG).show()
                }
            }

        }
    }

    private fun loginUser(){
        val email = emailText.text.toString()
        val password = passwordText.text.toString()

        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if(task.isSuccessful){
                checkUser()
            } else {
                task.exception?.let {
                    Toast.makeText(baseContext, it.message, Toast.LENGTH_LONG).show()
                }
            }

        }
    }
}