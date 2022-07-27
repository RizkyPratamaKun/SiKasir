package com.project.sikasir.produk.pembelian

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.project.sikasir.R
import kotlinx.android.synthetic.main.kelolapembelian.*
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

class kelolaPembelian : AppCompatActivity() {
    val Rp = NumberFormat.getCurrencyInstance(Locale("in", "ID"))
    val waktu = SimpleDateFormat("dd-MM-yyyy,HH:mm:ss").format(Date())
    private var USERNAME_KEY = "username_key"
    private var username_key = ""
    private var username_key_new = ""
    var namaPegawai = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.kelolapembelian)
        getUsernameLocal()

        val namaProduk: String = intent.getStringExtra("Nama_Produk").toString()
        val HModal: String = intent.getStringExtra("harga_modal").toString()
        val stok: String = intent.getStringExtra("stok").toString()

        minus.isEnabled = false
        kp_tv_tgl.text = waktu

        if (stok.isNotEmpty() || stok == "null") {
            tv_jml_pembelian.text = stok
        }
        if (namaProduk.isNotEmpty() || namaProduk == "null") {
            kp_tv_nama.text = namaProduk
        }

        if (HModal.isEmpty() || HModal == "null" || HModal == "Rp ") {
            hilangkanTVmodal()
        } else {
            ed_harga_kosong.visibility = View.GONE
            kp_tv_harga.isEnabled = true
            btn_ubah_harga.visibility = View.VISIBLE
            kp_tv_harga.visibility = View.VISIBLE
            kp_tv_harga.text = HModal
        }

        btn_ubah_harga.setOnClickListener {
            hilangkanTVmodal()
        }

        plus.setOnClickListener {
            minus.isEnabled = true
            jumlah(tv_jml_pembelian.text.toString().toInt() + 1)
        }
        minus.setOnClickListener {
            if (tv_jml_pembelian.text.toString().toInt() <= stok.toInt()) {
                minus.isEnabled = false
                Toast.makeText(this, "Jumlah Produk tidak bisa kurang dari stok yang ada!", Toast.LENGTH_SHORT).show()
            } else {
                jumlah(tv_jml_pembelian.text.toString().toInt() - 1)
            }
        }

        tv_jml_pembelian.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (kp_tv_harga.isEnabled) {
                    totalPembelian(kp_tv_harga.text.toString().replace(",00", "").filter { it.isDigit() }.toInt() * tv_jml_pembelian.text.toString().toInt())
                } else {
                    if (ed_harga_kosong.text.isNotEmpty()) {
                        totalPembelian(ed_harga_kosong.text.toString().toInt() * tv_jml_pembelian.text.toString().toInt())
                    }
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        btn_simpan_pembelian.setOnClickListener {
            if (kp_tv_harga.isEnabled) {
                simpanPembelian()
            } else {
                if (ed_harga_kosong.text.isNotEmpty()) {
                    simpanPembelian()
                } else {
                    ed_harga_kosong.error = "isi harga terlebih dahulu"
                }
            }
        }
    }

    private fun hilangkanTVmodal() {
        ed_harga_kosong.visibility = View.VISIBLE
        kp_tv_harga.visibility = View.GONE
        kp_tv_harga.isEnabled = false
        btn_ubah_harga.visibility = View.GONE
    }

    private fun totalPembelian(hasil: Int) {
        val totalString = Rp.format(hasil)
        val total = totalString.substring(0, 2) + " " + totalString.substring(2, totalString.length)

        kp_total.text = total
    }

    private fun jumlah(hasil: Int) {
        tv_jml_pembelian.text = hasil.toString()
    }

    private fun simpanPembelian() {
        val stok: String = intent.getStringExtra("stok").toString()
        val namaProduk: String = kp_tv_nama.text.toString()
        val tanggal: String = kp_tv_tgl.text.toString()
        val keterangan: String = kp_edt_keterangan.text.toString()

        val EdHarga: String = ed_harga_kosong.text.toString()
        val TvHarga: String = kp_tv_harga.text.toString().replace(",00", "").filter { it.isDigit() }

        val jumlah: String = (tv_jml_pembelian.text.toString().toInt() - stok.toInt()).toString()
        val total: String = kp_total.text.toString()

        val reference = FirebaseDatabase.getInstance().reference.child("Pembelian").child(waktu)
        reference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (!dataSnapshot.exists()) {
                    dataSnapshot.ref.child("namaProduk").setValue(namaProduk)
                    dataSnapshot.ref.child("tanggal").setValue(tanggal)
                    dataSnapshot.ref.child("tglLong").setValue(Calendar.getInstance().time.time)
                    dataSnapshot.ref.child("jumlah_Produk").setValue(jumlah)
                    dataSnapshot.ref.child("totalPembelian").setValue(total)
                    dataSnapshot.ref.child("keterangan").setValue(keterangan)
                    dataSnapshot.ref.child("pegawai").setValue(namaPegawai)

                    if (kp_tv_harga.isEnabled) {
                        dataSnapshot.ref.child("harga_Modal").setValue(Rp.format(TvHarga.toInt()))
                        updateData(
                            namaProduk,
                            Rp.format(TvHarga.toInt()),
                            tv_jml_pembelian.text.toString()
                        )
                    } else {
                        dataSnapshot.ref.child("harga_Modal").setValue(Rp.format(EdHarga.toInt()))
                        updateData(
                            namaProduk,
                            Rp.format(EdHarga.toInt()),
                            tv_jml_pembelian.text.toString()
                        )
                    }
                }
            }

            override fun onCancelled(dataSnapshot: DatabaseError) {}
        })
        finish()
        Toast.makeText(this, "Data pembelian berhasil ditambahkan", Toast.LENGTH_SHORT).show()
    }

    private fun updateData(Nama_Produk: String, Harga_Modal: String, Stok: String) {

        val reference = FirebaseDatabase.getInstance().getReference("Produk")

        val produk = mapOf<String, String>(
            "nama_Produk" to Nama_Produk,
            "harga_Modal" to Harga_Modal,
            "stok" to Stok
        )

        reference.child(Nama_Produk).updateChildren(produk)
    }

    private fun getUsernameLocal() {
        val sharedPreference: SharedPreferences = getSharedPreferences(USERNAME_KEY, Context.MODE_PRIVATE)
        username_key_new = sharedPreference.getString(username_key, "").toString()

        val reference = FirebaseDatabase.getInstance().reference.child("Pegawai").child(username_key_new)
        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                namaPegawai = dataSnapshot.child("Nama_Pegawai").value.toString()
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }
}