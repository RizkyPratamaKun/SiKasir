package com.project.sikasir

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.project.sikasir.adapter.adapterpegawai
import com.project.sikasir.listview.Pegawai
import kotlinx.android.synthetic.main.activity_a5_kelolapegawai.*

class a5_kelolapegawai : AppCompatActivity() {
    //FIREBASE
    private lateinit var dbref: DatabaseReference
    private lateinit var userRecyclerview: RecyclerView
    private lateinit var userArrayList: ArrayList<Pegawai>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_a5_kelolapegawai)

        tvA5toA2.setOnClickListener { _ ->
            finish()
        }
        fabTambahPegawai.setOnClickListener {
            val intent = Intent(this, a9_tambahpegawai::class.java)
            startActivity(intent)
        }

        //RecyclerView
        userRecyclerview = reclistpegawai
        userRecyclerview.layoutManager = GridLayoutManager(this, 2)
        userRecyclerview.setHasFixedSize(true)
        userArrayList = arrayListOf<Pegawai>()
        getUserData()
    }

    private fun getUserData() {
        dbref = FirebaseDatabase.getInstance().getReference("Pegawai")
        dbref.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (userSnapshot in snapshot.children) {
                        val pegawai = userSnapshot.getValue(Pegawai::class.java)
                        userArrayList.add(pegawai!!)
                    }
                    userRecyclerview.adapter = adapterpegawai(userArrayList)
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }
}