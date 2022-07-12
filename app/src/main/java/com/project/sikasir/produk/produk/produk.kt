package com.project.sikasir.produk.produk

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.project.sikasir.R
import kotlinx.android.synthetic.main.fragment_produk.*
import kotlinx.android.synthetic.main.fragment_produk.view.*

class produk : Fragment() {
    private lateinit var listProduk: ArrayList<classProduk>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_produk, container, false)

        view.fabTambahProduk.setOnClickListener {
            startActivity(Intent(activity, kelolaProduk::class.java))
        }
        view.rv_pegawai.layoutManager = GridLayoutManager(activity, 1)
        view.rv_pegawai.setHasFixedSize(true)
        getProduk()
        return view
    }

    fun getProduk() {
        listProduk = arrayListOf()
        val dbref = FirebaseDatabase.getInstance().getReference("Produk")
        dbref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (snap in snapshot.children) {
                        val produk = snap.getValue(classProduk::class.java)
                        listProduk.add(produk!!)
                    }
                    rv_pegawai.adapter = adapterProduk(listProduk)
                }
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }
}
