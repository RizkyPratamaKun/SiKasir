package com.project.sikasir.produk.produk

import android.app.Activity
import android.app.AlertDialog
import android.content.ContentResolver
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.project.sikasir.R
import com.project.sikasir.produk.scanBarcodeTambahProduk
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.produk_kelola.*
import java.util.*


class kelolaProduk : AppCompatActivity() {
    private lateinit var reference: DatabaseReference
    private lateinit var storage: StorageReference
    private var PHOTO_MAX: Int = 1
    private lateinit var photo_location: Uri
    val kode = UUID.randomUUID().toString()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.produk_kelola)

        //Tangkap
        val QR: String = intent.getStringExtra("DataQR").toString()
        val edit: String = intent.getStringExtra("Edit").toString()

        //Set
        onClick()
        setSwitch()

        if (edit == "true") {
            setEdit()
            btnSimpanProduk.setOnClickListener {
                editProduk()
                finish()
            }
        } else {
            cv_hapus.visibility = View.GONE
            btnSimpanProduk.setOnClickListener {
                tambahProduk()
                finish()
            }
        }
    }

    private fun updateData(Nama_Produk: String, Harga_Jual: String, Merek: String, Kategori: String, Harga_Modal: String, Barcode: String, Stok: String) {

        val reference = FirebaseDatabase.getInstance().getReference("Produk")

        val produk = mapOf<String, String>(
            "nama_Produk" to Nama_Produk,
            "harga_Jual" to Harga_Jual,
            "merek" to Merek,
            "kategori" to Kategori,
            "harga_Modal" to Harga_Modal,
            "barcode" to Barcode,
            "stok" to Stok
        )

        reference.child(Nama_Produk).updateChildren(produk)
    }

    private fun deleteProduk() {
        reference = FirebaseDatabase.getInstance().reference.child("Produk").child(edNamaProduk.text.toString())

        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (userSnapshot in snapshot.children) {
                        userSnapshot.ref.removeValue()
                    }
                    Toast.makeText(this@kelolaProduk, "Data berhasil terhapus", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }

    private fun editProduk() {
        val nama: String = intent.getStringExtra("Nama_Produk").toString()

        val namaProduk: String = edNamaProduk.text.toString()
        val hargaJual: String = edHargaJual.text.toString()
        val merek: String = edMerek.text.toString()
        val kategori: String = edKategori.text.toString()
        val hargaModal: String = edHargaModal.text.toString()
        val barcode: String = edBarcode.text.toString()
        val stok: String = edStok.text.toString()

        if (namaProduk.isEmpty()) {
            edNamaProduk.error = "Nama Produk tidak boleh kosong"
        } else {
            if (hargaJual.isEmpty()) {
                edHargaJual.error = "Harga Jual tidak boleh kosong"
            } else {
                if (merek.isEmpty()) {
                    edMerek.error = "Nomor HP tidak boleh kosong"
                } else {
                    if (kategori.isEmpty()) {
                        edKategori.error = "Kategori Harus diisi"
                    } else {
                        if (stok.isEmpty()) {
                            edStok.error = "Stok Harus diisi"
                        } else {
                            reference = FirebaseDatabase.getInstance().reference.child("Produk").child(nama)

                            reference.addValueEventListener(object : ValueEventListener {
                                override fun onDataChange(snapshot: DataSnapshot) {

                                    updateData(namaProduk, hargaJual, merek, kategori, hargaModal, barcode, stok)

                                    if (select_photo.alpha == 0f) {
                                        uploadPhoto()
                                    }
                                }

                                override fun onCancelled(error: DatabaseError) {}
                            })
                            Toast.makeText(this, "$namaProduk Berhasil Dirubah", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

    private fun tambahProduk() {
        val namaProduk: String = edNamaProduk.text.toString()
        val hargaJual: String = edHargaJual.text.toString()
        val merek: String = edMerek.text.toString()
        val kategori: String = edKategori.text.toString()
        val hargaModal: String = edHargaModal.text.toString()
        val barcode: String = edBarcode.text.toString()
        val stok: String = edStok.text.toString()

        if (namaProduk.isEmpty()) {
            edNamaProduk.error = "Nama Produk tidak boleh kosong"
        } else {
            if (hargaJual.isEmpty()) {
                edHargaJual.error = "Harga Jual tidak boleh kosong"
            } else {
                if (merek.isEmpty()) {
                    edMerek.error = "Nomor HP tidak boleh kosong"
                } else {
                    if (kategori.isEmpty()) {
                        edKategori.error = "Kategori Harus diisi"
                    } else {
                        if (stok.isEmpty()) {
                            edStok.error = "Stok Harus diisi"
                        } else {
                            reference = FirebaseDatabase.getInstance().reference.child("Produk").child(kode)
                            reference.addListenerForSingleValueEvent(object : ValueEventListener {
                                override fun onDataChange(dataSnapshot: DataSnapshot) {
                                    if (!dataSnapshot.exists()) {

                                        if (select_photo.alpha == 0f) {
                                            uploadPhoto()
                                        }
                                        dataSnapshot.ref.child("kode_Produk").setValue(kode)
                                        dataSnapshot.ref.child("nama_Produk").setValue(namaProduk)
                                        dataSnapshot.ref.child("harga_Jual").setValue(hargaJual)
                                        dataSnapshot.ref.child("merek").setValue(merek)
                                        dataSnapshot.ref.child("kategori").setValue(kategori)
                                        dataSnapshot.ref.child("stok").setValue(stok)

                                        if (hargaModal.isEmpty()) {
                                            dataSnapshot.ref.child("harga_Modal").setValue("0")
                                        } else {
                                            dataSnapshot.ref.child("harga_Modal").setValue(hargaModal)
                                        }

                                        dataSnapshot.ref.child("barcode").setValue(barcode)
                                    } else {
                                        val alert = AlertDialog.Builder(this@kelolaProduk)
                                        alert.setTitle("Peringatan")
                                        alert.setMessage("Data Sudah Ada!")
                                        alert.setPositiveButton("OK", null)
                                        alert.show()
                                    }
                                }

                                override fun onCancelled(dataSnapshot: DatabaseError) {}
                            })
                            Toast.makeText(this, "$namaProduk Berhasil Ditambahkan", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

    private fun setEdit() {
        cv_hapus.visibility = View.VISIBLE
        switch1.isChecked = true
        edHargaModal.visibility = View.VISIBLE
        tv_opsional1.visibility = View.VISIBLE
        tv_opsional2.visibility = View.VISIBLE
        edBarcode.visibility = View.VISIBLE
        ivToAddQR.visibility = View.VISIBLE

        val nama: String = intent.getStringExtra("Nama_Produk").toString()
        val hJual: String = intent.getStringExtra("Harga_Jual").toString()
        val kategori: String = intent.getStringExtra("Kategori").toString()
        val harga_modal: String = intent.getStringExtra("harga_modal").toString()
        val barcode: String = intent.getStringExtra("Barcode").toString()
        val merek: String = intent.getStringExtra("Merek").toString()
        val foto: String = intent.getStringExtra("Foto").toString()
        val stok: String = intent.getStringExtra("Stok").toString()

        if (harga_modal != "null") {
            edHargaModal.setText(harga_modal)
        }
        if (barcode != "null") {
            edBarcode.setText(barcode)
        }

        Picasso.get().load(foto).centerCrop().fit().into(iv_produk)

        edNamaProduk.setText(nama)
        edHargaJual.setText(hJual)
        edKategori.setText(kategori)
        edMerek.setText(merek)
        edStok.setText(stok)
    }

    private fun setSwitch() {
        if (switch1.isChecked) {
            textView17.setOnClickListener {
                switch1.isChecked = false
                edHargaModal.visibility = View.GONE
                tv_opsional1.visibility = View.GONE
                tv_opsional2.visibility = View.GONE
                edBarcode.visibility = View.GONE
                ivToAddQR.visibility = View.GONE
            }
        } else {
            textView17.setOnClickListener {
                switch1.isChecked = true
                edHargaModal.visibility = View.VISIBLE
                tv_opsional1.visibility = View.VISIBLE
                tv_opsional2.visibility = View.VISIBLE
                edBarcode.visibility = View.VISIBLE
                ivToAddQR.visibility = View.VISIBLE
            }
        }
    }

    private fun onClick() {
        switch1.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                // The switch enabled
                edHargaModal.visibility = View.VISIBLE
                edBarcode.visibility = View.VISIBLE
                tv_opsional1.visibility = View.VISIBLE
                tv_opsional2.visibility = View.VISIBLE
                ivToAddQR.visibility = View.VISIBLE
            } else {
                // The switch disabled
                edHargaModal.visibility = View.GONE
                tv_opsional1.visibility = View.GONE
                tv_opsional2.visibility = View.GONE
                edBarcode.visibility = View.GONE
                ivToAddQR.visibility = View.GONE
            }
        }

        cv_hapus.setOnClickListener {
            val builder = AlertDialog.Builder(this@kelolaProduk)
            builder.setMessage("Yakin Untuk Menghapus Data?")
                .setCancelable(false)
                .setPositiveButton("Ya") { _, _ ->
                    deleteProduk()
                }
                .setNegativeButton("Tidak") { dialog, _ ->
                    dialog.dismiss()
                }
            val alert = builder.create()
            alert.show()
        }

        ivToAddQR.setOnClickListener { startActivity(Intent(this, scanBarcodeTambahProduk::class.java)) }

        tvA7toA6.setOnClickListener { finish() }

/*        edKategori.setOnClickListener { onClickKategori().show(supportFragmentManager, kategori) }*/

        select_photo.setOnClickListener { findPhoto() }
        cv_btnupload.setOnClickListener { findPhoto() }
    }

    private fun findPhoto() {
        val pictureIntent = Intent()
        pictureIntent.type = "image/*"
        pictureIntent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(pictureIntent, PHOTO_MAX)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PHOTO_MAX && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            photo_location = data.data!!
            Picasso.get().load(photo_location).centerCrop().fit().into(iv_produk)
            select_photo.alpha = 0f
        }
    }

    fun getFileExtension(uri: Uri): String? {
        val contentResolver: ContentResolver = contentResolver
        val mimeTypeMap: MimeTypeMap = MimeTypeMap.getSingleton()
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri))
    }

    private fun uploadPhoto() {
        btnSimpanProduk.isEnabled = false
        "Loading ...".also { btnSimpanProduk.text = it }

        storage = FirebaseStorage.getInstance().reference.child("Photoproduk").child(edNamaProduk.text.toString())

        val mStorageReference: StorageReference = storage.child(System.currentTimeMillis().toString() + "." + getFileExtension(photo_location))

        mStorageReference.putFile(photo_location)
            .addOnFailureListener { Toast.makeText(this, "Failure Upload", Toast.LENGTH_SHORT).show() }
            .addOnSuccessListener {
                mStorageReference.downloadUrl.addOnSuccessListener { uri ->
                    reference.ref.child("Foto").setValue(uri.toString())
                }

            }.addOnCompleteListener {}
    }
}