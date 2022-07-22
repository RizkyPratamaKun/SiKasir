package com.project.sikasir.laporan.ringkasan

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.project.sikasir.R
import com.project.sikasir.laporan.riwayat.classRiwayat
import kotlinx.android.synthetic.main.laporan_ringkasan.*
import java.text.NumberFormat
import java.util.*

class ringkasan : AppCompatActivity() {
    val pList = ArrayList<classRiwayat>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.laporan_ringkasan)
        tgl()
        getRingkasan()
        tvA3toA5.setOnClickListener { finish() }
    }

    private fun tgl() {
        editTextDate3.setOnClickListener {
            val datePicker = MaterialDatePicker.Builder.dateRangePicker().build()
            datePicker.show(supportFragmentManager, "DatePicker")

            datePicker.addOnPositiveButtonClickListener {
                editTextDate3.setText(datePicker.headerText)
                Toast.makeText(this, "${datePicker.headerText}", Toast.LENGTH_LONG).show()
            }

            datePicker.addOnNegativeButtonClickListener {
            }

            datePicker.addOnCancelListener {
            }
        }
    }

    private fun getRingkasan() {
        val refProduk = FirebaseDatabase.getInstance().getReference("Transaksi")

        refProduk.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()) {
                    pList.clear()
                    var totvar = 0
                    var diskonvar = 0
                    var modalvar = 0
                    for (snap in snapshot.children) {
                        val t = snap.getValue(classRiwayat::class.java)

                        if (snap.child("total").exists()) {
                            totvar += Integer.parseInt(snap.child("total").getValue(String::class.java)!!.replace(",00", "").replace(".", "").replace("Rp ", ""))
                        }
                        if (snap.child("diskon").exists()) {
                            diskonvar += Integer.parseInt(snap.child("diskon").getValue(String::class.java)!!.replace(",00", "").replace(".", "").replace("Rp ", ""))
                        }
                        if (snap.child("total_Modal").exists()) {
                            modalvar += Integer.parseInt(snap.child("total_Modal").getValue(String::class.java)!!.replace(",00", "").replace(".", "").replace("Rp ", ""))
                        }
                        pList.add(t!!)
                    }
                    val tpenjualan = totvar - diskonvar
                    val tKeutungan = tpenjualan - modalvar

                    val b = NumberFormat.getCurrencyInstance(Locale("in", "ID")).format(totvar)
                    val total = b.substring(0, 2) + " " + b.substring(2, b.length)

                    val a = NumberFormat.getCurrencyInstance(Locale("in", "ID")).format(diskonvar)
                    val diskon = a.substring(0, 2) + " " + a.substring(2, a.length)

                    val s = NumberFormat.getCurrencyInstance(Locale("in", "ID")).format(tpenjualan)
                    val totalpenjualan = s.substring(0, 2) + " " + s.substring(2, s.length)

                    val c = NumberFormat.getCurrencyInstance(Locale("in", "ID")).format(modalvar)
                    val modal = c.substring(0, 2) + " " + c.substring(2, c.length)

                    val d = NumberFormat.getCurrencyInstance(Locale("in", "ID")).format(tKeutungan)
                    val keuntungan = d.substring(0, 2) + " " + d.substring(2, d.length)

                    textView32.text = total
                    tv_tpenjualan.text = totalpenjualan
                    textView57.text = totalpenjualan
                    textView59.text = modal
                    textView61.text = keuntungan
                    tv_tp_diskon.text = diskon

                }
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }
}