package com.project.sikasir.transaksi.keranjang

import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
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
        groupDiskon.visibility = View.GONE

        val Nama_Produk: String = intent.getStringExtra("Nama_Produk").toString()
        val jumlahProduk: String = intent.getStringExtra("jumlahProduk").toString()
        val harga: String = intent.getStringExtra("harga").toString()
        val total: String = intent.getStringExtra("total").toString()

        tv_harga_keranjang.text = harga
        tv_jumlah_keranjang.text = jumlahProduk
        tv_namaitem.text = Nama_Produk
        tv_sub_total.text = total
        tv_total_keranjang.text = total

        switchPembayaran.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                tv_sub_total_judul.text = "Sub-total"
                groupDiskon.visibility = View.VISIBLE
                tv_diskon_keranjang.visibility = View.VISIBLE
                tv_diskon_judul.visibility = View.VISIBLE
                tv_total_judul.visibility = View.VISIBLE
                tv_total_keranjang.visibility = View.VISIBLE
            } else {
                tv_sub_total_judul.text = "Total"
                groupDiskon.visibility = View.GONE
                tv_diskon_keranjang.visibility = View.GONE
                tv_diskon_judul.visibility = View.GONE
                tv_total_judul.visibility = View.GONE
                tv_total_keranjang.visibility = View.GONE
            }
        }

        plus.setOnClickListener {
            setjumlahProduk(tv_jumlah_keranjang.text.toString().toInt() + 1)
            subTotal(tv_jumlah_keranjang.text.toString().toInt() * harga.filter { it.isDigit() }.toInt())

            val jumlah = tv_jumlah_keranjang.text.toString()
            val harga = tv_harga_keranjang.text.toString().filter { it.isDigit() }.toInt()
            val diskon = edDiskonRp.text.toString().filter { it.isDigit() }.toInt().toString()
            val stotal = tv_sub_total.text.toString().replace(",00", "").filter { it.isDigit() }.toInt()

            val tDiskon = (jumlah.toInt() * harga) - (Integer.parseInt(diskon) * Integer.parseInt(stotal.toString())) / 100

            totalKeranjang(tDiskon)

            totalDiskon(Integer.parseInt(stotal.toString()) - tDiskon)
        }

        minus.setOnClickListener {
            if (tv_jumlah_keranjang.text.toString().toInt() <= 0) {
                hapusKeranjang()
            } else {
                setjumlahProduk(tv_jumlah_keranjang.text.toString().toInt() - 1)
                subTotal(tv_jumlah_keranjang.text.toString().toInt() * harga.filter { it.isDigit() }.toInt())

                val jumlah = tv_jumlah_keranjang.text.toString()
                val harga = tv_harga_keranjang.text.toString().filter { it.isDigit() }.toInt()
                val diskon = edDiskonRp.text.toString().filter { it.isDigit() }.toInt().toString()
                val stotal = tv_sub_total.text.toString().replace(",00", "").filter { it.isDigit() }.toInt()

                val tDiskon = (jumlah.toInt() * harga) - (Integer.parseInt(diskon) * Integer.parseInt(stotal.toString())) / 100

                totalKeranjang(tDiskon)

                totalDiskon(Integer.parseInt(stotal.toString()) - tDiskon)
            }
        }

        toggle.setOnCheckedChangeListener(
            RadioGroup.OnCheckedChangeListener { group, checkedId ->
                val radio: RadioButton = findViewById(checkedId)
                when (radio) {
                    tog_persen -> {
                        edDiskonRp.setText("")

                        edDiskonRp.addTextChangedListener(object : TextWatcher {
                            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                                if (s.toString().length != 0) {
                                    if (s.toString().toInt() >= 100) {
                                        edDiskonRp.error = "Diskon Maksimal 100%"
                                        edDiskonRp.setText("0")
                                    } else {
                                        val jumlah = tv_jumlah_keranjang.text.toString()
                                        val harga = tv_harga_keranjang.text.toString().filter { it.isDigit() }.toInt()
                                        val diskon = edDiskonRp.text.toString().filter { it.isDigit() }.toInt().toString()
                                        val stotal = tv_sub_total.text.toString().replace(",00", "").filter { it.isDigit() }.toInt()

                                        val tDiskon = (jumlah.toInt() * harga) - (Integer.parseInt(diskon) * Integer.parseInt(stotal.toString())) / 100

                                        totalKeranjang(tDiskon)

                                        totalDiskon(Integer.parseInt(stotal.toString()) - tDiskon)
                                    }
                                } else {
                                    edDiskonRp.setText("0")
                                }
                            }

                            override fun afterTextChanged(p0: Editable?) {}
                        })
                    }
                    tog_rp -> {
                        edDiskonRp.setText("")

                        edDiskonRp.addTextChangedListener(object : TextWatcher {
                            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                                if (s.toString().length != 0) {
                                    totalKeranjang(
                                        tv_jumlah_keranjang.text.toString().toInt() * harga.filter { it.isDigit() }.toInt()
                                                - Integer.parseInt(edDiskonRp.text.toString().filter { it.isDigit() }.toInt().toString())
                                    )
                                } else {
                                    edDiskonRp.setText("0")
                                }
                            }

                            override fun afterTextChanged(p0: Editable?) {}
                        })
                    }
                }
            }
        )

        cl_hapus.setOnClickListener { hapusKeranjang() }

        imageView23.setOnClickListener { finish() }

        btn_simpan.setOnClickListener {
            if (tv_jumlah_keranjang.text.toString().toInt() == 0) {
                hapusKeranjang()
            } else {
                Toast.makeText(this, "Keranjang berhasil diubah", Toast.LENGTH_SHORT).show()
                updateData(Nama_Produk, tv_jumlah_keranjang.text.toString(), tv_sub_total.text.toString())
                finish()
            }
        }

    }

    private fun setjumlahProduk(number: Int) {
        tv_jumlah_keranjang.text = "$number"
    }

    private fun subTotal(hasil: Int) {
        val totalString = NumberFormat.getCurrencyInstance(Locale("in", "ID")).format(hasil)
        val total = totalString.substring(0, 2) + " " + totalString.substring(2, totalString.length)

        tv_sub_total.text = total
    }

    private fun totalKeranjang(hasil: Int) {
        val totalString = NumberFormat.getCurrencyInstance(Locale("in", "ID")).format(hasil)
        val total = totalString.substring(0, 2) + " " + totalString.substring(2, totalString.length)

        tv_total_keranjang.text = total
    }

    private fun totalDiskon(hasil: Int) {
        val totalString = NumberFormat.getCurrencyInstance(Locale("in", "ID")).format(hasil)
        val total = totalString.substring(0, 2) + " " + totalString.substring(2, totalString.length)

        tv_diskon_keranjang.text = total
    }

    private fun hapusKeranjang() {
        val Nama_Produk: String = intent.getStringExtra("Nama_Produk").toString()
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Yakin Untuk Menghapus Data?").setCancelable(false).setPositiveButton("Ya") { dialog, id ->
            val reference = FirebaseDatabase.getInstance().reference.child("Keranjang").child(Nama_Produk)
            reference.removeValue()
            Toast.makeText(this, "Data berhasil dihapus", Toast.LENGTH_SHORT).show()
            finish()
        }
            .setNegativeButton("Tidak") { dialog, id -> dialog.dismiss() }
        val alert = builder.create()
        alert.show()
    }

    private fun updateData(Nama_Produk: String, jumlahProduk: String, Total: String) {
        val reference = FirebaseDatabase.getInstance().getReference("Keranjang")

        val keranjang = mapOf<String, String>(
            "jumlah_Produk" to jumlahProduk,
            "total" to Total
        )

        reference.child(Nama_Produk).updateChildren(keranjang)
    }
}