package com.project.sikasir.dialog

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.google.firebase.database.*
import com.project.sikasir.R
import com.project.sikasir.a3_kelolaproduk
import kotlinx.android.synthetic.main.dialog_tambah.*
import kotlinx.android.synthetic.main.dialog_tambah.view.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * Dibuat oleh RizkyPratama pada 21-Apr-22.
 */
class DialogTambahKategori : DialogFragment() {
    private var USERNAME_KEY = "username_key"
    private var username_key = ""
    private var username_key_new = ""
    private lateinit var reference: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //Tanggalan
        val sdf = SimpleDateFormat("dd-MM-yyyy hh:mm:ss")
        val currentDate = sdf.format(Date())

        dialog!!.window?.setBackgroundDrawableResource(R.drawable.round_corner)
        val view: View = inflater.inflate(R.layout.dialog_tambah, container, false)

        view.edKategoriNama.hint = "Nama Kategori"

        view.tvsimpankategori.setOnClickListener {
            val Djawaban: String = edKategoriNama.text.toString()
            val Tanggal: String = currentDate

            if (Djawaban.isEmpty()) {
                Toast.makeText(
                    activity,
                    "Isi nama kategori terlebih dahulu!",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                //menyimpan ke firebase
                reference = FirebaseDatabase
                    .getInstance()
                    .reference
                    .child("Kategori")
                    .child(Tanggal)

                reference.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        dataSnapshot.ref.child("Nama_Kategori")
                            .setValue(edKategoriNama.text.toString())
                    }

                    override fun onCancelled(databaseError: DatabaseError) {}
                })
                val intent = Intent(activity, a3_kelolaproduk::class.java)
                startActivity(intent)

                //fragment toast
                Toast.makeText(
                    this@DialogTambahKategori.requireActivity(),
                    "Data Berhasil Ditambahkan",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        // Return the fragment view/layout
        return view
    }

    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * 0.85).toInt()
        dialog!!.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
    }


}