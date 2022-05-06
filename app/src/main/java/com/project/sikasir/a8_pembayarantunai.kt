package com.project.sikasir

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_a8_pembayarantunai.*
import java.text.NumberFormat

class a8_pembayarantunai : AppCompatActivity(), TextWatcher {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_a8_pembayarantunai)

        tvA8toA7.setOnClickListener { _ ->
            startActivity(Intent(this, a7_pembayaran::class.java))
            finish()
        }
    }

    private var current: String = ""

    /**
     * A function that is called to notify you that, within s, the count characters beginning at start
     * are about to be replaced by new text with length after. It is an override function of the
     * TextWatcher interface.
     *
     * @param p0 The text that is about to change
     * @param p1 The start of the text that is about to be changed
     * @param p2 The number of characters that were replaced.
     * @param p3 Int - The number of characters that will be replaced
     */
    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        TODO("Not yet implemented")
    }

    /**
     * A function that is called to notify you that, within s, the count characters beginning at start
     * are about to be replaced by new text with length after. It is an override function of the
     * TextWatcher interface.
     *
     * @param p0 The text that is about to change
     * @param p1 The start of the text that is about to be changed
     * @param p2 The number of characters that were replaced.
     * @param p3 Int - The number of characters that will be replaced
     */
    override fun onTextChanged(
        s: CharSequence,
        start: Int,
        before: Int,
        count: Int
    ) {
        if (s.toString() != current) {
            edUangDiterima.removeTextChangedListener(this)

            val cleanString: String = s.replace("""[$,.]""".toRegex(), "")

            val parsed = cleanString.toDouble()
            val formatted = NumberFormat.getCurrencyInstance().format((parsed / 100))

            current = formatted
            edUangDiterima.setText(formatted)
            edUangDiterima.setSelection(formatted.length)
            edUangDiterima.addTextChangedListener(this)
        }
    }

    /**
     * A function that is called after the text has changed.
     *
     * @param p0 Editable? - This is the text that has been changed.
     */
    override fun afterTextChanged(p0: Editable?) {
        TODO("Not yet implemented")
    }
}