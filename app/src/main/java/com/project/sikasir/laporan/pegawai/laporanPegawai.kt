package com.project.sikasir.laporan.pegawai

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.project.sikasir.R
import com.project.sikasir.listview.classPegawai
import kotlinx.android.synthetic.main.laporan_pegawai.*

class laporanPegawai : AppCompatActivity() {
    val pegawaiList = ArrayList<classPegawai>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.laporan_pegawai)
    }

    private fun getPegawai() {
        rv_peg.layoutManager = GridLayoutManager(this, 2)
        rv_peg.setHasFixedSize(true)
        val refProduk = FirebaseDatabase.getInstance().getReference("Transaksi")

        refProduk.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    rv_peg.visibility = View.VISIBLE
                    pegawaiList.clear()
                    for (snap in snapshot.children) {
                        val kat = snap.getValue(classPegawai::class.java)
                        pegawaiList.add(kat!!)
                    }
                    rv_peg.adapter = adapterLaporanPegawai(pegawaiList)
                } else {
                    rv_peg.visibility = View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }

}