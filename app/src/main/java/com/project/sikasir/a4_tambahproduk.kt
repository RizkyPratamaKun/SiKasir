package com.project.sikasir

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*
import com.project.sikasir.barcode.ScanBarcodeTambahProduk
import com.project.sikasir.dialog.DialogMerk
import com.project.sikasir.dialog.DialogTambahKategori
import kotlinx.android.synthetic.main.activity_a4_tambahproduk.*

class a4_tambahproduk : AppCompatActivity() {
    //Firebase RealtimeDatabase
    private lateinit var reference: DatabaseReference // penyimpanan data secara lokal storage
    private var USERNAME_KEY = "username_key"
    private var username_key = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_a4_tambahproduk)

        ivToAddQR.setOnClickListener {
            val intent = Intent(this, ScanBarcodeTambahProduk::class.java)
            startActivity(intent)
        }
        tvA7toA6.setOnClickListener { _ ->
            val intent = Intent(this, a3_kelolaproduk::class.java)
            startActivity(intent)
            finish()
        }
        edMerek.setOnClickListener {
            DialogMerk().show(supportFragmentManager, "Dialog 1")
        }
        edKategori.setOnClickListener {
            DialogTambahKategori().show(supportFragmentManager, "Dialog 2")
        }

        switch1.setOnCheckedChangeListener { buttonView, isChecked ->
            /* A switch listener. */
            if (isChecked) {
                // The switch enabled
                edHargaModal.visibility = View.VISIBLE
                edBarcode.visibility = View.VISIBLE
                ivToAddQR.visibility = View.VISIBLE
            } else {
                // The switch disabled
                edHargaModal.visibility = View.GONE
                edBarcode.visibility = View.GONE
                ivToAddQR.visibility = View.GONE
            }
        }


        btnSimpanProduk.setOnClickListener(View.OnClickListener {
            val NamaProduk: String = edNamaProduk.text.toString()
            val Hjual: String = edHargaJual.text.toString()
            val Merk: String = edMerek.text.toString()
            val Kategori: String = edKategori.text.toString()
            val Hmodal: String = edHargaModal.text.toString()
            val Barcode: String = edBarcode.text.toString()

            //menyimpan di lokal storage/smartphone
            val sharedPreferences: SharedPreferences =
                getSharedPreferences(USERNAME_KEY, Context.MODE_PRIVATE)
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.putString(username_key, NamaProduk).apply()

            if (NamaProduk.isEmpty()) {
                /* A function to show an error message if the user doesn't fill the form. */
                edNamaProduk.error = "Nama Produk tidak boleh kosong"
            } else {
                if (Hjual.isEmpty()) {
                    edHargaJual.error = "Harga Jual tidak boleh kosong"
                } else {
                    if (Merk.isEmpty()) {
                        edMerek.error = "Nomor HP tidak boleh kosong"
                    } else {
                        if (Kategori.isEmpty()) {
                            edKategori.error = "Kategori Harus diisi"
                        } else {
                            //menyimpan ke firebase database
                            reference = FirebaseDatabase
                                .getInstance()
                                .reference
                                .child("Produk")
                                .child(NamaProduk)

                            /* A function to check if the data is already exist or not. */
                            reference.addListenerForSingleValueEvent(object :
                                ValueEventListener {
                                override fun onDataChange(dataSnapshot: DataSnapshot) {
                                    if (!dataSnapshot.exists()) {
                                        dataSnapshot.ref.child("Nama_Produk").setValue(NamaProduk)
                                        dataSnapshot.ref.child("Harga_Jual").setValue(Hjual)
                                        dataSnapshot.ref.child("Merek").setValue(Merk)
                                        dataSnapshot.ref.child("Kategori").setValue(Kategori)
                                        dataSnapshot.ref.child("Harga_Modal").setValue(Hmodal)
                                        dataSnapshot.ref.child("Barcode").setValue(Barcode)
                                    } else {
                                        val alert = AlertDialog.Builder(this@a4_tambahproduk)
                                        alert.setTitle("Peringatan")
                                        alert.setMessage("Data Sudah Ada!")
                                        alert.setPositiveButton("OK", null)
                                        alert.show()
                                    }
                                }

                                override fun onCancelled(dataSnapshot: DatabaseError) {

                                }
                            })
                            Toast.makeText(
                                this,
                                NamaProduk + " Berhasil Ditambahkan",
                                Toast.LENGTH_SHORT
                            ).show()
                            // berpindah activity
                            val intent = Intent(this, a5_kelolapegawai::class.java)
                            startActivity(intent)
                        }
                    }
                }
            }
        })
    }
}