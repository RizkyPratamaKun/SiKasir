package com.project.sikasir

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_register.*

class registerActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        auth = FirebaseAuth.getInstance()

        buttonDaftar.setOnClickListener {
            signUpUser()
        }
    }

    fun signUpUser() {
        if (etUsernameReg.text.toString().isEmpty()) {
            etUsernameReg.error = "Tolong Masukan ID Anda"
            etUsernameReg.requestFocus()
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(etUsernameReg.text.toString()).matches()) {
            etUsernameReg.error = "Format ID salah"
            etUsernameReg.requestFocus()
            return
        }
        if (etPasswordReg.text.toString().isEmpty()) {
            etPasswordReg.error = "Tolong Masukan Password Anda"
            etPasswordReg.requestFocus()
            return
        }

        auth.createUserWithEmailAndPassword(
            etUsernameReg.text.toString(),
            etPasswordReg.text.toString()
        )
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    startActivity(Intent(this, loginActivity::class.java))
                    finish()
                    Toast.makeText(
                        baseContext, "Pendaftaran Berhasil.",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    // If sign in fails, display a message to the user.
                    Toast.makeText(
                        baseContext, "Pendaftaran gagal, coba beberapa saat lagi.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }
}