package com.project.sikasir.produk.viewpager

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import com.project.sikasir.R
import kotlinx.android.synthetic.main.produk_dashboard.*

class viewPagerMenu : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.produk_dashboard)

        viewPagerProduk.adapter = viewpageradapter(supportFragmentManager, lifecycle)

        TabLayoutMediator(
            tabLayoutproduk, viewPagerProduk,
            { tab, position ->
                when (position) {
                    0 -> {
                        tab.text = "Kelola Produk"
                    }
                    1 -> {
                        tab.text = "Kelola Kategori"
                    }
                }
            }).attach()

        tvA3toA2.setOnClickListener { _ ->
            finish()
        }
    }
}

