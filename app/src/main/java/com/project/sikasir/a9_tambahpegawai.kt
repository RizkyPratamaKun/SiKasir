package com.project.sikasir

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_a9_tambahpegawai.*

class a9_tambahpegawai : AppCompatActivity() {
    //Firebase RealtimeDatabase
    private lateinit var reference: DatabaseReference // penyimpanan data secara lokal storage
    private var USERNAME_KEY = "username_key"
    private var username_key = ""
    private var akses = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_a9_tambahpegawai)

        val id = getRandomString(5)
        tv_id.text = id

        // Spinner
        val jab = resources.getStringArray(R.array.Jabatan)
        val spinner = findViewById<Spinner>(R.id.spinner)
        if (spinner != null) {
            val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item, jab
            )
            spinner.adapter = adapter
            spinner.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View, position: Int, id: Long
                ) {
                    akses = jab[position].toString()
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }
        }

        //Button Eksekusi Simpan Pegawai
        btnsimpan_pegawai.setOnClickListener(View.OnClickListener {
            val Nama: String = edNamaPegawai.text.toString()
            val Jabatan: String = edNamaJabatan.text.toString()
            val HP: String = edHp.text.toString()
            val Email: String = edemailpegawai.text.toString()
            val Pin: String = edpin.text.toString()

            //menyimpan di lokal storage/smartphone
            val sharedPreferences: SharedPreferences =
                getSharedPreferences(USERNAME_KEY, Context.MODE_PRIVATE)
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.putString(username_key, edNamaPegawai.text.toString()).apply()

            if (Nama.isEmpty()) {
                edNamaPegawai.error = "Nama Pegawai tidak boleh kosong"
            } else {
                if (Jabatan.isEmpty()) {
                    edNamaJabatan.error = "Nama Jabatan tidak boleh kosong"
                } else {
                    if (HP.isEmpty()) {
                        edHp.error = "Nomor HP tidak boleh kosong"
                    } else {
                        if (Email.isEmpty()) {
                            edemailpegawai.error = "Email tidak boleh kosong"
                        } else {
                            if (Pin.isEmpty()) {
                                edpin.error = "Pin Harus diisi"
                            } else {
                                if (Pin.trim().length < 6) {
                                    edpin.error = "Pin Harus 6 digit angka"
                                } else {
                                    //menyimpan ke firebase database
                                    reference = FirebaseDatabase
                                        .getInstance()
                                        .reference
                                        .child("Pegawai")
                                        .child(Nama)
                                    reference.addListenerForSingleValueEvent(object :
                                        ValueEventListener {
                                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                                            if (dataSnapshot.exists()) {
                                                val alert =
                                                    AlertDialog.Builder(this@a9_tambahpegawai)
                                                alert.setTitle("Peringatan")
                                                alert.setMessage("Data dengan nama " + Nama + " Sudah Ada!")
                                                alert.setPositiveButton("Ok", null)
                                                alert.show()
                                            } else {
                                                dataSnapshot.ref.child("Nama_Pegawai")
                                                    .setValue(Nama)
                                                dataSnapshot.ref.child("Nama_Jabatan")
                                                    .setValue(Jabatan)
                                                dataSnapshot.ref.child("Hak_Akses").setValue(akses)
                                                dataSnapshot.ref.child("HP").setValue(HP)
                                                dataSnapshot.ref.child("Email_Pegawai")
                                                    .setValue(Email)
                                                dataSnapshot.ref.child("Pin").setValue(Pin)
                                                //berpindah activity
                                                val gotoHomeIntent =
                                                    Intent(
                                                        this@a9_tambahpegawai,
                                                        a5_kelolapegawai::class.java
                                                    )
                                                startActivity(gotoHomeIntent)
                                                finish()
                                                Toast.makeText(
                                                    this@a9_tambahpegawai,
                                                    Nama + " Berhasil Ditambahkan",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            }
                                        }

                                        override fun onCancelled(dataSnapshot: DatabaseError) {

                                        }
                                    })
                                }
                            }
                        }
                    }
                }
            }
        })

        /* A function that will be executed when the button is clicked. */
        tvA7toA6.setOnClickListener { _ ->
            finish()
        }
    }

    fun getRandomString(length: Int): String {
        val allowedChars = ('0'..'9')
        return (1..length)
            .map { allowedChars.random() }
            .joinToString("")
    }
}