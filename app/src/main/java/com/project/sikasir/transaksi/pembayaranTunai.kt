package com.project.sikasir.transaksi

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.media.MediaPlayer
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.firebase.database.*
import com.project.sikasir.R
import kotlinx.android.synthetic.main.transaksi_pembayaran_tunai.*
import java.text.DecimalFormat
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

class pembayaranTunai : AppCompatActivity() {
    private var USERNAME_KEY = "username_key"
    private var username_key = ""
    private var username_key_new = ""
    var diterima = ""
    var kembalian = ""

    private lateinit var reference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.transaksi_pembayaran_tunai)

        getUsernameLocal()
        getIdPegawai()
        getIdProduk()
        onClick()
        handleEditText()
        setText()
    }

    private fun setText() {
        val tagihanBiasa = intent.getStringExtra("tagihan").toString()

        tv_tagihan.text = tagihanBiasa

        susuk1.text = tagihanBiasa
        susuk2.text = tagihanBiasa
        susuk3.text = tagihanBiasa
        susuk4.text = tagihanBiasa
    }

    private fun handleEditText() {
        val format: NumberFormat = DecimalFormat("Rp#,###")
        edUangPass.setBackgroundColor(ContextCompat.getColor(baseContext, android.R.color.holo_blue_bright))
        val tagihan: String = intent.getStringExtra("tagihan").toString().replace(",00", "").replace(".", "").replace("Rp ", "")

        edUangPass.setOnClickListener {
            diterima = tagihan
            tembakData()
        }

        edUangDiterima.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.toString().isEmpty()) {
                    edUangPass.setBackgroundColor(ContextCompat.getColor(baseContext, android.R.color.holo_blue_bright))
                    edUangPass.text = "Uang Pas"
                    edUangPass.isEnabled = true
                    edUangPass.isClickable = true
                    edUangPass.setOnClickListener {
                        diterima = tagihan
                        tembakData()
                    }
                } else {
                    if (s.toString().toInt() < tagihan.toInt()) {
                        val kurang = tagihan.toInt() - s.toString().toInt()
                        edUangPass.setBackgroundColor(ContextCompat.getColor(baseContext, android.R.color.darker_gray))
                        edUangPass.text = "Uang Kurang " + format.format(kurang)
                        edUangPass.isEnabled = false
                        edUangPass.isClickable = false
                    } else {
                        val k = s.toString().toInt() - tagihan.toInt()
                        edUangPass.setBackgroundColor(ContextCompat.getColor(baseContext, android.R.color.holo_green_light))
                        edUangPass.text = "Kembalian " + format.format(k)
                        edUangPass.isEnabled = true
                        edUangPass.isClickable = true
                        kembalian = k.toString()

                        edUangPass.setOnClickListener {
                            diterima = edUangDiterima.text.toString()
                            tembakData()
                        }
                    }
                }
            }
        })
    }

    private fun onClick() {
        tvA8toA7.setOnClickListener {
            startActivity(Intent(this, transaksi::class.java))
            finish()
        }

        susuk1.setOnClickListener {
            diterima = susuk1.text.toString()
            tembakData()
        }
        susuk2.setOnClickListener {
            diterima = susuk2.text.toString()
            tembakData()
        }
        susuk3.setOnClickListener {
            diterima = susuk3.text.toString()
            tembakData()
        }
        susuk4.setOnClickListener {
            diterima = susuk4.text.toString()
            tembakData()
        }
    }

    val sdf = SimpleDateFormat("dd-MM-yyyy hh:mm:ss")
    val waktu = sdf.format(Date())

    val ko = SimpleDateFormat("ddMMyyyyhhmmss")
    val kode = ko.format(Date())

    //pegawai
    var namaPegawai = ""
    var jabatanPegawai = ""

    //produk
    var namaProduk = ""
    var harga_jual = ""
    var Jumlah_Produk = ""


    fun tembakData() {
        val mediaPlayer = MediaPlayer.create(baseContext, R.raw.mario)
        mediaPlayer.start()

        val total: String = intent.getStringExtra("tagihan").toString()

        val intent = Intent(this, transaksiBerhasil::class.java)
        intent.putExtra("Diterima", diterima)
        intent.putExtra("Total_Tagihan", total)
        intent.putExtra("Tanggalan", waktu)
        intent.putExtra("Pegawai", namaPegawai)
        intent.putExtra("Jabatan", jabatanPegawai)
        intent.putExtra("Kembalian", kembalian)
        startActivity(intent)
        finish()
    }

    fun getUsernameLocal() {
        val sharedPreference: SharedPreferences = getSharedPreferences(USERNAME_KEY, Context.MODE_PRIVATE)
        username_key_new = sharedPreference.getString(username_key, "").toString()
    }

    fun simpanDetailTransaksi() {
        reference = FirebaseDatabase.getInstance().reference.child("DetailTransaksi").child(kode)
        reference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataSnapshot.ref.child("Detail_Transaksi").setValue(kode)
                dataSnapshot.ref.child("Tanggal").setValue(waktu)
                dataSnapshot.ref.child("Produk").setValue(namaProduk)
                Jumlah_Produk = dataSnapshot.ref.child("Jumlah_Produk").setValue(waktu).toString()
                dataSnapshot.ref.child("Sub_Total").setValue(Jumlah_Produk.toInt() * harga_jual.toInt())
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }

    fun simpanTransaksi() {
        val total: String = intent.getStringExtra("tagihan").toString()
        reference = FirebaseDatabase.getInstance().reference.child("Transaksi").child(kode)

        reference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataSnapshot.ref.child("Kode_Transaksi").setValue(kode)
                dataSnapshot.ref.child("Tanggal").setValue(waktu)
                dataSnapshot.ref.child("Pegawai").setValue(namaPegawai)
                dataSnapshot.ref.child("Total").setValue(total)

                Toast.makeText(this@pembayaranTunai, "Data Berhasil Ditambahkan", Toast.LENGTH_SHORT).show()
                simpanDetailTransaksi()
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }

    private fun getIdPegawai() {
        reference = FirebaseDatabase.getInstance().reference.child("Pegawai").child(username_key_new)
        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                namaPegawai = dataSnapshot.child("Nama_Pegawai").value.toString()
                jabatanPegawai = dataSnapshot.child("Nama_Jabatan").value.toString()
            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        })
    }

    private fun getIdProduk() {
        reference = FirebaseDatabase.getInstance().reference.child("Produk").child(username_key_new)
        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                namaProduk = dataSnapshot.child("Nama_Produk").value.toString()
                harga_jual = dataSnapshot.child("Harga_Jual").value.toString()
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }
}
