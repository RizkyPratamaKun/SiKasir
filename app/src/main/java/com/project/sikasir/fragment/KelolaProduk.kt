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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_kelola_produk, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fabTambahProduk.setOnClickListener {
            activity?.let {
                val intent = Intent(it, a4_tambahproduk::class.java)
                it.startActivity(intent)
            }
        }
    }
}