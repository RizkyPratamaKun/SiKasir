package com.project.sikasir

import android.content.Intent
import android.os.Bundle
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
        if (tvUsernameReg.text.toString().isEmpty()) {
            tvUsernameReg.error = "Tolong Masukan ID Anda"
            tvUsernameReg.requestFocus()
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(tvUsernameReg.text.toString()).matches()) {
            tvUsernameReg.error = "Format ID salah"
            tvUsernameReg.requestFocus()
            return

        }
        if (tvPasswordReg.text.toString().isEmpty()) {
            tvPasswordReg.error = "Tolong Masukan Password Anda"
            tvPasswordReg.requestFocus()
            return
        }

        auth.createUserWithEmailAndPassword(
            tvUsernameReg.text.toString(),
            tvPasswordReg.text.toString()
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
                    // If sign in fails, display a message to the user.
                    Toast.makeText(
                        baseContext, "Pendaftaran gagal, coba beberapa saat lagi.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }
}