package com.project.sikasir.laporan.pegawai

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.project.sikasir.R
import kotlinx.android.synthetic.main.laporan_pegawai.*
import java.text.NumberFormat
import java.util.*

class laporanPegawai : AppCompatActivity() {
    val pList = ArrayList<classLapPegawai>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.laporan_pegawai)

        tvA3toA4.setOnClickListener { finish() }

        getPegawai()
        iv_logo_laporan_pegawai.setOnClickListener {
        }
    }

    private fun getPegawai() {
        rv_peg.layoutManager = GridLayoutManager(this, 1)
        rv_peg.setHasFixedSize(true)

        val refProduk = FirebaseDatabase.getInstance().getReference("Transaksi").orderByValue()

        refProduk.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()) {
                    var i = 0
                    var tot = 0
                    pList.clear()

                    for (snap in snapshot.children) {
                        val t = snap.getValue(classLapPegawai::class.java)
                        //Total Entitas
                        i += 1
                        if (snap.child("total").exists()) {
                            tot += Integer.parseInt(snap.child("total").getValue(String::class.java)!!.replace(",00", "").replace(".", "").replace("Rp ", ""))
                        }

                        println(snap.child("namaPegawai"))
                        println(snap.child("namaPegawai"))
                        println(snap.child("namaPegawai"))
                        println(snap.child("namaPegawai"))
                        println(snap.child("namaPegawai"))

                        pList.add(t!!)
                    }
                    val totalString = NumberFormat.getCurrencyInstance(Locale("in", "ID")).format(tot)
                    val total = totalString.substring(0, 2) + " "

                    tv_omset.text = total
                    tv_jmltransaksi.text = i.toString()
                    rv_peg.adapter = adapterLaporanPegawai(pList)
                }
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }

}