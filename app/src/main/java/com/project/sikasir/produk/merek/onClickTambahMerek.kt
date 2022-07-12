package com.project.sikasir.produk.merek

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.google.firebase.database.*
import com.project.sikasir.R
import kotlinx.android.synthetic.main.dialog_tambah.*
import kotlinx.android.synthetic.main.dialog_tambah.view.*

/**
 * Dibuat oleh RizkyPratama pada 21-Apr-22.
 */
class onClickTambahMerek : DialogFragment() {
    private var USERNAME_KEY = "username_key"
    private var username_key = ""
    private var username_key_new = ""
    private lateinit var reference: DatabaseReference

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dialog!!.window?.setBackgroundDrawableResource(R.drawable.round_corner)
        val view: View = inflater.inflate(R.layout.dialog_tambah, container, false)

        //Set
        view.edNama.hint = "Nama Merek"

        //onClick
        view.tv_batal.setOnClickListener {
            navigateToBackStack()
        }
        view.tvSimpan.setOnClickListener {
            simpanMerek()
        }
        // Return the fragment view/layout
        return view
    }

    private fun simpanMerek() {
        if (edNama.text.toString().isEmpty()) {
            edNama.error = "isi nama merek!"
        } else {
            //menyimpan ke firebase
            reference = FirebaseDatabase.getInstance().reference.child("Merek").child(edNama.text.toString())
            reference.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.exists()) {
                        val alert = AlertDialog.Builder(this@onClickTambahMerek.requireActivity())
                        alert.setTitle("Peringatan")
                        alert.setMessage("Data dengan nama " + edNama.text.toString() + " Sudah Ada!")
                        alert.setPositiveButton("Ok", null)
                        alert.show()
                    } else {
                        dataSnapshot.ref.child("Nama_Merek").setValue(edNama.text.toString())
                        //fragment toast
                        Toast.makeText(this@onClickTambahMerek.requireActivity(), "Data Berhasil Ditambahkan", Toast.LENGTH_SHORT).show()
                        navigateToBackStack()
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {}
            })
        }

    }

    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * 0.85).toInt()
        dialog!!.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    private fun navigateToBackStack() {
        requireActivity().supportFragmentManager.beginTransaction().remove(this).commit()
        requireActivity().supportFragmentManager.popBackStack()
    }
}