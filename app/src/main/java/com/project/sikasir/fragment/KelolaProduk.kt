package com.project.sikasir.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.project.sikasir.R
import com.project.sikasir.a4_tambahproduk
import com.project.sikasir.adapter.adapterproduk
import com.project.sikasir.listview.Produk
import kotlinx.android.synthetic.main.fragment_kelola_produk.view.*

class KelolaProduk : Fragment() {
    //FIREBASE
    private lateinit var dbref: DatabaseReference
    private lateinit var userRecyclerview: RecyclerView
    private lateinit var userArrayList: ArrayList<Produk>

    private fun getUserData() {
        dbref = FirebaseDatabase.getInstance().getReference("Produk")
        dbref.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (userSnapshot in snapshot.children) {
                        val produk = userSnapshot.getValue(Produk::class.java)
                        userArrayList.add(produk!!)
                    }
                    userRecyclerview.adapter = adapterproduk(userArrayList)
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_kelola_produk, container, false)

        view.fabTambahProduk.setOnClickListener { view ->
            val intent = Intent(activity, a4_tambahproduk::class.java)
            startActivity(intent)
        }

        // Return the fragment view/layout
        return view
    }
}
