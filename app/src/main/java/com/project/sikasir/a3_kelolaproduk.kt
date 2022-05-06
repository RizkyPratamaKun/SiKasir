package com.project.sikasir

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import com.project.sikasir.fragment.adapter.ViewPagerAdapter
import kotlinx.android.synthetic.main.activity_a3_kelolaproduk.*

class a3_kelolaproduk : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_a3_kelolaproduk)

        /* The above code is creating an instance of ViewPagerAdapter and assigning it to the
        viewPagerProduk. */
        viewPagerProduk.adapter = ViewPagerAdapter(supportFragmentManager, lifecycle)

        /* A function that is used to set the text of the tab layout. */
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

