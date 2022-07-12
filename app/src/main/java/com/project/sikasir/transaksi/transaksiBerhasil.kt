package com.project.sikasir.transaksi

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mazenrashed.printooth.Printooth
import com.mazenrashed.printooth.data.printable.Printable
import com.mazenrashed.printooth.data.printable.RawPrintable
import com.mazenrashed.printooth.data.printable.TextPrintable
import com.mazenrashed.printooth.data.printer.DefaultPrinter
import com.mazenrashed.printooth.ui.ScanningActivity
import com.mazenrashed.printooth.utilities.Printing
import com.mazenrashed.printooth.utilities.PrintingCallback
import com.project.sikasir.R
import kotlinx.android.synthetic.main.transaksi_berhasil.*
import java.text.DecimalFormat
import java.text.NumberFormat

class transaksiBerhasil : AppCompatActivity() {

    private var printing: Printing? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.transaksi_berhasil)

        //tangkap
        val Diterima: String = this.intent.getStringExtra("Diterima").toString()
        val Total_Tagihan: String = this.intent.getStringExtra("Total_Tagihan").toString()
        val currentDate: String = this.intent.getStringExtra("Tanggalan").toString()
        val nama: String = this.intent.getStringExtra("Pegawai").toString()
        val jabatan: String = this.intent.getStringExtra("Jabatan").toString()
        val kembalian: String = this.intent.getStringExtra("Kembalian").toString()

        //Set
        val format: NumberFormat = DecimalFormat("Rp#,###")
        Printooth.init(this)
        if (Printooth.hasPairedPrinter())
            printing = Printooth.printer()
        initListeners()

        tv_tgl.text = currentDate
        tv_jabatan.text = jabatan
        tv_namapegawai.text = nama
        tv_diterima.text = format.format(Diterima.toDouble())
        tv_totaltagihan.text = format.format(Total_Tagihan.toDouble())

        if (Total_Tagihan == Diterima) {
            textView42.visibility = View.GONE
            tv_kembalian.visibility = View.GONE
        } else {
            textView42.visibility = View.VISIBLE
            tv_kembalian.visibility = View.VISIBLE
            tv_kembalian.text = format.format(kembalian.toDouble())
        }

        //onClick
        button2.setOnClickListener {
            val intent = Intent(this, transaksi::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun initListeners() {
        cetak_struk.setOnClickListener {
            if (!Printooth.hasPairedPrinter()) startActivityForResult(
                Intent(this, ScanningActivity::class.java),
                ScanningActivity.SCANNING_FOR_PRINTER
            )
            else printSomePrintable()
        }

        printing?.printingCallback = object : PrintingCallback {
            override fun connectingWithPrinter() {
                Toast.makeText(this@transaksiBerhasil, "Connecting with printer", Toast.LENGTH_SHORT).show()
            }

            override fun printingOrderSentSuccessfully() {
                Toast.makeText(this@transaksiBerhasil, "Order sent to printer", Toast.LENGTH_SHORT).show()
            }

            override fun connectionFailed(error: String) {
                Toast.makeText(this@transaksiBerhasil, "Failed to connect printer", Toast.LENGTH_SHORT).show()
            }

            override fun onError(error: String) {
                Toast.makeText(this@transaksiBerhasil, error, Toast.LENGTH_SHORT).show()
            }

            override fun onMessage(message: String) {
                Toast.makeText(this@transaksiBerhasil, "Message: $message", Toast.LENGTH_SHORT).show()
            }

            override fun disconnected() {
                Toast.makeText(this@transaksiBerhasil, "Disconnected Printer", Toast.LENGTH_SHORT).show()
            }

        }
    }

    private fun printSomePrintable() {
        val printables = getSomePrintables()
        printing?.print(printables)
    }


    private fun getSomePrintables() = ArrayList<Printable>().apply {
        add(RawPrintable.Builder(byteArrayOf(27, 100, 4)).build()) // feed lines example in raw mode

        add(
            TextPrintable.Builder()
                .setText("Aida Putra \nJl. Mayjen. Sutoyo No.4, Kab. Bantul, Daerah Istimewa Yogyakarta 55711\n\n-----------------------------")
                .setLineSpacing(DefaultPrinter.LINE_SPACING_60)
                .setAlignment(DefaultPrinter.ALIGNMENT_CENTER)
                .setEmphasizedMode(DefaultPrinter.EMPHASIZED_MODE_BOLD)
                .setUnderlined(DefaultPrinter.UNDERLINED_MODE_ON)
                .setNewLinesAfter(1)
                .build()
        )

        add(
            TextPrintable.Builder()
                .setText("Pembayaran:")
                .setCharacterCode(DefaultPrinter.CHARCODE_PC1252)
                .setNewLinesAfter(1)
                .build()
        )
        add(
            TextPrintable.Builder()
                .setText(tv_namapegawai.text.toString())
                .setAlignment(DefaultPrinter.ALIGNMENT_RIGHT)
                .setEmphasizedMode(DefaultPrinter.EMPHASIZED_MODE_BOLD)
                .setUnderlined(DefaultPrinter.UNDERLINED_MODE_ON)
                .setNewLinesAfter(1)
                .build()
        )

        add(
            TextPrintable.Builder()
                .setText("Total Tagihan:")
                .setCharacterCode(DefaultPrinter.CHARCODE_PC1252)
                .setNewLinesAfter(1)
                .build()
        )
        add(
            TextPrintable.Builder()
                .setText(tv_totaltagihan.text.toString())
                .setAlignment(DefaultPrinter.ALIGNMENT_RIGHT)
                .setEmphasizedMode(DefaultPrinter.EMPHASIZED_MODE_BOLD)
                .setUnderlined(DefaultPrinter.UNDERLINED_MODE_ON)
                .setNewLinesAfter(1)
                .build()
        )

        add(
            TextPrintable.Builder()
                .setText("Diterima:")
                .setCharacterCode(DefaultPrinter.CHARCODE_PC1252)
                .setNewLinesAfter(1)
                .build()
        )
        add(
            TextPrintable.Builder()
                .setText(tv_diterima.text.toString())
                .setAlignment(DefaultPrinter.ALIGNMENT_RIGHT)
                .setEmphasizedMode(DefaultPrinter.EMPHASIZED_MODE_BOLD)
                .setUnderlined(DefaultPrinter.UNDERLINED_MODE_ON)
                .setNewLinesAfter(1)
                .build()
        )

        add(
            TextPrintable.Builder()
                .setText("Kembalian:")
                .setCharacterCode(DefaultPrinter.CHARCODE_PC1252)
                .setNewLinesAfter(1)
                .build()
        )
        add(
            TextPrintable.Builder()
                .setText(tv_kembalian.text.toString() + "\n\n")
                .setAlignment(DefaultPrinter.ALIGNMENT_RIGHT)
                .setEmphasizedMode(DefaultPrinter.EMPHASIZED_MODE_BOLD)
                .setUnderlined(DefaultPrinter.UNDERLINED_MODE_ON)
                .setNewLinesAfter(1)
                .build()
        )
    }
}