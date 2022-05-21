package com.project.sikasir

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_a15_about.*


class a15_about : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_a15_about)

        tv_backabout.setOnClickListener {
            finish()
        }

        tv_contactme.setOnClickListener {
            try {
                val telegram = Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/Ronysayur"))
                telegram.setPackage("org.telegram.messenger")
                startActivity(telegram)
            } catch (e: Exception) {
                Toast.makeText(this, "Telegram app is not installed", Toast.LENGTH_LONG).show()
            }
        }
    }
}