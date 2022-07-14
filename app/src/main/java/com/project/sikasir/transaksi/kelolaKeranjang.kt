package com.project.sikasir.transaksi

import android.app.AlertDialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase
import com.project.sikasir.R
import kotlinx.android.synthetic.main.kelolakeranjang.*
import java.text.NumberFormat
import java.util.*

class kelolaKeranjang : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.kelolakeranjang)

        val Nama_Produk: String = intent.getStringExtra("Nama_Produk").toString()
        val jumlahProduk: String = intent.getStringExtra("jumlahProduk").toString()
        val harga: String = intent.getStringExtra("harga").toString()
        val total: String = intent.getStringExtra("total").toString()

        tv_harga_keranjang.text = harga
        tv_jumlah_keranjang.text = jumlahProduk
        tv_namaitem.text = Nama_Produk
        tv_total_keranjang.text = total

        plus.setOnClickListener {
            display(tv_jumlah_keranjang.text.toString().toInt() + 1)
            kali(tv_jumlah_keranjang.text.toString().toInt() * harga.replace(".", "").replace("Rp ", "").toInt())
        }
        minus.setOnClickListener {
            if (tv_jumlah_keranjang.text.toString().toInt() <= 0) {
                hapusKeranjang()
            } else {
                display(tv_jumlah_keranjang.text.toString().toInt() - 1)
                kali(tv_jumlah_keranjang.text.toString().toInt() * harga.replace(".", "").replace("Rp ", "").toInt())
            }
        }

        cl_hapus.setOnClickListener { hapusKeranjang() }
        btn_simpan.setOnClickListener {
            if (tv_jumlah_keranjang.text.toString().toInt() == 0) {
                hapusKeranjang()
            }
        }

    }

    private fun display(number: Int) {
        tv_jumlah_keranjang.text = "$number"
    }

    private fun kali(hasil: Int) {
        val totalString = NumberFormat.getCurrencyInstance(Locale("in", "ID")).format(hasil)
        val total = totalString.substring(0, 2) + " " + totalString.substring(2, totalString.length)
        tv_total_keranjang.text = total
    }

    private fun hapusKeranjang() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Yakin Untuk Menghapus Data?")
            .setCancelable(false)
            .setPositiveButton("Ya") { dialog, id ->
                deleteKeranjang()
                Toast.makeText(this, "Data berhasil dihapus", Toast.LENGTH_SHORT).show()
                finish()
            }
            .setNegativeButton("Tidak") { dialog, id -> dialog.dismiss() }
        val alert = builder.create()
        alert.show()
    }

    private fun deleteKeranjang() {
        val Nama_Produk: String = intent.getStringExtra("Nama_Produk").toString()
        val reference = FirebaseDatabase.getInstance().reference.child("Keranjang").child(Nama_Produk)
        reference.removeValue()
    }
}