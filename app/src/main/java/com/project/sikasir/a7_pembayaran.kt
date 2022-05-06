package com.project.sikasir

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_a7_pembayaran.*

class a7_pembayaran : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_a7_pembayaran)
        /* A click listener for the card view. */
        cvTunai.setOnClickListener { _ ->
            startActivity(Intent(this, a8_pembayarantunai::class.java))
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