package com.project.sikasir.menu

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.project.sikasir.R
import com.project.sikasir.transaksi.keranjang.classKeranjang
import com.project.sikasir.transaksi.pembayaran.classPembayaran
import kotlinx.android.synthetic.main.about.*

class aboutMe : AppCompatActivity() {
    val pembayaranList = ArrayList<classPembayaran>()
    var listKeranjang = ArrayList<classKeranjang>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.about)

        tv_nama.setOnClickListener {
            val terbanyak = FirebaseDatabase.getInstance().getReference("Transaksi").orderByChild("namaPegawai")

            terbanyak.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        var namaPeg = 0
                        var nama = ""
                        for (snap in snapshot.children) {
                            val pembayaran = snap.child("produk/0/nama_Produk").value

                            println("snap")
                            println(snap)

                            println("produk")
                            println(snap.child("produk"))

                            println("namaPegawai")
                            println(snap.child("namaPegawai"))

                            println("Pembayaran")
                            println(pembayaran)
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {}
            })
        }

        tv_devjudul.setOnClickListener {
        }

        iv_gambar.setOnClickListener {
            val produk = FirebaseDatabase.getInstance().getReference("Transaksi")
            produk.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        for (snap in snapshot.children) {
                            val pembayaran = snap.getValue(classPembayaran::class.java)

                            println("pembayaran")
                            if (pembayaran != null) {
                                println(pembayaran.emailPegawai!!.count())
                                Log.i("Cases", "M case count: " + snapshot.childrenCount)
                            }
                        }
                    } else {
                        Toast.makeText(this@aboutMe, "snapshot Kosong", Toast.LENGTH_SHORT).show()
                        println(snapshot)
                    }
                }

                override fun onCancelled(error: DatabaseError) {}
            })
        }

        tv_opsional2.setOnClickListener {
            val terbanyak = FirebaseDatabase.getInstance().getReference("Transaksi").orderByChild("namaPegawai")

            terbanyak.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        var namaPeg = 0
                        var nama = ""
                        for (snap in snapshot.children) {
                            val pembayaran = snap.child("produk/0/nama_Produk").value

                            println("snap")
                            println(snap)

                            println("produk")
                            println(snap.child("produk"))

                            println("namaPegawai")
                            println(snap.child("namaPegawai").childrenCount)

                            println("Pembayaran")
                            println(pembayaran)
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {}
            })
        }

        tv_backabout.setOnClickListener {
            finish()
        }

        tv_contactme.setOnClickListener {
            try {
                val telegram = Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/Ronysayur"))
                telegram.setPackage("org.telegram.messenger")
                startActivity(telegram)
            } catch (e: Exception) {
                Toast.makeText(this, "Telegram app is not installed", Toast.LENGTH_LONG).show()
            }
        }
    }
}