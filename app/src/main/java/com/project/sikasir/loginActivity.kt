package com.project.sikasir

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class loginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        auth = FirebaseAuth.getInstance()

        val buttonLog = findViewById<TextView>(R.id.buttonLogin)
        val buttonReg = findViewById<TextView>(R.id.buttonRegistrasi)

        buttonLog.setOnClickListener { _ ->
            startActivity(Intent(this, a2_menu::class.java))
            finish()
            //do what you want after click inside here
        }
        buttonReg.setOnClickListener { _ ->
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
            reload()
        }

        fun UpdateUI(currentUser: FirebaseUser?) {

        }
    }
}