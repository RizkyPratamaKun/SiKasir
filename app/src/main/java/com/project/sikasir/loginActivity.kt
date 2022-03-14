package com.project.sikasir

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Toast
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
            dologin()
            //do what you want after click inside here
        }
        buttonRegistrasi.setOnClickListener { _ ->
            startActivity(Intent(this, registerActivity::class.java))
            finish()
            //do what you want after click inside here
        }

    }

    private fun dologin() {
        if (etEmailLog.text.toString().isEmpty()) {
            etEmailLog.error = "Tolong Masukan ID Anda"
            etEmailLog.requestFocus()
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(etEmailLog.text.toString()).matches()) {
            etEmailLog.error = "Format ID salah"
            etEmailLog.requestFocus()
            return
        }
        if (etPassLog.text.toString().isEmpty()) {
            etPassLog.error = "Tolong Masukan Password Anda"
            etPassLog.requestFocus()
            return
        }

        auth.signInWithEmailAndPassword(etEmailLog.text.toString(), etPassLog.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithEmail:success")
                    val user = auth.currentUser
                    UpdateUI(user)
                } else {
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    // If sign in fails, display a message to the user.
                    UpdateUI(null)
                }
            }
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        UpdateUI(currentUser)
    }

    private fun UpdateUI(currentUser: FirebaseUser?) {
        if (currentUser != null) {
            startActivity(Intent(this, a2_menu::class.java))
            finish()
        } else {

            Toast.makeText(
                baseContext, "Authentication failed.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}
