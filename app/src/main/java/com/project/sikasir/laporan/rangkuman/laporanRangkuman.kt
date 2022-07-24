package com.project.sikasir.laporan.rangkuman

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.project.sikasir.R
import com.project.sikasir.laporan.laporan
import com.project.sikasir.laporan.riwayat.adapterRiwayat
import com.project.sikasir.laporan.riwayat.classRiwayat
import com.project.sikasir.transaksi.riwayat.riwayatTransaksi
import kotlinx.android.synthetic.main.laporan_rangkuman.*
import java.text.NumberFormat
import java.util.*

class laporanRangkuman : AppCompatActivity() {
    val pList = ArrayList<classRiwayat>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.laporan_rangkuman)

        getRiwayat()

        tvA3toA3.setOnClickListener { startActivity(Intent(this, laporan::class.java)) }
        tvA3toA3.setOnClickListener { finish() }
        transaksi_detail.setOnClickListener { startActivity(Intent(this, riwayatTransaksi::class.java)) }
        editTextDate.setOnClickListener {
            tgl()
        }
    }

    private fun tgl() {
        val datePicker = MaterialDatePicker.Builder.dateRangePicker().build()
        datePicker.show(supportFragmentManager, "DatePicker")

        editTextDate.isEnabled = false
        Toast.makeText(this, "tunggu sebentar", Toast.LENGTH_SHORT).show()

        datePicker.addOnPositiveButtonClickListener {
            val awal = datePicker.selection!!.first
            val akhir = datePicker.selection!!.second

            editTextDate.setText(datePicker.headerText)

            editTextDate.isEnabled = true
            Toast.makeText(this, datePicker.headerText, Toast.LENGTH_LONG).show()
        }

        datePicker.addOnNegativeButtonClickListener {
            editTextDate.isEnabled = true
        }

        datePicker.addOnCancelListener {
            editTextDate.isEnabled = true
        }
    }

    private fun getRiwayat() {
        rv_riwayat.layoutManager = GridLayoutManager(this, 1)
        rv_riwayat.setHasFixedSize(true)

        val refProduk = FirebaseDatabase.getInstance().getReference("Transaksi")

        refProduk.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()) {
                    pList.clear()
                    var tot = 0
                    var i = 0
                    for (snap in snapshot.children) {
                        i += 1
                        val t = snap.getValue(classRiwayat::class.java)

                        if (snap.child("total").exists()) {
                            tot += Integer.parseInt(snap.child("total").getValue(String::class.java)!!.replace(",00", "").replace(".", "").replace("Rp ", ""))
                        }

                        val totalString = NumberFormat.getCurrencyInstance(Locale("in", "ID")).format(tot)
                        val total = totalString.substring(0, 2) + " " + totalString.substring(2, totalString.length)

                        keuntungan_nama.text = total

                        pList.add(t!!)
                    }
                    totaltransaksi_nama.text = i.toString() + " Transaksi"
                    rv_riwayat.adapter = adapterRiwayat(pList)
                }
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }
}