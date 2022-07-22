package com.project.sikasir.produk.merek

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.GridLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.project.sikasir.R
import kotlinx.android.synthetic.main.dialog_utama.*
import kotlinx.android.synthetic.main.dialog_utama.view.*

/**
 * Dibuat oleh RizkyPratama pada 21-Apr-22.
 */
class onClickMerek : DialogFragment() {
    private lateinit var dataMerek: ArrayList<classMerek>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dialog!!.window?.setBackgroundDrawableResource(R.drawable.round_corner)
        val view: View = inflater.inflate(R.layout.dialog_utama, container, false)

        //tangkap
        val kategori: String = activity?.intent?.getStringExtra("tambahkategori").toString()
        val Nama_Kategori: String = activity?.intent?.getStringExtra("Nama_Kategori").toString()

        //Set
        getMerek()

        //OnClick
        view.tv_tambah.setOnClickListener {
            onClickTambahMerek().show(parentFragmentManager, "Dialog 2")
            activity?.intent?.putExtra("tambahkategori", kategori)
        }
        view.tv_batal.setOnClickListener {
            navigateToBackStack()
        }

        view.rv_utama.layoutManager = GridLayoutManager(activity, 1)
        view.rv_utama.setHasFixedSize(true)
        dataMerek = arrayListOf<classMerek>()

        return view
    }

    private fun getMerek() {
        val dbref = FirebaseDatabase.getInstance().getReference("Merek")
        dbref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    dataMerek.clear()
                    for (snapshot in snapshot.children) {
                        val kat = snapshot.getValue(classMerek::class.java)
                        dataMerek.add(kat!!)
                    }
                    rv_utama.adapter = adapterMerek(dataMerek)
                }
            }

            override fun onCancelled(error: DatabaseError) {}
        })
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