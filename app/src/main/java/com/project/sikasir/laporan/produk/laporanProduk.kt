package com.project.sikasir.laporan.produk

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.project.sikasir.R
import com.project.sikasir.produk.produk.classProduk
import kotlinx.android.synthetic.main.laporan_produk.*

class laporanProduk : AppCompatActivity() {
    val listProduk = ArrayList<classProduk>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.laporan_produk)

        getLaporanProduk()
    }

    private fun getLaporanProduk() {
        rv_riwayat.layoutManager = GridLayoutManager(this, 1)
        rv_riwayat.setHasFixedSize(true)

        val dbref = FirebaseDatabase.getInstance().getReference("Produk")
        dbref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                listProduk.clear()
                if (snapshot.exists()) {
                    for (snap in snapshot.children) {
                        val produk = snap.getValue(classProduk::class.java)
                        listProduk.add(produk!!)
                    }
                    rv_riwayat.adapter = adapterLaporanProduk(listProduk)
                }
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }
}