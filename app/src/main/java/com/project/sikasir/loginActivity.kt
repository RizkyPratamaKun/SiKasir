package com.project.sikasir

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_login.*

class loginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        auth = FirebaseAuth.getInstance()


        buttonLogin.setOnClickListener { _ ->
            startActivity(Intent(this, a2_menu::class.java))
            finish()
            //do what you want after click inside here
        }
        buttonRegistrasi.setOnClickListener { _ ->
            startActivity(Intent(this, registerActivity::class.java))
            finish()
            //do what you want after click inside here
        }

    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if (currentUser != null) {
        }

        fun UpdateUI(currentUser: FirebaseUser?) {

        }
    }
}