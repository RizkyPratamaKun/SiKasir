package com.project.sikasir.transaksi

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import com.project.sikasir.R
import kotlinx.android.synthetic.main.transaksi_pembayaran.*

class pembayaran : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.transaksi_pembayaran)

        cvTunai.setOnClickListener { _ ->
            startActivity(Intent(this, pembayaranTunai::class.java))
            finish()
        }
        tvA7toA6.setOnClickListener { _ ->
            finish()
        }

        /* A switch listener. */
        switchPembayaran.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                // The switch enabled
                groupDiskon.visibility = View.VISIBLE
            } else {
                // The switch disabled
                groupDiskon.visibility = View.GONE
            }
        }

        /* A listener for the radio group. */
        toggle.setOnCheckedChangeListener(
            RadioGroup.OnCheckedChangeListener { group, checkedId ->
                val radio: RadioButton = findViewById(checkedId)
                edDiskon.hint = radio.text
            }
        )


    }
}