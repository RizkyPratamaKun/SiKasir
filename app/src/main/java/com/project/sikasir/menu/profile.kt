package com.project.sikasir.menu

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*
import com.project.sikasir.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.profile.*

class profile : AppCompatActivity() {

    private var USERNAME_KEY = "username_key"
    private var username_key = ""
    private var username_key_new = ""

    private lateinit var reference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile)

        //set
        getUsernameLocal()
        getProfildata()
    }

    fun getProfildata() {
        reference = FirebaseDatabase.getInstance().reference.child("Pegawai").child(username_key_new)
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                profil_tv_nama.text = dataSnapshot.child("Nama_Pegawai").value.toString()
                profil_tv_jabatan.text = dataSnapshot.child("Nama_Jabatan").value.toString()
                profil_tvEmail.text = dataSnapshot.child("Email_Pegawai").value.toString()
                tv_nohp.text = dataSnapshot.child("HP").value.toString()
                tv_hakAkses.text = dataSnapshot.child("Hak_Akses").value.toString()

                Picasso.get().load(dataSnapshot.child("url_photo_profile").value.toString()).centerCrop().fit().into(profil_iv)
            }

            override fun onCancelled(p0: DatabaseError) {}
        }
        reference.addListenerForSingleValueEvent(postListener)
    }

    fun getUsernameLocal() {
        val sharedPreference: SharedPreferences = getSharedPreferences(USERNAME_KEY, Context.MODE_PRIVATE)
        username_key_new = sharedPreference.getString(username_key, "").toString()
    }
}