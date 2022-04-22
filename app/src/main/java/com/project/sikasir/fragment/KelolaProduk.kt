package com.project.sikasir.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.project.sikasir.R
import com.project.sikasir.a4_tambahproduk
import kotlinx.android.synthetic.main.fragment_kelola_produk.*

class KelolaProduk : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        flTambahProduk.setOnClickListener {
            val intent = Intent(activity, a4_tambahproduk::class.java)
            activity?.startActivity(intent)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_kelola_produk, container, false)

    }


}