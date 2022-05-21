package com.project.sikasir.fragment

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
import com.project.sikasir.`class`.Kategori
import com.project.sikasir.adapter.adapterkategori
import com.project.sikasir.dialog.DialogTambahKategori
import kotlinx.android.synthetic.main.fragment_kelola_kategori.view.*

class KelolaKategori : Fragment() {
    //FIREBASE
    private lateinit var DataArrayKategori: ArrayList<Kategori>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_kelola_kategori, container, false)

        view.fabTambahKategori.setOnClickListener { view ->
            DialogTambahKategori().show(childFragmentManager, "Dialog Kategori")
        }

        //RecyclerView
        view.rv_kategori.layoutManager = GridLayoutManager(activity, 1)
        view.rv_kategori.setHasFixedSize(true)
        DataArrayKategori = arrayListOf<Kategori>()

        //Get Kategori
        var dbref =
            FirebaseDatabase
                .getInstance()
                .getReference("Kategori")

        dbref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                println(snapshot)
                if (snapshot.exists()) {
                    for (snapshot in snapshot.children) {
                        val kat = snapshot.getValue(Kategori::class.java)
                        println(kat)
                        DataArrayKategori.add(kat!!)
                    }
                    view.rv_kategori.adapter = adapterkategori(DataArrayKategori)
                }
            }

            override fun onCancelled(error: DatabaseError) {}
        })
        return view
    }
}