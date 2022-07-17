package com.project.sikasir.transaksi.pembayaran

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.project.sikasir.R
import kotlinx.android.synthetic.main.transaksi_pembayaran.*

class pembayaran : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.transaksi_pembayaran)

        cvTunai.setOnClickListener {
            startActivity(Intent(this, pembayaranTunai::class.java))
            finish()
        }
        tvA7toA6.setOnClickListener {
            finish()
        }
    }
}