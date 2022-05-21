package com.project.sikasir

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_a12_register.*

class a13_register : AppCompatActivity() {

    //Firebase RealtimeDatabase
    private lateinit var reference: DatabaseReference // penyimpanan data secara lokal storage
    private var USERNAME_KEY = "username_key"
    private var username_key = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_a12_register)

        //menuju balaman resgister
        btnmasukreg.setOnClickListener(View.OnClickListener {
            intent = Intent(this, a12_login::class.java)
            startActivity(intent)
        })

        //focus pertama edit nama
        edt_dftrnama.isFocusableInTouchMode = true
        edt_dftrnama.requestFocus()

        //Tambah "Users" node ke firebase
        tv_btndftr.setOnClickListener(View.OnClickListener {
            //menyimpan di lokal storage/smartphone
            val sharedPreferences: SharedPreferences =
                getSharedPreferences(USERNAME_KEY, Context.MODE_PRIVATE)
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.putString(username_key, edt_dftrnama.text.toString()).apply()

            //menyimpan ke firebase database
            reference = FirebaseDatabase
                .getInstance()
                .reference
                .child("Users")
                .child(edt_dftrnama.text.toString())

            reference.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    dataSnapshot.ref.child("username").setValue(edt_dftrnama.text.toString())
                    dataSnapshot.ref.child("password").setValue(edt_passdftr.text.toString())
                    dataSnapshot.ref.child("email_address").setValue(edt_emaildftr.text.toString())
                }

                override fun onCancelled(dataSnapshot: DatabaseError) {

                }
            })

            Toast.makeText(this, "Data Berhasil Tersimpan", Toast.LENGTH_SHORT).show()
            // berpindah activity
            val registerActivityIntent = Intent(this, a12_login::class.java)
            startActivity(registerActivityIntent)
        })
    }
}
