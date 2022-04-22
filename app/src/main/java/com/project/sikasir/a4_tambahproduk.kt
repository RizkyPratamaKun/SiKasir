package com.project.sikasir

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_a4tambahproduk.*

class a4_tambahproduk : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_a4tambahproduk)

        tvA4toA3.setOnClickListener { _ ->
            startActivity(Intent(this, a3_kelolaproduk::class.java))
            finish()
            //do what you want after click inside here
        }

        switch1.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                // The switch enabled
                edHargaModal.visibility = View.VISIBLE
                edBarcode.visibility = View.VISIBLE
            } else {
                // The switch disabled
                edHargaModal.visibility = View.GONE
                edBarcode.visibility = View.GONE
            }
        }

    }
}