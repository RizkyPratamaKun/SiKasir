package com.project.sikasir

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_a5_kelolapegawai.*

class a5_kelolapegawai : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_a5_kelolapegawai)

        tvA5toA2.setOnClickListener { _ ->
            finish()
        }

    }
}