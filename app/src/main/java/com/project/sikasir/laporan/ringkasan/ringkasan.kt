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
import com.project.sikasir.transaksi.pembayaran.classTransaksi
import kotlinx.android.synthetic.main.laporan_ringkasan.*
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

class ringkasan : AppCompatActivity() {
    val pList = ArrayList<classRingkasan>()
    val waktu = SimpleDateFormat("dd-MM-yyyy").format(Date())
    val Rp = NumberFormat.getCurrencyInstance(Locale("in", "ID"))

    private lateinit var ssTransaksi: DataSnapshot

    private var awal = -1L
    private var akhir = -1L
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.laporan_ringkasan)

        editTextDate3.setText(waktu)
        getData()

        editTextDate3.setOnClickListener {
            val datePicker = MaterialDatePicker.Builder.dateRangePicker().build()
            datePicker.show(supportFragmentManager, "DatePicker")

            editTextDate3.isEnabled = false
            Toast.makeText(this, "tunggu sebentar", Toast.LENGTH_SHORT).show()

            datePicker.addOnPositiveButtonClickListener {
                awal = datePicker.selection!!.first
                akhir = datePicker.selection!!.second
                initAdapter()

                editTextDate3.setText(datePicker.headerText)
                editTextDate3.isEnabled = true
            }

            datePicker.addOnNegativeButtonClickListener {
                editTextDate3.isEnabled = true
            }

            datePicker.addOnCancelListener {
                editTextDate3.isEnabled = true
            }
        }

        tvA3toA5.setOnClickListener { finish() }
    }

    private fun getData() {
        val refTransaksi = FirebaseDatabase.getInstance().getReference("Transaksi")

        refTransaksi.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshotTransaksi: DataSnapshot) {
                if (snapshotTransaksi.exists()) {
                    ssTransaksi = snapshotTransaksi
                    initAdapter()
                }
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }

    private fun initAdapter() {
        if (this::ssTransaksi.isInitialized) {
            pList.clear()
            var totvar = 0
            var diskonvar = 0
            var modalvar = 0

            for (snapTransaksi in ssTransaksi.children) {
                if (awal == -1L && akhir == -1L) {
                    for (snapTransaksi in ssTransaksi.children) {
                        if (snapTransaksi.child("total").exists()) {
                            totvar += Integer.parseInt(snapTransaksi.child("total").getValue(String::class.java)!!.replace(",00", "").filter { it.isDigit() })
                        }
                        if (snapTransaksi.child("diskon").exists()) {
                            diskonvar += Integer.parseInt(snapTransaksi.child("diskon").getValue(String::class.java)!!.replace(",00", "").filter { it.isDigit() })
                        }
                        if (snapTransaksi.child("total_Modal").exists()) {
                            modalvar += Integer.parseInt(snapTransaksi.child("total_Modal").getValue(String::class.java)!!.replace(",00", "").filter { it.isDigit() })
                        }

                        val tpenjualan = totvar - diskonvar
                        val tKeutungan = tpenjualan - modalvar

                        val b = Rp.format(totvar)
                        val total = b.substring(0, 2) + " " + b.substring(2, b.length)

                        val a = Rp.format(diskonvar)
                        val diskon = a.substring(0, 2) + " " + a.substring(2, a.length)

                        val s = Rp.format(tpenjualan)
                        val totalpenjualan = s.substring(0, 2) + " " + s.substring(2, s.length)

                        val c = Rp.format(modalvar)
                        val modal = c.substring(0, 2) + " " + c.substring(2, c.length)

                        val d = Rp.format(tKeutungan)
                        val keuntungan = d.substring(0, 2) + " " + d.substring(2, d.length)

                        textView32.text = total
                        tv_tpenjualan.text = totalpenjualan
                        textView57.text = totalpenjualan
                        textView59.text = modal
                        textView61.text = keuntungan
                        tv_tp_diskon.text = diskon
                    }
                } else {
                    for (snapTransaksi in ssTransaksi.children) {
                        val t = snapTransaksi.getValue(classTransaksi::class.java)
                        if (t?.tanggal!! >= awal && t.tanggal!! <= akhir) {
                            if (snapTransaksi.child("total").exists()) {
                                totvar += Integer.parseInt(snapTransaksi.child("total").getValue(String::class.java)!!.replace(",00", "").replace(".", "").replace("Rp ", ""))
                            }
                            if (snapTransaksi.child("total").exists()) {
                                totvar += Integer.parseInt(snapTransaksi.child("total").getValue(String::class.java)!!.replace(",00", "").replace(".", "").replace("Rp ", ""))
                            }
                            if (snapTransaksi.child("diskon").exists()) {
                                diskonvar += Integer.parseInt(snapTransaksi.child("diskon").getValue(String::class.java)!!.replace(",00", "").replace(".", "").replace("Rp ", ""))
                            }
                            if (snapTransaksi.child("total_Modal").exists()) {
                                modalvar += Integer.parseInt(snapTransaksi.child("total_Modal").getValue(String::class.java)!!.replace(",00", "").replace(".", "").replace("Rp ", ""))
                            }
                            val tpenjualan = totvar - diskonvar
                            val tKeutungan = tpenjualan - modalvar

                            val b = Rp.format(totvar)
                            val total = b.substring(0, 2) + " " + b.substring(2, b.length)

                            val a = Rp.format(diskonvar)
                            val diskon = a.substring(0, 2) + " " + a.substring(2, a.length)

                            val s = Rp.format(tpenjualan)
                            val totalpenjualan = s.substring(0, 2) + " " + s.substring(2, s.length)

                            val c = Rp.format(modalvar)
                            val modal = c.substring(0, 2) + " " + c.substring(2, c.length)

                            val d = Rp.format(tKeutungan)
                            val keuntungan = d.substring(0, 2) + " " + d.substring(2, d.length)

                            textView32.text = total
                            tv_tpenjualan.text = totalpenjualan
                            textView57.text = totalpenjualan
                            textView59.text = modal
                            textView61.text = keuntungan
                            tv_tp_diskon.text = diskon
                        }
                    }
                }
            }
        }
    }
}