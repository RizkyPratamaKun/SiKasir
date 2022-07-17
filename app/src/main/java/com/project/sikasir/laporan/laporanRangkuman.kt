package com.project.sikasir.laporan

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.firebase.database.*
import com.project.sikasir.R
import com.project.sikasir.transaksi.riwayat.adapterRiwayat
import com.project.sikasir.transaksi.riwayat.classRiwayat
import kotlinx.android.synthetic.main.laporan_rangkuman.*

class laporanRangkuman : AppCompatActivity() {
    private lateinit var dataRiwayat: ArrayList<classRiwayat>
    private lateinit var dbref: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.laporan_rangkuman)

        //Set
        getRiwayatTransaksi()

        //onClick
        tvA3toA3.setOnClickListener {
            startActivity(Intent(this, laporan::class.java))
        }

        editTextDate.setOnClickListener {
            val datePicker = MaterialDatePicker.Builder.dateRangePicker().build()
            datePicker.show(supportFragmentManager, "DatePicker")

            datePicker.addOnPositiveButtonClickListener {
                editTextDate.setText(datePicker.headerText)
                Toast.makeText(this, "${datePicker.headerText}", Toast.LENGTH_LONG).show()
            }

            datePicker.addOnNegativeButtonClickListener {
            }

            datePicker.addOnCancelListener {
            }
        }


    }

    //Get Riwayat Transaksi
    private fun getRiwayatTransaksi() {
        dbref = FirebaseDatabase.getInstance().getReference("Transaksi")
        dbref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (userSnapshot in snapshot.children) {
                        val Class = userSnapshot.getValue(classRiwayat::class.java)
                        dataRiwayat.add(Class!!)
                    }
                    rv_riwayat.adapter = adapterRiwayat(dataRiwayat)
                }
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }
}