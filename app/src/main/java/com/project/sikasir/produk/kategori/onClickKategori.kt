package com.project.sikasir.produk.kategori

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
class onClickKategori : DialogFragment() {
    private lateinit var dataKategori: ArrayList<classKategori>
    var merek = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dialog!!.window?.setBackgroundDrawableResource(R.drawable.round_corner)
        val view: View = inflater.inflate(R.layout.dialog_utama, container, false)

        //set
        getKategori()
        view.tv_tambah.text = "Tambah Kategori +"
        val merek: String = activity?.intent?.getStringExtra("tambahmerek").toString()

        //OnClick
        view.tv_tambah.setOnClickListener {
            onClickTambahKategori().show(parentFragmentManager, "Dialog 2")
            activity?.intent?.putExtra("tambahmerek", merek)
        }
        view.tv_batal.setOnClickListener {
            navigateToBackStack()
        }

        //RecyclerView
        view.rv_utama.layoutManager = GridLayoutManager(activity, 1)
        view.rv_utama.setHasFixedSize(true)
        dataKategori = arrayListOf<classKategori>()

        return view
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

    private fun getKategori() {
        var dbref = FirebaseDatabase.getInstance().getReference("Kategori")
        dbref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    dataKategori.clear()
                    for (snapshot in snapshot.children) {
                        val kat = snapshot.getValue(classKategori::class.java)
                        dataKategori.add(kat!!)
                    }
                    rv_utama.adapter = adapterSelectKategori(dataKategori)
                }
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }
}