package com.project.sikasir

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.project.sikasir.dialog.DialogMerk
import com.project.sikasir.dialog.DialogTambahKategori
import kotlinx.android.synthetic.main.activity_a4_tambahproduk.*

class a4_tambahproduk : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_a4_tambahproduk)
        val DataQR = intent.getStringExtra("DataQR")

        ivToAddQR.setOnClickListener {
            val intent = Intent(this, ScanBarcodeTambahProduk::class.java)
            startActivity(intent)
        }

        tvA7toA6.setOnClickListener { _ ->
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
    }
}