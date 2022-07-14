package com.project.sikasir.transaksi

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.media.MediaPlayer
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
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

    val keranjangList = ArrayList<classKeranjang>()

    private lateinit var reference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.transaksi_pembayaran_tunai)

        tv_judul.setOnClickListener {
            println(keranjangList)
        }

        getUsernameLocal()
        getIdPegawai()
        onClick()
        handleEditText()
        getKeranjang()
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

    private fun simpanDetailTransaksi() {
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

    private fun simpanTransaksi() {
        reference = FirebaseDatabase.getInstance().reference.child("Transaksi").child(kode)
        val total: String = intent.getStringExtra("tagihan").toString()

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

    private fun getKeranjang() {
        rv_pembayaran.layoutManager = GridLayoutManager(this, 1)
        rv_pembayaran.setHasFixedSize(true)

        val refKeranjang = FirebaseDatabase.getInstance().getReference("Keranjang")

        refKeranjang.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {

                    rv_pembayaran.visibility = View.VISIBLE
                    cl_keranjang_kosong.visibility = View.GONE
                    keranjangList.clear()

                    var i = 0
                    var sum = 0

                    for (snapKeranjang in snapshot.children) {
                        //DataKeranjang
                        val keranjang = snapKeranjang.getValue(classKeranjang::class.java)
                        keranjangList.add(keranjang!!)
                        //Total Harga di Keranjang
                        sum += Integer.parseInt(snapKeranjang.child("total").getValue(String::class.java)!!.replace(",00", "").replace(".", "").replace("Rp ", ""))
                        //total barang di keranjang
                        i += 1
                    }
                    val totalString = NumberFormat.getCurrencyInstance(Locale("in", "ID")).format(sum)
                    val total = totalString.substring(0, 2) + " " + totalString.substring(2, totalString.length)

                    tv_tagihan.text = total
                    rv_pembayaran.adapter = adapterKeranjang(keranjangList)
                } else {
                    startActivity(Intent(this@pembayaranTunai, transaksi::class.java))
                    finish()
                    cl_keranjang_kosong.visibility = View.VISIBLE
                    rv_pembayaran.visibility = View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }
}
