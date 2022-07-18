package com.project.sikasir.transaksi.keranjang

import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.RadioButton
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
        val jumlahProduk: String = intent.getStringExtra("Jumlah_Produk").toString()
        val harga: String = intent.getStringExtra("Harga").toString()
        val subTotal: String = intent.getStringExtra("stot").toString()
        val nama_Diskon: String = intent.getStringExtra("Nama_Diskon").toString()
        val diskon: String = intent.getStringExtra("Diskon").toString()

        tv_harga_keranjang.text = harga
        tv_jumlah_keranjang.text = jumlahProduk
        tv_namaitem.text = Nama_Produk
        tv_sub_total.text = subTotal

        if (diskon.isEmpty() || diskon == "0" || diskon == "null") {
            switchPembayaran.isChecked = false
            tv_sub_total_judul.text = "Total"
            groupDiskon.visibility = View.GONE
            tv_diskon_keranjang.visibility = View.GONE
            tv_diskon_judul.visibility = View.GONE
            tv_total_judul.visibility = View.GONE
            tv_total_keranjang.visibility = View.GONE
            tv_total_keranjang.text = "0"
        } else {
            switchPembayaran.isChecked = true
            tv_sub_total_judul.text = "Sub-total"
            groupDiskon.visibility = View.VISIBLE
            tv_diskon_keranjang.visibility = View.VISIBLE
            tv_diskon_judul.visibility = View.VISIBLE
            tv_total_judul.visibility = View.VISIBLE
            tv_total_keranjang.visibility = View.VISIBLE

            edDiskonRp.setText(diskon.replace(",00", "").filter { it.isDigit() })
            tv_diskon_keranjang.text = diskon

            tv_total_keranjang.text =
                totalKeranjang(
                    Integer.parseInt(tv_sub_total.text.toString().replace(",00", "").filter { it.isDigit() })
                            - Integer.parseInt(tv_diskon_keranjang.text.toString().replace(",00", "").filter { it.isDigit() })
                ).toString()


            if (nama_Diskon.isNotEmpty()) {
                edNamaDiskon.setText(nama_Diskon)
            }
        }

        switchPembayaran.setOnCheckedChangeListener { _, isChecked ->
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
        }

        minus.setOnClickListener {
            if (tv_jumlah_keranjang.text.toString().toInt() <= 0) {
                hapusKeranjang()
            } else {
                setjumlahProduk(tv_jumlah_keranjang.text.toString().toInt() - 1)
                subTotal(tv_jumlah_keranjang.text.toString().toInt() * harga.filter { it.isDigit() }.toInt())
            }
        }

        toggle.setOnCheckedChangeListener { _, checkedId ->
            val radio: RadioButton = findViewById(checkedId)
            Toast.makeText(this, "Diskon berdasarkan " + radio.text, Toast.LENGTH_SHORT).show()

            when (radio) {
                tog_persen -> {
                    edDiskonRp.setText("")
                    edDiskonRp.addTextChangedListener(object : TextWatcher {
                        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                            val jumlah = tv_jumlah_keranjang.text.toString().toInt()
                            val harga = tv_harga_keranjang.text.toString().filter { it.isDigit() }.toInt()
                            val diskon = edDiskonRp.text.toString().filter { it.isDigit() }
                            val stotal = tv_sub_total.text.toString().replace(",00", "").filter { it.isDigit() }.toInt()

                            if (diskon.isNotEmpty()) {

                                val diskonPersen = (jumlah * harga) - (Integer.parseInt(diskon) * Integer.parseInt(stotal.toString())) / 100 //(jumlah * harga) - (diskon * sub-total)/100

                                totalKeranjang(diskonPersen)
                                totalDiskon(Integer.parseInt(stotal.toString()) - diskonPersen)
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
                            val jumlah = tv_jumlah_keranjang.text.toString().toInt()
                            val harga = tv_harga_keranjang.text.toString().filter { it.isDigit() }.toInt()
                            val diskon = edDiskonRp.text.toString().filter { it.isDigit() }
                            val stotal = tv_sub_total.text.toString().replace(",00", "").filter { it.isDigit() }.toInt()

                            if (diskon.isNotEmpty()) {
                                val diskonRupiah = (jumlah * harga) - Integer.parseInt(diskon) //(jumlah * harga) - diskon

                                totalKeranjang(diskonRupiah)
                                totalDiskon(Integer.parseInt(stotal.toString()) - diskonRupiah)
                            } else {
                                edDiskonRp.setText("0")
                            }
                        }

                        override fun afterTextChanged(p0: Editable?) {}
                    })
                }
            }
        }

        cl_hapus.setOnClickListener { hapusKeranjang() }

        imageView23.setOnClickListener { finish() }

        btn_simpan.setOnClickListener {
            if (tv_jumlah_keranjang.text.toString().toInt() == 0) {
                hapusKeranjang()
            } else {
                Toast.makeText(this, "Keranjang berhasil diubah", Toast.LENGTH_SHORT).show()
                updateData(
                    Nama_Produk,
                    harga,
                    tv_jumlah_keranjang.text.toString(),
                    edNamaDiskon.text.toString(),
                    tv_diskon_keranjang.text.toString(),
                    tv_total_keranjang.text.toString()
                )
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

    private fun updateData(Nama_Produk: String, Harga: String, jumlah_Produk: String, nama_Diskon: String, diskon: String, total: String) {

        val reference = FirebaseDatabase.getInstance().getReference("Keranjang")

        val keranjang = mapOf<String, String>(
            "nama_Produk" to Nama_Produk,
            "harga" to Harga,
            "jumlah_Produk" to jumlah_Produk,
            "nama_Diskon" to nama_Diskon,
            "diskon" to diskon,
            "total" to total
        )

        reference.child(Nama_Produk).updateChildren(keranjang)
    }

    private fun Transajs(Nama_Produk: String, Harga: String, jumlah_Produk: String, nama_Diskon: String, diskon: String, total: String) {

        val reference = FirebaseDatabase.getInstance().getReference("Keranjang")

        val keranjang = mapOf<String, String>(
            "nama_Produk" to Nama_Produk,
            "harga" to Harga,
            "jumlah_Produk" to jumlah_Produk,
            "nama_Diskon" to nama_Diskon,
            "diskon" to diskon,
            "total" to total
        )

        reference.child(Nama_Produk).updateChildren(keranjang)
    }
}