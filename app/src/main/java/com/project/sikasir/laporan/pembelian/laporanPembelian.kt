package com.project.sikasir.laporan.pembelian

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
import com.project.sikasir.produk.pembelian.classPembelian
import com.project.sikasir.produk.produk.classProduk
import kotlinx.android.synthetic.main.laporan_pembelian.*
import java.text.NumberFormat
import java.util.*

class laporanPembelian : AppCompatActivity() {
    val pembelian = ArrayList<classLapPembelian>()
    val Rp = NumberFormat.getCurrencyInstance(Locale("in", "ID"))

    private lateinit var ssPembelian: DataSnapshot
    private lateinit var ssProduk: DataSnapshot

    private var awal = -1L
    private var akhir = -1L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.laporan_pembelian)
        getData()
        tvA3toA6.setOnClickListener { finish() }
        editTextDate4.setOnClickListener { tgl() }
    }

    private fun tgl() {
        val datePicker = MaterialDatePicker.Builder.dateRangePicker().build()
        datePicker.show(supportFragmentManager, "DatePicker")

        editTextDate4.isEnabled = false
        Toast.makeText(this, "tunggu sebentar", Toast.LENGTH_SHORT).show()

        datePicker.addOnPositiveButtonClickListener {
            awal = datePicker.selection!!.first
            akhir = datePicker.selection!!.second

            editTextDate4.setText(datePicker.headerText)
            editTextDate4.isEnabled = true
            initAdapter()
        }

        datePicker.addOnNegativeButtonClickListener { editTextDate4.isEnabled = true }

        datePicker.addOnCancelListener { editTextDate4.isEnabled = true }
    }

    private fun getData() {
        rv_riwayat.layoutManager = GridLayoutManager(this, 1)
        rv_riwayat.setHasFixedSize(true)

        val refPembelian = FirebaseDatabase.getInstance().getReference("Pembelian").orderByValue()
        val refProduk = FirebaseDatabase.getInstance().getReference("Produk")

        refPembelian.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshotPembelian: DataSnapshot) {
                if (snapshotPembelian.exists()) {

                    refProduk.addValueEventListener(object : ValueEventListener {
                        override fun onDataChange(snapshotProduk: DataSnapshot) {
                            if (snapshotProduk.exists()) {
                                ssPembelian = snapshotPembelian
                                ssProduk = snapshotProduk
                                initAdapter()
                            }
                        }

                        override fun onCancelled(error: DatabaseError) {}
                    })
                }
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }

    private fun initAdapter() {
        if (this::ssPembelian.isInitialized && this::ssProduk.isInitialized) {
            var jumlahPembelian = 0
            var totalTransaksi = 0
            pembelian.clear()

            //jika awal dan akhir kosong
            if (awal == -1L && akhir == -1L) {
                for (snapPembelian in ssPembelian.children) {
                    val cd = snapPembelian.getValue(classPembelian::class.java)
                    jumlahPembelian += cd?.jumlah_Produk?.toInt()!!
                    totalTransaksi += cd.totalPembelian?.replace(",00", "")?.filter { it.isDigit() }?.toInt()!!
                }
            } else {
                for (snapPembelian in ssPembelian.children) {
                    val cd = snapPembelian.getValue(classPembelian::class.java)
                    println("cdawal" + cd)
                    //jika tanggal lebih dari sama dengan awal dan tanggal kurang dari sama dengan akhir
                    if (cd?.tglLong!! >= awal && cd.tglLong!! <= akhir) {
                        jumlahPembelian += cd.jumlah_Produk?.toInt()!!
                        totalTransaksi += cd.totalPembelian?.replace(",00", "")?.filter { it.isDigit() }?.toInt()!!
                    }
                }
            }


            val totalString = Rp.format(totalTransaksi)
            val total = totalString.substring(0, 2) + " " + totalString.substring(2, totalString.length)
            totaltransaksi_nama_pembelian.text = total
            totaltransaksi_nama.text = "$jumlahPembelian Produk"

            //RV
            for (snapProduk in ssProduk.children) {
                val p = snapProduk.getValue(classProduk::class.java)
                val clp = classLapPembelian(p?.nama_Produk, p?.harga_Modal)

                //jika awal dan akhir kosong
                if (awal == -1L && akhir == -1L) {
                    for (snapPembelian in ssPembelian.children) {
                        val cp = snapPembelian.getValue(classPembelian::class.java)
                        println("snapPembelian" + snapPembelian)
                        println("CD" + cp)
                        if (cp?.namaProduk.equals(p?.nama_Produk)) {
                            clp.jumlah_Produk = clp.jumlah_Produk?.plus(1)
                            clp.totalPembelian = cp?.totalPembelian?.replace(",00", "")?.filter { it.isDigit() }?.let { clp.totalPembelian?.plus(it.toInt()) }
                        }
                    }
                } else {
                    for (snapPembelian in ssPembelian.children) {
                        val cp = snapPembelian.getValue(classPembelian::class.java)
                        println("snapPembelian" + snapPembelian)
                        println("CD" + cp)
                        if (cp?.namaProduk.equals(p?.nama_Produk) && cp?.tglLong!! >= awal && cp.tglLong!! <= akhir) {
                            clp.jumlah_Produk = clp.jumlah_Produk?.plus(1)
                            clp.totalPembelian = cp.totalPembelian?.replace(",00", "")?.filter { it.isDigit() }?.let { clp.totalPembelian?.plus(it.toInt()) }
                        }
                    }
                }
                pembelian.add(clp)
            }
            rv_riwayat.adapter = adapterLaporanPembelian(pembelian)
        }
    }
}