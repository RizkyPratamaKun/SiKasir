package com.project.sikasir.produk.kategori

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
import kotlinx.android.synthetic.main.fragment_kategori.*
import kotlinx.android.synthetic.main.fragment_kategori.view.*

class menuKategori : Fragment() {
    //FIREBASE
    private lateinit var dataKategori: ArrayList<classKategori>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_kategori, container, false)

        //Set
        getKategori()

        //OnClick
        view.fabTambahKategori.setOnClickListener { view ->
            val bundle = Bundle()
            bundle.putString("Edit", "false")
            val dialogFragment = onClickTambahKategori()
            dialogFragment.arguments = bundle
            dialogFragment.show(requireActivity().supportFragmentManager, "dialog_event")
        }

        //RecyclerView
        view.rv_kategori.layoutManager = GridLayoutManager(activity, 1)
        view.rv_kategori.setHasFixedSize(true)
        dataKategori = arrayListOf<classKategori>()

        return view
    }

    fun getKategori() {
        var dbref = FirebaseDatabase.getInstance().getReference("Kategori")
        dbref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    dataKategori.clear()
                    for (snapshot in snapshot.children) {
                        val kat = snapshot.getValue(classKategori::class.java)
                        dataKategori.add(kat!!)
                    }
                    rv_kategori.adapter = adapterKategori(dataKategori)
                }
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }
}