package com.project.sikasir

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_a2_menu.*

class a2_menu : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_a2_menu)

        cvProduk.setOnClickListener { _ ->
            startActivity(Intent(this, a3_kelolaproduk::class.java))
            finish()
            //do what you want after click inside here
        }
        cvPegawai.setOnClickListener { _ ->
            startActivity(Intent(this, a3_kelolaproduk::class.java))
            finish()
            //do what you want after click inside here
        }
        cvPanduanPengguna.setOnClickListener { _ ->
            startActivity(Intent(this, a3_kelolaproduk::class.java))
            finish()
            //do what you want after click inside here
        }
    }
}